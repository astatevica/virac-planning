import React, { useEffect, useState } from "react";
import StudentWorkService from "../services/StudentWorkService";
import { data } from "react-router-dom";

const StudentWorkList = () => {

  const [studentWorks, setStudentWorks] = useState([]);

  // ===== FORM STATE =====
  const [name, setName] = useState("");
  const [studentName, setStudentName] = useState("");
  const [studentSurname, setStudentSurname] = useState("");
  const [degree, setDegree] = useState("");

  // EDIT
  const [editId, setEditId] = useState(null);

  // FILTER
  const [filterDegree, setFilterDegree] = useState("");

  useEffect(() => {
    loadAll();
  }, []);

  /* ================= LOAD ================= */

  const loadAll = () => {
    StudentWorkService.getAll()
      .then(res => setStudentWorks(res.data))
      .catch(() => alert("Failed to load student works"));
  };

  /* ================= CREATE ================= */

  const addStudentWork = () => {
    if (!name || !studentName || !studentSurname || !degree) {
      alert("All fields are required");
      return;
    }

    StudentWorkService.create({
      name,
      studentName,
      studentSurname,
      degree
    })
      .then(() => {
        clearForm();
        loadAll();
      })
      .catch(err => alert(err.response?.data || "Create failed"));
  };

  /* ================= UPDATE ================= */

  const startEdit = (sw) => {
    setEditId(sw.idStudWork);
    setName(sw.name);
    setStudentName(sw.studentName);
    setStudentSurname(sw.studentSurname);
    setDegree(sw.degree);
  };

  const saveEdit = () => {
    StudentWorkService.update(editId, {
      name,
      studentName,
      studentSurname,
      degree
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

  const deleteStudentWork = (id) => {
    if (!window.confirm("Delete student work?")) return;

    StudentWorkService.delete(id)
      .then(loadAll)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  /* ================= FILTER ================= */

  const filterByDegree = () => {
    if (!filterDegree) {
      loadAll();
      return;
    }
    console.log(data);

    StudentWorkService.getByDegree(filterDegree)
      .then(res => setStudentWorks(res.data))
      .catch(() => alert("No student works found"));
  };

  /* ================= UTILS ================= */

  const clearForm = () => {
    setName("");
    setStudentName("");
    setStudentSurname("");
    setDegree("");
  };

  /* ================= RENDER ================= */

  return (
    <div>
      <h2>Student Works</h2>

      {/* ADD / UPDATE */}
      <div style={{ marginBottom: "20px" }}>
        <input
          placeholder="Work name"
          value={name}
          onChange={e => setName(e.target.value)}
        />

        <input
          placeholder="Student name"
          value={studentName}
          onChange={e => setStudentName(e.target.value)}
        />

        <input
          placeholder="Student surname"
          value={studentSurname}
          onChange={e => setStudentSurname(e.target.value)}
        />

        <select value={degree} onChange={e => setDegree(e.target.value)}>
          <option value="">Select degrees</option>
          <option value="bakalaurs">BAKALAURS</option>
          <option value="magistrs">MAĢISTRS</option>
          <option value="doktors">DOKTORS</option>
          <option value="pirma_cikla">PIRMĀ CIKLA</option>
          <option value="cits">Cits</option>
        </select>

        {editId ? (
          <>
            <button onClick={saveEdit}>Save</button>
            <button onClick={cancelEdit}>Cancel</button>
          </>
        ) : (
          <button onClick={addStudentWork}>Add</button>
        )}
      </div>

      {/* FILTER */}
      <div style={{ marginBottom: "15px" }}>
        <select value={filterDegree} onChange={e => setFilterDegree(e.target.value)}>
          <option value="">All degrees</option>
          <option value="bakalaurs">BAKALAURS</option>
          <option value="magistrs">MAĢISTRS</option>
          <option value="doktors">DOKTORS</option>
          <option value="pirma_cikla">PIRMĀ CIKLA</option>
          <option value="cits">Cits</option>
        </select>
        <button onClick={filterByDegree}>Filter by degree</button>
      </div>

      {/* LIST */}
      <ul>
        {studentWorks.map(sw => (
          <li key={sw.idStudWork}>
            <b>{sw.name}</b> — {sw.studentName} {sw.studentSurname} | Degree: {sw.degree}
            <button onClick={() => startEdit(sw)}>Update</button>
            <button onClick={() => deleteStudentWork(sw.idStudWork)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default StudentWorkList;
