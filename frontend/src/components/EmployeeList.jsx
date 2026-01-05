import React, { useEffect, useState } from "react";
import EmployeeService from "../services/EmployeeService";
import DepartmentService from "../services/DepartmentService";

const EmployeeList = () => {
  const [employees, setEmployees] = useState([]);
  const [departments, setDepartments] = useState([]);

  // FORM STATE
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [position, setPosition] = useState("");
  const [departmentId, setDepartmentId] = useState("");

  // EDIT
  const [editId, setEditId] = useState(null);

  // FILTER
  const [filterDepartmentId, setFilterDepartmentId] = useState("");

  useEffect(() => {
    loadEmployees();
    loadDepartments();
  }, []);

  /* ================= LOAD ================= */

  const loadEmployees = () => {
    EmployeeService.getAll()
      .then(res => setEmployees(res.data))
      .catch(() => alert("Failed to load employees"));
  };

  const loadDepartments = () => {
    DepartmentService.getAll()
      .then(res => setDepartments(res.data))
      .catch(() => alert("Failed to load departments"));
  };

  /* ================= CREATE ================= */

  const addEmployee = () => {
    if (!name || !surname || !position || !departmentId) {
      alert("All fields are required");
      return;
    }

    EmployeeService.create({
      name,
      surname,
      position,
      department: {
        idDepartment: Number(departmentId)
      }
    })
      .then(() => {
        clearForm();
        loadEmployees();
      })
      .catch(err => alert(err.response?.data || "Create failed"));
  };

  /* ================= UPDATE ================= */

  const startEdit = (emp) => {
    setEditId(emp.idEmployee);
    setName(emp.name);
    setSurname(emp.surname);
    setPosition(emp.position);
    setDepartmentId(emp.viracDepartment?.idDepartment || "");
  };

  const saveEdit = () => {
    EmployeeService.update(editId, {
      name,
      surname,
      position,
      department: {
        idDepartment: Number(departmentId)
      }
    })
      .then(() => {
        cancelEdit();
        loadEmployees();
      })
      .catch(err => alert(err.response?.data || "Update failed"));
  };

  const cancelEdit = () => {
    setEditId(null);
    clearForm();
  };

  /* ================= DELETE ================= */

  const deleteEmployee = (id) => {
    if (!window.confirm("Delete employee?")) return;

    EmployeeService.delete(id)
      .then(loadEmployees)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  /* ================= FILTER ================= */

  const filterEmployees = () => {
    if (!filterDepartmentId) {
      loadEmployees();
      return;
    }

    EmployeeService.getByDepartment(filterDepartmentId)
      .then(res => setEmployees(res.data))
      .catch(() => alert("No employees found"));
  };

  /* ================= UTILS ================= */

  const clearForm = () => {
    setName("");
    setSurname("");
    setPosition("");
    setDepartmentId("");
  };

  /* ================= RENDER ================= */

  return (
    <div>
      <h2>Employees</h2>

      {/* ADD / UPDATE */}
      <div style={{ marginBottom: "20px" }}>
        <input
          placeholder="Name"
          value={name}
          onChange={e => setName(e.target.value)}
        />

        <input
          placeholder="Surname"
          value={surname}
          onChange={e => setSurname(e.target.value)}
        />

        <input
          placeholder="Position"
          value={position}
          onChange={e => setPosition(e.target.value)}
        />

        <select
          value={departmentId}
          onChange={e => setDepartmentId(e.target.value)}
        >
          <option value="">Select department</option>
          {departments.map(dep => (
            <option key={dep.idDepartment} value={dep.idDepartment}>
              {dep.name}
            </option>
          ))}
        </select>

        {editId ? (
          <>
            <button onClick={saveEdit}>Save</button>
            <button onClick={cancelEdit}>Cancel</button>
          </>
        ) : (
          <button onClick={addEmployee}>Add</button>
        )}
      </div>

      {/* FILTER */}
      <div style={{ marginBottom: "15px" }}>
        <select
          value={filterDepartmentId}
          onChange={e => setFilterDepartmentId(e.target.value)}
        >
          <option value="">All departments</option>
          {departments.map(dep => (
            <option key={dep.idDepartment} value={dep.idDepartment}>
              {dep.name}
            </option>
          ))}
        </select>

        <button onClick={filterEmployees}>Filter</button>
      </div>

      {/* LIST */}
      <ul>
        {employees.map(emp => (
          <li key={emp.id}>
            {emp.name} {emp.surname} |{" "}
            {emp.department?.name || "No department"} |{" "}
            {emp.position}
            <button onClick={() => startEdit(emp)}>Update</button>
            <button onClick={() => deleteEmployee(emp.id)}>
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default EmployeeList;
