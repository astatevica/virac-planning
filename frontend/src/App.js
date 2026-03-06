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
import Home from "./pages/Home";
import Admin from "./pages/Admin";
import UserDashboard from "./pages/UserDashboard";
import UserAllPlans from "./pages/UserAllPlans";
import UserPlanView from "./pages/UserPlanView";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Navigation />

        <div style={{ padding: "20px" }}>
          <Routes>

            {/*PUBLIC ROUTES*/}
            <Route path="/login" element={<Login />} />

            {/*Authenticated-only route (any role) */}
            <Route path="/home" element={<ProtectedRoute><Home/></ProtectedRoute>}/>

            {/*PROTECTED ROUTES*/}
            <Route element={<ProtectedRoute allowedRoles={["ADMIN"]} />}>
              <Route path="/admin/dashboard" element={<Admin />} />
              <Route path="/admin/departments" element={<DepartmentList />} />
              <Route path="/admin/employee" element={<EmployeeList />} />
              <Route path="/admin/project-management" element={<ProjectManagementList />} />
              <Route path="/admin/project" element={<ProjectList />} />
              <Route path="/admin/project-plan" element={<ProjectPlanList />} />
              <Route path="/admin/student-work" element={<StudentWorkList />} />
              <Route path="/admin/work-plan" element={<WorkPlanList />} />
              <Route path="/admin/journal" element={<JournalList />} />
              <Route path="/admin/scientific-articles" element={<ScientificArticlesList />} />
              <Route path="/admin/article-plan" element={<ArticlePlanList />} />
              <Route path="/admin/course" element={<CourseList />} />
              <Route path="/admin/course-plan" element={<CoursePlanList />} />
              <Route path="/admin/year" element={<YearList />} />
              <Route path="/admin/plan" element={<PlanList />} />
              <Route path="/admin/plans/:id" element={<PlanView />} />
            </Route>

            {/*PROTECTED ROUTES*/}
            <Route element={<ProtectedRoute allowedRoles={["USER"]} />}>
              <Route path="/user/dashboard" element={<UserDashboard />} />
              <Route path="/user/plans" element={<UserAllPlans />} />
              <Route path="/user/full-plan/:id" element={<UserPlanView />} />
            </Route>


          </Routes>
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
