import React, { useEffect, useState, useRef } from "react";
import axios from "axios";

const API = "http://localhost:8080/api";

const AutoTextarea = React.memo(function AutoTextarea({
  name,
  value,
  onChange,
  placeholder
}) {
  const ref = useRef(null);

  useEffect(() => {
    if (ref.current) {
      ref.current.style.height = "auto";
      ref.current.style.height = ref.current.scrollHeight + "px";
    }
  }, [value]);

  return (
    <textarea
      ref={ref}
      name={name}
      value={value}
      onChange={onChange}
      rows={3}
      placeholder={placeholder}
      style={{
        width: "95%",
        resize: "none",
        overflow: "hidden",
        padding: "8px",
        lineHeight: "1.5"
      }}
    />
  );
});

export default function PlanForm({ selectedPlan, onSuccess, onCancel }) {
  //controlls inputs
  const [employees, setEmployees] = useState([]);
  const [years, setYears] = useState([]);

  const [plan, setPlan] = useState({
    idEmployee: "",
    idYear: "",

    numOfProjects: "", 
    numOfArticles: "", 
    numOfCourses: "", 
    numOfStudWork: "", 

    partInConf: "",
    partInConfEnd: "",

    comAbConf: "",
    comAbConfEnd: "",

    promoOfResearch: "",
    promoOfResearchEnd: "",

    adminWork: "",
    adminWorkEnd: "",

    projApplicSub: "",
    projApplicSubEnd: "",

    skillsDevelopment: "",
    skillsDevelopmentEnd: "",

    participationInSeminars: "",
    participationInSeminarsEnd: "",

    otherJobs: "",
    otherJobsEnd: ""
  });

  useEffect(() => {
    axios.get(`${API}/employee`).then(res => setEmployees(res.data));
    axios.get(`${API}/year`).then(res => setYears(res.data));
  }, []);


  useEffect(() => {
  if (selectedPlan) {
    setPlan({ ...selectedPlan }); //clone to avoid reference issues
  } 
  }, [selectedPlan]);


  const handleChange = (e) => {
    const { name, value } = e.target;
    if (!name) return;

    setPlan(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    console.log("Sending:", plan);

    const request = plan.idPlan
      ? axios.put(`${API}/plan/${plan.idPlan}`, plan) // UPDATE
      : axios.post(`${API}/plan`, plan);              // CREATE

    request
      .then(() => {
        alert(plan.idPlan ? "Plan updated successfully" : "Plan created successfully");
        setPlan({
          idEmployee: "",
          idYear: "",
          numOfProjects: "",
          numOfArticles: "",
          numOfCourses: "",
          numOfStudWork: "",
          partInConf: "",
          partInConfEnd: "",
          comAbConf: "",
          comAbConfEnd: "",
          promoOfResearch: "",
          promoOfResearchEnd: "",
          adminWork: "",
          adminWorkEnd: "",
          projApplicSub: "",
          projApplicSubEnd: "",
          skillsDevelopment: "",
          skillsDevelopmentEnd: "",
          participationInSeminars: "",
          participationInSeminarsEnd: "",
          otherJobs: "",
          otherJobsEnd: ""
        });
        onSuccess?.();
      })
      .catch(err =>
        alert(err.response?.data?.message || "Error saving plan")
      );
  };


  const NumberField = ({ label, name }) => (
    <div style={{ marginBottom: 10 }}>
      <label>{label}</label><br />
      <input type="number" name={name} value={plan[name]} onChange={handleChange} />
    </div>
  );

  const TextPair = ({ label, planned, completed }) => (
    <tr>
        <td><strong>{label}</strong></td>

        <td>
        <AutoTextarea
            name={planned}
            value={plan[planned] ?? ""}
            onChange={handleChange}
            rows={3}
            placeholder="Planned at beginning of year"
        />
        </td>

        <td>
        <AutoTextarea
            name={completed}
            value={plan[completed] ?? ""}
            onChange={handleChange}
            rows={3}
            placeholder="Completed by end of year"
        />
        </td>
    </tr>
   );

  return (
    <form onSubmit={handleSubmit} style={{ maxWidth: 1100, margin: "auto" }}>
      <hr />
      <h2>Annual Employee Plan Form</h2>

      <label>Employee</label><br />
      <select name="idEmployee" value={plan.idEmployee} onChange={handleChange} required>
        <option value="">-- Select Employee --</option>
        {employees.map(e => (
          <option key={e.idEmployee} value={e.id}>
            {e.name} {e.surname}
          </option>
        ))}
      </select>

      <br /><br />

      <label>Year</label><br />
      <select name="idYear" value={plan.idYear} onChange={handleChange}>
        <option value="">Select year</option>
        {years.map(y => (
          <option key={y.idYear} value={y.idYear}>
            {y.yearNumber}
          </option>
        ))}
      </select>

      <hr />

      <h3>Quantitative Indicators</h3>
      <NumberField label="Number of Research Projects" name="numOfProjects" />
      <NumberField label="Number of Scientific Articles" name="numOfArticles" />
      <NumberField label="Courses Taught" name="numOfCourses" />
      <NumberField label="Student Works Supervised" name="numOfStudWork" />

      <hr />

      <h3>Planned vs Completed Activities</h3>
      <table border="1" width="100%" cellPadding="8">
        <thead>
          <tr>
            <th>Activity</th>
            <th>Planned (Beginning of year)</th>
            <th>Completed (End of year)</th>
          </tr>
        </thead>
        <tbody>
          <TextPair label="Conference Participation" planned="partInConf" completed="partInConfEnd" />
          <TextPair label="Conference Abstracts" planned="comAbConf" completed="comAbConfEnd" />
          <TextPair label="Research Promotion" planned="promoOfResearch" completed="promoOfResearchEnd" />
          <TextPair label="Project Applications" planned="projApplicSub" completed="projApplicSubEnd" />
          <TextPair label="Skill Development" planned="skillsDevelopment" completed="skillsDevelopmentEnd" />
          <TextPair label="Seminar Participation" planned="participationInSeminars" completed="participationInSeminarsEnd" />
          <TextPair label="Administrative Work" planned="adminWork" completed="adminWorkEnd" />
          <TextPair label="Other Duties" planned="otherJobs" completed="otherJobsEnd" />
        </tbody>
      </table>

      <br />
      <button type="submit">
        {plan.idPlan ? "Update Plan" : "Save Plan"}
      </button>

      {plan.idPlan && (
        <button
          type="button"
          onClick={() => {
            onCancel();
            setPlan({
              idEmployee: "",
              idYear: "",
              numOfProjects: "",
              numOfArticles: "",
              numOfCourses: "",
              numOfStudWork: "",
              partInConf: "",
              partInConfEnd: "",
              comAbConf: "",
              comAbConfEnd: "",
              promoOfResearch: "",
              promoOfResearchEnd: "",
              adminWork: "",
              adminWorkEnd: "",
              projApplicSub: "",
              projApplicSubEnd: "",
              skillsDevelopment: "",
              skillsDevelopmentEnd: "",
              participationInSeminars: "",
              participationInSeminarsEnd: "",
              otherJobs: "",
              otherJobsEnd: ""
            });
          }}
          style={{ marginLeft: 10 }}
        >
          Cancel
        </button>
      )}

    </form>
  );
}
