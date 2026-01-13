import React, { useEffect, useState } from "react";
import ScientificArticlesService from "../services/ScientificArticlesService";
import JournalService from "../services/JournalService";

const ScientificArticlesList = () => {
  const [articles, setArticles] = useState([]);
  const [journals, setJournals] = useState([]);

  // ADD
  const [newName, setNewName] = useState("");
  const [newCoAuthors, setNewCoAuthors] = useState("");
  const [newJournalId, setNewJournalId] = useState("");

  // UPDATE
  const [editId, setEditId] = useState(null);
  const [editName, setEditName] = useState("");
  const [editCoAuthors, setEditCoAuthors] = useState("");
  const [editJournalId, setEditJournalId] = useState("");

  // FILTER
  const [filterCoAuthor, setFilterCoAuthor] = useState("");

  useEffect(() => {
    loadArticles();
    loadJournals();
  }, []);

  const loadArticles = () => {
    ScientificArticlesService.getAll()
      .then(res => setArticles(res.data))
      .catch(err => alert(err.response?.data || "Load failed"));
  };

  const loadJournals = () => {
    JournalService.getAll()
      .then(res => setJournals(res.data));
  };

  // CREATE
  const addArticle = () => {
    if (!newName || !newJournalId) {
      alert("Name and Journal are required");
      return;
    }

    ScientificArticlesService.create({
      name: newName,
      coAuthors: newCoAuthors,
      idJournal: newJournalId
    })
      .then(() => {
        setNewName("");
        setNewCoAuthors("");
        setNewJournalId("");
        loadArticles();
      })
      .catch(err => alert(err.response?.data || "Add failed"));
  };

  // DELETE
  const deleteArticle = (id) => {
    ScientificArticlesService.delete(id)
      .then(loadArticles)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  // UPDATE
  const startEdit = (art) => {
    setEditId(art.idArticle);
    setEditName(art.name);
    setEditCoAuthors(art.coAuthors);
    setEditJournalId(art.idJournal);
  };

  const cancelEdit = () => {
    setEditId(null);
    setEditName("");
    setEditCoAuthors("");
    setEditJournalId("");
  };

  const saveEdit = () => {
    ScientificArticlesService.update(editId, {
      name: editName,
      coAuthors: editCoAuthors,
      idJournal: editJournalId
    })
      .then(() => {
        cancelEdit();
        loadArticles();
      })
      .catch(err => alert(err.response?.data || "Update failed"));
  };

  // FILTER
  const filterByCoAuthor = () => {
    if (!filterCoAuthor) {
      loadArticles();
      return;
    }

    ScientificArticlesService.filterByCoAuthor(filterCoAuthor)
      .then(res => setArticles(res.data))
      .catch(() => alert("No articles found"));
  };

  return (
    <div>
      <h2>Scientific Articles</h2>

      {/* 🔍 FILTER */}
      <div style={{ marginBottom: "15px" }}>
        <input
          placeholder="Filter by co-author"
          value={filterCoAuthor}
          onChange={e => setFilterCoAuthor(e.target.value)}
        />
        <button onClick={filterByCoAuthor}>Filter</button>
        <button onClick={loadArticles}>Clear</button>
      </div>

      {/* ➕ ADD */}
      <div style={{ marginBottom: "15px" }}>
        <input
          placeholder="Article name"
          value={newName}
          onChange={e => setNewName(e.target.value)}
        />
        <input
          placeholder="Co-authors"
          value={newCoAuthors}
          onChange={e => setNewCoAuthors(e.target.value)}
        />
        <select
          value={newJournalId}
          onChange={e => setNewJournalId(e.target.value)}
        >
          <option value="">Select journal</option>
          {journals.map(j => (
            <option key={j.idJournal} value={j.idJournal}>
              {j.name}
            </option>
          ))}
        </select>
        <button onClick={addArticle}>Add</button>
      </div>

      {/* 📄 TABLE */}
      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Co-authors</th>
            <th>Journal</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {articles.map(art => (
            <tr key={art.idArticle}>
              {editId === art.idArticle ? (
                <>
                  <td>{art.idArticle}</td>
                  <td>
                    <input
                      value={editName}
                      onChange={e => setEditName(e.target.value)}
                    />
                  </td>
                  <td>
                    <input
                      value={editCoAuthors}
                      onChange={e => setEditCoAuthors(e.target.value)}
                    />
                  </td>
                  <td>
                    <select
                      value={editJournalId}
                      onChange={e => setEditJournalId(e.target.value)}
                    >
                      {journals.map(j => (
                        <option key={j.idJournal} value={j.idJournal}>
                          {j.name}
                        </option>
                      ))}
                    </select>
                  </td>
                  <td>
                    <button onClick={saveEdit}>Save</button>
                    <button onClick={cancelEdit}>Cancel</button>
                  </td>
                </>
              ) : (
                <>
                  <td>{art.idArticle}</td>
                  <td>{art.name}</td>
                  <td>{art.coAuthors}</td>
                  <td>{art.idJournal}</td>
                  <td>
                    <button onClick={() => startEdit(art)}>Edit</button>
                    <button onClick={() => deleteArticle(art.idArticle)}>
                      Delete
                    </button>
                  </td>
                </>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ScientificArticlesList;
