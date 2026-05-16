import React from "react";
import Modal from "./Modal";

export default function ProjectModals(props) {
  const {
    openPlan,
    isProjectModalOpen,
    projectSearch,
    setProjectSearch,
    setSelectedProject,
    projectOptions,
    getProjectId,
    toDateInputValue,
    openPlanProjects,
    projectModalFieldErrors,
    selectedProject,
    formatProjectText,
    projectTasks,
    setProjectTasks,
    projectWorkDone,
    setProjectWorkDone,
    projectModalMessage,
    handleSaveProjectFromModal,
    isProjectSaving,
    closeProjectModal,
    getButtonStyle,
    isProjectDeleteModalOpen,
    isProjectDeleting,
    projectDeleteMessage,
    getProjectPlanId,
    handleDeleteProjectPlan,
    closeProjectDeleteModal,
    isProjectEditModalOpen,
    projectEditTarget,
    projectEditName,
    setProjectEditName,
    projectEditFieldErrors,
    projectEditNumber,
    setProjectEditNumber,
    projectEditManagementId,
    setProjectEditManagementId,
    projectEditStartDate,
    setProjectEditStartDate,
    projectEditEndDate,
    setProjectEditEndDate,
    projectEditAcronym,
    setProjectEditAcronym,
    projectEditTasks,
    setProjectEditTasks,
    projectEditWorkDone,
    setProjectEditWorkDone,
    projectEditMessage,
    handleEditProjectPlan,
    isProjectEditing,
    closeProjectEditModal,
  } = props;

  return (
    <>
      <Modal open={isProjectModalOpen}>
        <h3>Add Project To Plan #{openPlan?.idPlan}</h3>
        <div style={{ color: "green", marginTop: 4, marginBottom: 10 }}>
          After adding Project you will be able to edit only "Tasks" and "Work done" fields.
        </div>

        <div style={{ marginBottom: 10 }}>
          <label>Project autocomplete</label>
          <input
            data-testid="cypress-project-search"
            type="text"
            value={projectSearch}
            onChange={(e) => {
              setProjectSearch(e.target.value);
              setSelectedProject(null);
            }}
            placeholder="Type at least 2 characters..."
            style={{ width: "100%", marginTop: 4 }}
          />
          {projectOptions.length > 0 && (
            <div style={{ border: "1px solid #ccc", maxHeight: 160, overflowY: "auto", marginTop: 4 }}>
              {projectOptions.map((project) => (
                <div
                  key={getProjectId(project) || project.name}
                  onClick={() => {
                    setSelectedProject(project);
                    setProjectSearch(
                      `${project.name} (Number: ${project.number}, Start: ${toDateInputValue(project.startDate)}, End: ${toDateInputValue(project.endDate)})`
                    );
                    const existing = openPlanProjects.find(
                      (p) => Number(getProjectId(p)) === Number(getProjectId(project))
                    );
                    props.setProjectOptions([]);
                    if (existing) {
                      props.setProjectModalMessage("Project already attached to this plan.");
                    } else {
                      props.setProjectModalMessage("");
                    }
                  }}
                  style={{ padding: "6px 8px", cursor: "pointer" }}
                >
                  {project.name} | {project.acronym} | #{project.number}
                </div>
              ))}
            </div>
          )}
          {projectModalFieldErrors.idProject && (
            <div style={{ color: "red", marginTop: 4 }}>
              {projectModalFieldErrors.idProject}
            </div>
          )}
        </div>

        {selectedProject && (
          <div style={{ marginBottom: 10 }}>
            <div>Selected: {formatProjectText(selectedProject)}</div>
          </div>
        )}

        <div style={{ marginBottom: 10 }}>
          <label>Tasks</label>
          <textarea
            data-testid="cypress-tasks-project"
            rows={3}
            value={projectTasks}
            onChange={(e) => setProjectTasks(e.target.value)}
            style={{ width: "100%", marginTop: 4 }}
          />
          {projectModalFieldErrors.tasks && (
            <div style={{ color: "red", marginTop: 4 }}>
              {projectModalFieldErrors.tasks}
            </div>
          )}
        </div>

        <div style={{ marginBottom: 10 }}>
          <label>Work done</label>
          <textarea
            data-testid="cypress-work-done-plan"
            rows={3}
            value={projectWorkDone}
            onChange={(e) => setProjectWorkDone(e.target.value)}
            style={{ width: "100%", marginTop: 4 }}
          />
          {projectModalFieldErrors.workDone && (
            <div style={{ color: "red", marginTop: 4 }}>
              {projectModalFieldErrors.workDone}
            </div>
          )}
        </div>

        {projectModalMessage && (
          <div style={{ color: "red", marginBottom: 10 }}>{projectModalMessage}</div>
        )}

        <button data-testid="cypress-save-project" type="button" onClick={handleSaveProjectFromModal} disabled={isProjectSaving} style={getButtonStyle("add", isProjectSaving)}>
          {isProjectSaving ? "Saving..." : "Save Project"}
        </button>{" "}
        <button data-testid="cypress-cancel-project" type="button" onClick={closeProjectModal} disabled={isProjectSaving} style={getButtonStyle("cancel", isProjectSaving)}>
          Cancel
        </button>
      </Modal>

      <Modal open={isProjectDeleteModalOpen} width={600}>
        <h3>Delete Project From Plan #{openPlan?.idPlan}</h3>

        {openPlanProjects.length === 0 ? (
          <p>No projects attached to this plan.</p>
        ) : (
          <div style={{ maxHeight: 260, overflowY: "auto", border: "1px solid #ddd", padding: 8 }}>
            {openPlanProjects.map((project, idx) => (
              <div key={`${getProjectPlanId(project) || getProjectId(project) || idx}-${idx}`} style={{ marginBottom: 8 }}>
                <div>{formatProjectText(project)}</div>
                <button
                  data-testid={`cypress-delete-project-${idx}`}
                  type="button"
                  onClick={() => handleDeleteProjectPlan(project)}
                  disabled={isProjectDeleting}
                  style={getButtonStyle("delete", isProjectDeleting)}
                >
                  {isProjectDeleting ? "Deleting..." : "Delete"}
                </button>
              </div>
            ))}
          </div>
        )}

        {projectDeleteMessage && (
          <div style={{ color: "red", marginTop: 8 }}>{projectDeleteMessage}</div>
        )}

        <button type="button" onClick={closeProjectDeleteModal} disabled={isProjectDeleting}>
          Close
        </button>
      </Modal>

      <Modal open={isProjectEditModalOpen}>
        <h3>Edit Project</h3>
        <div style={{ marginBottom: 10 }}>
          <div>{projectEditTarget ? formatProjectText(projectEditTarget) : ""}</div>
        </div>

        <div style={{ marginBottom: 10 }}>
          <label>Name</label>
          <input type="text" value={projectEditName} onChange={(e) => setProjectEditName(e.target.value)} disabled style={{ width: "100%", marginTop: 4 }} />
          {projectEditFieldErrors.name && <div style={{ color: "red", marginTop: 4 }}>{projectEditFieldErrors.name}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Number</label>
          <input type="number" value={projectEditNumber} onChange={(e) => setProjectEditNumber(e.target.value)} disabled style={{ width: "100%", marginTop: 4 }} />
          {projectEditFieldErrors.number && <div style={{ color: "red", marginTop: 4 }}>{projectEditFieldErrors.number}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Management ID</label>
          <input type="number" value={projectEditManagementId} onChange={(e) => setProjectEditManagementId(e.target.value)} disabled style={{ width: "100%", marginTop: 4 }} />
          {projectEditFieldErrors.managementId && <div style={{ color: "red", marginTop: 4 }}>{projectEditFieldErrors.managementId}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Start Date</label>
          <input type="date" value={projectEditStartDate} onChange={(e) => setProjectEditStartDate(e.target.value)} disabled style={{ width: "100%", marginTop: 4 }} />
          {projectEditFieldErrors.startDate && <div style={{ color: "red", marginTop: 4 }}>{projectEditFieldErrors.startDate}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>End Date</label>
          <input type="date" value={projectEditEndDate} onChange={(e) => setProjectEditEndDate(e.target.value)} disabled style={{ width: "100%", marginTop: 4 }} />
          {projectEditFieldErrors.endDate && <div style={{ color: "red", marginTop: 4 }}>{projectEditFieldErrors.endDate}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Acronym</label>
          <input type="text" value={projectEditAcronym} onChange={(e) => setProjectEditAcronym(e.target.value)} disabled style={{ width: "100%", marginTop: 4 }} />
          {projectEditFieldErrors.acronym && <div style={{ color: "red", marginTop: 4 }}>{projectEditFieldErrors.acronym}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Tasks</label>
          <textarea data-testid="cypress-edit-project-tasks" rows={3} value={projectEditTasks} onChange={(e) => setProjectEditTasks(e.target.value)} style={{ width: "100%", marginTop: 4 }} />
          {projectEditFieldErrors.tasks && <div style={{ color: "red", marginTop: 4 }}>{projectEditFieldErrors.tasks}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Work done</label>
          <textarea data-testid="cypress-edit-project-work-done" rows={3} value={projectEditWorkDone} onChange={(e) => setProjectEditWorkDone(e.target.value)} style={{ width: "100%", marginTop: 4 }} />
          {projectEditFieldErrors.workDone && <div style={{ color: "red", marginTop: 4 }}>{projectEditFieldErrors.workDone}</div>}
        </div>

        {projectEditMessage && <div style={{ color: "red", marginBottom: 10 }}>{projectEditMessage}</div>}

        <button data-testid="cypress-edit-project-save" type="button" onClick={handleEditProjectPlan} disabled={isProjectEditing} style={getButtonStyle("update", isProjectEditing)}>
          {isProjectEditing ? "Saving..." : "Save"}
        </button>{" "}
        <button data-testid="cypress-close-delete-project-modal" type="button" onClick={closeProjectEditModal} disabled={isProjectEditing} style={getButtonStyle("cancel", isProjectEditing)}>
          Cancel
        </button>
      </Modal>
    </>
  );
}
