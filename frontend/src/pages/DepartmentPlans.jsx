import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import DepartmentHeadService from "../services/DepartmentHeadService";

const getEmployeeKey = (employee) => String(employee.idEmployee ?? employee.id);

export default function DepartmentPlans() {
  const navigate = useNavigate();
  const [plans, setPlans] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [years, setYears] = useState([]);
  const [selectedYear, setSelectedYear] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadInitialData = async () => {
      try {
        const [plansRes, employeesRes, yearsRes] = await Promise.all([
          DepartmentHeadService.getDepartmentPlans(),
          DepartmentHeadService.getEmployeesByDepartment(),
          api.get("/year/all").catch(() => ({ data: [] })),
        ]);

        setPlans(plansRes.data);
        setEmployees(employeesRes.data);
        setYears(yearsRes.data);
        setError("");
      } catch {
        setError("Failed to load department plans.");
      } finally {
        setLoading(false);
      }
    };

    loadInitialData();
  }, []);

  useEffect(() => {
    const loadPlans = async () => {
      setLoading(true);

      try {
        const response = selectedYear
          ? await DepartmentHeadService.getDepartmentPlansByYear(selectedYear)
          : await DepartmentHeadService.getDepartmentPlans();

        setPlans(response.data);
        setError("");
      } catch {
        setPlans([]);
        setError("Failed to filter department plans.");
      } finally {
        setLoading(false);
      }
    };

    loadPlans();
  }, [selectedYear]);

  const employeeMap = Object.fromEntries(
    employees.map((employee) => [
      getEmployeeKey(employee),
      `${employee.name} ${employee.surname}`,
    ])
  );

  const employeeById = Object.fromEntries(
    employees.map((employee) => [getEmployeeKey(employee), employee])
  );

  const yearMap = Object.fromEntries(
    years.map((year) => [String(year.idYear), year.yearNumber])
  );

  return (
    <div style={{ maxWidth: 1100, margin: "0 auto" }}>
      <h2>Department Plans</h2>

      {error && <p style={{ color: "crimson" }}>{error}</p>}

      <div style={{ marginBottom: "16px" }}>
        <label htmlFor="department-year-filter">Year:</label>{" "}
        <select
          id="department-year-filter"
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

      {loading ? (
        <p>Loading plans...</p>
      ) : plans.length === 0 ? (
        <p>No plans found for the selected year.</p>
      ) : (
        <table border="1" cellPadding="6" width="100%">
          <thead>
            <tr>
              <th>ID</th>
              <th>Employee</th>
              <th>Year</th>
              <th>Projects</th>
              <th>Articles</th>
              <th>Courses</th>
              <th>Student Work</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {plans.map((plan) => {
              const employee = employeeById[String(plan.idEmployee)];
              const employeeName =
                employeeMap[String(plan.idEmployee)] || `Employee ID: ${plan.idEmployee}`;

              return (
                <tr key={plan.idPlan}>
                  <td>{plan.idPlan}</td>
                  <td>{employeeName}</td>
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
                            employee,
                            employeeName,
                            yearNumber: yearMap[String(plan.idYear)] || plan.idYear,
                          },
                        })
                      }
                    >
                      View plan
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      )}
    </div>
  );
}
