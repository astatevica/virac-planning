import React, { useEffect, useState } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import PlanService from "../services/PlanService";
import api from "../api/api";
import { useAuth } from "../auth/AuthContext";

const API = "/admin";

export default function PlanView() {
  const { id } = useParams();
  const navigate = useNavigate();
  const location = useLocation();
  const { role } = useAuth();

  const [plan, setPlan] = useState(null);
  const [employees, setEmployees] = useState([]);
  const [years, setYears] = useState([]);
  const [loadError, setLoadError] = useState("");

  useEffect(() => {
    const loadPlanView = async () => {
      try {
        const planRes = await PlanService.getById(id);
        setPlan(planRes.data);
        setLoadError("");
      } catch {
        setLoadError("Failed to load plan.");
      }

      try {
        const yearsRes = await api.get("/year/all");
        setYears(yearsRes.data);
      } catch {
        setYears([]);
      }

      if (role === "ADMIN") {
        try {
          const employeesRes = await api.get(`${API}/employee/all`);
          setEmployees(employeesRes.data);
        } catch {
          setEmployees([]);
        }
      } else if (location.state?.employee) {
        setEmployees([location.state.employee]);
      } else {
        setEmployees([]);
      }
    };

    loadPlanView();
  }, [id, location.state, role]);

  if (loadError) return <p>{loadError}</p>;
  if (!plan) return <p>Loading...</p>;

  const employee = employees.find((entry) => entry.id === plan.idEmployee);
  const year = years.find((entry) => entry.idYear === plan.idYear);
  const fallbackEmployeeName =
    location.state?.employeeName || `Employee ID: ${plan.idEmployee}`;
  const fallbackYearNumber = location.state?.yearNumber || plan.idYear;

  return (
    <div style={{ maxWidth: 1100, margin: "auto" }}>
      <h2>Plan Details</h2>

      <p>
        <strong>Employee:</strong>{" "}
        {employee ? `${employee.name} ${employee.surname}` : fallbackEmployeeName}
      </p>
      <p>
        <strong>Year:</strong> {year?.yearNumber || fallbackYearNumber}
      </p>

      <hr />

      <h3>Quantitative Indicators</h3>
      <ul>
        <li>Projects: {plan.numOfProjects}</li>
        <li>Articles: {plan.numOfArticles}</li>
        <li>Courses: {plan.numOfCourses}</li>
        <li>Student Works: {plan.numOfStudWork}</li>
      </ul>

      <hr />

      <h3>Planned vs Completed</h3>
      <table border="1" width="100%" cellPadding="8">
        <thead>
          <tr>
            <th>Activity</th>
            <th>Planned</th>
            <th>Completed</th>
          </tr>
        </thead>
        <tbody>
          <tr><td>Conference Participation</td><td>{plan.partInConf}</td><td>{plan.partInConfEnd}</td></tr>
          <tr><td>Conference Abstracts</td><td>{plan.comAbConf}</td><td>{plan.comAbConfEnd}</td></tr>
          <tr><td>Research Promotion</td><td>{plan.promoOfResearch}</td><td>{plan.promoOfResearchEnd}</td></tr>
          <tr><td>Project Applications</td><td>{plan.projApplicSub}</td><td>{plan.projApplicSubEnd}</td></tr>
          <tr><td>Skill Development</td><td>{plan.skillsDevelopment}</td><td>{plan.skillsDevelopmentEnd}</td></tr>
          <tr><td>Seminars</td><td>{plan.participationInSeminars}</td><td>{plan.participationInSeminarsEnd}</td></tr>
          <tr><td>Administrative Work</td><td>{plan.adminWork}</td><td>{plan.adminWorkEnd}</td></tr>
          <tr><td>Other Duties</td><td>{plan.otherJobs}</td><td>{plan.otherJobsEnd}</td></tr>
        </tbody>
      </table>

      <br />

      <button
        onClick={() =>
          navigate(role === "USER_DEPART" ? "/admin/dashboard" : "/admin/plan")
        }
      >
        Back
      </button>
    </div>
  );
}
