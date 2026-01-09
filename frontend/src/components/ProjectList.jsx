import React, { useEffect, useState } from "react";
import ProjectService from "../services/ProjectService";
import ProjectManagementService from "../services/ProjectManagementService";

const ProjectList = () => {

  const [projects, setProjects] = useState([]);
  const [managements, setManagements] = useState([]);

  // ===== FORM STATE =====
  const [name, setName] = useState("");
  const [number, setNumber] = useState("");
  const [managementId, setManagementId] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [acronym, setAcronym] = useState("");

  // EDIT
  const [editId, setEditId] = useState(null);

  // ===== FILTERS =====
  const [filterNumber, setFilterNumber] = useState("");
  const [filterStartDate, setFilterStartDate] = useState("");
  const [filterEndDate, setFilterEndDate] = useState("");

  useEffect(() => {
    loadAll();
    loadManagements();
  }, []);

  /* ================= LOAD ================= */

  const loadAll = () => {
    ProjectService.getAll()
      .then(res => setProjects(res.data))
      .catch(() => alert("Failed to load projects"));
  };

  const loadManagements = () => {
    ProjectManagementService.getAll()
      .then(res => setManagements(res.data))
      .catch(() => alert("Failed to load managements"));
  };

  /* ================= CREATE ================= */

  const addProject = () => {
    if (!name || !number || !managementId || !startDate || !endDate || !acronym) {
      alert("All fields are required");
      return;
    }

    ProjectService.create({
      name,
      number: Number(number),
      managementId: Number(managementId),
      startDate,
      endDate,
      acronym
    })
      .then(() => {
        clearForm();
        loadAll();
      })
      .catch(err => alert(err.response?.data || "Create failed"));
  };

  /* ================= UPDATE ================= */

  const startEdit = (pr) => {
    setEditId(pr.idProject);
    setName(pr.name);
    setNumber(pr.number);
    setManagementId(pr.managementId);
    setStartDate(pr.startDate);
    setEndDate(pr.endDate);
    setAcronym(pr.acronym);
  };

  const saveEdit = () => {
    ProjectService.update(editId, {
      name,
      number: Number(number),
      managementId: Number(managementId),
      startDate,
      endDate,
      acronym
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

  const deleteProject = (id) => {
    if (!window.confirm("Delete project?")) return;

    ProjectService.delete(id)
      .then(loadAll)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  /* ================= FILTERS ================= */

  const filterByNumber = () => {
    if (!filterNumber) {
      loadAll();
      return;
    }

    ProjectService.getByNumber(Number(filterNumber))
      .then(res => setProjects(res.data))
      .catch(() => alert("No projects found"));
  };

  const filterByStartDate = () => {
    if (!filterStartDate) {
      loadAll();
      return;
    }

    ProjectService.getByStartDate(filterStartDate)
      .then(res => setProjects(res.data))
      .catch(() => alert("No projects found"));
  };

  const filterByEndDate = () => {
    if (!filterEndDate) {
      loadAll();
      return;
    }

    ProjectService.getByEndDate(filterEndDate)
      .then(res => setProjects(res.data))
      .catch(() => alert("No projects found"));
  };

  /* ================= UTILS ================= */

  const clearForm = () => {
    setName("");
    setNumber("");
    setManagementId("");
    setStartDate("");
    setEndDate("");
    setAcronym("");
  };

  /* ================= RENDER ================= */

  return (
    <div>
      <h2>Projects</h2>

      {/* ADD / UPDATE */}
      <div style={{ marginBottom: "20px" }}>
        <input placeholder="Name" value={name} onChange={e => setName(e.target.value)} />
        <input placeholder="Number" type="number" value={number} onChange={e => setNumber(e.target.value)} />

        <select value={managementId} onChange={e => setManagementId(e.target.value)}>
          <option value="">Select management</option>
          {managements.map(pm => (
            <option key={pm.idProjectManag} value={pm.idProjectManag}>
              {pm.idProjectManag}
            </option>
          ))}
        </select>

        <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)} />
        <input type="date" value={endDate} onChange={e => setEndDate(e.target.value)} />
        <input placeholder="Acronym" value={acronym} onChange={e => setAcronym(e.target.value)} />

        {editId ? (
          <>
            <button onClick={saveEdit}>Save</button>
            <button onClick={cancelEdit}>Cancel</button>
          </>
        ) : (
          <button onClick={addProject}>Add</button>
        )}
      </div>

      {/* FILTERS */}
      <div style={{ marginBottom: "15px" }}>
        <input
          placeholder="Project number"
          type="number"
          value={filterNumber}
          onChange={e => setFilterNumber(e.target.value)}
        />
        <button onClick={filterByNumber}>Filter by number</button>
      </div>

      <div style={{ marginBottom: "15px" }}>
        <input type="date" value={filterStartDate} onChange={e => setFilterStartDate(e.target.value)} />
        <button onClick={filterByStartDate}>Filter by start date</button>
      </div>

      <div style={{ marginBottom: "15px" }}>
        <input type="date" value={filterEndDate} onChange={e => setFilterEndDate(e.target.value)} />
        <button onClick={filterByEndDate}>Filter by end date</button>
      </div>

      {/* LIST */}
      <ul>
        {projects.map(pr => (
          <li key={pr.idProject}>
            <b>{pr.name}</b> ({pr.acronym}) | № {pr.number} |
            Management: {pr.managementId} |
            {pr.startDate} → {pr.endDate}
            <button onClick={() => startEdit(pr)}>Update</button>
            <button onClick={() => deleteProject(pr.idProject)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ProjectList;
