import React, { useEffect, useState } from "react";
import ArticlePlanService from "../services/ArticlePlanService";
import PlanService from "../services/PlanService";
import ScientificArticlesService from "../services/ScientificArticlesService";

const ArticlePlanList = () => {

  const [articlePlans, setArticlePlans] = useState([]);
  const [plans, setPlans] = useState([]);
  const [articles, setArticles] = useState([]);

  // ===== FORM =====
  const [idPlan, setIdPlan] = useState("");
  const [idScientificArticles, setIdScientificArticles] = useState("");
  const [articleComments, setArticleComments] = useState("");
  const [publicationLink, setPublicationLink] = useState("");

  // EDIT
  const [editId, setEditId] = useState(null);

  // FILTER
  const [filterPlanId, setFilterPlanId] = useState("");

  useEffect(() => {
    loadAll();
    loadPlans();
    loadArticles();
  }, []);

  /* ================= LOAD ================= */

  const loadAll = () => {
    ArticlePlanService.getAll()
      .then(res => setArticlePlans(res.data))
      .catch(() => alert("Failed to load article plans"));
  };

  const loadPlans = () => {
    PlanService.getAll()
      .then(res => setPlans(res.data))
      .catch(() => alert("Failed to load plans"));
  };

  const loadArticles = () => {
    ScientificArticlesService.getAll()
      .then(res => setArticles(res.data))
      .catch(() => alert("Failed to load articles"));
  };

  /* ================= CREATE ================= */

  const addArticlePlan = () => {
    if (!idPlan || !idScientificArticles) {
      alert("Plan and article are required");
      return;
    }

    ArticlePlanService.create({
      idPlan: Number(idPlan),
      idScientificArticles: Number(idScientificArticles),
      articleComments,
      publicationLink
    })
      .then(() => {
        clearForm();
        loadAll();
      })
      .catch(err => alert(err.response?.data || "Create failed"));
  };

  /* ================= UPDATE ================= */

  const startEdit = (ap) => {
    setEditId(ap.idArticlePlan);
    setIdPlan(ap.idPlan);
    setIdScientificArticles(ap.idScientificArticles);
    setArticleComments(ap.articleComments);
    setPublicationLink(ap.publicationLink);
  };

  const saveEdit = () => {
    ArticlePlanService.update(editId, {
      idArticlePlan: editId,
      idPlan: Number(idPlan),
      idScientificArticles: Number(idScientificArticles),
      articleComments,
      publicationLink
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

  const deleteArticlePlan = (id) => {
    if (!window.confirm("Delete article plan?")) return;

    ArticlePlanService.delete(id)
      .then(loadAll)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  /* ================= FILTER ================= */

  const filterByPlan = () => {
    if (!filterPlanId) {
      loadAll();
      return;
    }

    ArticlePlanService.filterByPlan(Number(filterPlanId))
      .then(res => setArticlePlans(res.data))
      .catch(() => alert("No records found"));
  };

  /* ================= UTILS ================= */

  const clearForm = () => {
    setIdPlan("");
    setIdScientificArticles("");
    setArticleComments("");
    setPublicationLink("");
  };

  /* ================= RENDER ================= */

  return (
    <div>
      <h2>Article Plans</h2>

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

        <select
          value={idScientificArticles}
          onChange={e => setIdScientificArticles(e.target.value)}
        >
          <option value="">Select article</option>
          {articles.map(a => (
            <option key={a.idScientificArticles} value={a.idArticle}>
              {a.name}idArticle
            </option>
          ))}
        </select>

        <input
          placeholder="Article comments"
          value={articleComments}
          onChange={e => setArticleComments(e.target.value)}
        />

        <input
          placeholder="Publication link"
          value={publicationLink}
          onChange={e => setPublicationLink(e.target.value)}
        />

        {editId ? (
          <>
            <button onClick={saveEdit}>Save</button>
            <button onClick={cancelEdit}>Cancel</button>
          </>
        ) : (
          <button onClick={addArticlePlan}>Add</button>
        )}
      </div>

      {/* FILTER */}
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

      {/* LIST */}
      <ul>
        {articlePlans.map(ap => (
          <li key={ap.idArticlePlan}>
            Plan {ap.idPlan} | Article {ap.idScientificArticles} |
            Comments: {ap.articleComments || "-"} |
            Link:{" "}{ap.publicationLink ? (
                  <a href={ap.publicationLink} target="_blank" rel="noopener noreferrer">
                    {ap.publicationLink}
                  </a>) : ("-")}
            <button onClick={() => startEdit(ap)}>Update</button>
            <button onClick={() => deleteArticlePlan(ap.idArticlePlan)}>
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ArticlePlanList;
