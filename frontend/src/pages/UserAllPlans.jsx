import React, { useEffect, useState } from "react";
import UserPlanService from "../services/UserPlanService";
import api from "../api/api";
import { useNavigate } from "react-router-dom";

const buttonStyle = {
  background: "#0d6efd",
  color: "#fff",
  border: "1px solid #0d6efd",
  borderRadius: "4px",
  padding: "6px 12px",
  cursor: "pointer",
};

export default function UserAllPlans() {

  const navigate = useNavigate();

  const [plans, setPlans] = useState([]);
  const [years, setYears] = useState([]);

  const [selectedYear, setSelectedYear] = useState("");
  const [selectedProject, setSelectedProject] = useState("");
  const [planProjects, setPlanProjects] = useState({});
  const [projects, setProjects] = useState([]);

  const [filterError, setFilterError] = useState("");
  const [noResults, setNoResults] = useState(false);

  useEffect(() => {
    loadPlans();
    loadFilters();
    loadProjects();
  }, []);

  const loadPlans = async () => {
    try {
      const res = await UserPlanService.getAll();
      setPlans(res.data);

      // ielādē projektus katram plānam
      const projectData = {};

      for (let plan of res.data) {
        const projectRes = await UserPlanService.getByPlan(plan.idPlan);
        projectData[plan.idPlan] = projectRes.data;
      }

      setPlanProjects(projectData);

    } catch (err) {
      console.error(err);
    }
  };

  const loadProjects = async () => {
    const res = await api.get("/user/all/projects");
    setProjects(res.data);
  };

  const loadFilters = async () => {
    try {
      const [yearRes] = await Promise.all([
        api.get("/year/all")
      ]);

      setYears(yearRes.data);

      if (yearRes.data.length === 0 ) {
        setFilterError("No filter data available.");
      } else {
        setFilterError("");
      }

    } catch (err) {
      setFilterError("Failed to load filter data.");
    }
  };

  // Year number map (to show yearNumber instead of id)
  const yearMap = Object.fromEntries(
    years.map(y => [y.idYear, y.yearNumber])
  );

  const projectMap = Object.fromEntries(
    projects.map(p => [p.idProject.toString(), p.name])
  );

  // Filter logic
  useEffect(() => {

  let request;

  if (selectedYear) {
    request = UserPlanService.getByYear(selectedYear);
  } 
  else if (selectedProject) {
    request = UserPlanService.getByProject(selectedProject);
  } 
  else {
    loadPlans();
    return;
  }

  request
    .then(async res => {
      setPlans(res.data);
      setNoResults(res.data.length === 0);

      // ⚠ svarīgi: pārlādē arī planProjects
      const projectData = {};

      for (let plan of res.data) {
        const projectRes = await UserPlanService.getByPlan(plan.idPlan);
        projectData[plan.idPlan] = projectRes.data;
      }

      setPlanProjects(projectData);

    })
    .catch(() => {
      setPlans([]);
      setNoResults(true);
    });

}, [selectedYear, selectedProject]);

  return (
    <div>

      <h2>My Plans</h2>

      {filterError && (
        <p style={{ color: "red" }}>{filterError}</p>
      )}

      <h3>Filters</h3>

      <div style={{ marginBottom: 16 }}>
        <label>Year:</label>{" "}
        <select
          value={selectedYear}
          onChange={e => {
            setSelectedProject("");
            setSelectedYear(e.target.value);
          }}
        >
          <option value="">All</option>
          {years.map(y => (
            <option key={y.idYear} value={y.idYear}>
              {y.yearNumber}
            </option>
          ))}
        </select>

        <label style={{ marginLeft: 15 }}>Project:</label>{" "}
        <select
          value={selectedProject}
          onChange={e => {
            setSelectedYear("");
            setSelectedProject(e.target.value);
          }}
        >
          <option value="">All</option>
          {projects.map(p => (
            <option key={p.idProject} value={p.idProject}>
              {p.name}
            </option>
          ))}
        </select>

        <button
          onClick={() => {
            setSelectedYear("");
            setSelectedProject("");
            loadPlans();
          }}
          style={{ ...buttonStyle, marginLeft: 10 }}
        >
          Clear
        </button>
      </div>

      {noResults && (
        <p style={{ color: "orange", fontWeight: "bold" }}>
          No plans match selected filters.
        </p>
      )}

      <table
        style={{
          width: "100%",
          borderCollapse: "collapse",
          border: "1px solid #aeb6bf",
          background: "#fff"
        }}
      >
        <thead>
          <tr>
            <th style={{ textAlign: "left", padding: "10px 12px", background: "#f6f8fa" }}>ID</th>
            <th style={{ textAlign: "left", padding: "10px 12px", background: "#f6f8fa" }}>Year</th>
            <th style={{ textAlign: "left", padding: "10px 12px", background: "#f6f8fa" }}>Projects count</th>
            <th style={{ textAlign: "left", padding: "10px 12px", background: "#f6f8fa" }}>Project name</th>
            <th style={{ textAlign: "left", padding: "10px 12px", background: "#f6f8fa" }}>Articles</th>
            <th style={{ textAlign: "left", padding: "10px 12px", background: "#f6f8fa" }}>Courses</th>
            <th style={{ textAlign: "left", padding: "10px 12px", background: "#f6f8fa" }}>Student Work</th>
            <th style={{ textAlign: "left", padding: "10px 12px", background: "#f6f8fa" }}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {plans.length === 0 ? (
            <tr>
              <td colSpan="8" style={{ padding: "12px" }}>
                No plans found
              </td>
            </tr>
          ) : (
            plans.map(pl => (
              <tr key={pl.idPlan}>
                <td style={{ padding: "10px 12px", border: "1px solid #b7c0c8" }}>{pl.idPlan}</td>
                <td style={{ padding: "10px 12px", border: "1px solid #b7c0c8" }}>{yearMap[pl.idYear] || pl.idYear}</td>
                <td style={{ padding: "10px 12px", border: "1px solid #b7c0c8" }}>{pl.numOfProjects}</td>
                <td style={{ padding: "10px 12px", border: "1px solid #b7c0c8" }}>
                  {planProjects[pl.idPlan]?.length > 0 ? (
                    planProjects[pl.idPlan].map(p => (
                      <div key={p.idProjectPlan}>
                        {projectMap[Number(p.idProject.toString())] || `Project ${p.idProject}`}
                      </div>
                    ))
                  ) : (
                    ""
                  )}
                </td>
                <td style={{ padding: "10px 12px", border: "1px solid #b7c0c8" }}>{pl.numOfArticles}</td>
                <td style={{ padding: "10px 12px", border: "1px solid #b7c0c8" }}>{pl.numOfCourses}</td>
                <td style={{ padding: "10px 12px", border: "1px solid #b7c0c8" }}>{pl.numOfStudWork}</td>
                <td style={{ padding: "10px 12px", border: "1px solid #b7c0c8" }}>
                  <button
                    style={buttonStyle}
                    onClick={() => navigate(`/user/full-plan/${pl.idPlan}`)}
                  >
                    Open
                  </button>
                </td>
              </tr>
            ))
          )} 
        </tbody>
      </table>

    </div>
  );
}
