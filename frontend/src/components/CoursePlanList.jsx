// import React, { useEffect, useState } from "react";
// import CoursePlanService from "../services/CoursePlanService";

// const CoursePlanList = () => {
//   const [coursePlans, setCoursePlans] = useState([]);

//   // ADD / EDIT FIELDS
//   const [idCourse, setIdCourse] = useState("");
//   const [idPlan, setIdPlan] = useState("");
//   const [workDone, setWorkDone] = useState("");

//   // UPDATE
//   const [editId, setEditId] = useState(null);

//   // FILTER
//   const [filterPlanId, setFilterPlanId] = useState("");

//   useEffect(() => {
//     loadAll();
//   }, []);

//   const loadAll = () => {
//     CoursePlanService.getAll()
//       .then(res => setCoursePlans(res.data))
//       .catch(err => alert(err.response?.data || "Failed to load course plans"));
//   };

//   const loadByPlan = () => {
//     if (!filterPlanId) {
//       loadAll();
//       return;
//     }

//     CoursePlanService.getByPlanId(filterPlanId)
//       .then(res => setCoursePlans(res.data))
//       .catch(err => alert(err.response?.data || "Filter failed"));
//   };

//   // CREATE
//   const addCoursePlan = () => {
//     if (!idCourse || !idPlan) {
//       alert("Course ID and Plan ID are required");
//       return;
//     }

//     CoursePlanService.create({
//       idCourse: Number(idCourse),
//       idPlan: Number(idPlan),
//       workDone
//     })
//       .then(() => {
//         clearForm();
//         loadAll();
//       })
//       .catch(err => alert(err.response?.data || "Create failed"));
//   };

//   // DELETE
//   const deleteCoursePlan = (id) => {
//     CoursePlanService.delete(id)
//       .then(loadAll)
//       .catch(err => alert(err.response?.data || "Delete failed"));
//   };

//   // UPDATE
//   const startEdit = (cp) => {
//     setEditId(cp.idCoursePlan);
//     setIdCourse(cp.idCourse);
//     setIdPlan(cp.idPlan);
//     setWorkDone(cp.workDone || "");
//   };

//   const cancelEdit = () => {
//     setEditId(null);
//     clearForm();
//   };

//   const saveEdit = () => {
//     CoursePlanService.update(editId, {
//       idCourse: Number(idCourse),
//       idPlan: Number(idPlan),
//       workDone
//     })
//       .then(() => {
//         cancelEdit();
//         loadAll();
//       })
//       .catch(err => alert(err.response?.data || "Update failed"));
//   };

//   const clearForm = () => {
//     setIdCourse("");
//     setIdPlan("");
//     setWorkDone("");
//   };

//   return (
//     <div>
//       <h2>Course Plans</h2>

//       {/* 🔍 FILTER */}
//       <div style={{ marginBottom: "10px" }}>
//         <input
//           placeholder="Filter by Plan ID"
//           value={filterPlanId}
//           onChange={e => setFilterPlanId(e.target.value)}
//         />
//         <button onClick={loadByPlan}>Filter</button>
//         <button onClick={loadAll}>Clear</button>
//       </div>

//       {/* ➕ ADD / ✏️ UPDATE */}
//       <div style={{ marginBottom: "15px" }}>
//         <input
//           placeholder="Course ID"
//           value={idCourse}
//           onChange={e => setIdCourse(e.target.value)}
//         />
//         <input
//           placeholder="Plan ID"
//           value={idPlan}
//           onChange={e => setIdPlan(e.target.value)}
//         />
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
//           <button onClick={addCoursePlan}>Add</button>
//         )}
//       </div>

//       {/* 📄 LIST */}
//       <ul>
//         {coursePlans.map(cp => (
//           <li key={cp.idCoursePlan}>
//             Course ID: {cp.idCourse} | Plan ID: {cp.idPlan} | Work:{" "}
//             {cp.workDone || "-"}
//             <button onClick={() => startEdit(cp)}>Update</button>
//             <button onClick={() => deleteCoursePlan(cp.idCoursePlan)}>
//               Delete
//             </button>
//           </li>
//         ))}
//       </ul>
//     </div>
//   );
// };

// export default CoursePlanList;
