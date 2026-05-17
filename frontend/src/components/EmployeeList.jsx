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
  const [nameDepartment, setIdDepartment] = useState("");

  // EDIT
  const [editId, setEditId] = useState(null);

  // FILTER
  const [filterIdDepartment, setFilterIdDeparment] = useState("");

  useEffect(() => {
    loadEmployees();
    loadDepartments();
  }, []);

  //LOAD
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

  //CREATE
  const addEmployee = () => {
    if (!name || !surname || !position || !nameDepartment) {
      alert("All fields are required");
      return;
    }
    
    EmployeeService.create({
      name,
      surname,
      position,
      nameDepartment
    })
      .then(() => {
        clearForm();
        loadEmployees();
      })
      .catch(err => alert(err.response?.data || "Create failed"));
  };

  //UPDATE
  const startEdit = (emp) => {
    setEditId(emp.id);
    setName(emp.name);
    setSurname(emp.surname);
    setPosition(emp.position);
    setIdDepartment(emp.nameDepartment || "");
  };

  const saveEdit = () => {
    EmployeeService.update(editId, {
      name,
      surname,
      position,
      nameDepartment
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

  //DELETE
  const deleteEmployee = (id) => {
    if (!window.confirm("Delete employee?")) return;

    EmployeeService.delete(id)
      .then(loadEmployees)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  //FILTER
  const filterEmployees = () => {
    if (!filterIdDepartment) {
      loadEmployees();
      return;
    }
    
    EmployeeService.filterByDepartment(filterIdDepartment)
      .then(res => setEmployees(res.data))
      .catch(() => alert("No employees found"));
  };

  //UTILS
  const clearForm = () => {
    setName("");
    setSurname("");
    setPosition("");
    setIdDepartment("");
  };

  //RENDER
  return (
    <div>
      <h2>Employees</h2>

      {/* ADD / UPDATE */}
      <div style={{ marginBottom: "20px" }}>
        <input
          data-testid="cypress-employee-name"
          placeholder="Name"
          value={name}
          onChange={e => setName(e.target.value)}
        />

        <input
          data-testid="cypress-employee-surname"
          placeholder="Surname"
          value={surname}
          onChange={e => setSurname(e.target.value)}
        />

        <input
          data-testid="cypress-employee-position"
          placeholder="Position"
          value={position}
          onChange={e => setPosition(e.target.value)}
        />

        <select
          data-testid="cypress-employee-department"
          value={nameDepartment}
          onChange={e => setIdDepartment(e.target.value)}
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
            <button data-testid="cypress-employee-save" onClick={saveEdit}>Save</button>
            <button data-testid="cypress-employee-edit" onClick={cancelEdit}>Cancel</button>
          </>
        ) : (
          <button data-testid="cypress-employee-add" onClick={addEmployee}>Add</button>
        )}
      </div>

      {/* FILTER */}
      <div style={{ marginBottom: "15px" }}>
        <select
          data-testid="cypress-filter-department"
          value={filterIdDepartment}
          onChange={e => setFilterIdDeparment(e.target.value)}
        >
          <option value="">All departments</option>
          {departments.map(dep => (
            <option key={dep.idDepartment} value={dep.idDepartment}>
              {dep.name}              
            </option>
          ))}
        </select>
  
        <button data-testid="cypress-filter-button" onClick={filterEmployees}>Filter</button>
      </div>

      {/* LIST */}
      <ul>
        {employees.map(emp => (
          <li  data-testid={`employee-row-${emp.idEmployee}`} key={emp.id}>
            {emp.name} {emp.surname} |{" "}
            {emp.nameDepartment || "No department"} |{" "}
            {emp.position}
            <button data-testid="cypress-employee-update" onClick={() => startEdit(emp)}>Update</button>
            <button data-testid="cypress-employee-delete" onClick={() => deleteEmployee(emp.idEmployee)}>
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default EmployeeList;
