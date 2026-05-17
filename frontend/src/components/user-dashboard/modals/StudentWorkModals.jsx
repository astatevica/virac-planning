import React from "react";
import Modal from "./Modal";

export default function StudentWorkModals(props) {
  const {
    openPlan,
    isStudentWorkModalOpen,
    newStudentWork,
    setNewStudentWork,
    studentWorkFieldErrors,
    degreeOptions,
    handleSaveStudentWorkFromModal,
    isStudentWorkSaving,
    closeStudentWorkModal,
    getButtonStyle,
    studentWorkModalMessage,
    isStudentWorkDeleteModalOpen,
    openPlanStudentWorks,
    getStudentWorkId,
    handleDeleteStudentWorkPlan,
    isStudentWorkDeleting,
    closeStudentWorkDeleteModal,
    studentWorkDeleteMessage,
    isStudentWorkEditModalOpen,
    studentWorkEditForm,
    setStudentWorkEditForm,
    studentWorkEditFieldErrors,
    handleEditStudentWorkPlan,
    isStudentWorkEditing,
    closeStudentWorkEditModal,
    studentWorkEditMessage,
  } = props;

  return (
    <>
      <Modal open={isStudentWorkModalOpen}>
        <h3>Add Student Work To Plan #{openPlan?.idPlan}</h3>
        <div style={{ color: "green", marginTop: 4, marginBottom: 10 }}>
          After adding Student Work you will be able to edit only "Work done" field.
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Work name</label>
          <input data-testid="sw-work-name" type="text" value={newStudentWork.name} onChange={(e) => setNewStudentWork((prev) => ({ ...prev, name: e.target.value }))} style={{ width: "100%", marginTop: 4 }} />
          {studentWorkFieldErrors.name && <div style={{ color: "red", marginTop: 4 }}>{studentWorkFieldErrors.name}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Student name</label>
          <input data-testid="sw-student-name" type="text" value={newStudentWork.studentName} onChange={(e) => setNewStudentWork((prev) => ({ ...prev, studentName: e.target.value }))} style={{ width: "100%", marginTop: 4 }} />
          {studentWorkFieldErrors.studentName && <div style={{ color: "red", marginTop: 4 }}>{studentWorkFieldErrors.studentName}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Student surname</label>
          <input data-testid="sw-student-surname" type="text" value={newStudentWork.studentSurname} onChange={(e) => setNewStudentWork((prev) => ({ ...prev, studentSurname: e.target.value }))} style={{ width: "100%", marginTop: 4 }} />
          {studentWorkFieldErrors.studentSurname && <div style={{ color: "red", marginTop: 4 }}>{studentWorkFieldErrors.studentSurname}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Degree</label>
          <select data-testid="sw-degree" value={newStudentWork.degree} onChange={(e) => setNewStudentWork((prev) => ({ ...prev, degree: e.target.value }))} style={{ width: "100%", marginTop: 4 }}>
            <option value="">Select degree</option>
            {degreeOptions.map((d) => (
              <option key={d} value={d}>{d}</option>
            ))}
          </select>
          {studentWorkFieldErrors.degree && <div style={{ color: "red", marginTop: 4 }}>{studentWorkFieldErrors.degree}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Work done</label>
          <textarea data-testid="sw-work-done" rows={2} value={newStudentWork.workDone} onChange={(e) => setNewStudentWork((prev) => ({ ...prev, workDone: e.target.value }))} style={{ width: "100%", marginTop: 4, resize: "vertical" }} />
          {studentWorkFieldErrors.workDone && <div style={{ color: "red", marginTop: 4 }}>{studentWorkFieldErrors.workDone}</div>}
        </div>
        <button data-testid="sw-save-button" type="button" onClick={handleSaveStudentWorkFromModal} disabled={isStudentWorkSaving} style={getButtonStyle("add", isStudentWorkSaving)}>
          {isStudentWorkSaving ? "Saving..." : "Save Student Work"}
        </button>{" "}
        <button data-testid="sw-cancel-button" type="button" onClick={closeStudentWorkModal} disabled={isStudentWorkSaving} style={getButtonStyle("cancel", isStudentWorkSaving)}>
          Cancel
        </button>
        {studentWorkModalMessage && <p style={{ color: "red", marginTop: 8 }}>{studentWorkModalMessage}</p>}
      </Modal>

      <Modal open={isStudentWorkDeleteModalOpen}>
        <h3>Delete Student Work From Plan #{openPlan?.idPlan}</h3>
        {openPlanStudentWorks.length === 0 ? (
          <p>No student work attached.</p>
        ) : (
          <ol style={{ paddingLeft: 20 }}>
            {openPlanStudentWorks.map((work, idx) => (
              <li data-testid={`sw-delete-${idx}`} key={`${getStudentWorkId(work) || idx}-${idx}`} style={{ marginBottom: 8 }}>
                {`Name: ${work.name || ""} | Student: ${work.studentName || ""} ${work.studentSurname || ""} | Degree: ${work.degree || ""}`}
                <button type="button" onClick={() => handleDeleteStudentWorkPlan(work)} style={{ ...getButtonStyle("delete", isStudentWorkDeleting), marginTop: 4 }}>
                  {isStudentWorkDeleting ? "Deleting..." : "Delete"}
                </button>
              </li>
            ))}
          </ol>
        )}
        <button data-testid="sw-delete-cancel-button" type="button" onClick={closeStudentWorkDeleteModal} disabled={isStudentWorkDeleting}>Close</button>
        {studentWorkDeleteMessage && <p style={{ color: "red", marginTop: 8 }}>{studentWorkDeleteMessage}</p>}
      </Modal>

      <Modal open={isStudentWorkEditModalOpen}>
        <h3>Edit Student Work</h3>
        <div style={{ marginBottom: 10 }}>
          <label>Work name</label>
          <input type="text" value={studentWorkEditForm.name} onChange={(e) => setStudentWorkEditForm((prev) => ({ ...prev, name: e.target.value }))} disabled style={{ width: "100%", marginTop: 4 }} />
          {studentWorkEditFieldErrors.name && <div style={{ color: "red", marginTop: 4 }}>{studentWorkEditFieldErrors.name}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Student name</label>
          <input type="text" value={studentWorkEditForm.studentName} onChange={(e) => setStudentWorkEditForm((prev) => ({ ...prev, studentName: e.target.value }))} disabled style={{ width: "100%", marginTop: 4 }} />
          {studentWorkEditFieldErrors.studentName && <div style={{ color: "red", marginTop: 4 }}>{studentWorkEditFieldErrors.studentName}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Student surname</label>
          <input type="text" value={studentWorkEditForm.studentSurname} onChange={(e) => setStudentWorkEditForm((prev) => ({ ...prev, studentSurname: e.target.value }))} disabled style={{ width: "100%", marginTop: 4 }} />
          {studentWorkEditFieldErrors.studentSurname && <div style={{ color: "red", marginTop: 4 }}>{studentWorkEditFieldErrors.studentSurname}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Degree</label>
          <select value={studentWorkEditForm.degree} onChange={(e) => setStudentWorkEditForm((prev) => ({ ...prev, degree: e.target.value }))} disabled style={{ width: "100%", marginTop: 4 }}>
            <option value="">Select degree</option>
            {degreeOptions.map((d) => (
              <option key={d} value={d}>{d}</option>
            ))}
          </select>
          {studentWorkEditFieldErrors.degree && <div style={{ color: "red", marginTop: 4 }}>{studentWorkEditFieldErrors.degree}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Work done</label>
          <textarea data-testid="sw-edit-work-done" rows={2} value={studentWorkEditForm.workDone} onChange={(e) => setStudentWorkEditForm((prev) => ({ ...prev, workDone: e.target.value }))} style={{ width: "100%", marginTop: 4, resize: "vertical" }} />
          {studentWorkEditFieldErrors.workDone && <div style={{ color: "red", marginTop: 4 }}>{studentWorkEditFieldErrors.workDone}</div>}
        </div>
        <button data-testid="sw-edit-save-button" type="button" onClick={handleEditStudentWorkPlan} disabled={isStudentWorkEditing} style={getButtonStyle("update", isStudentWorkEditing)}>
          {isStudentWorkEditing ? "Saving..." : "Save"}
        </button>{" "}
        <button type="button" onClick={closeStudentWorkEditModal} disabled={isStudentWorkEditing} style={getButtonStyle("cancel", isStudentWorkEditing)}>
          Cancel
        </button>
        {studentWorkEditMessage && <p style={{ color: "red", marginTop: 8 }}>{studentWorkEditMessage}</p>}
      </Modal>
    </>
  );
}
