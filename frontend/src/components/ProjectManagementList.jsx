import React, { useEffect, useState } from "react";
import ProjectManagementService from "../services/ProjectManagementService";
import EmployeeService from "../services/EmployeeService";

const ProjectManagementList = () => {

  const [managements, setManagements] = useState([]);
  const [employees, setEmployees] = useState([]);

  // FORM
  const [employeeId, setEmployeeId] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  // EDIT
  const [editId, setEditId] = useState(null);

  // FILTER
  const [filterEmployeeId, setFilterEmployeeId] = useState("");

  useEffect(() => {
    loadAll();
    loadEmployees();
  }, []);

  /* ================= LOAD ================= */

  const loadAll = () => {
    ProjectManagementService.getAll()
      .then(res => {
        console.log("ALL MANAGEMENT:", res.data);
        setManagements(res.data);
      })
      .catch(() => alert("Failed to load project management"));
  };

  const loadEmployees = () => {
    EmployeeService.getAll()
      .then(res => setEmployees(res.data))
      .catch(() => alert("Failed to load employees"));
  };

  /* ================= CREATE ================= */

  const addManagement = () => {
    if (!employeeId || !startDate || !endDate) {
      alert("All fields required");
      return;
    }

    ProjectManagementService.create({
      employeeId,
      startDate,
      endDate
    })
      .then(() => {
        clearForm();
        loadAll();
      })
      .catch(err => alert(err.response?.data || "Create failed"));
  };

  /* ================= UPDATE ================= */

  const startEdit = (pm) => {
    setEditId(pm.idProjectManag);
    setEmployeeId(pm.employeeId);
    setStartDate(pm.startDate);
    setEndDate(pm.endDate);
  };

  const saveEdit = () => {
    ProjectManagementService.update(editId, {
      employeeId,
      startDate,
      endDate
    })
      .then(() => {
        cancelEdit();
        loadAll();
      })
      .catch(err => alert(err.response?.data || "Update failed"));
  };

  const cancelEdit = () => {
    setEditId(null);
    clearForm();
  };

  /* ================= DELETE ================= */

  const deleteManagement = (id) => {
    if (!window.confirm("Delete management?")) return;

    ProjectManagementService.delete(id)
      .then(loadAll)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  /* ================= FILTER ================= */

  const filterByEmployee = () => {
    if (!filterEmployeeId) {
      loadAll();
      return;
    }

    ProjectManagementService.getByEmployee(Number(filterEmployeeId))
      .then(res => {
        console.log("FILTERED:", res.data);
        setManagements(res.data);
      })
      .catch(() => alert("No records found"));
  };

  /* ================= UTILS ================= */

  const clearForm = () => {
    setEmployeeId("");
    setStartDate("");
    setEndDate("");
  };

  /* ================= RENDER ================= */

  return (
    <div>
      <h2>Project Management</h2>

      {/* ADD / UPDATE */}
      <div style={{ marginBottom: "20px" }}>
        <select value={employeeId} onChange={e => setEmployeeId(e.target.value)}>
          <option value="">Select employee</option>
          {employees.map(emp => (
            <option key={emp.id} value={emp.id}>
              {emp.name} {emp.surname}
            </option>
          ))}
        </select>

        <input
          type="date"
          value={startDate}
          onChange={e => setStartDate(e.target.value)}
        />

        <input
          type="date"
          value={endDate}
          onChange={e => setEndDate(e.target.value)}
        />

        {editId ? (
          <>
            <button onClick={saveEdit}>Save</button>
            <button onClick={cancelEdit}>Cancel</button>
          </>
        ) : (
          <button onClick={addManagement}>Add</button>
        )}
      </div>

      {/* FILTER */}
      <div style={{ marginBottom: "15px" }}>
        <select
          value={filterEmployeeId}
          onChange={e => setFilterEmployeeId(e.target.value)}
        >
          <option value="">All employees</option>
          {employees.map(emp => (
            <option key={emp.id} value={emp.id}>
              {emp.id} {emp.name} {emp.surname}
            </option>
          ))}
        </select>

        <button onClick={filterByEmployee}>Filter</button>
      </div>

      {/* LIST */}
      <ul>
        {managements.map(pm => (
          <li key={pm.idProjectManag}>
            {"Employee id: "}<b>{pm.employeeId}</b>
            | {pm.startDate} → {pm.endDate}
            <button onClick={() => startEdit(pm)}>Update</button>
            <button onClick={() => deleteManagement(pm.idProjectManag)}>
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ProjectManagementList;
