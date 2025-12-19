import React, { useState } from "react";
import DepartmentService from "../services/DepartmentService";

const DepartmentForm = ({ onCreated }) => {
  const [name, setName] = useState("");

  const submit = (e) => {
    e.preventDefault();

    DepartmentService.create({ name })
      .then(() => {
        setName("");
        onCreated();
      })
      .catch(err => alert(err.response.data));
  };

  return (
    <form onSubmit={submit}>
      <input
        type="text"
        placeholder="Department name"
        value={name}
        onChange={e => setName(e.target.value)}
      />
      <button type="submit">Add</button>
    </form>
  );
};

export default DepartmentForm;
