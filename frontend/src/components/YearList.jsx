import React, { useEffect, useState } from "react";
import YearService from "../services/YearService";

const YearList = () => {
  const [years, setYears] = useState([]);

  // ADD
  const [yearNumber, setYearNumber] = useState("");

  // UPDATE
  const [editId, setEditId] = useState(null);
  const [editYearNumber, setEditYearNumber] = useState("");

  useEffect(() => {
    loadYears();
  }, []);

  const loadYears = () => {
    YearService.getAll()
      .then(res => setYears(res.data))
      .catch(err => alert(err.response?.data || "Failed to load years"));
  };

  // CREATE
  const addYear = () => {
    if (!yearNumber) {
      alert("Year number is required");
      return;
    }

    YearService.create({ yearNumber: Number(yearNumber) })
      .then(() => {
        setYearNumber("");
        loadYears();
      })
      .catch(err => alert(err.response?.data || "Create failed"));
  };

  // DELETE
  const deleteYear = (id) => {
    YearService.delete(id)
      .then(loadYears)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  // UPDATE
  const startEdit = (y) => {
    setEditId(y.idYear);
    setEditYearNumber(y.yearNumber);
  };

  const cancelEdit = () => {
    setEditId(null);
    setEditYearNumber("");
  };

  const saveEdit = () => {
    if (!editYearNumber) {
      alert("Year number is required");
      return;
    }

    YearService.update(editId, { yearNumber: Number(editYearNumber) })
      .then(() => {
        cancelEdit();
        loadYears();
      })
      .catch(err => alert(err.response?.data || "Update failed"));
  };

  return (
    <div>
      <h2>Years</h2>

      {/* ➕ ADD YEAR */}
      <div style={{ marginBottom: "15px" }}>
        <input
          type="number"
          placeholder="Year number"
          value={yearNumber}
          onChange={e => setYearNumber(e.target.value)}
        />
        <button onClick={addYear}>Add</button>
      </div>

      {/* 📄 LIST */}
      <ul>
        {years.map(y => (
          <li key={y.idYear}>
            {editId === y.idYear ? (
              <>
                <input
                  type="number"
                  value={editYearNumber}
                  onChange={e => setEditYearNumber(e.target.value)}
                />
                <button onClick={saveEdit}>Save</button>
                <button onClick={cancelEdit}>Cancel</button>
              </>
            ) : (
              <>
                {y.yearNumber}
                <button onClick={() => startEdit(y)}>Update</button>
                <button onClick={() => deleteYear(y.idYear)}>Delete</button>
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default YearList;
