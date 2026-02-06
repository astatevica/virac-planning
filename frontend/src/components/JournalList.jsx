import React, { useEffect, useState } from "react";
import JournalService from "../services/JournalService";

const JournalList = () => {
  const [journals, setJournals] = useState([]);

  // ADD
  const [newName, setNewName] = useState("");

  // UPDATE
  const [editidJournal, setEditidJournal] = useState(null);
  const [editName, setEditName] = useState("");

  useEffect(() => {
    loadJournals();
  }, []);

  const loadJournals = () => {
    JournalService.getAll()
      .then(res => setJournals(res.data));
  };

  // CREATE
  const addJournal = () => {
    if (!newName.trim()) {
      alert("Journal name cannot be empty");
      return;
    }

    JournalService.create({ name: newName })
      .then(() => {
        setNewName("");
        loadJournals();
      })
      .catch(err => alert(err.response?.data || "Add failed"));
  };

  // DELETE
  const deleteJournals = (idJournal) => {
    JournalService.delete(idJournal)
      .then(loadJournals)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  // UPDATE
  const startEdit = (dep) => {
    setEditidJournal(dep.idJournal);
    setEditName(dep.name);
  };

  const cancelEdit = () => {
    setEditidJournal(null);
    setEditName("");
  };

  const saveEdit = () => {
    if (!editName.trim()) {
      alert("Name cannot be empty");
      return;
    }

    JournalService.update(editidJournal, { name: editName })
      .then(() => {
        cancelEdit();
        loadJournals();
      })
      .catch(err => alert(err.response?.data || "Update failed"));
  };

  return (
    <div>
      <h2>Journals</h2>

      <div style={{ marginBottom: "15px" }}>
        <input
          placeholder="New journal name"
          value={newName}
          onChange={e => setNewName(e.target.value)}
        />
        <button onClick={addJournal}>Add</button>
      </div>

      {/* 📄 LIST */}
      <ul>
        {journals.map(dep => (
          <li key={dep.idJournal}>
            {editidJournal === dep.idJournal ? (
              <>
                <input
                  value={editName}
                  onChange={e => setEditName(e.target.value)}
                />
                <button onClick={saveEdit}>Save</button>
                <button onClick={cancelEdit}>Cancel</button>
              </>
            ) : (
              <>
                {dep.name}
                <button onClick={() => startEdit(dep)}>Update</button>
                <button onClick={() => deleteJournals(dep.idJournal)}>Delete</button>
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default JournalList;
