import React from "react";
import DepartmentService from "../services/DepartmentService";

const DepartmentList = ({ departments, onDelete }) => {

  const deleteDepartment = (id) => {
    DepartmentService.delete(id)
      .then(() => onDelete());
  };

  return (
    <ul>
      {departments.map(dep => (
        <li key={dep.id}>
          {dep.name}
          <button onClick={() => deleteDepartment(dep.id)}>Delete</button>
        </li>
      ))}
    </ul>
  );
};

export default DepartmentList;
