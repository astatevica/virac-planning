import React, { useState } from "react";
import DepartmentService from "../services/DepartmentService";

const DepartmentForm = ({ onCreated }) => {
  const [name, setName] = useState("");

  const submit = (e) => {
    e.preventDefault();

    if (!name.trim()) return;

    DepartmentService.create({ name })
      .then(() => {
        setName("");
        onCreated();
      })
      .catch(err => alert(err.response?.data || "Error"));
  };

  return (
    <form onSubmit={submit}>
      <input
        value={name}
        onChange={e => setName(e.target.value)}
        placeholder="Department name"
      />
      <button type="submit">Add</button>
    </form>
  );
};

export default DepartmentForm;
