import React, { useEffect, useState } from "react";
import CoursePlanService from "../services/CoursePlanService";
import CourseService from "../services/CourseService";
import PlanService from "../services/PlanService";

const CoursePlanList = () => {
  const [coursePlans, setCoursePlans] = useState([]);
  const [courses, setCourses] = useState([]);
  const [plans, setPlans] = useState([]);

  // ADD / EDIT
  const [idCourse, setIdCourse] = useState("");
  const [idPlan, setIdPlan] = useState("");
  const [workDone, setWorkDone] = useState("");

  // EDIT
  const [editId, setEditId] = useState(null);

  // FILTER
  const [filterPlanId, setFilterPlanId] = useState("");

  useEffect(() => {
    loadAll();
    loadCourses();
    loadPlans();
  }, []);

  /* ================= LOAD ================= */

  const loadAll = () => {
    CoursePlanService.getAll()
      .then(res => setCoursePlans(res.data))
      .catch(() => alert("Failed to load course plans"));
  };

  const loadCourses = () => {
    CourseService.getAll()
      .then(res => setCourses(res.data))
      .catch(() => alert("Failed to load courses"));
  };

  const loadPlans = () => {
    PlanService.getAll()
      .then(res => setPlans(res.data))
      .catch(() => alert("Failed to load plans"));
  };

  /* ================= FILTER ================= */

  const loadByPlan = () => {
    if (!filterPlanId) {
      loadAll();
      return;
    }

    CoursePlanService.getByPlanId(Number(filterPlanId))
      .then(res => setCoursePlans(res.data))
      .catch(() => alert("Filter failed"));
  };

  /* ================= CREATE ================= */

  const addCoursePlan = () => {
    if (!idCourse || !idPlan) {
      alert("Course and Plan are required");
      return;
    }

    CoursePlanService.create({
      idPlan: Number(idPlan),
      idCourse: Number(idCourse),
      workDone
    })
      .then(() => {
        clearForm();
        loadAll();
      })
      .catch(err => alert(err.response?.data || "Create failed"));
  };

  /* ================= UPDATE ================= */

  const startEdit = (cp) => {
    setEditId(cp.idCoursePlan);
    setIdCourse(String(cp.idCourse));
    setIdPlan(String(cp.idPlan));
    setWorkDone(cp.workDone || "");
  };

  const saveEdit = () => {
    CoursePlanService.update(editId, {
      idCourse: Number(idCourse),
      idPlan: Number(idPlan),
      workDone
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

  const deleteCoursePlan = (id) => {
    if (!window.confirm("Delete course plan?")) return;

    CoursePlanService.delete(id)
      .then(loadAll)
      .catch(() => alert("Delete failed"));
  };

  /* ================= UTILS ================= */

  const clearForm = () => {
    setIdCourse("");
    setIdPlan("");
    setWorkDone("");
  };

  /* ================= RENDER ================= */

  return (
    <div>
      <h2>Course Plans</h2>

      {/* FILTER */}
      <div style={{ marginBottom: "15px" }}>
        <select
          value={filterPlanId}
          onChange={e => setFilterPlanId(e.target.value)}
        >
          <option value="">All plans</option>
          {plans.map(p => (
            <option key={p.idPlan} value={p.idPlan}>
              Plan #{p.idPlan}
            </option>
          ))}
        </select>
        <button onClick={loadByPlan}>Filter</button>
        <button onClick={loadAll}>Clear</button>
      </div>

      {/* ADD / EDIT */}
      <div style={{ marginBottom: "20px" }}>
        <select value={idPlan} onChange={e => setIdPlan(e.target.value)}>
          <option value="">Select plan</option>
          {plans.map(p => (
            <option key={p.idPlan} value={p.idPlan}>
              Plan #{p.idPlan}
            </option>
          ))}
        </select>

        <select value={idCourse} onChange={e => setIdCourse(e.target.value)}>
          <option value="">Select course</option>
          {courses.map(c => (
            <option key={c.idCourse} value={c.idCourse}>
              {c.idCourse} {c.name}
            </option>
          ))}
        </select>

        <input
          placeholder="Work done"
          value={workDone}
          onChange={e => setWorkDone(e.target.value)}
        />

        {editId ? (
          <>
            <button onClick={saveEdit}>Save</button>
            <button onClick={cancelEdit}>Cancel</button>
          </>
        ) : (
          <button onClick={addCoursePlan}>Add</button>
        )}
      </div>

      {/*LIST */}
      <ul>
        {coursePlans.map(cp => (
          <li key={cp.idCoursePlan}>
            {/* Kāpēc te jāmaina vietām, lai ielādētu pareizo?*/}
            Course: {cp.idPlan} | Plan: {cp.idCourse} |  Work done: {cp.workDone || " -"}
            <button onClick={() => startEdit(cp)}>Update</button>
            <button onClick={() => deleteCoursePlan(cp.idCoursePlan)}>
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CoursePlanList;
