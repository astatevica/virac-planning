import React, { useEffect, useState } from "react";
import DepartmentService from "../services/DepartmentService";

const DepartmentList = () => {
  const [departments, setDepartments] = useState([]);

  // ADD
  const [newName, setNewName] = useState("");
  const [newHeadName, setNewHeadName] = useState("");
  const [newHeadSurname, setNewHeadSurname] = useState("");

  // UPDATE
  const [editId, setEditId] = useState(null);
  const [editName, setEditName] = useState("");
  const [editHeadName, setEditHeadName] = useState("");
  const [editHeadSurname, setEditHeadSurname] = useState("");

  useEffect(() => {
    loadDepartments();
  }, []);

  const loadDepartments = () => {
    DepartmentService.getAll()
      .then(res => setDepartments(res.data));
  };

  // CREATE
  const addDepartment = () => {
    if (!newName.trim()) {
      alert("Department name cannot be empty");
      return;
    }

    DepartmentService.create({ 
      name: newName,
      headName: newHeadName,
      headSurname: newHeadSurname 
    })
      .then(() => {
        setNewName("");
        setNewHeadName("");
        setNewHeadSurname("");
        loadDepartments();
      })
      .catch(err => alert(err.response?.data || "Add failed"));
  };

  // DELETE
  const deleteDepartment = (id) => {
    DepartmentService.delete(id)
      .then(loadDepartments)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  // UPDATE
  const startEdit = (dep) => {
    setEditId(dep.id);
    setEditName(dep.name);
    setEditHeadName(dep.headName);
    setEditHeadSurname(dep.headSurname);
  };

  const cancelEdit = () => {
    setEditId(null);
    setEditName("");
    setEditHeadName("");
    setEditHeadSurname("");
  };

  const saveEdit = () => {
    if (!editName.trim()) {
      alert("Name cannot be empty");
      return;
    }

    DepartmentService.update(editId, { name: editName, headName: editHeadName,
      headSurname: editHeadSurname  })
      .then(() => {
        cancelEdit();
        loadDepartments();
      })
      .catch(err => alert(err.response?.data || "Update failed"));
  };

  return (
    <div>
      <h2>Departments</h2>

      {/* ADD DEPARTMENT */}
      <div style={{ marginBottom: "15px" }}>
        <input
          placeholder="New department name"
          value={newName}
          onChange={e => setNewName(e.target.value)}
        />
        <input
          placeholder="New head name"
          value={newHeadName}
          onChange={e => setNewHeadName(e.target.value)}
        />
        <input
          placeholder="New head surname"
          value={newHeadSurname}
          onChange={e => setNewHeadSurname(e.target.value)}
        />
        <button onClick={addDepartment}>Add</button>
      </div>

      {/* LIST */}
      <ul>
        {departments.map(dep => (
          <li key={dep.id}>
            {editId === dep.id ? (
              <>
                <input
                  value={editName}
                  onChange={e => setEditName(e.target.value)}
                />
                <input
                  value={editHeadName}
                  onChange={e => setEditHeadName(e.target.value)}
                />
                <input
                  value={editHeadSurname}
                  onChange={e => setEditHeadSurname(e.target.value)}
                />
                <button onClick={saveEdit}>Save</button>
                <button onClick={cancelEdit}>Cancel</button>
              </>
            ) : (
              <>
                {dep.name} |{" "}
                {dep.headName || "Add: NAME |"}{" "}  
                {dep.headSurname || "Add: SURNAME"} {" "}
                <button onClick={() => startEdit(dep)}>Update</button>
                <button onClick={() => deleteDepartment(dep.id)}>Delete</button>
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default DepartmentList;
