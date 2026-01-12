// import React, { useEffect, useState } from "react";
// import WorkPlanService from "../services/WorkPlanService";
// import StudentWorkService from "../services/StudentWorkService";
// import PlanService from "../services/PlanService";

// const WorkPlanList = () => {

//   const [workPlans, setWorkPlans] = useState([]);
//   const [studentWorks, setStudentWorks] = useState([]);
//   const [plans, setPlans] = useState([]);

//   // ===== FORM =====
//   const [studentWorkId, setStudentWorkId] = useState("");
//   const [planId, setPlanId] = useState("");
//   const [workDone, setWorkDone] = useState("");

//   // EDIT
//   const [editId, setEditId] = useState(null);

//   // ===== FILTERS =====
//   const [filterStudentWorkId, setFilterStudentWorkId] = useState("");
//   const [filterPlanId, setFilterPlanId] = useState("");

//   useEffect(() => {
//     loadAll();
//     loadStudentWorks();
//     loadPlans();
//   }, []);

//   /* ================= LOAD ================= */

//   const loadAll = () => {
//     WorkPlanService.getAll()
//       .then(res => setWorkPlans(res.data))
//       .catch(() => alert("Failed to load work plans"));
//   };

//   const loadStudentWorks = () => {
//     StudentWorkService.getAll()
//       .then(res => setStudentWorks(res.data))
//       .catch(() => alert("Failed to load student works"));
//   };

//   const loadPlans = () => {
//     PlanService.getAll()
//       .then(res => setPlans(res.data))
//       .catch(() => alert("Failed to load plans"));
//   };

//   /* ================= CREATE ================= */

//   const addWorkPlan = () => {
//     if (!studentWorkId || !planId || !workDone) {
//       alert("All fields are required");
//       return;
//     }

//     WorkPlanService.create({
//       idStudentWork: Number(studentWorkId),
//       idPlan: Number(planId),
//       workDone
//     })
//       .then(() => {
//         clearForm();
//         loadAll();
//       })
//       .catch(err => alert(err.response?.data || "Create failed"));
//   };

//   /* ================= UPDATE ================= */

//   const startEdit = (wp) => {
//     setEditId(wp.idWorkPlan);
//     setStudentWorkId(wp.idStudentWork);
//     setPlanId(wp.idPlan);
//     setWorkDone(wp.workDone);
//   };

//   const saveEdit = () => {
//     WorkPlanService.update(editId, {
//       idStudentWork: Number(studentWorkId),
//       idPlan: Number(planId),
//       workDone
//     })
//       .then(() => {
//         cancelEdit();
//         loadAll();
//       })
//       .catch(err => alert(err.response?.data || "Update failed"));
//   };

//   const cancelEdit = () => {
//     setEditId(null);
//     clearForm();
//   };

//   /* ================= DELETE ================= */

//   const deleteWorkPlan = (id) => {
//     if (!window.confirm("Delete work plan?")) return;

//     WorkPlanService.delete(id)
//       .then(loadAll)
//       .catch(err => alert(err.response?.data || "Delete failed"));
//   };

//   /* ================= FILTERS ================= */

//   const filterByStudentWork = () => {
//     if (!filterStudentWorkId) {
//       loadAll();
//       return;
//     }

//     WorkPlanService.getByStudentWork(Number(filterStudentWorkId))
//       .then(res => setWorkPlans(res.data))
//       .catch(() => alert("No records found"));
//   };

//   const filterByPlan = () => {
//     if (!filterPlanId) {
//       loadAll();
//       return;
//     }

//     WorkPlanService.getByPlan(Number(filterPlanId))
//       .then(res => setWorkPlans(res.data))
//       .catch(() => alert("No records found"));
//   };

//   /* ================= UTILS ================= */

//   const clearForm = () => {
//     setStudentWorkId("");
//     setPlanId("");
//     setWorkDone("");
//   };

//   /* ================= RENDER ================= */

//   return (
//     <div>
//       <h2>Work Plans</h2>

//       {/* ADD / UPDATE */}
//       <div style={{ marginBottom: "20px" }}>
//         <select value={studentWorkId} onChange={e => setStudentWorkId(e.target.value)}>
//           <option value="">Select student work</option>
//           {studentWorks.map(sw => (
//             <option key={sw.idStudWork} value={sw.idStudWork}>
//               {sw.idStudWork} – {sw.name}
//             </option>
//           ))}
//         </select>

//         <select value={planId} onChange={e => setPlanId(e.target.value)}>
//           <option value="">Select plan</option>
//           {plans.map(pl => (
//             <option key={pl.idPlan} value={pl.idPlan}>
//               {pl.idPlan}
//             </option>
//           ))}
//         </select>

//         <input
//           placeholder="Work done"
//           value={workDone}
//           onChange={e => setWorkDone(e.target.value)}
//         />

//         {editId ? (
//           <>
//             <button onClick={saveEdit}>Save</button>
//             <button onClick={cancelEdit}>Cancel</button>
//           </>
//         ) : (
//           <button onClick={addWorkPlan}>Add</button>
//         )}
//       </div>

//       {/* FILTERS */}
//       <div style={{ marginBottom: "15px" }}>
//         <select
//           value={filterStudentWorkId}
//           onChange={e => setFilterStudentWorkId(e.target.value)}
//         >
//           <option value="">All student works</option>
//           {studentWorks.map(sw => (
//             <option key={sw.idStudWork} value={sw.idStudWork}>
//               {sw.idStudWork}
//             </option>
//           ))}
//         </select>

//         <button onClick={filterByStudentWork}>Filter by student work</button>
//       </div>

//       <div style={{ marginBottom: "15px" }}>
//         <select
//           value={filterPlanId}
//           onChange={e => setFilterPlanId(e.target.value)}
//         >
//           <option value="">All plans</option>
//           {plans.map(pl => (
//             <option key={pl.idPlan} value={pl.idPlan}>
//               {pl.idPlan}
//             </option>
//           ))}
//         </select>

//         <button onClick={filterByPlan}>Filter by plan</button>
//       </div>

//       {/* LIST */}
//       <ul>
//         {workPlans.map(wp => (
//           <li key={wp.idWorkPlan}>
//             StudentWork: <b>{wp.idStudentWork}</b> | Plan: <b>{wp.idPlan}</b> |
//             Work: {wp.workDone}
//             <button onClick={() => startEdit(wp)}>Update</button>
//             <button onClick={() => deleteWorkPlan(wp.idWorkPlan)}>Delete</button>
//           </li>
//         ))}
//       </ul>
//     </div>
//   );
// };

// export default WorkPlanList;
