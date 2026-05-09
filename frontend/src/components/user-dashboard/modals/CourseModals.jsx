import React from "react";
import Modal from "./Modal";

export default function CourseModals(props) {
  const {
    openPlan,
    isCourseModalOpen,
    courseMode,
    setCourseMode,
    courseSearch,
    setCourseSearch,
    setSelectedCourse,
    courseOptions,
    formatCourseText,
    openPlanCourses,
    getWorkDoneText,
    newCourse,
    setNewCourse,
    courseWorkDone,
    setCourseWorkDone,
    courseModalFieldErrors,
    handleSaveCourseFromModal,
    isCourseSaving,
    closeCourseModal,
    getButtonStyle,
    courseModalMessage,
    isCourseDeleteModalOpen,
    isCourseDeleting,
    courseDeleteMessage,
    getCoursePlanId,
    handleDeleteCoursePlan,
    closeCourseDeleteModal,
    isCourseEditModalOpen,
    courseEditTarget,
    courseEditName,
    setCourseEditName,
    courseEditEcts,
    setCourseEditEcts,
    courseEditSemester,
    setCourseEditSemester,
    courseEditFaculty,
    setCourseEditFaculty,
    courseEditWorkDone,
    setCourseEditWorkDone,
    courseEditFieldErrors,
    handleEditCoursePlan,
    isCourseEditing,
    closeCourseEditModal,
    courseEditMessage,
  } = props;

  return (
    <>
      <Modal open={isCourseModalOpen}>
        <h3>Add Course To Plan #{openPlan?.idPlan}</h3>
        <div style={{ color: "green", marginTop: 4, marginBottom: 10 }}>
          After adding Course you will be able to edit only "Work done" field.
        </div>

        <div style={{ marginBottom: 10 }}>
          <label>
            <input type="radio" name="courseMode" checked={courseMode === "existing"} onChange={() => setCourseMode("existing")} />{" "}
            Use existing course
          </label>{"  "}
          <label>
            <input type="radio" name="courseMode" checked={courseMode === "new"} onChange={() => setCourseMode("new")} />{" "}
            Create new course
          </label>
        </div>

        {courseMode === "existing" ? (
          <div style={{ marginBottom: 10 }}>
            <label>Course autocomplete</label>
            <input
              type="text"
              value={courseSearch}
              onChange={(e) => {
                setCourseSearch(e.target.value);
                setSelectedCourse(null);
              }}
              placeholder="Type at least 2 characters..."
              style={{ width: "100%", marginTop: 4 }}
            />
            {courseOptions.length > 0 && (
              <div style={{ border: "1px solid #ccc", maxHeight: 160, overflowY: "auto", marginTop: 4 }}>
                {courseOptions.map((course) => (
                  <div
                    key={course.idCourse}
                    onClick={() => {
                      setSelectedCourse(course);
                      setCourseSearch(`${course.name} (ECTS: ${course.ectsCredits}, ${course.semester}, ${course.faculty})`);
                      props.setCourseOptions([]);
                    }}
                    style={{ padding: 8, cursor: "pointer", borderBottom: "1px solid #eee" }}
                  >
                    {course.name} | ECTS: {course.ectsCredits} | Semester: {course.semester} | Faculty: {course.faculty}
                    {(() => {
                      const existing = openPlanCourses.find((c) => Number(c?.idCourse) === Number(course.idCourse));
                      const wd = existing ? getWorkDoneText(existing) : "";
                      return wd ? ` | Work done: ${wd}` : "";
                    })()}
                  </div>
                ))}
              </div>
            )}
          </div>
        ) : (
          <div style={{ marginBottom: 10 }}>
            <label>Name</label>
            <input type="text" value={newCourse.name} onChange={(e) => setNewCourse((prev) => ({ ...prev, name: e.target.value }))} style={{ width: "100%", marginBottom: 6 }} />
            {courseModalFieldErrors.name && <div style={{ color: "red", marginBottom: 6 }}>{courseModalFieldErrors.name}</div>}
            <label>ECTS</label>
            <input type="number" value={newCourse.ectsCredits} onChange={(e) => setNewCourse((prev) => ({ ...prev, ectsCredits: e.target.value }))} style={{ width: "100%", marginBottom: 6 }} />
            {courseModalFieldErrors.ectsCredits && <div style={{ color: "red", marginBottom: 6 }}>{courseModalFieldErrors.ectsCredits}</div>}
            <label>Semester</label>
            <input type="text" value={newCourse.semester} onChange={(e) => setNewCourse((prev) => ({ ...prev, semester: e.target.value }))} style={{ width: "100%", marginBottom: 6 }} />
            {courseModalFieldErrors.semester && <div style={{ color: "red", marginBottom: 6 }}>{courseModalFieldErrors.semester}</div>}
            <label>Faculty</label>
            <input type="text" value={newCourse.faculty} onChange={(e) => setNewCourse((prev) => ({ ...prev, faculty: e.target.value }))} style={{ width: "100%" }} />
            {courseModalFieldErrors.faculty && <div style={{ color: "red", marginTop: 6 }}>{courseModalFieldErrors.faculty}</div>}
          </div>
        )}

        <div style={{ marginBottom: 10 }}>
          <label>Work done</label>
          <textarea rows={3} value={courseWorkDone} onChange={(e) => setCourseWorkDone(e.target.value)} style={{ width: "100%", marginTop: 4, resize: "vertical" }} />
          {courseModalFieldErrors.workDone && <div style={{ color: "red", marginTop: 4 }}>{courseModalFieldErrors.workDone}</div>}
        </div>

        <button type="button" onClick={handleSaveCourseFromModal} disabled={isCourseSaving} style={getButtonStyle("add", isCourseSaving)}>
          {isCourseSaving ? "Saving..." : "Save Course"}
        </button>{" "}
        <button type="button" onClick={closeCourseModal} disabled={isCourseSaving} style={getButtonStyle("cancel", isCourseSaving)}>
          Cancel
        </button>
        {courseModalMessage && <p style={{ color: "red", marginTop: 8 }}>{courseModalMessage}</p>}
      </Modal>

      <Modal open={isCourseDeleteModalOpen}>
        <h3>Delete Course From Plan #{openPlan?.idPlan}</h3>
        {openPlanCourses.length === 0 ? (
          <p>No courses attached.</p>
        ) : (
          <ol style={{ paddingLeft: 20 }}>
            {openPlanCourses.map((course, idx) => (
              <li key={`${getCoursePlanId(course) || idx}-${idx}`} style={{ marginBottom: 8 }}>
                <div>{formatCourseText(course)}</div>
                <button type="button" onClick={() => handleDeleteCoursePlan(course)} style={{ ...getButtonStyle("delete", isCourseDeleting), marginTop: 4 }}>
                  {isCourseDeleting ? "Deleting..." : "Delete"}
                </button>
              </li>
            ))}
          </ol>
        )}
        <button type="button" onClick={closeCourseDeleteModal} disabled={isCourseDeleting}>Close</button>
        {courseDeleteMessage && <p style={{ color: "red", marginTop: 8 }}>{courseDeleteMessage}</p>}
      </Modal>

      <Modal open={isCourseEditModalOpen}>
        <h3>Edit Course Work Done</h3>
        <div style={{ marginBottom: 10 }}><div>{courseEditTarget ? formatCourseText(courseEditTarget) : ""}</div></div>
        <div style={{ marginBottom: 10 }}>
          <label>Name</label>
          <input type="text" value={courseEditName} onChange={(e) => setCourseEditName(e.target.value)} disabled style={{ width: "100%", marginTop: 4 }} />
          {courseEditFieldErrors.name && <div style={{ color: "red", marginTop: 4 }}>{courseEditFieldErrors.name}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>ECTS</label>
          <input type="number" value={courseEditEcts} onChange={(e) => setCourseEditEcts(e.target.value)} disabled style={{ width: "100%", marginTop: 4 }} />
          {courseEditFieldErrors.ectsCredits && <div style={{ color: "red", marginTop: 4 }}>{courseEditFieldErrors.ectsCredits}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Semester</label>
          <input type="text" value={courseEditSemester} onChange={(e) => setCourseEditSemester(e.target.value)} disabled style={{ width: "100%", marginTop: 4 }} />
          {courseEditFieldErrors.semester && <div style={{ color: "red", marginTop: 4 }}>{courseEditFieldErrors.semester}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Faculty</label>
          <input type="text" value={courseEditFaculty} onChange={(e) => setCourseEditFaculty(e.target.value)} disabled style={{ width: "100%", marginTop: 4 }} />
          {courseEditFieldErrors.faculty && <div style={{ color: "red", marginTop: 4 }}>{courseEditFieldErrors.faculty}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Work done</label>
          <textarea rows={3} value={courseEditWorkDone} onChange={(e) => setCourseEditWorkDone(e.target.value)} style={{ width: "100%", marginTop: 4, resize: "vertical" }} />
          {courseEditFieldErrors.workDone && <div style={{ color: "red", marginTop: 4 }}>{courseEditFieldErrors.workDone}</div>}
        </div>
        <button type="button" onClick={handleEditCoursePlan} disabled={isCourseEditing} style={getButtonStyle("update", isCourseEditing)}>
          {isCourseEditing ? "Saving..." : "Save"}
        </button>{" "}
        <button type="button" onClick={closeCourseEditModal} disabled={isCourseEditing} style={getButtonStyle("cancel", isCourseEditing)}>
          Cancel
        </button>
        {courseEditMessage && <p style={{ color: "red", marginTop: 8 }}>{courseEditMessage}</p>}
      </Modal>
    </>
  );
}
