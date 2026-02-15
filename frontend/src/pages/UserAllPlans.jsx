import React, { useEffect, useState } from "react";
import UserPlanService from "../services/UserPlanService";
import api from "../api/api";

export default function UserAllPlans() {

  const [plans, setPlans] = useState([]);
  const [years, setYears] = useState([]);
  const [projects, setProjects] = useState([]);

  const [selectedYear, setSelectedYear] = useState("");
  const [selectedProject, setSelectedProject] = useState("");

  useEffect(() => {
    loadAll();
    loadFilters();
  }, []);

  const loadAll = () => {
    UserPlanService.getAll()
      .then(res => setPlans(res.data))
      .catch(() => alert("Failed loading plans"));
  };

  const loadFilters = async () => {
    const [yearRes, projectRes] = await Promise.all([
      api.get("/year"),
      api.get("/project")
    ]);

    setYears(yearRes.data);
    setProjects(projectRes.data);
  };

  useEffect(() => {

    if (selectedYear) {
      UserPlanService.getByYear(selectedYear)
        .then(res => setPlans(res.data));
    }
    else if (selectedProject) {
      UserPlanService.getByProject(selectedProject)
        .then(res => setPlans(res.data));
    }
    else {
      loadAll();
    }

  }, [selectedYear, selectedProject]);

  return (
    <div>
      <h2>All My Plans</h2>

      <h3>Filters</h3>

      <label>Year:</label>
      <select value={selectedYear} onChange={e => {
        setSelectedProject("");
        setSelectedYear(e.target.value);
      }}>
        <option value="">All</option>
        {years.map(y => (
          <option key={y.idYear} value={y.idYear}>
            {y.yearNumber}
          </option>
        ))}
      </select>

      <label style={{ marginLeft: 10 }}>Project:</label>
      <select value={selectedProject} onChange={e => {
        setSelectedYear("");
        setSelectedProject(e.target.value);
      }}>
        <option value="">All</option>
        {projects.map(p => (
          <option key={p.idProject} value={p.idProject}>
            {p.title}
          </option>
        ))}
      </select>

      <button
        onClick={() => {
          setSelectedYear("");
          setSelectedProject("");
          loadAll();
        }}
        style={{ marginLeft: 10 }}
      >
        Clear
      </button>

      <hr />

      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>ID</th>
            <th>Year</th>
            <th>Projects</th>
            <th>Articles</th>
            <th>Courses</th>
          </tr>
        </thead>
        <tbody>
          {plans.map(pl => (
            <tr key={pl.idPlan}>
              <td>{pl.idPlan}</td>
              <td>{pl.idYear}</td>
              <td>{pl.numOfProjects}</td>
              <td>{pl.numOfArticles}</td>
              <td>{pl.numOfCourses}</td>
            </tr>
          ))}
        </tbody>
      </table>

    </div>
  );
}
