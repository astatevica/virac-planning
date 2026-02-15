import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import UserPlanService from "../services/UserPlanService";
import api from "../api/api";

export default function UserDashboard() {

  const navigate = useNavigate();
  const [plans, setPlans] = useState([]);
  const [currentYearId, setCurrentYearId] = useState(null);
  const [years, setYears] = useState([]);
  const currentYear = new Date().getFullYear(); // 2026

  useEffect(() => {
    const loadCurrentYearPlans = async () => {
        try {
        const yearsRes = await api.get("/user/filter/years"); // instead of /admin/year
        setYears(yearsRes.data);

        const year = yearsRes.data.find(y => y.yearNumber === currentYear);

        if (!year) {
            alert(`Year ${currentYear} not found`);
            return;
        }

        const yearId = year.idYear;
        setCurrentYearId(yearId);

        const plansRes = await UserPlanService.getByYear(yearId);
        setPlans(plansRes.data);

        } catch (err) {
        alert("Failed loading dashboard");
        }
    };

    loadCurrentYearPlans();
    }, [currentYear]); // ✅ add currentYear here


  return (
    <div>
      <h2>User Dashboard</h2>
      <h3>Current Year Plans ({currentYear}) {currentYearId && `(ID: ${currentYearId})`}</h3>

      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>ID</th>
            <th>Projects</th>
            <th>Articles</th>
            <th>Courses</th>
            <th>Student Work</th>
          </tr>
        </thead>
        <tbody>
          {plans.length === 0 ? (
            <tr>
              <td colSpan="5">No plans for current year</td>
            </tr>
          ) : (
            plans.map(pl => (
              <tr key={pl.idPlan}>
                <td>{pl.idPlan}</td>
                <td>{pl.numOfProjects}</td>
                <td>{pl.numOfArticles}</td>
                <td>{pl.numOfCourses}</td>
                <td>{pl.numOfStudWork}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>

      <br />
      <button onClick={() => navigate("/user/plans")}>
        View All Plans
      </button>
    </div>
  );
}
