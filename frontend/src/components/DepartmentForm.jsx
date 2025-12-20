import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import DepartmentService from "../services/DepartmentService";

const DepartmentForm = () => {
  const [name, setName] = useState("");
  const navigate = useNavigate();

  const submit = (e) => {
    e.preventDefault();

    DepartmentService.create({ name })
      .then(() => navigate("/departments"))
      .catch(err => alert(err.response?.data || "Error"));
  };

  return (
    <div>
      <h2>Add Department</h2>
      <form onSubmit={submit}>
        <input
          value={name}
          onChange={e => setName(e.target.value)}
          placeholder="Department name"
        />
        <button type="submit">Add</button>
      </form>
    </div>
  );
};

export default DepartmentForm;
