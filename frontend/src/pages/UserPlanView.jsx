import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import UserPlanService from "../services/UserPlanService";
import api from "../api/api";


export default function UserPlanView() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [plan, setPlan] = useState(null);
  const [years, setYears] = useState([]);

  useEffect(() => {
    UserPlanService.getPlanView(id).then(res => setPlan(res.data));
    api.get(`/year`).then(res => setYears(res.data));
  }, [id]);

  if (!plan) return <p>Loading...</p>;

  const year = years.find(y => y.idYear === plan.idYear);

  return (
    <div style={{ maxWidth: 1100, margin: "auto" }}>
      <h2>Plan Details</h2>

      <p><strong>Year:</strong> {year?.yearNumber}</p>

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

      <button onClick={() => navigate("/user/plans")}>
        ← Back to Plans
      </button>
    </div>
  );
}
