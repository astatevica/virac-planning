import React, { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import UserPlanService from "../services/UserPlanService";
import api from "../api/api";

export default function UserDashboard() {
  const navigate = useNavigate();
  const [plans, setPlans] = useState([]);
  const [currentYearId, setCurrentYearId] = useState(null);
  const [openPlan, setOpenPlan] = useState(null);
  const [openPlanCourses, setOpenPlanCourses] = useState([]);
  const [saveMessage, setSaveMessage] = useState("");
  const [isSaving, setIsSaving] = useState(false);

  const [isCourseModalOpen, setIsCourseModalOpen] = useState(false);
  const [courseMode, setCourseMode] = useState("existing");
  const [courseSearch, setCourseSearch] = useState("");
  const [courseOptions, setCourseOptions] = useState([]);
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [courseWorkDone, setCourseWorkDone] = useState("");
  const [isCourseSaving, setIsCourseSaving] = useState(false);
  const [newCourse, setNewCourse] = useState({
    name: "",
    ectsCredits: "",
    semester: "",
    faculty: ""
  });

  const currentYear = new Date().getFullYear();

  const tableRows = [
    { key: "projects", label: "Projects", planned: "numOfProjects", done: "ProjectDTO", type: "number", actions: ["Add Project", "Delete Project"] },
    { key: "articles", label: "Articles", planned: "numOfArticles", done: "ArticleDTO", type: "number", actions: ["Add Article", "Delete Article"] },
    { key: "conferences", label: "Conferences", planned: "partInConf", done: "partInConfEnd", type: "text" },
    { key: "conferenceComments", label: "Comments about conferences", planned: "comAbConf", done: "comAbConfEnd", type: "text" },
    { key: "courses", label: "Courses", planned: "numOfCourses", done: "CourseDTO", type: "number", actions: ["Add Course", "Delete Course"] },
    { key: "studentWork", label: "Student work", planned: "numOfStudWork", done: "StudentWorkDTO", type: "number", actions: ["Add Student Work", "Delete Student Work"] },
    { key: "research", label: "Research", planned: "promoOfResearch", done: "promoOfResearchEnd", type: "text" },
    { key: "administrative", label: "Administrative work", planned: "adminWork", done: "adminWorkEnd", type: "text" },
    { key: "applications", label: "Project aplications", planned: "projApplicSub", done: "projApplicSubEnd", type: "text" },
    { key: "skills", label: "Skill development", planned: "skillsDevelopment", done: "skillsDevelopmentEnd", type: "text" },
    { key: "seminars", label: "Seminars", planned: "participationInSeminars", done: "participationInSeminarsEnd", type: "text" },
    { key: "other", label: "Other", planned: "otherJobs", done: "otherJobsEnd", type: "text" }
  ];

  const extractStatus = useCallback((planLike) => {
    if (!planLike) return "";
    return (
      planLike.planStatus ||
      planLike.status ||
      planLike.plan_status ||
      planLike.planstatus ||
      ""
    )
      .toString()
      .toLowerCase();
  }, []);

  const attachPlanContextToOpenPlan = useCallback(async (idPlan) => {
    try {
      const [planRes, fullPlanRes] = await Promise.all([
        UserPlanService.getPlanView(idPlan),
        UserPlanService.getFullPlan(idPlan)
      ]);

      const fetchedStatus = extractStatus(planRes.data);
      setOpenPlan((prev) => {
        if (!prev || prev.idPlan !== idPlan) return prev;
        return {
          ...prev,
          ...(fetchedStatus ? { planStatus: fetchedStatus } : {})
        };
      });

      setOpenPlanCourses(fullPlanRes.data?.courses || []);
    } catch (err) {
      console.error("Could not load plan context for dashboard", err);
      setOpenPlanCourses([]);
    }
  }, [extractStatus]);

  useEffect(() => {
    const loadCurrentYearPlans = async () => {
      try {
        const yearsRes = await api.get("/year");

        const yearObj = yearsRes.data.find((y) => y.yearNumber === currentYear);
        if (!yearObj) {
          alert(`Year ${currentYear} not found`);
          return;
        }

        const yearId = yearObj.idYear;
        setCurrentYearId(yearId);

        const plansRes = await UserPlanService.getByYear(yearId);
        setPlans(plansRes.data);
        if (plansRes.data.length > 0) {
          const firstPlan = { ...plansRes.data[0] };
          setOpenPlan(firstPlan);
          await attachPlanContextToOpenPlan(firstPlan.idPlan);
        } else {
          setOpenPlan(null);
          setOpenPlanCourses([]);
        }
      } catch (err) {
        console.error(err);
        alert("Failed loading dashboard");
      }
    };

    loadCurrentYearPlans();
  }, [currentYear, attachPlanContextToOpenPlan]);

  useEffect(() => {
    if (!isCourseModalOpen || courseMode !== "existing") return;

    if (courseSearch.trim().length < 2) {
      setCourseOptions([]);
      return;
    }

    const timeoutId = setTimeout(async () => {
      try {
        const res = await UserPlanService.searchCoursesAutocomplete(courseSearch.trim());
        setCourseOptions(res.data || []);
      } catch (err) {
        console.error(err);
        setCourseOptions([]);
      }
    }, 250);

    return () => clearTimeout(timeoutId);
  }, [courseSearch, courseMode, isCourseModalOpen]);

  const handleOpenPlanTextChange = (e) => {
    const { name, value } = e.target;
    setOpenPlan((prev) => ({ ...prev, [name]: value }));
  };

  const handleOpenPlanNumberChange = (e) => {
    const { name, value } = e.target;
    const parsed = value === "" ? "" : Number(value);
    setOpenPlan((prev) => ({ ...prev, [name]: Number.isNaN(parsed) ? "" : parsed }));
  };

  const handleOpenPlanSelect = (e) => {
    const selectedId = Number(e.target.value);
    const selected = plans.find((p) => p.idPlan === selectedId);
    setOpenPlan(selected ? { ...selected } : null);
    setSaveMessage("");

    if (selected?.idPlan) {
      attachPlanContextToOpenPlan(selected.idPlan);
    } else {
      setOpenPlanCourses([]);
    }
  };

  const handleSaveOpenPlan = async () => {
    if (!openPlan) return;

    try {
      setIsSaving(true);
      setSaveMessage("");

      const updateDto = {
        numOfProjects: Number(openPlan.numOfProjects) || 0,
        numOfArticles: Number(openPlan.numOfArticles) || 0,
        partInConf: openPlan.partInConf || "",
        partInConfEnd: openPlan.partInConfEnd || "",
        comAbConf: openPlan.comAbConf || "",
        comAbConfEnd: openPlan.comAbConfEnd || "",
        numOfCourses: Number(openPlan.numOfCourses) || 0,
        numOfStudWork: Number(openPlan.numOfStudWork) || 0,
        promoOfResearch: openPlan.promoOfResearch || "",
        promoOfResearchEnd: openPlan.promoOfResearchEnd || "",
        adminWork: openPlan.adminWork || "",
        adminWorkEnd: openPlan.adminWorkEnd || "",
        projApplicSub: openPlan.projApplicSub || "",
        projApplicSubEnd: openPlan.projApplicSubEnd || "",
        skillsDevelopment: openPlan.skillsDevelopment || "",
        skillsDevelopmentEnd: openPlan.skillsDevelopmentEnd || "",
        participationInSeminars: openPlan.participationInSeminars || "",
        participationInSeminarsEnd: openPlan.participationInSeminarsEnd || "",
        otherJobs: openPlan.otherJobs || "",
        otherJobsEnd: openPlan.otherJobsEnd || ""
      };

      await UserPlanService.updatePlanForUserByOpenPlan(updateDto);
      setSaveMessage("Plan updated successfully.");

      const refreshed = await UserPlanService.getByYear(currentYearId);
      setPlans(refreshed.data);

      const refreshedPlan = refreshed.data.find((p) => p.idPlan === openPlan.idPlan);
      if (refreshedPlan) {
        setOpenPlan({ ...refreshedPlan });
        await attachPlanContextToOpenPlan(refreshedPlan.idPlan);
      } else {
        setOpenPlan(null);
        setOpenPlanCourses([]);
      }
    } catch (err) {
      console.error(err);
      setSaveMessage(err.response?.data?.message || "Failed to update open plan.");
    } finally {
      setIsSaving(false);
    }
  };

  const resetCourseModal = () => {
    setCourseMode("existing");
    setCourseSearch("");
    setCourseOptions([]);
    setSelectedCourse(null);
    setCourseWorkDone("");
    setNewCourse({
      name: "",
      ectsCredits: "",
      semester: "",
      faculty: ""
    });
  };

  const openCourseModal = () => {
    resetCourseModal();
    setIsCourseModalOpen(true);
  };

  const closeCourseModal = () => {
    setIsCourseModalOpen(false);
    resetCourseModal();
  };

  const handleSaveCourseFromModal = async () => {
    if (!openPlan?.idPlan) return;

    if (!courseWorkDone.trim()) {
      setSaveMessage("Please provide work done for course.");
      return;
    }

    try {
      setIsCourseSaving(true);

      if (courseMode === "existing") {
        if (!selectedCourse?.idCourse) {
          setSaveMessage("Please select course from autocomplete.");
          return;
        }

        await UserPlanService.saveCoursePlan(
          selectedCourse.idCourse,
          openPlan.idPlan,
          courseWorkDone.trim()
        );
      } else {
        if (
          !newCourse.name.trim() ||
          !newCourse.ectsCredits ||
          !newCourse.semester.trim() ||
          !newCourse.faculty.trim()
        ) {
          setSaveMessage("Please fill all new course fields.");
          return;
        }

        await UserPlanService.createCourseForPlan(openPlan.idPlan, courseWorkDone.trim(), {
          name: newCourse.name.trim(),
          ectsCredits: Number(newCourse.ectsCredits),
          semester: newCourse.semester.trim(),
          faculty: newCourse.faculty.trim()
        });
      }

      await attachPlanContextToOpenPlan(openPlan.idPlan);
      setSaveMessage("Course saved and attached to plan.");
      closeCourseModal();
    } catch (err) {
      console.error(err);
      setSaveMessage(err.response?.data?.message || err.response?.data || "Failed to save course.");
    } finally {
      setIsCourseSaving(false);
    }
  };

  const formatCourseText = (course) => {
    const labels = {
      idCourse: "ID",
      name: "Name",
      ectsCredits: "ECTS",
      semester: "Semester",
      faculty: "Faculty",
      workDone: "Work done"
    };

    return Object.entries(course || {})
      .filter(([, value]) => value !== null && value !== undefined && value !== "" && typeof value !== "object")
      .map(([key, value]) => `${labels[key] || key}: ${value}`)
      .join(" | ");
  };

  const normalizedStatus = extractStatus(openPlan);
  const isPlannedFrozen = normalizedStatus === "planned_frozen";

  return (
    <div>
      <h2>User Dashboard</h2>
      <h3>
        Current Year Plans ({currentYear})
        {currentYearId && ` (ID: ${currentYearId})`}
      </h3>

      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>ID</th>
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
              <td colSpan="6">No plans for current year</td>
            </tr>
          ) : (
            plans.map((pl) => (
              <tr key={pl.idPlan}>
                <td>{pl.idPlan}</td>
                <td>{pl.numOfProjects}</td>
                <td>{pl.numOfArticles}</td>
                <td>{pl.numOfCourses}</td>
                <td>{pl.numOfStudWork}</td>
                <td>
                  <button onClick={() => navigate(`/user/full-plan/${pl.idPlan}`)}>
                    Open
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>

      <br />
      <button onClick={() => navigate("/user/plans")}>View All Plans</button>

      <h2 style={{ marginTop: 20 }}>Open Plan Activities</h2>

      {plans.length > 0 && (
        <div style={{ marginBottom: 10 }}>
          <label>Plan:</label>{" "}
          <select value={openPlan?.idPlan || ""} onChange={handleOpenPlanSelect}>
            {plans.map((pl) => (
              <option key={pl.idPlan} value={pl.idPlan}>
                Plan #{pl.idPlan}
              </option>
            ))}
          </select>
        </div>
      )}

      <table border="1" cellPadding="6" width="100%">
        <thead>
          <tr>
            <th>Activity</th>
            <th>Planned</th>
            <th>Done</th>
            <th>Buttons</th>
          </tr>
        </thead>
        <tbody>
          {!openPlan ? (
            <tr>
              <td colSpan="4">No open plan available.</td>
            </tr>
          ) : (
            tableRows.map((row) => (
              <tr key={row.key}>
                <td>{row.label}</td>
                <td>
                  {row.type === "number" ? (
                    <input
                      type="number"
                      name={row.planned}
                      value={openPlan[row.planned] ?? ""}
                      onChange={handleOpenPlanNumberChange}
                      disabled={isPlannedFrozen}
                      style={{ width: "95%" }}
                    />
                  ) : (
                    <textarea
                      name={row.planned}
                      value={openPlan[row.planned] || ""}
                      onChange={handleOpenPlanTextChange}
                      disabled={isPlannedFrozen}
                      rows={2}
                      style={{ width: "95%", resize: "vertical" }}
                    />
                  )}
                </td>
                <td>
                  {row.done.endsWith("DTO") ? (
                    row.done === "CourseDTO" ? (
                      openPlanCourses.length > 0 ? (
                        <ol style={{ margin: 0, paddingLeft: 20 }}>
                          {openPlanCourses.map((course, idx) => (
                            <li key={`${course.idCourse || idx}-${idx}`}>
                              {formatCourseText(course)}
                            </li>
                          ))}
                        </ol>
                      ) : (
                        <span>No courses attached</span>
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
                      style={{ width: "95%", resize: "vertical" }}
                    />
                  )}
                </td>
                <td>
                  {row.actions?.length ? (
                    <>
                      <button
                        type="button"
                        disabled={isPlannedFrozen}
                        onClick={
                          row.key === "courses"
                            ? openCourseModal
                            : () => setSaveMessage(`${row.actions[0]} is not connected yet.`)
                        }
                      >
                        {row.actions[0]}
                      </button>
                      {" "}
                      <button
                        type="button"
                        disabled={isPlannedFrozen}
                        onClick={() => setSaveMessage(`${row.actions[1]} is not connected yet.`)}
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
                <button type="button" onClick={handleSaveOpenPlan} disabled={isSaving}>
                  {isSaving ? "Saving..." : "Save changes"}
                </button>
              </td>
              <td></td>
            </tr>
          )}
        </tbody>
      </table>

      {isCourseModalOpen && (
        <div
          style={{
            position: "fixed",
            inset: 0,
            background: "rgba(0, 0, 0, 0.45)",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            zIndex: 1000
          }}
        >
          <div style={{ background: "#fff", width: 700, maxWidth: "95%", padding: 16 }}>
            <h3>Add Course To Plan #{openPlan?.idPlan}</h3>

            <div style={{ marginBottom: 10 }}>
              <label>
                <input
                  type="radio"
                  name="courseMode"
                  checked={courseMode === "existing"}
                  onChange={() => setCourseMode("existing")}
                />
                {" "}Use existing course
              </label>
              {"  "}
              <label>
                <input
                  type="radio"
                  name="courseMode"
                  checked={courseMode === "new"}
                  onChange={() => setCourseMode("new")}
                />
                {" "}Create new course
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
                          setCourseSearch(
                            `${course.name} (ECTS: ${course.ectsCredits}, ${course.semester}, ${course.faculty})`
                          );
                          setCourseOptions([]);
                        }}
                        style={{ padding: 8, cursor: "pointer", borderBottom: "1px solid #eee" }}
                      >
                        {course.name} | ECTS: {course.ectsCredits} | Semester: {course.semester} | Faculty: {course.faculty}
                      </div>
                    ))}
                  </div>
                )}
              </div>
            ) : (
              <div style={{ marginBottom: 10 }}>
                <label>Name</label>
                <input
                  type="text"
                  value={newCourse.name}
                  onChange={(e) => setNewCourse((prev) => ({ ...prev, name: e.target.value }))}
                  style={{ width: "100%", marginBottom: 6 }}
                />
                <label>ECTS</label>
                <input
                  type="number"
                  value={newCourse.ectsCredits}
                  onChange={(e) => setNewCourse((prev) => ({ ...prev, ectsCredits: e.target.value }))}
                  style={{ width: "100%", marginBottom: 6 }}
                />
                <label>Semester</label>
                <input
                  type="text"
                  value={newCourse.semester}
                  onChange={(e) => setNewCourse((prev) => ({ ...prev, semester: e.target.value }))}
                  style={{ width: "100%", marginBottom: 6 }}
                />
                <label>Faculty</label>
                <input
                  type="text"
                  value={newCourse.faculty}
                  onChange={(e) => setNewCourse((prev) => ({ ...prev, faculty: e.target.value }))}
                  style={{ width: "100%" }}
                />
              </div>
            )}

            <div style={{ marginBottom: 10 }}>
              <label>Work done</label>
              <textarea
                rows={3}
                value={courseWorkDone}
                onChange={(e) => setCourseWorkDone(e.target.value)}
                style={{ width: "100%", marginTop: 4, resize: "vertical" }}
              />
            </div>

            <button type="button" onClick={handleSaveCourseFromModal} disabled={isCourseSaving}>
              {isCourseSaving ? "Saving..." : "Save Course"}
            </button>
            {" "}
            <button type="button" onClick={closeCourseModal} disabled={isCourseSaving}>
              Cancel
            </button>
          </div>
        </div>
      )}

      {saveMessage && (
        <p style={{ color: saveMessage.toLowerCase().includes("success") ? "green" : "red" }}>
          {saveMessage}
        </p>
      )}
    </div>
  );
}
