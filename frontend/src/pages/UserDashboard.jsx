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
  const [locallyDeletedCourseIds, setLocallyDeletedCourseIds] = useState([]);
  const [saveMessage, setSaveMessage] = useState("");
  const [isSaving, setIsSaving] = useState(false);

  const [isCourseModalOpen, setIsCourseModalOpen] = useState(false);
  const [courseMode, setCourseMode] = useState("existing");
  const [courseSearch, setCourseSearch] = useState("");
  const [courseOptions, setCourseOptions] = useState([]);
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [courseWorkDone, setCourseWorkDone] = useState("");
  const [isCourseSaving, setIsCourseSaving] = useState(false);
  const [courseModalMessage, setCourseModalMessage] = useState("");
  const [newCourse, setNewCourse] = useState({
    name: "",
    ectsCredits: "",
    semester: "",
    faculty: ""
  });
  const [isCourseDeleteModalOpen, setIsCourseDeleteModalOpen] = useState(false);
  const [isCourseDeleting, setIsCourseDeleting] = useState(false);
  const [courseDeleteMessage, setCourseDeleteMessage] = useState("");
  const [isCourseEditModalOpen, setIsCourseEditModalOpen] = useState(false);
  const [courseEditMessage, setCourseEditMessage] = useState("");
  const [courseEditWorkDone, setCourseEditWorkDone] = useState("");
  const [courseEditTarget, setCourseEditTarget] = useState(null);
  const [isCourseEditing, setIsCourseEditing] = useState(false);

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

  const normalizeMessage = (msg) => {
    if (msg === null || msg === undefined) return "";
    if (typeof msg === "string") return msg;
    if (typeof msg === "number" || typeof msg === "boolean") return String(msg);
    try {
      if (typeof msg.message === "string") return msg.message;
      return JSON.stringify(msg);
    } catch {
      return String(msg);
    }
  };

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

      const fullPlan = fullPlanRes.data || {};
      console.log("FULL PLAN:", fullPlan);
      if (Array.isArray(fullPlan.coursePlans) && fullPlan.coursePlans.length > 0) {
        const normalized = fullPlan.coursePlans
          .filter((cp) => !cp?.deleted && !cp?.isDeleted && !cp?.course?.deleted && !cp?.course?.isDeleted)
          .map((cp) => {
            const courseData = {
              ...(cp.course || {}),
              ...(cp.courseDTO || {})
            };

            return {
              ...courseData,
              idCoursePlan: cp.idCoursePlan ?? cp.idCoursePlanDTO ?? cp.coursePlanId ?? null,
              workDone: cp.workDone ?? cp.work_done ?? ""
            };
          });
        setOpenPlanCourses(normalized);
        setLocallyDeletedCourseIds([]);
      } else if (Array.isArray(fullPlan.coursePlanDTOs) && fullPlan.coursePlanDTOs.length > 0) {
        const courses = Array.isArray(fullPlan.courses) ? fullPlan.courses : [];
        const normalized = fullPlan.coursePlanDTOs.map((cp) => {
          const matched = courses.find((c) => Number(c.idCourse) === Number(cp.idCourse)) || {};
          return {
            ...matched,
            idCoursePlan: cp.idCoursePlan ?? cp.idCoursePlanDTO ?? cp.coursePlanId ?? null,
            workDone: cp.workDone ?? ""
          };
        }).filter((cp) => !cp?.deleted && !cp?.isDeleted);
        setOpenPlanCourses(normalized);
        setLocallyDeletedCourseIds([]);
      } else {
        const courses = Array.isArray(fullPlan.courses) ? fullPlan.courses : [];
        const filtered = courses.filter((c) => !c?.deleted && !c?.isDeleted);
        const deletedSet = new Set(locallyDeletedCourseIds.map((id) => Number(id)));
        const visible = filtered.filter((c) => !deletedSet.has(Number(c?.idCourse)));
        setOpenPlanCourses((prev) => {
          if (visible.length === 0 && prev && prev.length > 0) return prev;
          return visible;
        });
      }
    } catch (err) {
      console.error("Could not load plan context for dashboard", err);
      setOpenPlanCourses([]);
    }
  }, [extractStatus, locallyDeletedCourseIds]);

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
      setSaveMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to update open plan."));
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
    setCourseModalMessage("");
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

  const openCourseDeleteModal = () => {
    setCourseDeleteMessage("");
    setIsCourseDeleteModalOpen(true);
  };

  const closeCourseDeleteModal = () => {
    setIsCourseDeleteModalOpen(false);
    setCourseDeleteMessage("");
  };

  const openCourseEditModal = (course) => {
    setCourseEditTarget(course);
    setCourseEditWorkDone(getWorkDoneText(course));
    setCourseEditMessage("");
    setIsCourseEditModalOpen(true);
  };

  const closeCourseEditModal = () => {
    setIsCourseEditModalOpen(false);
    setCourseEditTarget(null);
    setCourseEditWorkDone("");
    setCourseEditMessage("");
  };

  const handleSaveCourseFromModal = async () => {
    if (!openPlan?.idPlan) return;

    if (!courseWorkDone.trim()) {
      setCourseModalMessage("Please provide work done for course.");
      return;
    }

    try {
      setIsCourseSaving(true);

      if (courseMode === "existing") {
        if (!selectedCourse?.idCourse) {
          setCourseModalMessage("Please select course from autocomplete.");
          return;
        }
        const alreadyAdded = openPlanCourses.some((c) => {
          const existingId = c?.idCourse ?? c?.courseId ?? c?.course?.idCourse;
          return Number(existingId) === Number(selectedCourse.idCourse);
        });
        if (alreadyAdded) {
          setCourseModalMessage("This course is already attached to the plan.");
          return;
        }

        await UserPlanService.saveCoursePlan(
          selectedCourse.idCourse,
          openPlan.idPlan,
          courseWorkDone.trim()
        );
        setLocallyDeletedCourseIds((prev) =>
          prev.filter((id) => Number(id) !== Number(selectedCourse.idCourse))
        );
      } else {
        if (
          !newCourse.name.trim() ||
          !newCourse.ectsCredits ||
          !newCourse.semester.trim() ||
          !newCourse.faculty.trim()
        ) {
          setCourseModalMessage("Please fill all new course fields.");
          return;
        }
        const normalizedNew = {
          name: newCourse.name.trim().toLowerCase(),
          ectsCredits: Number(newCourse.ectsCredits),
          semester: newCourse.semester.trim().toLowerCase(),
          faculty: newCourse.faculty.trim().toLowerCase()
        };
        const duplicateByFields = openPlanCourses.some((c) => {
          const existing = {
            name: (c?.name || c?.courseName || "").toString().trim().toLowerCase(),
            ectsCredits: Number(c?.ectsCredits ?? c?.ects ?? 0),
            semester: (c?.semester || "").toString().trim().toLowerCase(),
            faculty: (c?.faculty || "").toString().trim().toLowerCase()
          };
          return (
            existing.name &&
            existing.name === normalizedNew.name &&
            existing.ectsCredits === normalizedNew.ectsCredits &&
            existing.semester === normalizedNew.semester &&
            existing.faculty === normalizedNew.faculty
          );
        });
        if (duplicateByFields) {
          setCourseModalMessage("This course already exists in the plan.");
          return;
        }

        const createRes = await UserPlanService.createCourseForPlan(openPlan.idPlan, courseWorkDone.trim(), {
          name: newCourse.name.trim(),
          ectsCredits: Number(newCourse.ectsCredits),
          semester: newCourse.semester.trim(),
          faculty: newCourse.faculty.trim()
        });
        const createdId = createRes?.data?.idCourse;
        if (createdId) {
          setLocallyDeletedCourseIds((prev) =>
            prev.filter((id) => Number(id) !== Number(createdId))
          );
        }
      }

      await attachPlanContextToOpenPlan(openPlan.idPlan);
      setCourseModalMessage("");
      setSaveMessage("Course saved and attached to plan.");
      closeCourseModal();
    } catch (err) {
      console.error(err);
      setCourseModalMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to save course."));
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

  const getWorkDoneText = (course) =>
    course?.workDone ??
    course?.work_done ??
    course?.coursePlan?.workDone ??
    course?.coursePlan?.work_done ??
    course?.coursePlanDTO?.workDone ??
    course?.coursePlanDTO?.work_done ??
    "";

  const getCoursePlanId = (course) =>
    course?.idCoursePlan ??
    course?.coursePlanId ??
    course?.idCoursePlanDTO ??
    course?.coursePlan?.idCoursePlan ??
    course?.coursePlan?.idCoursePlanDTO ??
    course?.planCourseId ??
    null;

  const handleDeleteCoursePlan = async (course) => {
    const idCourse =
      course?.idCourse ??
      course?.courseId ??
      course?.course?.idCourse ??
      course?.courseDTO?.idCourse ??
      null;
    if (!idCourse) {
      setCourseDeleteMessage("Course ID not found, cannot delete.");
      return;
    }
    if (!openPlan?.idPlan) {
      setCourseDeleteMessage("Plan is not selected.");
      return;
    }

    if (!window.confirm("Delete this course from plan?")) return;

    try {
      setIsCourseDeleting(true);
      await UserPlanService.deleteCoursePlan(openPlan.idPlan, idCourse);
      setOpenPlanCourses((prev) =>
        prev.filter((c) => {
          const cid =
            c?.idCourse ??
            c?.courseId ??
            c?.course?.idCourse ??
            c?.courseDTO?.idCourse ??
            null;
          return Number(cid) !== Number(idCourse);
        })
      );
      setLocallyDeletedCourseIds((prev) => {
        const next = new Set(prev.map((id) => Number(id)));
        next.add(Number(idCourse));
        return Array.from(next);
      });
      await attachPlanContextToOpenPlan(openPlan.idPlan);
      setCourseDeleteMessage("");
      setSaveMessage("Course deleted from plan.");
      closeCourseDeleteModal();
    } catch (err) {
      console.error(err);
      setCourseDeleteMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to delete course."));
    } finally {
      setIsCourseDeleting(false);
    }
  };

  const handleEditCoursePlan = async () => {
    if (!courseEditTarget) return;
    if (!openPlan?.idPlan) {
      setCourseEditMessage("Plan is not selected.");
      return;
    }
    const idCourse =
      courseEditTarget?.idCourse ??
      courseEditTarget?.courseId ??
      courseEditTarget?.course?.idCourse ??
      courseEditTarget?.courseDTO?.idCourse ??
      null;
    if (!idCourse) {
      setCourseEditMessage("Course ID not found.");
      return;
    }
    if (!courseEditWorkDone.trim()) {
      setCourseEditMessage("Please provide work done.");
      return;
    }

    try {
      setIsCourseEditing(true);
      const dto = {
        idPlan: openPlan.idPlan,
        idCourse,
        workDone: courseEditWorkDone.trim()
      };
      await UserPlanService.updateCoursePlanWorkDone(
        openPlan.idPlan,
        idCourse,
        courseEditWorkDone.trim(),
        dto
      );
      setOpenPlanCourses((prev) =>
        prev.map((c) => {
          const cid =
            c?.idCourse ??
            c?.courseId ??
            c?.course?.idCourse ??
            c?.courseDTO?.idCourse ??
            null;
          if (Number(cid) !== Number(idCourse)) return c;
          return { ...c, workDone: courseEditWorkDone.trim() };
        })
      );
      await attachPlanContextToOpenPlan(openPlan.idPlan);
      setSaveMessage("Course updated.");
      closeCourseEditModal();
    } catch (err) {
      console.error(err);
      setCourseEditMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to update course."));
    } finally {
      setIsCourseEditing(false);
    }
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
                              {" "}
                              <button type="button" onClick={() => openCourseEditModal(course)}>
                                Edit
                              </button>
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
                        onClick={
                          row.key === "courses"
                            ? openCourseDeleteModal
                            : () => setSaveMessage(`${row.actions[1]} is not connected yet.`)
                        }
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
                        {(() => {
                          const existing = openPlanCourses.find(
                            (c) => Number(c?.idCourse) === Number(course.idCourse)
                          );
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
            {courseModalMessage && (
              <p style={{ color: "red", marginTop: 8 }}>{courseModalMessage}</p>
            )}
          </div>
        </div>
      )}

      {isCourseDeleteModalOpen && (
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
            <h3>Delete Course From Plan #{openPlan?.idPlan}</h3>

            {openPlanCourses.length === 0 ? (
              <p>No courses attached.</p>
            ) : (
              <ol style={{ paddingLeft: 20 }}>
                {openPlanCourses.map((course, idx) => (
                  <li key={`${getCoursePlanId(course) || idx}-${idx}`} style={{ marginBottom: 8 }}>
                    <div>{formatCourseText(course)}</div>
                    <button
                      type="button"
                      onClick={() => handleDeleteCoursePlan(course)}
                      style={{ marginTop: 4 }}
                    >
                      {isCourseDeleting ? "Deleting..." : "Delete"}
                    </button>
                  </li>
                ))}
              </ol>
            )}

            <button type="button" onClick={closeCourseDeleteModal} disabled={isCourseDeleting}>
              Close
            </button>
            {courseDeleteMessage && (
              <p style={{ color: "red", marginTop: 8 }}>{courseDeleteMessage}</p>
            )}
          </div>
        </div>
      )}

      {isCourseEditModalOpen && (
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
            <h3>Edit Course Work Done</h3>
            <div style={{ marginBottom: 10 }}>
              <div>{courseEditTarget ? formatCourseText(courseEditTarget) : ""}</div>
            </div>
            <div style={{ marginBottom: 10 }}>
              <label>Work done</label>
              <textarea
                rows={3}
                value={courseEditWorkDone}
                onChange={(e) => setCourseEditWorkDone(e.target.value)}
                style={{ width: "100%", marginTop: 4, resize: "vertical" }}
              />
            </div>
            <button type="button" onClick={handleEditCoursePlan} disabled={isCourseEditing}>
              {isCourseEditing ? "Saving..." : "Save"}
            </button>
            {" "}
            <button type="button" onClick={closeCourseEditModal} disabled={isCourseEditing}>
              Cancel
            </button>
            {courseEditMessage && (
              <p style={{ color: "red", marginTop: 8 }}>{courseEditMessage}</p>
            )}
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
