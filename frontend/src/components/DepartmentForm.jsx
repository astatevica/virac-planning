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
      .catch(err => {
        if (Array.isArray(err.response?.data)) {
            alert(err.response.data[0].defaultMessage);
        } else {
            alert(err.response?.data || "Error occurred");
        }
});

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