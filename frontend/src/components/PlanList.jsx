import React, { useEffect, useState } from "react";
import PlanService from "../services/PlanService";
import PlanForm from "./PlanForm";

const PlanList = () => {
  const [plans, setPlans] = useState([]);
  const [editingPlan, setEditingPlan] = useState(null);

  useEffect(() => {
    loadPlans();
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

  return (
    <div>
      <h2>Plans</h2>

      <PlanForm
        selectedPlan={editingPlan}
        onSuccess={() => {
          setEditingPlan(null);
          loadPlans();
        }}
        onCancel={() => setEditingPlan(null)}
      />

      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>ID</th>
            <th>Employee</th>
            <th>Year</th>
            <th>Projects</th>
            <th>Articles</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {plans.map(pl => (
            <tr key={pl.idPlan}>
              <td>{pl.idPlan}</td>
              <td>{pl.idEmployee}</td>
              <td>{pl.idYear}</td>
              <td>{pl.numOfProjects}</td>
              <td>{pl.numOfArticles}</td>
              <td>
                <button onClick={() => setEditingPlan(pl)}>Edit</button>
                <button onClick={() => deletePlan(pl.idPlan)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default PlanList;
