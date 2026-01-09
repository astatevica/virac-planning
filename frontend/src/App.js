import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navigation from "./components/Navigation";
import DepartmentList from "./components/DepartmentList";
import EmployeeList from "./components/EmployeeList";
import ProjectManagementList from "./components/ProjectManagementList";
import ProjectList from "./components/ProjectList";

function App() {
  return (
    <BrowserRouter>
      <Navigation />

      <div style={{ padding: "20px" }}>
        <Routes>
          <Route path="/" element={<h2>Welcome to VIRAC</h2>} />
          <Route path="/departments" element={<DepartmentList />} />
          <Route path="/employee" element={<EmployeeList />} />
          <Route path="/project-management" element={<ProjectManagementList />} />
          <Route path="/project" element={<ProjectList />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
