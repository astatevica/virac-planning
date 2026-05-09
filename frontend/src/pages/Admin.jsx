import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import { useAuth } from "../auth/AuthContext";
import AdminService from "../services/AdminService";
import DepartmentHeadService from "../services/DepartmentHeadService";
import { exportPlanFile } from "../utils/planExport";
import "./Admin.css";

const API = "/admin";

const toDateInputValue = (value) => {
  if (!value) return "";
  return value.slice(0, 10);
};

const SchedulerUpdateSection = () => {
  const [schedulerRow, setSchedulerRow] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isSaving, setIsSaving] = useState(false);
  const [message, setMessage] = useState("");

  const currentYearNumber = new Date().getFullYear();

  useEffect(() => {
    const loadScheduler = async () => {
      try {
        const yearsRes = await api.get("/year/all");
        const years = yearsRes.data || [];

        const currentYear = years.find(
          (y) => Number(y.yearNumber) === currentYearNumber
        );

        if (!currentYear) {
          setMessage("Year not found");
          setSchedulerRow(null);
          return;
        }

        const res = await AdminService.getSchedulerByYear(currentYear.idYear);
        const data = res.data;

        setSchedulerRow({
          idYear: currentYear.idYear,
          plannedFreezeDate: toDateInputValue(data?.plannedFreezeDate),
          doneFreezeDate: toDateInputValue(data?.doneFreezeDate),
        });
      } catch {
        setMessage("Load failed");
      } finally {
        setIsLoading(false);
      }
    };

    loadScheduler();
  }, [currentYearNumber]);

  const handleChange = (field, value) => {
    setSchedulerRow((prev) => ({ ...prev, [field]: value }));
  };

  const handleSave = async (row) => {
    if (!row.plannedFreezeDate || !row.doneFreezeDate) {
      setMessage("Both freeze dates are required.");
      return;
    }

    setIsSaving(true);

    try {
      await AdminService.updateScheduler({
        idYear: Number(row.idYear),
        plannedFreezeDate: row.plannedFreezeDate,
        doneFreezeDate: row.doneFreezeDate,
      });
      setMessage("Scheduler updated successfully.");
    } catch {
      setMessage("Failed to update scheduler.");
    } finally {
      setIsSaving(false);
    }
  };

  return (
    <section className="admin-scheduler-section">
      <h2 className="admin-scheduler-title">Scheduler for current year: {currentYearNumber}</h2>
      <p className="admin-scheduler-warning">
        This is the scheduler where the administrator can change the plan
        submission deadline dates for curren year.
      </p>

      {message && (
        <p className={message.toLowerCase().includes("success") ? "admin-message-success" : "admin-message-error"}>
          {message}
        </p>
      )}

      {isLoading ? (
        <p>Loading scheduler...</p>
      ) : !schedulerRow ? (
        <p>No scheduler row available for {currentYearNumber}.</p>
      ) : (
        <table className="admin-table">
          <thead>
            <tr>
              <th>Year</th>
              <th>Planned Freeze Date</th>
              <th>Done Freeze Date</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <tr key={schedulerRow.idYear}>
              <td>{currentYearNumber}</td>
              <td>
                <input
                  type="date"
                  value={schedulerRow.plannedFreezeDate}
                  onChange={(event) =>
                    handleChange("plannedFreezeDate", event.target.value)
                  }
                />
              </td>
              <td>
                <input
                  type="date"
                  value={schedulerRow.doneFreezeDate}
                  onChange={(event) =>
                    handleChange("doneFreezeDate", event.target.value)
                  }
                />
              </td>
              <td>
                <button
                  type="button"
                  onClick={() => handleSave(schedulerRow)}
                  disabled={isSaving}
                >
                  {isSaving ? "Saving..." : "Save"}
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      )}
    </section>
  );
};

const DepartmentHeadDashboard = () => {
  const navigate = useNavigate();
  const [departmentCredentials, setDepartmentCredentials] = useState(null);
  const [employees, setEmployees] = useState([]);
  const [departmentPlans, setDepartmentPlans] = useState([]);
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [employeePlans, setEmployeePlans] = useState([]);
  const [years, setYears] = useState([]);
  const [selectedYear, setSelectedYear] = useState("");
  const [loadingEmployees, setLoadingEmployees] = useState(true);
  const [loadingPlans, setLoadingPlans] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadDashboard = async () => {
      try {
        const [credentialsRes, employeesRes, plansRes, yearsRes] = await Promise.all([
          DepartmentHeadService.getDepartmentCredentials(),
          DepartmentHeadService.getEmployeesByDepartment(),
          DepartmentHeadService.getDepartmentPlans(),
          api.get("/year/all").catch(() => ({ data: [] })),
        ]);

        setDepartmentCredentials(credentialsRes.data);
        setEmployees(employeesRes.data);
        setDepartmentPlans(plansRes.data);
        setYears(yearsRes.data);
        setError("");
      } catch {
        setError("Failed to load department dashboard data.");
      } finally {
        setLoadingEmployees(false);
      }
    };

    loadDashboard();
  }, []);

  const loadEmployeePlans = async (employee) => {
    setSelectedEmployee(employee);
    setSelectedYear("");
    setLoadingPlans(true);

    try {
      const plansRes = await DepartmentHeadService.getEmployeePlans(employee.idEmployee);
      setEmployeePlans(plansRes.data);
      setError("");
    } catch {
      setEmployeePlans([]);
      setError("Failed to load employee plans.");
    } finally {
      setLoadingPlans(false);
    }
  };

  const yearMap = Object.fromEntries(
    years.map((year) => [String(year.idYear), year.yearNumber])
  );

  const departmentPlanCountByEmployee = departmentPlans.reduce((acc, plan) => {
    const key = String(plan.idEmployee);
    acc[key] = (acc[key] || 0) + 1;
    return acc;
  }, {});

  const visiblePlans = selectedYear
    ? employeePlans.filter((plan) => String(plan.idYear) === selectedYear)
    : employeePlans;

  const handleExportDocx = (idPlan) => {
    exportPlanFile(idPlan, "docx");
  };

  return (
    <div className="admin-page-container">
      <h2>Department Dashboard</h2>

      {error && <p className="admin-message-error">{error}</p>}

      {departmentCredentials && (
        <div className="admin-info-card">
          <div>
            <strong>Department:</strong> {departmentCredentials.name}
          </div>
          <div>
            <strong>Head:</strong> {departmentCredentials.headName} {departmentCredentials.headSurname}
          </div>
        </div>
      )}

      <div className="admin-layout">
        <section className="admin-sidebar-section">
          <h3>Employees</h3>
          {loadingEmployees ? (
            <p>Loading employees...</p>
          ) : employees.length === 0 ? (
            <p>No employees found in this department.</p>
          ) : (
            <div className="admin-employee-grid">
              {employees.map((employee) => (
                <button
                  key={employee.id}
                  type="button"
                  onClick={() => loadEmployeePlans(employee)}
                  className={`admin-employee-button ${
                    selectedEmployee?.id === employee.id
                      ? "admin-employee-button-selected"
                      : ""
                  }`}
                >
                  <strong>
                    {employee.name} {employee.surname}
                  </strong>
                  <div>{employee.position || "No position"}</div>
                  <div>
                    Plans in department archive:{" "}
                    {departmentPlanCountByEmployee[String(employee.idEmployee)] || 0}
                  </div>
                </button>
              ))}
            </div>
          )}
        </section>

        <section className="admin-main-section">
          <h3>
            {selectedEmployee
              ? `${selectedEmployee.name} ${selectedEmployee.surname} plans`
              : "Select an employee"}
          </h3>

          {selectedEmployee && (
            <div className="admin-filter-row">
              <label htmlFor="department-plan-year">Year:</label>{" "}
              <select
                id="department-plan-year"
                value={selectedYear}
                onChange={(event) => setSelectedYear(event.target.value)}
              >
                <option value="">All</option>
                {years.map((year) => (
                  <option key={year.idYear} value={year.idYear}>
                    {year.yearNumber}
                  </option>
                ))}
              </select>
            </div>
          )}

          {!selectedEmployee ? (
            <p>Choose an employee to see all of their plans.</p>
          ) : loadingPlans ? (
            <p>Loading plans...</p>
          ) : visiblePlans.length === 0 ? (
            <p>No plans found for the selected employee.</p>
          ) : (
            <table className="admin-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Year</th>
                  <th>Projects</th>
                  <th>Articles</th>
                  <th>Courses</th>
                  <th>Student Work</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {visiblePlans.map((plan) => (
                  <tr key={plan.idPlan}>
                    <td>{plan.idPlan}</td>
                    <td>{yearMap[String(plan.idYear)] || plan.idYear}</td>
                    <td>{plan.numOfProjects}</td>
                    <td>{plan.numOfArticles}</td>
                    <td>{plan.numOfCourses}</td>
                    <td>{plan.numOfStudWork}</td>
                    <td>
                      <button
                        type="button"
                        onClick={() =>
                          navigate(`/admin/plans/${plan.idPlan}`, {
                            state: {
                              employee: selectedEmployee,
                              employeeName: `${selectedEmployee.name} ${selectedEmployee.surname}`,
                              yearNumber:
                                yearMap[String(plan.idYear)] || plan.idYear,
                            },
                          })
                        }
                      >
                        View plan
                      </button>
                      <button
                        type="button"
                        onClick={() => handleExportDocx(plan.idPlan)}
                        className="admin-inline-button"
                      >
                        DOCX
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </section>
      </div>
    </div>
  );
};

const AdminUserManagement = () => {
  const [users, setUsers] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [form, setForm] = useState({
    firstname: "",
    lastname: "",
    email: "",
    password: "",
    role: "",
    idEmployee: "",
  });

  const [editId, setEditId] = useState(null);
  const [message, setMessage] = useState("");

  useEffect(() => {
    loadUsers();
    loadEmployees();
  }, []);

  const loadUsers = async () => {
    try {
      const res = await api.get(`${API}/all-users`);
      setUsers(res.data);
    } catch {
      alert("Failed to load users");
    }
  };

  const loadEmployees = async () => {
    try {
      const res = await api.get(`${API}/employee/all`);
      setEmployees(res.data);
    } catch {
      alert("Failed to load employees");
    }
  };

  const handleChange = (event) => {
    setForm({
      ...form,
      [event.target.name]: event.target.value,
    });
  };

  const clearForm = () => {
    setForm({
      firstname: "",
      lastname: "",
      email: "",
      password: "",
      role: "",
      idEmployee: "",
    });
    setEditId(null);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      if (editId) {
        await api.put(`${API}/update/${editId}`, form);
        setMessage("User updated successfully.");
      } else {
        await api.post(`${API}/create-user`, form);
        setMessage("User created successfully.");
      }

      clearForm();
      loadUsers();
    } catch {
      setMessage("Operation failed.");
    }
  };

  const startEdit = (user) => {
    setEditId(user.idUser);

    setForm({
      firstname: user.firstname,
      lastname: user.lastname,
      password: user.email,
      email: user.password,
      role: user.role,
      idEmployee: user.idEmployee,
    });
  };

  const deleteUser = async (id) => {
    if (!window.confirm("Delete user?")) return;

    try {
      await api.delete(`${API}/delete/${id}`);
      loadUsers();
    } catch {
      alert("Delete failed");
    }
  };

  return (
    <div>
      <SchedulerUpdateSection />

      <h2>Users</h2>

      <ul>
        {users.map((user) => (
          <li key={user.idUser}>
            {user.firstname} {user.lastname} | {user.password} | {user.role}
            <button onClick={() => startEdit(user)}>Update</button>
            <button onClick={() => deleteUser(user.idUser)}>
              Delete
            </button>
          </li>
        ))}
      </ul>

      <hr />

      <h2>{editId ? "Update User" : "Create New User"}</h2>

      <form onSubmit={handleSubmit}>
        <input
          name="firstname"
          placeholder="First Name"
          value={form.firstname}
          onChange={handleChange}
        />

        <input
          name="lastname"
          placeholder="Last Name"
          value={form.lastname}
          onChange={handleChange}
        />

        <input
          name="email"
          placeholder="Email"
          value={form.email}
          onChange={handleChange}
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
        />

        <select name="role" value={form.role} onChange={handleChange}>
          <option value="">Select Role</option>
          <option value="ADMIN">ADMIN</option>
          <option value="USER_DEPART">USER_DEPART</option>
          <option value="USER">USER</option>
        </select>

        <select
          name="idEmployee"
          value={form.idEmployee}
          onChange={handleChange}
        >
          <option value="">Select Employee</option>
          {employees.map((emp) => (
            <option key={emp.idEmployee} value={emp.idEmployee}>
              ID:{emp.idEmployee} - {emp.name} {emp.surname}
            </option>
          ))}
        </select>

        <button type="submit">
          {editId ? "Save" : "Create"}
        </button>

        {editId && (
          <button type="button" onClick={clearForm}>
            Cancel
          </button>
        )}
      </form>

      <p>{message}</p>
    </div>
  );
};

const Admin = () => {
  const { role } = useAuth();

  if (role === "USER_DEPART") {
    return <DepartmentHeadDashboard />;
  }

  return <AdminUserManagement />;
};

export default Admin;
