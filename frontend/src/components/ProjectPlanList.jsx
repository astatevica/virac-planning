import React, { useEffect, useState } from "react";
import ProjectPlanService from "../services/ProjectPlanService";
import ProjectService from "../services/ProjectService";
import PlanService from "../services/PlanService"; // assumes you already have this

const ProjectPlanList = () => {

  const [projectPlans, setProjectPlans] = useState([]);
  const [projects, setProjects] = useState([]);
  const [plans, setPlans] = useState([]);

  // ===== FORM =====
  const [idPlan, setIdPlan] = useState("");
  const [idProject, setIdProject] = useState("");
  const [tasks, setTasks] = useState("");
  const [workDone, setWorkDone] = useState("");

  // EDIT
  const [editId, setEditId] = useState(null);

  // FILTERS
  const [filterPlanId, setFilterPlanId] = useState("");
  const [filterProjectId, setFilterProjectId] = useState("");

  useEffect(() => {
    loadAll();
    loadProjects();
    loadPlans();
  }, []);

  /* ================= LOAD ================= */

  const loadAll = () => {
    ProjectPlanService.getAll()
      .then(res => setProjectPlans(res.data))
      .catch(() => alert("Failed to load project plans"));
  };

  const loadProjects = () => {
    ProjectService.getAll()
      .then(res => setProjects(res.data))
      .catch(() => alert("Failed to load projects"));
  };

  const loadPlans = () => {
    PlanService.getAll()
      .then(res => setPlans(res.data))
      .catch(() => alert("Failed to load plans"));
  };

  /* ================= CREATE ================= */

  const addProjectPlan = () => {
    if (!idPlan || !idProject) {
      alert("All fields are required");
      return;
    }

    ProjectPlanService.create({
      idPlan: Number(idPlan),
      idProject: Number(idProject),
      tasks,
      workDone
    })
      .then(() => {
        clearForm();
        loadAll();
      })
      .catch(err => alert(err.response?.data || "Create failed"));
  };

  /* ================= UPDATE ================= */

  const startEdit = (pp) => {
    setEditId(pp.idProjectPlan);
    setIdPlan(pp.idPlan);
    setIdProject(pp.idProject);
    setTasks(pp.tasks);
    setWorkDone(pp.workDone);
  };

  const saveEdit = () => {
    ProjectPlanService.update(editId, {
      idProjectPlan: editId,
      idPlan: Number(idPlan),
      idProject: Number(idProject),
      tasks,
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

  const deleteProjectPlan = (id) => {
    if (!window.confirm("Delete project plan?")) return;

    ProjectPlanService.delete(id)
      .then(loadAll)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  /* ================= FILTERS ================= */

  const filterByPlan = () => {
    if (!filterPlanId) {
      loadAll();
      return;
    }

    ProjectPlanService.getByPlan(Number(filterPlanId))
      .then(res => setProjectPlans(res.data))
      .catch(() => alert("No records found"));
  };

  const filterByProject = () => {
    if (!filterProjectId) {
      loadAll();
      return;
    }

    ProjectPlanService.getByProject(Number(filterProjectId))
      .then(res => setProjectPlans(res.data))
      .catch(() => alert("No records found"));
  };

  /* ================= UTILS ================= */

  const clearForm = () => {
    setIdPlan("");
    setIdProject("");
    setTasks("");
    setWorkDone("");
  };

  /* ================= RENDER ================= */

  return (
    <div>
      <h2>Project Plans</h2>

      {/* ADD / UPDATE */}
      <div style={{ marginBottom: "20px" }}>
        <select value={idPlan} onChange={e => setIdPlan(e.target.value)}>
          <option value="">Select plan</option>
          {plans.map(p => (
            <option key={p.idPlan} value={p.idPlan}>
              {p.idPlan}
            </option>
          ))}
        </select>

        <select value={idProject} onChange={e => setIdProject(e.target.value)}>
          <option value="">Select project</option>
          {projects.map(pr => (
            <option key={pr.idProject} value={pr.idProject}>
              {pr.name}
            </option>
          ))}
        </select>

        <input
          placeholder="Tasks"
          value={tasks}
          onChange={e => setTasks(e.target.value)}
        />

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
          <button onClick={addProjectPlan}>Add</button>
        )}
      </div>

      {/* FILTERS */}
      <div style={{ marginBottom: "15px" }}>
        <select value={filterPlanId} onChange={e => setFilterPlanId(e.target.value)}>
          <option value="">All plans</option>
          {plans.map(p => (
            <option key={p.idPlan} value={p.idPlan}>
              {p.idPlan}
            </option>
          ))}
        </select>
        <button onClick={filterByPlan}>Filter by plan</button>
      </div>

      <div style={{ marginBottom: "15px" }}>
        <select value={filterProjectId} onChange={e => setFilterProjectId(e.target.value)}>
          <option value="">All projects</option>
          {projects.map(pr => (
            <option key={pr.idProject} value={pr.idProject}>
              {pr.name}
            </option>
          ))}
        </select>
        <button onClick={filterByProject}>Filter by project</button>
      </div>

      {/* LIST */}
      <ul>
        {projectPlans.map(pp => (
          <li key={pp.idProjectPlan}>
            Plan {pp.idPlan} | Project {pp.idProject} |
            Tasks: {pp.tasks} | Work done: {pp.workDone}
            <button onClick={() => startEdit(pp)}>Update</button>
            <button onClick={() => deleteProjectPlan(pp.idProjectPlan)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ProjectPlanList;
