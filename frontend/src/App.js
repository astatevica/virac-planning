import React, { useEffect, useState } from "react";
import DepartmentList from "./components/DepartmentList";
import DepartmentForm from "./components/DepartmentForm";
import DepartmentService from "./services/DepartmentService";

function App() {
  const [departments, setDepartments] = useState([]);

  const loadDepartments = () => {
    DepartmentService.getAll()
      .then(res => setDepartments(res.data))
      .catch(err => console.error(err));
  };

  useEffect(() => {
    loadDepartments();
  }, []);

  return (
    <div>
      <h1>Departments</h1>
      <DepartmentForm onCreated={loadDepartments} />
      <DepartmentList
        departments={departments}
        onDelete={loadDepartments}
      />
    </div>
  );
}

export default App;
