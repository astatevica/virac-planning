import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navigation from "./components/Navigation";
import DepartmentList from "./components/DepartmentList";
import EmployeeList from "./components/EmployeeList";
import ProjectManagementList from "./components/ProjectManagementList";
import ProjectList from "./components/ProjectList";
import StudentWorkList from "./components/StudentWorkList";
import JournalList from "./components/JournalList";
import ScientificArticlesList from "./components/ScientificArticlesList";

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
          {/* <Route path="/project-plan" element={<ProjectPlanList />} /> */}
          <Route path="/student-work" element={<StudentWorkList />} />
          {/* <Route path="/work-plan" element={<WorkPlanList />} /> */}
          <Route path="/journal" element={<JournalList />} />
          <Route path="/scientific-articles" element={<ScientificArticlesList />} />
          {/* <Route path="/article-plan" element={<ArticlePlanList />} /> */}
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
