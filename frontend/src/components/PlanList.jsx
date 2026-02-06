import React, { useEffect, useState } from "react";
import PlanService from "../services/PlanService";
import PlanForm from "./PlanForm";
import api from "../api/api";
import { useNavigate } from "react-router-dom";


const API = "/admin";

const PlanList = () => {
  const navigate = useNavigate();

  const [plans, setPlans] = useState([]);
  const [editingPlan, setEditingPlan] = useState(null);

  //Add filter state
  const [selectedEmployee, setSelectedEmployee] = useState("");
  const [selectedYear, setSelectedYear] = useState("");
  const [selectedDepartment, setSelectedDepartment] = useState("");
  const [filterError, setFilterError] = useState("");
  const [noResults, setNoResults] = useState(false);


  //Load employees & years for dropdowns
  const [employees, setEmployees] = useState([]);
  const [years, setYears] = useState([]);
  const [departments, setDepartments] = useState([]);

  useEffect(() => {
    loadPlans();
    loadFilters();
  }, []);

  const loadPlans = () => {
    PlanService.getAll()
      .then(res => setPlans(res.data))
      .catch(err => alert(err.response?.data || "Load failed"));
  };

  const deletePlan = (id) => {
    PlanService.delete(id)
      .then(loadPlans)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  // const loadFilters = () => {
  //   api.get(`${API}/employee`).then(res => setEmployees(res.data));
  //   api.get(`${API}/year`).then(res => setYears(res.data));
  //   api.get(`${API}/department`).then(res => setDepartments(res.data));
  // };

  const loadFilters = async () => {
    try {
      const [empRes, yearRes, depRes] = await Promise.all([
        api.get(`${API}/employee`),
        api.get(`${API}/year`),
        api.get(`${API}/department`)
      ]);

      setEmployees(empRes.data);
      setYears(yearRes.data);
      setDepartments(depRes.data);

      if (
        empRes.data.length === 0 &&
        yearRes.data.length === 0 &&
        depRes.data.length === 0
      ) {
        setFilterError("No filter data available.");
      } else {
        setFilterError("");
      }

    } catch (err) {
      setFilterError("Failed to load filter data.");
    }
  };

  //To retrieve names for table
  const employeeMap = Object.fromEntries(
    employees.map(e => [e.id, `${e.name} ${e.surname}`])
  );

  const yearMap = Object.fromEntries(
    years.map(y => [y.idYear, y.yearNumber])
  );

  //Trigger filtering automatically and filtering logic
  useEffect(() => {
    let request;

    if (selectedEmployee) {
      request = PlanService.getByEmployee(selectedEmployee);
    } else if (selectedYear) {
      request = PlanService.getByYear(selectedYear);
    } else if (selectedDepartment) {
      request = PlanService.getByDepartment(selectedDepartment);
    } else {
      loadPlans();
      return;
    }

    request
      .then(res => {
        setPlans(res.data);
        setNoResults(res.data.length === 0);
      })
      .catch(() => {
        setPlans([]);
        setNoResults(true);
      });

  }, [selectedEmployee, selectedYear, selectedDepartment]);

  return (
    <div>

      {filterError && (
        <p style={{ color: "red" }}>{filterError}</p>
      )}
      <h3>Filters</h3>

        <label>Employee:</label>
        <select value={selectedEmployee} onChange={e => setSelectedEmployee(e.target.value)}>
          <option value="">All</option>
          {employees.map(e => (
            <option key={e.idEmployee} value={e.id}>
              ID:{e.id} - {e.name} {e.surname}
            </option>
          ))}
        </select>

        <label style={{ marginLeft: 10 }}>Year:</label>
        <select value={selectedYear} onChange={e => setSelectedYear(e.target.value)}>
          <option value="">All</option>
          {years.map(y => (
            <option key={y.idYear} value={y.idYear}>
              {y.yearNumber}
            </option>
          ))}
        </select>
        
        <label style={{ marginLeft: 10 }}>Department:</label>
        <select value={selectedDepartment} onChange={e => setSelectedDepartment(e.target.value)}>
          <option value="">All</option>
          {departments.map(d => (
            <option key={d.idDepatrtment} value={d.idDepatrtment}>
              {d.name}
            </option>
          ))}
        </select>

        <button
          onClick={() => {
            setSelectedEmployee("");
            setSelectedYear("");
            setSelectedDepartment("");
            loadPlans();
          }}
          style={{ marginLeft: 10 }}
        >
          Clear
        </button>

      {noResults && (
        <p style={{ color: "orange", fontWeight: "bold" }}>
          No plans match the selected filters.
        </p>
      )}

      <h2>Plans</h2>

      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>ID</th>
            <th>Employee</th>
            <th>Year</th>
            <th>Projects</th>
            <th>Articles</th>
            <th>Courses</th>
            <th>Student Work</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {plans.length === 0 ? (
            <tr>
              <td colSpan="8" style={{ textAlign: "center", color: "gray" }}>
                No plans found for selected filters
              </td>
            </tr>
          ) : (
            plans.map(pl => (
              <tr key={pl.idPlan}>
                <td>{pl.idPlan}</td>
                <td>{employeeMap[pl.idEmployee] || pl.idEmployee}</td>
                <td>{yearMap[pl.idYear] || pl.idYear}</td>
                <td>{pl.numOfProjects}</td>
                <td>{pl.numOfArticles}</td>
                <td>{pl.numOfCourses}</td>
                <td>{pl.numOfStudWork}</td>
                <td>
                  <button onClick={() => setEditingPlan(pl)}>Edit</button> 
                  <button onClick={() => deletePlan(pl.idPlan)}>Delete</button>
                  <button onClick={() => navigate(`/admin/plans/${pl.idPlan}`)}>View</button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>

      <PlanForm
        selectedPlan={editingPlan}
        onSuccess={() => {
          setEditingPlan(null);
          loadPlans();
        }}
        onCancel={() => setEditingPlan(null)}
      />

    </div>
  );
};

export default PlanList;
