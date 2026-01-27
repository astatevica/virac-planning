import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navigation from "./components/Navigation";

// Auth
import { AuthProvider } from "./auth/AuthContext";
import ProtectedRoute from "./auth/ProtectedRoute";
import Login from "./pages/Login";

// Pages
import DepartmentList from "./components/DepartmentList";
import EmployeeList from "./components/EmployeeList";
import ProjectManagementList from "./components/ProjectManagementList";
import ProjectList from "./components/ProjectList";
import StudentWorkList from "./components/StudentWorkList";
import JournalList from "./components/JournalList";
import ScientificArticlesList from "./components/ScientificArticlesList";
import CourseList from "./components/CourseList";
import YearList from "./components/YearList";
import PlanList from "./components/PlanList";
import ProjectPlanList from "./components/ProjectPlanList";
import ArticlePlanList from "./components/ArticlePlanList";
import CoursePlanList from "./components/CoursePlanList";
import WorkPlanList from "./components/WorkPlanList";
import PlanView from "./components/PlanView";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Navigation />

        <div style={{ padding: "20px" }}>
          <Routes>

            {/*PUBLIC ROUTES*/}
            <Route path="/login" element={<Login />} />

            {/*PROTECTED ROUTES*/}
            <Route element={<ProtectedRoute />}>
              <Route path="/" element={<h2>Welcome to VIRAC</h2>} />

              <Route path="/departments" element={<DepartmentList />} />
              <Route path="/employee" element={<EmployeeList />} />
              <Route path="/project-management" element={<ProjectManagementList />} />
              <Route path="/project" element={<ProjectList />} />
              <Route path="/project-plan" element={<ProjectPlanList />} />
              <Route path="/student-work" element={<StudentWorkList />} />
              <Route path="/work-plan" element={<WorkPlanList />} />
              <Route path="/journal" element={<JournalList />} />
              <Route path="/scientific-articles" element={<ScientificArticlesList />} />
              <Route path="/article-plan" element={<ArticlePlanList />} />
              <Route path="/course" element={<CourseList />} />
              <Route path="/course-plan" element={<CoursePlanList />} />
              <Route path="/year" element={<YearList />} />
              <Route path="/plan" element={<PlanList />} />
              <Route path="/plans/:id" element={<PlanView />} />
            </Route>

          </Routes>
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
