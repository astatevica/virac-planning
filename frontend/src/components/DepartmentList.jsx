import React, { useEffect, useState } from "react";
import DepartmentService from "../services/DepartmentService";

const DepartmentList = () => {
  const [departments, setDepartments] = useState([]);

  useEffect(() => {
    loadDepartments();
  }, []);

  const loadDepartments = () => {
    DepartmentService.getAll()
      .then(res => setDepartments(res.data));
  };

  const deleteDepartment = (id) => {
    DepartmentService.delete(id)
      .then(() => loadDepartments())
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  return (
    <div>
      <h2>Departments</h2>
      <ul>
        {departments.map(dep => {
          console.log("DEP OBJECT:", dep);

          return (
            <li key={dep.id}>
              {dep.name}
              <button onClick={() => deleteDepartment(dep.id)}>
                Delete
              </button>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default DepartmentList;
