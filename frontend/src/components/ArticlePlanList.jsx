// import React, { useEffect, useState } from "react";
// import ArticlePlanService from "../services/ArticlePlanService";

// const ArticlePlanList = () => {
//   const [articlePlans, setArticlePlans] = useState([]);

//   // ADD
//   const [idPlan, setIdPlan] = useState("");
//   const [idArticle, setIdArticle] = useState("");
//   const [articleComments, setArticleComments] = useState("");
//   const [publicationLink, setPublicationLink] = useState("");

//   // UPDATE
//   const [editId, setEditId] = useState(null);

//   // FILTER
//   const [filterPlanId, setFilterPlanId] = useState("");

//   useEffect(() => {
//     loadArticlePlans();
//   }, []);

//   const loadArticlePlans = () => {
//     ArticlePlanService.getAll()
//       .then(res => setArticlePlans(res.data))
//       .catch(err => alert(err.response?.data || "Load failed"));
//   };

//   // CREATE
//   const addArticlePlan = () => {
//     if (!idPlan || !idArticle) {
//       alert("Plan ID and Article ID are required");
//       return;
//     }

//     ArticlePlanService.create({
//       idPlan: Number(idPlan),
//       idArticlePlan: Number(idArticle),
//       articleComments,
//       publicationLink
//     })
//       .then(() => {
//         clearForm();
//         loadArticlePlans();
//       })
//       .catch(err => alert(err.response?.data || "Create failed"));
//   };

//   // DELETE
//   const deleteArticlePlan = (id) => {
//     ArticlePlanService.delete(id)
//       .then(loadArticlePlans)
//       .catch(err => alert(err.response?.data || "Delete failed"));
//   };

//   // UPDATE
//   const startEdit = (ap) => {
//     setEditId(ap.idArticlePlan);
//     setIdPlan(ap.idPlan);
//     setIdArticle(ap.idArticle);
//     setArticleComments(ap.articleComments || "");
//     setPublicationLink(ap.publicationLink || "");
//   };

//   const saveEdit = () => {
//     ArticlePlanService.update(editId, {
//       idPlan: Number(idPlan),
//       idArticlePlan: Number(idArticle),
//       articleComments,
//       publicationLink
//     })
//       .then(() => {
//         cancelEdit();
//         loadArticlePlans();
//       })
//       .catch(err => alert(err.response?.data || "Update failed"));
//   };

//   const cancelEdit = () => {
//     setEditId(null);
//     clearForm();
//   };

//   const clearForm = () => {
//     setIdPlan("");
//     setIdArticle("");
//     setArticleComments("");
//     setPublicationLink("");
//   };

//   // FILTER
//   const filterByPlan = () => {
//     if (!filterPlanId) {
//       loadArticlePlans();
//       return;
//     }

//     ArticlePlanService.filterByPlan(filterPlanId)
//       .then(res => setArticlePlans(res.data))
//       .catch(err => alert(err.response?.data || "Filter failed"));
//   };

//   return (
//     <div>
//       <h2>Article Plans</h2>

//       {/* 🔍 FILTER */}
//       <div style={{ marginBottom: "15px" }}>
//         <input
//           placeholder="Filter by Plan ID"
//           value={filterPlanId}
//           onChange={e => setFilterPlanId(e.target.value)}
//         />
//         <button onClick={filterByPlan}>Filter</button>
//         <button onClick={loadArticlePlans}>Clear</button>
//       </div>

//       {/* ➕ ADD / ✏️ EDIT */}
//       <div style={{ marginBottom: "15px" }}>
//         <input
//           placeholder="Plan ID"
//           value={idPlan}
//           onChange={e => setIdPlan(e.target.value)}
//         />
//         <input
//           placeholder="Article ID"
//           value={idArticle}
//           onChange={e => setIdArticle(e.target.value)}
//         />
//         <input
//           placeholder="Comments"
//           value={articleComments}
//           onChange={e => setArticleComments(e.target.value)}
//         />
//         <input
//           placeholder="Publication link"
//           value={publicationLink}
//           onChange={e => setPublicationLink(e.target.value)}
//         />

//         {editId ? (
//           <>
//             <button onClick={saveEdit}>Save</button>
//             <button onClick={cancelEdit}>Cancel</button>
//           </>
//         ) : (
//           <button onClick={addArticlePlan}>Add</button>
//         )}
//       </div>

//       {/* 📄 LIST */}
//       <ul>
//         {articlePlans.map(ap => (
//           <li key={ap.idArticlePlan}>
//             Plan: {ap.idPlan} | Article: {ap.idArticle} | {ap.articleComments}
//             {" "}
//             <a href={ap.publicationLink} target="_blank" rel="noreferrer">
//               Link
//             </a>
//             <button onClick={() => startEdit(ap)}>Update</button>
//             <button onClick={() => deleteArticlePlan(ap.idArticlePlan)}>
//               Delete
//             </button>
//           </li>
//         ))}
//       </ul>
//     </div>
//   );
// };

// export default ArticlePlanList;
