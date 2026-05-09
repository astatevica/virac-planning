import React from "react";

export default function CurrentYearPlansTable({
  currentYear,
  currentYearId,
  plans,
  navigate,
  handleExportPlan,
  getButtonStyle,
}) {
  return (
    <>
      <h3>
        Current Year Plans ({currentYear})
        {currentYearId && ` (ID: ${currentYearId})`}
      </h3>

      <table className="user-dashboard-table">
        <thead>
          <tr>
            <th className="user-dashboard-table-head">ID</th>
            <th className="user-dashboard-table-head">Projects</th>
            <th className="user-dashboard-table-head">Articles</th>
            <th className="user-dashboard-table-head">Courses</th>
            <th className="user-dashboard-table-head">Student Work</th>
            <th className="user-dashboard-table-head">Actions</th>
          </tr>
        </thead>
        <tbody>
          {plans.length === 0 ? (
            <tr>
              <td colSpan="6" className="user-dashboard-empty-cell">
                No plans for current year
              </td>
            </tr>
          ) : (
            plans.map((plan) => (
              <tr key={plan.idPlan}>
                <td className="user-dashboard-table-cell">{plan.idPlan}</td>
                <td className="user-dashboard-table-cell">{plan.numOfProjects}</td>
                <td className="user-dashboard-table-cell">{plan.numOfArticles}</td>
                <td className="user-dashboard-table-cell">{plan.numOfCourses}</td>
                <td className="user-dashboard-table-cell">{plan.numOfStudWork}</td>
                <td className="user-dashboard-table-cell">
                  <button
                    onClick={() => navigate(`/user/full-plan/${plan.idPlan}`)}
                    style={getButtonStyle("view")}
                  >
                    Open
                  </button>{" "}
                  <button
                    type="button"
                    onClick={() => handleExportPlan(plan.idPlan, "docx")}
                    style={getButtonStyle("view")}
                  >
                    DOCX
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>

      <div className="user-dashboard-actions-row">
        <button
          onClick={() => navigate("/user/plans")}
          style={getButtonStyle("view")}
        >
          View All Plans
        </button>
      </div>
    </>
  );
}
