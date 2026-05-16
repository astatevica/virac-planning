import React from "react";

export default function OpenPlanActivities({
  openPlan,
  tableRows,
  isPlannedFrozen,
  handleOpenPlanNumberChange,
  handleOpenPlanTextChange,
  openPlanProjects,
  openPlanCourses,
  openPlanArticles,
  openPlanStudentWorks,
  getProjectPlanId,
  getProjectId,
  getStudentWorkId,
  getArticlePlanId,
  getArticleId,
  formatProjectText,
  formatCourseText,
  formatArticleText,
  openProjectEditModal,
  openCourseEditModal,
  openStudentWorkEditModal,
  openArticleEditModal,
  getButtonStyle,
  onRowAction,
  handleSaveOpenPlan,
  isSaving,
}) {
  return (
    <>
      <h2 className="user-dashboard-section-title">Open Plan Activities</h2>

      <table className="user-dashboard-table">
        <thead>
          <tr>
            <th className="user-dashboard-table-head user-dashboard-col-activity">
              Activity
            </th>
            <th className="user-dashboard-table-head user-dashboard-col-content">
              Planned
            </th>
            <th className="user-dashboard-table-head user-dashboard-col-content">
              Done
            </th>
            <th className="user-dashboard-table-head user-dashboard-col-buttons">
              Buttons
            </th>
          </tr>
        </thead>
        <tbody>
          {!openPlan ? (
            <tr>
              <td colSpan="4" className="user-dashboard-empty-cell">
                No open plan available.
              </td>
            </tr>
          ) : (
            tableRows.map((row) => (
              <tr key={row.key}>
                <td className="user-dashboard-table-cell user-dashboard-cell-top">
                  {row.label}
                </td>
                <td className="user-dashboard-table-cell user-dashboard-cell-top">
                  {row.type === "number" ? (
                    <input
                      type="number"
                      name={row.planned}
                      value={openPlan[row.planned] ?? ""}
                      onChange={handleOpenPlanNumberChange}
                      disabled={isPlannedFrozen}
                      className="user-dashboard-input"
                    />
                  ) : (
                    <textarea
                      name={row.planned}
                      value={openPlan[row.planned] || ""}
                      onChange={handleOpenPlanTextChange}
                      disabled={isPlannedFrozen}
                      rows={2}
                      className="user-dashboard-textarea"
                    />
                  )}
                </td>
                <td className="user-dashboard-table-cell user-dashboard-cell-top">
                  {row.done.endsWith("DTO") ? (
                    row.done === "ProjectDTO" ? (
                      openPlanProjects.length > 0 ? (
                        <ol className="user-dashboard-inline-list">
                          {openPlanProjects.map((project, idx) => (
                            <li
                              key={`${getProjectPlanId(project) || getProjectId(project) || idx}-${idx}`}
                            >
                              {formatProjectText(project)}{" "}
                              <button
                                data-testid={`cypress-edit-project-${idx}`}
                                type="button"
                                onClick={() => openProjectEditModal(project)}
                                style={getButtonStyle("edit")}
                              >
                                Edit
                              </button>
                            </li>
                          ))}
                        </ol>
                      ) : (
                        <span>No projects attached</span>
                      )
                    ) : row.done === "CourseDTO" ? (
                      openPlanCourses.length > 0 ? (
                        <ol className="user-dashboard-inline-list">
                          {openPlanCourses.map((course, idx) => (
                            <li key={`${course.idCourse || idx}-${idx}`}>
                              {formatCourseText(course)}{" "}
                              <button
                                data-testid="cypress-edit-course"
                                type="button"
                                onClick={() => openCourseEditModal(course)}
                                style={getButtonStyle("edit")}
                              >
                                Edit
                              </button>
                            </li>
                          ))}
                        </ol>
                      ) : (
                        <span>No courses attached</span>
                      )
                    ) : row.done === "StudentWorkDTO" ? (
                      openPlanStudentWorks.length > 0 ? (
                        <ol className="user-dashboard-inline-list">
                          {openPlanStudentWorks.map((work, idx) => (
                            <li key={`${getStudentWorkId(work) || idx}-${idx}`}>
                              {`Name: ${work.name || ""} | Student: ${work.studentName || ""} ${work.studentSurname || ""} | Degree: ${work.degree || ""} | Work done: ${work.workDone || ""}`}{" "}
                              <button
                                data-testid="cypress-edit-student-work"
                                type="button"
                                onClick={() => openStudentWorkEditModal(work)}
                                style={getButtonStyle("edit")}
                              >
                                Edit
                              </button>
                            </li>
                          ))}
                        </ol>
                      ) : (
                        <span>No student work attached</span>
                      )
                    ) : row.done === "ArticleDTO" ? (
                      openPlanArticles.length > 0 ? (
                        <ol className="user-dashboard-inline-list">
                          {openPlanArticles.map((article, idx) => (
                            <li
                              key={`${getArticlePlanId(article) || getArticleId(article) || idx}-${idx}`}
                            >
                              {formatArticleText(article)}{" "}
                              <button
                                data-testid="cypress-edit-article"
                                type="button"
                                onClick={() => openArticleEditModal(article)}
                                style={getButtonStyle("edit")}
                              >
                                Edit
                              </button>
                            </li>
                          ))}
                        </ol>
                      ) : (
                        <span>No articles attached</span>
                      )
                    ) : (
                      <span>{row.done}</span>
                    )
                  ) : (
                    <textarea
                      name={row.done}
                      value={openPlan[row.done] || ""}
                      onChange={handleOpenPlanTextChange}
                      rows={2}
                      className="user-dashboard-textarea user-dashboard-textarea-done"
                    />
                  )}
                </td>
                <td className="user-dashboard-table-cell user-dashboard-cell-top">
                  {row.actions?.length ? (
                    <>
                      <button
                        type="button"
                        disabled={isPlannedFrozen}
                        style={getButtonStyle("add", isPlannedFrozen)}
                        onClick={() => onRowAction(row.key, 0)}
                      >
                        {row.actions[0]}
                      </button>{" "}
                      <button
                        type="button"
                        disabled={isPlannedFrozen}
                        style={getButtonStyle("delete", isPlannedFrozen)}
                        onClick={() => onRowAction(row.key, 1)}
                      >
                        {row.actions[1]}
                      </button>
                    </>
                  ) : (
                    ""
                  )}
                </td>
              </tr>
            ))
          )}
          {openPlan && (
            <tr>
              <td colSpan="2"></td>
              <td>
                <button
                  type="button"
                  onClick={handleSaveOpenPlan}
                  disabled={isSaving}
                  style={getButtonStyle("update", isSaving)}
                >
                  {isSaving ? "Saving..." : "Save changes"}
                </button>
              </td>
              <td></td>
            </tr>
          )}
        </tbody>
      </table>
    </>
  );
}
