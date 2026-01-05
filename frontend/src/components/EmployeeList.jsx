import React, { useEffect, useState } from "react";
import EmployeeService from "../services/EmployeeService";
import DepartmentService from "../services/DepartmentService";

const EmployeeList = () => {
  const [employees, setEmployees] = useState([]);
  const [departments, setDepartments] = useState([]);

  // ADD
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [departmentId, setDepartmentId] = useState("");
  const [position, setPosition] = useState("");

  // UPDATE
  const [editId, setEditId] = useState(null);

  // FILTER
  const [filterDepartmentId, setFilterDepartmentId] = useState("");

  useEffect(() => {
    loadEmployees();
    loadDepartments();
  }, []);

  const loadEmployees = () => {
    EmployeeService.getAll()
      .then(res => setEmployees(res.data))
      .catch(err => alert("Failed to load employees"));
  };

  const loadDepartments = () => {
    DepartmentService.getAll()
      .then(res => setDepartments(res.data))
      .catch(() => {});
  };

  // CREATE
  const addEmployee = () => {
    if (!name || !surname || !departmentId || !position) {
      alert("All fields are required");
      return;
    }

    EmployeeService.create({
      name,
      surame: surname, // backend typo preserved
      department: departmentId,
      position
    })
      .then(() => {
        clearForm();
        loadEmployees();
      })
      .catch(err => alert(err.response?.data || "Add failed"));
  };

  // DELETE
  const deleteEmployee = (id) => {
    EmployeeService.delete(id)
      .then(loadEmployees)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  // START UPDATE
  const startEdit = (emp) => {
    setEditId(emp.id);
    setName(emp.name);
    setSurname(emp.surname);
    setDepartmentId(emp.department.id);
    setPosition(emp.position);
  };

  const cancelEdit = () => {
    setEditId(null);
    clearForm();
  };

  // SAVE UPDATE
  const saveEdit = () => {
    EmployeeService.update(editId, {
      name,
      surame: surname,
      department: departmentId,
      position
    })
      .then(() => {
        cancelEdit();
        loadEmployees();
      })
      .catch(err => alert(err.response?.data || "Update failed"));
  };

  // FILTER
  const filterEmployees = () => {
    if (!filterDepartmentId) {
      loadEmployees();
      return;
    }

    EmployeeService.getByDepartment(filterDepartmentId)
      .then(res => setEmployees(res.data))
      .catch(err => alert("No employees found"));
  };

  const clearForm = () => {
    setName("");
    setSurname("");
    setDepartmentId("");
    setPosition("");
  };

  return (
    <div>
      <h2>Employees</h2>

      {/* ➕ ADD / UPDATE */}
      <div style={{ marginBottom: "20px" }}>
        <input placeholder="Name" value={name} onChange={e => setName(e.target.value)} />
        <input placeholder="Surname" value={surname} onChange={e => setSurname(e.target.value)} />

        <select value={departmentId} onChange={e => setDepartmentId(e.target.value)}>
          <option value="">Select department</option>
          {departments.map(dep => (
            <option key={dep.id} value={dep.id}>
              {dep.name}
            </option>
          ))}
        </select>

        <input placeholder="Position" value={position} onChange={e => setPosition(e.target.value)} />

        {editId ? (
          <>
            <button onClick={saveEdit}>Save</button>
            <button onClick={cancelEdit}>Cancel</button>
          </>
        ) : (
          <button onClick={addEmployee}>Add</button>
        )}
      </div>

      {/* 🔍 FILTER */}
      <div style={{ marginBottom: "15px" }}>
        <select value={filterDepartmentId} onChange={e => setFilterDepartmentId(e.target.value)}>
          <option value="">All departments</option>
          {departments.map(dep => (
            <option key={dep.id} value={dep.id}>
              {dep.name}
            </option>
          ))}
        </select>

        <button onClick={filterEmployees}>Filter</button>
      </div>

      {/* 📄 LIST */}
      <ul>
        {employees.map(emp => (
          <li key={emp.id}>
            {emp.name} {emp.surname} | {emp.department.name} | {emp.position}
            <button onClick={() => startEdit(emp)}>Update</button>
            <button onClick={() => deleteEmployee(emp.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default EmployeeList;
