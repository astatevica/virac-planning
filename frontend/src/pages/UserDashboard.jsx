import React, { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import UserPlanService from "../services/UserPlanService";
import api from "../api/api";
import { exportPlanFile } from "../utils/planExport";
import CurrentYearPlansTable from "../components/user-dashboard/CurrentYearPlansTable";
import OpenPlanActivities from "../components/user-dashboard/OpenPlanActivities";
import SchedulerDeadlineCard from "../components/user-dashboard/SchedulerDeadlineCard";
import ArticleModals from "../components/user-dashboard/modals/ArticleModals";
import CourseModals from "../components/user-dashboard/modals/CourseModals";
import ProjectModals from "../components/user-dashboard/modals/ProjectModals";
import StudentWorkModals from "../components/user-dashboard/modals/StudentWorkModals";
import "./UserDashboard.css";

export default function UserDashboard() {
  const navigate = useNavigate();
  const [plans, setPlans] = useState([]);
  const [currentYearId, setCurrentYearId] = useState(null);
  const [scheduler, setScheduler] = useState(null);
  const [openPlan, setOpenPlan] = useState(null);
  const [openPlanProjects, setOpenPlanProjects] = useState([]);
  const [locallyDeletedProjectIds, setLocallyDeletedProjectIds] = useState([]);
  const [openPlanCourses, setOpenPlanCourses] = useState([]);
  const [locallyDeletedCourseIds, setLocallyDeletedCourseIds] = useState([]);
  const [openPlanArticles, setOpenPlanArticles] = useState([]);
  const [locallyDeletedArticleIds, setLocallyDeletedArticleIds] = useState([]);
  const [openPlanStudentWorks, setOpenPlanStudentWorks] = useState([]);
  const [locallyDeletedStudentWorkIds, setLocallyDeletedStudentWorkIds] = useState([]);
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
  const [courseModalFieldErrors, setCourseModalFieldErrors] = useState({});
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
  const [courseEditName, setCourseEditName] = useState("");
  const [courseEditEcts, setCourseEditEcts] = useState("");
  const [courseEditSemester, setCourseEditSemester] = useState("");
  const [courseEditFaculty, setCourseEditFaculty] = useState("");
  const [courseEditFieldErrors, setCourseEditFieldErrors] = useState({});
  const [courseEditTarget, setCourseEditTarget] = useState(null);
  const [isCourseEditing, setIsCourseEditing] = useState(false);

  const [isProjectModalOpen, setIsProjectModalOpen] = useState(false);
  const [projectSearch, setProjectSearch] = useState("");
  const [projectOptions, setProjectOptions] = useState([]);
  const [selectedProject, setSelectedProject] = useState(null);
  const [projectTasks, setProjectTasks] = useState("");
  const [projectWorkDone, setProjectWorkDone] = useState("");
  const [isProjectSaving, setIsProjectSaving] = useState(false);
  const [projectModalMessage, setProjectModalMessage] = useState("");
  const [projectModalFieldErrors, setProjectModalFieldErrors] = useState({});
  const [isProjectDeleteModalOpen, setIsProjectDeleteModalOpen] = useState(false);
  const [isProjectDeleting, setIsProjectDeleting] = useState(false);
  const [projectDeleteMessage, setProjectDeleteMessage] = useState("");
  const [isProjectEditModalOpen, setIsProjectEditModalOpen] = useState(false);
  const [projectEditMessage, setProjectEditMessage] = useState("");
  const [projectEditFieldErrors, setProjectEditFieldErrors] = useState({});
  const [projectEditTarget, setProjectEditTarget] = useState(null);
  const [projectEditName, setProjectEditName] = useState("");
  const [projectEditNumber, setProjectEditNumber] = useState("");
  const [projectEditManagementId, setProjectEditManagementId] = useState("");
  const [projectEditStartDate, setProjectEditStartDate] = useState("");
  const [projectEditEndDate, setProjectEditEndDate] = useState("");
  const [projectEditAcronym, setProjectEditAcronym] = useState("");
  const [projectEditTasks, setProjectEditTasks] = useState("");
  const [projectEditWorkDone, setProjectEditWorkDone] = useState("");
  const [isProjectEditing, setIsProjectEditing] = useState(false);

  const [isArticleModalOpen, setIsArticleModalOpen] = useState(false);
  const [articleMode, setArticleMode] = useState("existing");
  const [articleSearch, setArticleSearch] = useState("");
  const [articleOptions, setArticleOptions] = useState([]);
  const [selectedArticle, setSelectedArticle] = useState(null);
  const [articleComments, setArticleComments] = useState("");
  const [articleLink, setArticleLink] = useState("");
  const [isArticleSaving, setIsArticleSaving] = useState(false);
  const [articleModalMessage, setArticleModalMessage] = useState("");
  const [articleModalFieldErrors, setArticleModalFieldErrors] = useState({});
  const [newArticle, setNewArticle] = useState({
    name: "",
    coAuthors: "",
    idJournal: ""
  });
  const [articleJournals, setArticleJournals] = useState([]);
  const [newJournalName, setNewJournalName] = useState("");
  const [isJournalCreating, setIsJournalCreating] = useState(false);
  const [journalMessage, setJournalMessage] = useState("");
  const [isArticleDeleteModalOpen, setIsArticleDeleteModalOpen] = useState(false);
  const [isArticleDeleting, setIsArticleDeleting] = useState(false);
  const [articleDeleteMessage, setArticleDeleteMessage] = useState("");
  const [isArticleEditModalOpen, setIsArticleEditModalOpen] = useState(false);
  const [articleEditMessage, setArticleEditMessage] = useState("");
  const [articleEditComments, setArticleEditComments] = useState("");
  const [articleEditLink, setArticleEditLink] = useState("");
  const [articleEditName, setArticleEditName] = useState("");
  const [articleEditCoAuthors, setArticleEditCoAuthors] = useState("");
  const [articleEditJournalId, setArticleEditJournalId] = useState("");
  const [articleEditFieldErrors, setArticleEditFieldErrors] = useState({});
  const [articleEditTarget, setArticleEditTarget] = useState(null);
  const [isArticleEditing, setIsArticleEditing] = useState(false);

  const [isStudentWorkModalOpen, setIsStudentWorkModalOpen] = useState(false);
  const [isStudentWorkSaving, setIsStudentWorkSaving] = useState(false);
  const [studentWorkModalMessage, setStudentWorkModalMessage] = useState("");
  const [studentWorkFieldErrors, setStudentWorkFieldErrors] = useState({});
  const [newStudentWork, setNewStudentWork] = useState({
    name: "",
    studentName: "",
    studentSurname: "",
    degree: "",
    workDone: ""
  });
  const [isStudentWorkDeleteModalOpen, setIsStudentWorkDeleteModalOpen] = useState(false);
  const [isStudentWorkDeleting, setIsStudentWorkDeleting] = useState(false);
  const [studentWorkDeleteMessage, setStudentWorkDeleteMessage] = useState("");
  const [isStudentWorkEditModalOpen, setIsStudentWorkEditModalOpen] = useState(false);
  const [studentWorkEditMessage, setStudentWorkEditMessage] = useState("");
  const [studentWorkEditFieldErrors, setStudentWorkEditFieldErrors] = useState({});
  const [isStudentWorkEditing, setIsStudentWorkEditing] = useState(false);
  const [studentWorkEditForm, setStudentWorkEditForm] = useState({
    idStudWork: "",
    name: "",
    studentName: "",
    studentSurname: "",
    degree: "",
    workDone: ""
  });
  const [degreeOptions, setDegreeOptions] = useState([]);

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
      if (typeof msg.message === "string" && Array.isArray(msg.errors)) {
        const details = msg.errors
          .map((e) => {
            const field = e?.field ? `${e.field}: ` : "";
            const message = e?.message || "";
            const rejected =
              e?.rejectedValue !== null && e?.rejectedValue !== undefined
                ? ` (rejected: ${e.rejectedValue})`
                : "";
            return `${field}${message}${rejected}`.trim();
          })
          .filter(Boolean)
          .join("; ");
        return details ? `${msg.message}. ${details}` : msg.message;
      }
      if (typeof msg.message === "string") return msg.message;
      return JSON.stringify(msg);
    } catch {
      return String(msg);
    }
  };

  const extractFieldErrors = (err) => {
    const data = err?.response?.data;
    if (!data || !Array.isArray(data.errors)) return {};
    const map = {};
    data.errors.forEach((e) => {
      if (e?.field) map[e.field] = e.message || "Invalid value";
    });
    return map;
  };

  const buttonStyles = {
    add: {
      backgroundColor: "#2e7d32",
      color: "#fff",
      border: "none",
      padding: "6px 10px",
      borderRadius: 4
    },
    update: {
      backgroundColor: "#2e7d32",
      color: "#fff",
      border: "none",
      padding: "6px 10px",
      borderRadius: 4
    },
    edit: {
      backgroundColor: "#ef6c00",
      color: "#fff",
      border: "none",
      padding: "6px 10px",
      borderRadius: 4
    },
    cancel: {
      backgroundColor: "#ef6c00",
      color: "#fff",
      border: "none",
      padding: "6px 10px",
      borderRadius: 4
    },
    delete: {
      backgroundColor: "#c62828",
      color: "#fff",
      border: "none",
      padding: "6px 10px",
      borderRadius: 4
    },
    view: {
      backgroundColor: "#1565c0",
      color: "#fff",
      border: "none",
      padding: "6px 10px",
      borderRadius: 4
    }
  };

  const getButtonStyle = (variant, disabled = false) => ({
    ...buttonStyles[variant],
    opacity: disabled ? 0.6 : 1,
    cursor: disabled ? "not-allowed" : "pointer"
  });

  const handleExportPlan = async (idPlan, type) => {
    await exportPlanFile(idPlan, type);
  };

  const toDateInputValue = (value) => {
    if (!value) return "";
    if (typeof value === "string") return value.slice(0, 10);
    if (typeof value === "object" && value.year && value.month && value.day) {
      const mm = String(value.month).padStart(2, "0");
      const dd = String(value.day).padStart(2, "0");
      return `${value.year}-${mm}-${dd}`;
    }
    return "";
  };

  const formatSchedulerDate = (value) => {
    const normalized = toDateInputValue(value);
    if (!normalized) return "Not set";
    return normalized;
  };

  const getDaysUntilDate = (value) => {
    const normalized = toDateInputValue(value);
    if (!normalized) return null;

    const today = new Date();
    const target = new Date(`${normalized}T00:00:00`);
    const todayStart = new Date(
      today.getFullYear(),
      today.getMonth(),
      today.getDate()
    );
    const diffMs = target.getTime() - todayStart.getTime();

    return Math.ceil(diffMs / (1000 * 60 * 60 * 24));
  };

  const getDeadlineClassName = (daysLeft) => {
    if (daysLeft === null) return "user-dashboard-deadline-neutral";
    if (daysLeft < 0) return "user-dashboard-deadline-expired";
    if (daysLeft <= 14) return "user-dashboard-deadline-warning";
    return "user-dashboard-deadline-ok";
  };

  const getProjectId = (project) =>
    project?.idProject ??
    project?.projectId ??
    project?.idProjectDTO ??
    project?.project?.idProject ??
    project?.projectDTO?.idProject ??
    null;

  const getProjectPlanId = (project) =>
    project?.idProjectPlan ??
    project?.projectPlanId ??
    project?.idProjectPlanDTO ??
    project?.projectPlan?.idProjectPlan ??
    project?.projectPlan?.idProjectPlanDTO ??
    project?.planProjectId ??
    null;

  const attachPlanContextToOpenPlan = useCallback(async (idPlan, overrides = {}) => {
    const deletedProjectIds = overrides.deletedProjectIds ?? locallyDeletedProjectIds;
    const deletedCourseIds = overrides.deletedCourseIds ?? locallyDeletedCourseIds;
    const deletedArticleIds = overrides.deletedArticleIds ?? locallyDeletedArticleIds;
    const deletedStudentWorkIds = overrides.deletedStudentWorkIds ?? locallyDeletedStudentWorkIds;
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
      if (Array.isArray(fullPlan.projectPlans) && fullPlan.projectPlans.length > 0) {
        const deletedSet = new Set(deletedProjectIds.map((id) => Number(id)));
        const normalized = fullPlan.projectPlans
          .filter((pp) => !pp?.deleted && !pp?.isDeleted && !pp?.project?.deleted && !pp?.project?.isDeleted)
          .map((pp) => {
            const projectData = {
              ...(pp.project || {}),
              ...(pp.projectDTO || {})
            };

            return {
              ...projectData,
              idProjectPlan: pp.idProjectPlan ?? pp.idProjectPlanDTO ?? pp.projectPlanId ?? null,
              tasks: pp.tasks ?? pp.projectTasks ?? pp.project_tasks ?? "",
              workDone: pp.workDone ?? pp.work_done ?? ""
            };
          })
          .filter((pp) => !deletedSet.has(Number(getProjectId(pp))));
        setOpenPlanProjects(normalized);
        setLocallyDeletedProjectIds([]);
      } else if (Array.isArray(fullPlan.projectPlanDTOs) && fullPlan.projectPlanDTOs.length > 0) {
        const projects = Array.isArray(fullPlan.projects) ? fullPlan.projects : [];
        const deletedSet = new Set(deletedProjectIds.map((id) => Number(id)));
        const normalized = fullPlan.projectPlanDTOs.map((pp) => {
          const idProject =
            pp.idProject ??
            pp.projectId ??
            pp.idProjectDTO ??
            null;
          const matched = projects.find((p) => Number(p.idProject ?? p.projectId) === Number(idProject)) || {};
          return {
            ...matched,
            idProject: matched.idProject ?? idProject,
            idProjectPlan: pp.idProjectPlan ?? pp.idProjectPlanDTO ?? pp.projectPlanId ?? null,
            tasks: pp.tasks ?? pp.projectTasks ?? pp.project_tasks ?? "",
            workDone: pp.workDone ?? pp.work_done ?? ""
          };
        }).filter((pp) => !pp?.deleted && !pp?.isDeleted && !deletedSet.has(Number(getProjectId(pp))));
        setOpenPlanProjects(normalized);
        setLocallyDeletedProjectIds([]);
      } else {
        const projects = Array.isArray(fullPlan.projects) ? fullPlan.projects : [];
        const filtered = projects.filter((p) => !p?.deleted && !p?.isDeleted);
        const deletedSet = new Set(deletedProjectIds.map((id) => Number(id)));
        const visible = filtered.filter((p) => !deletedSet.has(Number(getProjectId(p))));
        setOpenPlanProjects((prev) => {
          if (visible.length === 0 && prev && prev.length > 0) return prev;
          return visible;
        });
      }

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
        const deletedSet = new Set(deletedCourseIds.map((id) => Number(id)));
        const visible = filtered.filter((c) => !deletedSet.has(Number(c?.idCourse)));
        setOpenPlanCourses((prev) => {
          if (visible.length === 0 && prev && prev.length > 0) return prev;
          return visible;
        });
      }

      if (Array.isArray(fullPlan.articlePlans) && fullPlan.articlePlans.length > 0) {
        const deletedSet = new Set(deletedArticleIds.map((id) => Number(id)));
        const normalized = fullPlan.articlePlans
          .filter((ap) => !ap?.deleted && !ap?.isDeleted && !ap?.article?.deleted && !ap?.article?.isDeleted)
          .map((ap) => {
            const articleData = {
              ...(ap.article || {}),
              ...(ap.articleDTO || {}),
              ...(ap.scientificArticle || {}),
              ...(ap.scientificArticleDTO || {})
            };

            return {
              ...articleData,
              idArticle:
                articleData.idArticle ??
                ap.idArticle ??
                ap.idScientificArticles ??
                ap.idScientificArticle ??
                ap.articleId ??
                null,
              idArticlePlan: ap.idArticlePlan ?? ap.idArticlePlanDTO ?? ap.articlePlanId ?? null,
              articleComments:
                ap.articleComments ??
                ap.articleComment ??
                ap.comments ??
                ap.article_comments ??
                "",
              publicationLink:
                ap.publicationLink ??
                ap.publication_link ??
                ap.link ??
                ap.publicationUrl ??
                ap.publication_url ??
                ""
            };
          })
          .filter((ap) => !deletedSet.has(Number(getArticleId(ap))));
        setOpenPlanArticles(normalized);
        setLocallyDeletedArticleIds([]);
      } else if (Array.isArray(fullPlan.articlePlanDTOs) && fullPlan.articlePlanDTOs.length > 0) {
        const articles = Array.isArray(fullPlan.articles) ? fullPlan.articles : [];
        const deletedSet = new Set(deletedArticleIds.map((id) => Number(id)));
        const normalized = fullPlan.articlePlanDTOs.map((ap) => {
          const idArticle =
            ap.idArticle ??
            ap.idScientificArticles ??
            ap.idScientificArticle ??
            ap.articleId ??
            null;
          const matched = articles.find(
            (a) =>
              Number(a.idArticle ?? a.idScientificArticles ?? a.idScientificArticle ?? a.articleId) ===
              Number(idArticle)
          ) || {};
          return {
            ...matched,
            idArticle: matched.idArticle ?? idArticle,
            idArticlePlan: ap.idArticlePlan ?? ap.idArticlePlanDTO ?? ap.articlePlanId ?? null,
            articleComments: ap.articleComments ?? ap.articleComment ?? ap.comments ?? "",
            publicationLink:
              ap.publicationLink ??
              ap.publication_link ??
              ap.link ??
              ap.publicationUrl ??
              ap.publication_url ??
              ""
          };
        }).filter((ap) => !ap?.deleted && !ap?.isDeleted && !deletedSet.has(Number(getArticleId(ap))));
        setOpenPlanArticles(normalized);
        setLocallyDeletedArticleIds([]);
      } else {
        const articles = Array.isArray(fullPlan.articles) ? fullPlan.articles : [];
        const filtered = articles.filter((a) => !a?.deleted && !a?.isDeleted);
        const deletedSet = new Set(deletedArticleIds.map((id) => Number(id)));
        const visible = filtered.map((a) => ({
          ...a,
          idArticle:
            a?.idArticle ?? a?.idScientificArticles ?? a?.idScientificArticle ?? a?.articleId ?? null
        })).filter((a) => {
          const idArticle =
            a?.idArticle ?? a?.idScientificArticles ?? a?.idScientificArticle ?? a?.articleId ?? null;
          return !deletedSet.has(Number(idArticle));
        });
        setOpenPlanArticles((prev) => {
          if (visible.length === 0 && prev && prev.length > 0) return prev;
          return visible;
        });
      }

      const deletedWorkSet = new Set(deletedStudentWorkIds.map((id) => Number(id)));
      if (Array.isArray(fullPlan.workPlans) && fullPlan.workPlans.length > 0) {
        const normalized = fullPlan.workPlans
          .filter((wp) => !wp?.deleted && !wp?.isDeleted && !wp?.studentWork?.deleted && !wp?.studentWork?.isDeleted)
          .map((wp) => ({
            ...(wp.studentWork || {}),
            ...(wp.studentWorkDTO || {}),
            idStudWork: wp.idStudWork ?? wp.idStudentWork ?? wp.studentWorkId ?? wp.studentWorkDTO?.idStudWork ?? null,
            workDone: wp.workDone ?? wp.work_done ?? ""
          }))
          .filter((w) => !deletedWorkSet.has(Number(w.idStudWork)));
        setOpenPlanStudentWorks(normalized);
        setLocallyDeletedStudentWorkIds([]);
      } else {
        const works = Array.isArray(fullPlan.studentWork)
          ? fullPlan.studentWork
          : Array.isArray(fullPlan.studentWorks)
            ? fullPlan.studentWorks
            : [];
        const visible = works
          .filter((w) => !w?.deleted && !w?.isDeleted)
          .map((w) => ({
            ...w,
            idStudWork: w.idStudWork ?? w.idStudentWork ?? w.studentWorkId ?? null
          }))
          .filter((w) => !deletedWorkSet.has(Number(w.idStudWork)));
        setOpenPlanStudentWorks((prev) => {
          if (visible.length === 0 && prev && prev.length > 0) return prev;
          return visible;
        });
      }
    } catch (err) {
      console.error("Could not load plan context for dashboard", err);
      setOpenPlanProjects([]);
      setOpenPlanCourses([]);
      setOpenPlanArticles([]);
      setOpenPlanStudentWorks([]);
    }
  }, [extractStatus, locallyDeletedProjectIds, locallyDeletedCourseIds, locallyDeletedArticleIds, locallyDeletedStudentWorkIds]);

  useEffect(() => {
    if (degreeOptions.length > 0) return;
    const fallback = ["magistrs", "bakalaurs", "cits", "pirma_cikla", "doktors"];
    const loadDegrees = async () => {
      try {
        const res = await UserPlanService.getDegreeValues();
        const data = res.data || [];
        const normalized = Array.isArray(data)
          ? data.map((d) => (typeof d === "string" ? d : d?.name ?? d?.value ?? ""))
              .filter(Boolean)
          : [];
        setDegreeOptions(normalized.length > 0 ? normalized : fallback);
      } catch (err) {
        setDegreeOptions(fallback);
      }
    };
    loadDegrees();
  }, [degreeOptions.length]);

  useEffect(() => {
    const loadCurrentYearPlans = async () => {
      try {
        const yearsRes = await api.get("/year/all");

        const yearObj = yearsRes.data.find((y) => y.yearNumber === currentYear);
        if (!yearObj) {
          alert(`Year ${currentYear} not found`);
          return;
        }

        const yearId = yearObj.idYear;
        setCurrentYearId(yearId);

        const [plansRes, schedulerRes] = await Promise.all([
          UserPlanService.getByYear(yearId),
          UserPlanService.getSchedulerByYear(yearId).catch(() => ({ data: null }))
        ]);

        setScheduler(schedulerRes.data || null);
        setPlans(plansRes.data);
        if (plansRes.data.length > 0) {
          const firstPlan = { ...plansRes.data[0] };
          setOpenPlan(firstPlan);
          await attachPlanContextToOpenPlan(firstPlan.idPlan);
        } else {
          setOpenPlan(null);
          setOpenPlanProjects([]);
          setOpenPlanCourses([]);
          setOpenPlanArticles([]);
          setOpenPlanStudentWorks([]);
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

  useEffect(() => {
    if (!isProjectModalOpen) return;

    if (projectSearch.trim().length < 2) {
      setProjectOptions([]);
      return;
    }

    const timeoutId = setTimeout(async () => {
      try {
        const res = await UserPlanService.searchProjectsAutocomplete(projectSearch.trim());
        setProjectOptions(res.data || []);
      } catch (err) {
        console.error(err);
        setProjectOptions([]);
      }
    }, 250);

    return () => clearTimeout(timeoutId);
  }, [projectSearch, isProjectModalOpen]);

  useEffect(() => {
    if (!isArticleModalOpen || articleMode !== "existing") return;

    if (articleSearch.trim().length < 2) {
      setArticleOptions([]);
      return;
    }

    const timeoutId = setTimeout(async () => {
      try {
        const res = await UserPlanService.searchArticlesAutocomplete(articleSearch.trim());
        setArticleOptions(res.data || []);
      } catch (err) {
        console.error(err);
        setArticleOptions([]);
      }
    }, 250);

    return () => clearTimeout(timeoutId);
  }, [articleSearch, articleMode, isArticleModalOpen]);

  const loadJournals = async () => {
    try {
      const res = await UserPlanService.getAllJournals();
      setArticleJournals(res.data || []);
    } catch (err) {
      console.error(err);
      setArticleJournals([]);
    }
  };

  useEffect(() => {
    if (!isArticleModalOpen || articleMode !== "new") return;
    if (articleJournals.length > 0) return;
    loadJournals();
  }, [isArticleModalOpen, articleMode, articleJournals.length]);

  useEffect(() => {
    if (!isArticleEditModalOpen) return;
    if (articleJournals.length > 0) return;
    loadJournals();
  }, [isArticleEditModalOpen, articleJournals.length]);


  const handleOpenPlanTextChange = (e) => {
    const { name, value } = e.target;
    setOpenPlan((prev) => ({ ...prev, [name]: value }));
  };

  const handleOpenPlanNumberChange = (e) => {
    const { name, value } = e.target;
    const parsed = value === "" ? "" : Number(value);
    setOpenPlan((prev) => ({ ...prev, [name]: Number.isNaN(parsed) ? "" : parsed }));
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
        setOpenPlanProjects([]);
        setOpenPlanCourses([]);
        setOpenPlanArticles([]);
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
    setCourseModalFieldErrors({});
    setNewCourse({
      name: "",
      ectsCredits: "",
      semester: "",
      faculty: ""
    });
  };

  const resetProjectModal = () => {
    setProjectSearch("");
    setProjectOptions([]);
    setSelectedProject(null);
    setProjectTasks("");
    setProjectWorkDone("");
    setProjectModalMessage("");
    setProjectModalFieldErrors({});
  };

  const openProjectModal = () => {
    resetProjectModal();
    setIsProjectModalOpen(true);
  };

  const closeProjectModal = () => {
    setIsProjectModalOpen(false);
    resetProjectModal();
  };

  const openCourseModal = () => {
    resetCourseModal();
    setIsCourseModalOpen(true);
  };

  const closeCourseModal = () => {
    setIsCourseModalOpen(false);
    resetCourseModal();
  };

  const openProjectDeleteModal = () => {
    setProjectDeleteMessage("");
    setIsProjectDeleteModalOpen(true);
  };

  const openCourseDeleteModal = () => {
    setCourseDeleteMessage("");
    setIsCourseDeleteModalOpen(true);
  };

  const closeProjectDeleteModal = () => {
    setIsProjectDeleteModalOpen(false);
    setProjectDeleteMessage("");
  };

  const closeCourseDeleteModal = () => {
    setIsCourseDeleteModalOpen(false);
    setCourseDeleteMessage("");
  };

  const resetArticleModal = () => {
    setArticleMode("existing");
    setArticleSearch("");
    setArticleOptions([]);
    setSelectedArticle(null);
    setArticleComments("");
    setArticleLink("");
    setArticleModalMessage("");
    setArticleModalFieldErrors({});
    setNewJournalName("");
    setJournalMessage("");
    setNewArticle({
      name: "",
      coAuthors: "",
      idJournal: ""
    });
  };

  const openArticleModal = () => {
    resetArticleModal();
    setIsArticleModalOpen(true);
  };

  const closeArticleModal = () => {
    setIsArticleModalOpen(false);
    resetArticleModal();
  };

  const openProjectEditModal = (project) => {
    setProjectEditTarget(project);
    setProjectEditName(project?.name || "");
    setProjectEditNumber(project?.number ?? "");
    setProjectEditManagementId(
      project?.managementId ??
      project?.projectManagementId ??
      project?.idProjectManag ??
      project?.projectManagement?.idProjectManag ??
      ""
    );
    setProjectEditStartDate(toDateInputValue(project?.startDate ?? project?.start_date));
    setProjectEditEndDate(toDateInputValue(project?.endDate ?? project?.end_date));
    setProjectEditAcronym(project?.acronym || "");
    setProjectEditTasks(project?.tasks ?? project?.projectTasks ?? project?.project_tasks ?? "");
    setProjectEditWorkDone(project?.workDone ?? project?.work_done ?? "");
    setProjectEditMessage("");
    setProjectEditFieldErrors({});
    setIsProjectEditModalOpen(true);
  };

  const closeProjectEditModal = () => {
    setIsProjectEditModalOpen(false);
    setProjectEditTarget(null);
    setProjectEditName("");
    setProjectEditNumber("");
    setProjectEditManagementId("");
    setProjectEditStartDate("");
    setProjectEditEndDate("");
    setProjectEditAcronym("");
    setProjectEditTasks("");
    setProjectEditWorkDone("");
    setProjectEditMessage("");
    setProjectEditFieldErrors({});
  };

  const handleCreateJournal = async () => {
    if (!newJournalName.trim()) return;
    try {
      setIsJournalCreating(true);
      setJournalMessage("");
      await UserPlanService.createJournal({ name: newJournalName.trim() });
      const res = await UserPlanService.getAllJournals();
      const journals = res.data || [];
      setArticleJournals(journals);
      const created = journals.find(
        (j) => j.name?.toLowerCase() === newJournalName.trim().toLowerCase()
      );
      if (created?.idJournal) {
        setNewArticle((prev) => ({ ...prev, idJournal: created.idJournal.toString() }));
      }
      setNewJournalName("");
      setJournalMessage("Journal created.");
    } catch (err) {
      console.error(err);
      setJournalMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to create journal."));
    } finally {
      setIsJournalCreating(false);
    }
  };

  const openArticleDeleteModal = () => {
    setArticleDeleteMessage("");
    setIsArticleDeleteModalOpen(true);
  };

  const closeArticleDeleteModal = () => {
    setIsArticleDeleteModalOpen(false);
    setArticleDeleteMessage("");
  };

  const openArticleEditModal = (article) => {
    setArticleEditTarget(article);
    setArticleEditComments(getArticleComments(article));
    setArticleEditLink(getArticleLink(article));
    setArticleEditName(article?.name || article?.title || "");
    setArticleEditCoAuthors(article?.coAuthors || article?.coAuthor || "");
    setArticleEditJournalId(
      (article?.idJournal ?? article?.journalId ?? article?.idJournalDTO ?? "").toString()
    );
    setArticleEditMessage("");
    setArticleEditFieldErrors({});
    setIsArticleEditModalOpen(true);
  };

  const closeArticleEditModal = () => {
    setIsArticleEditModalOpen(false);
    setArticleEditTarget(null);
    setArticleEditComments("");
    setArticleEditLink("");
    setArticleEditName("");
    setArticleEditCoAuthors("");
    setArticleEditJournalId("");
    setArticleEditMessage("");
    setArticleEditFieldErrors({});
  };

  const openStudentWorkModal = () => {
    setStudentWorkModalMessage("");
    setStudentWorkFieldErrors({});
    setNewStudentWork({
      name: "",
      studentName: "",
      studentSurname: "",
      degree: "",
      workDone: ""
    });
    setIsStudentWorkModalOpen(true);
  };

  const closeStudentWorkModal = () => {
    setIsStudentWorkModalOpen(false);
  };

  const openStudentWorkDeleteModal = () => {
    setStudentWorkDeleteMessage("");
    setIsStudentWorkDeleteModalOpen(true);
  };

  const closeStudentWorkDeleteModal = () => {
    setIsStudentWorkDeleteModalOpen(false);
    setStudentWorkDeleteMessage("");
  };

  const openStudentWorkEditModal = (work) => {
    setStudentWorkEditForm({
      idStudWork: getStudentWorkId(work) ?? "",
      name: work?.name ?? "",
      studentName: work?.studentName ?? "",
      studentSurname: work?.studentSurname ?? "",
      degree: work?.degree?.name ?? work?.degree ?? "",
      workDone: work?.workDone ?? ""
    });
    setStudentWorkEditFieldErrors({});
    setStudentWorkEditMessage("");
    setIsStudentWorkEditModalOpen(true);
  };

  const closeStudentWorkEditModal = () => {
    setIsStudentWorkEditModalOpen(false);
    setStudentWorkEditFieldErrors({});
    setStudentWorkEditMessage("");
  };

  const openCourseEditModal = (course) => {
    setCourseEditTarget(course);
    setCourseEditWorkDone(getWorkDoneText(course));
    setCourseEditName(course?.name || "");
    setCourseEditEcts(course?.ectsCredits ?? "");
    setCourseEditSemester(course?.semester || "");
    setCourseEditFaculty(course?.faculty || "");
    setCourseEditMessage("");
    setCourseEditFieldErrors({});
    setIsCourseEditModalOpen(true);
  };

  const closeCourseEditModal = () => {
    setIsCourseEditModalOpen(false);
    setCourseEditTarget(null);
    setCourseEditWorkDone("");
    setCourseEditName("");
    setCourseEditEcts("");
    setCourseEditSemester("");
    setCourseEditFaculty("");
    setCourseEditMessage("");
    setCourseEditFieldErrors({});
  };

  const handleSaveCourseFromModal = async () => {
    if (!openPlan?.idPlan) return;

    try {
      setIsCourseSaving(true);
      setCourseModalFieldErrors({});

      if (courseMode === "existing") {
        await UserPlanService.saveCoursePlan({
          idPlan: openPlan.idPlan,
          idCourse: selectedCourse?.idCourse ?? 0,
          workDone: courseWorkDone.trim()
        });
        setLocallyDeletedCourseIds((prev) =>
          prev.filter((id) => Number(id) !== Number(selectedCourse?.idCourse))
        );
      } else {
        const createRes = await UserPlanService.createCourseForPlan(
          openPlan.idPlan,
          {
            idCourse: 0,
            name: newCourse.name.trim(),
            ectsCredits: Number(newCourse.ectsCredits) || 0,
            semester: newCourse.semester.trim(),
            faculty: newCourse.faculty.trim(),
            workDone: courseWorkDone.trim()
          }
        );
        const createdId = createRes?.data?.idCourse;
        if (createdId) {
          setLocallyDeletedCourseIds((prev) =>
            prev.filter((id) => Number(id) !== Number(createdId))
          );
        }
      }

      await attachPlanContextToOpenPlan(openPlan.idPlan);
      setCourseModalMessage("");
      // setSaveMessage("Course saved and attached to plan.");
      closeCourseModal();
    } catch (err) {
      console.error(err);
      setCourseModalFieldErrors(extractFieldErrors(err));
      setCourseModalMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to save course."));
    } finally {
      setIsCourseSaving(false);
    }
  };

  const handleSaveProjectFromModal = async () => {
    if (!openPlan?.idPlan) return;

    try {
      setIsProjectSaving(true);
      setProjectModalFieldErrors({});

      const selectedId = getProjectId(selectedProject);
      await UserPlanService.saveProjectPlan({
        idPlan: openPlan.idPlan,
        idProject: selectedId ?? 0,
        tasks: projectTasks.trim(),
        workDone: projectWorkDone.trim()
      });
      setLocallyDeletedProjectIds((prev) =>
        prev.filter((id) => Number(id) !== Number(selectedId))
      );

      await attachPlanContextToOpenPlan(openPlan.idPlan);
      setProjectModalMessage("");
      closeProjectModal();
    } catch (err) {
      console.error(err);
      setProjectModalFieldErrors(extractFieldErrors(err));
      setProjectModalMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to save project."));
    } finally {
      setIsProjectSaving(false);
    }
  };

  const handleSaveArticleFromModal = async () => {
    if (!openPlan?.idPlan) return;

    try {
      setIsArticleSaving(true);
      setArticleModalFieldErrors({});

      if (articleMode === "existing") {
        const selectedId =
          selectedArticle?.idArticle ??
          selectedArticle?.idScientificArticles ??
          selectedArticle?.idScientificArticle ??
          selectedArticle?.articleId ??
          0;

        await UserPlanService.saveArticlePlan(
          selectedId,
          openPlan.idPlan,
          {
            articleComments: articleComments.trim(),
            publicationLink: articleLink.trim()
          }
        );
        setLocallyDeletedArticleIds((prev) =>
          prev.filter((id) => Number(id) !== Number(selectedId))
        );
      } else {
        const createRes = await UserPlanService.createArticleForPlan(
          openPlan.idPlan,
          {
            name: newArticle.name.trim(),
            coAuthors: newArticle.coAuthors.trim(),
            idJournal: Number(newArticle.idJournal) || 0,
            articleComments: articleComments.trim(),
            publicationLink: articleLink.trim()
          }
        );
        const createdId = createRes?.data?.idArticle ?? createRes?.data?.idScientificArticles ?? null;
        if (createdId) {
          setLocallyDeletedArticleIds((prev) =>
            prev.filter((id) => Number(id) !== Number(createdId))
          );
        }
      }

      await attachPlanContextToOpenPlan(openPlan.idPlan);
      setArticleModalMessage("");
      setSaveMessage("Article saved and attached to plan.");
      closeArticleModal();
    } catch (err) {
      console.error(err);
      setArticleModalFieldErrors(extractFieldErrors(err));
      setArticleModalMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to save article."));
    } finally {
      setIsArticleSaving(false);
    }
  };

  const handleSaveStudentWorkFromModal = async () => {
    if (!openPlan?.idPlan) return;
    try {
      setIsStudentWorkSaving(true);
      setStudentWorkFieldErrors({});
      const dto = {
        idStudWork: Number(newStudentWork.idStudWork) || 0,
        name: newStudentWork.name,
        studentName: newStudentWork.studentName,
        studentSurname: newStudentWork.studentSurname,
        degree: newStudentWork.degree,
        workDone: newStudentWork.workDone
      };
      const res = await UserPlanService.createStudentWorkForPlan(openPlan.idPlan, dto);
      const createdId = res?.data?.idStudWork ?? res?.data?.idStudentWork ?? null;
      if (createdId) {
        setLocallyDeletedStudentWorkIds((prev) =>
          prev.filter((id) => Number(id) !== Number(createdId))
        );
      }
      await attachPlanContextToOpenPlan(openPlan.idPlan);
      setStudentWorkModalMessage("");
      setSaveMessage("Student work saved.");
      closeStudentWorkModal();
    } catch (err) {
      console.error(err);
      setStudentWorkFieldErrors(extractFieldErrors(err));
      setStudentWorkModalMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to save student work."));
    } finally {
      setIsStudentWorkSaving(false);
    }
  };

  const handleDeleteStudentWorkPlan = async (work) => {
    const idStudWork = getStudentWorkId(work);
    if (!idStudWork) return;
    if (!openPlan?.idPlan) return;
    if (!window.confirm("Delete this student work from plan?")) return;
    try {
      setIsStudentWorkDeleting(true);
      await UserPlanService.deleteStudentWorkPlan(openPlan.idPlan, idStudWork);
      setOpenPlanStudentWorks((prev) =>
        prev.filter((w) => Number(getStudentWorkId(w)) !== Number(idStudWork))
      );
      setLocallyDeletedStudentWorkIds((prev) => {
        const next = new Set(prev.map((id) => Number(id)));
        next.add(Number(idStudWork));
        return Array.from(next);
      });
      await attachPlanContextToOpenPlan(openPlan.idPlan);
      setStudentWorkDeleteMessage("");
      setSaveMessage("Student work deleted.");
      closeStudentWorkDeleteModal();
    } catch (err) {
      console.error(err);
      setStudentWorkDeleteMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to delete student work."));
    } finally {
      setIsStudentWorkDeleting(false);
    }
  };

  const handleEditStudentWorkPlan = async () => {
    if (!openPlan?.idPlan) return;
    const idStudWork = Number(studentWorkEditForm.idStudWork) || 0;
    if (!idStudWork) return;
    try {
      setIsStudentWorkEditing(true);
      setStudentWorkEditFieldErrors({});
      const dto = {
        idPlan: openPlan.idPlan,
        idStudWork,
        name: studentWorkEditForm.name,
        studentName: studentWorkEditForm.studentName,
        studentSurname: studentWorkEditForm.studentSurname,
        degree: studentWorkEditForm.degree,
        workDone: studentWorkEditForm.workDone
      };
      await UserPlanService.updateStudentWorkPlan(dto);
      setOpenPlanStudentWorks((prev) =>
        prev.map((w) => {
          if (Number(getStudentWorkId(w)) !== Number(idStudWork)) return w;
          return { ...w, ...dto };
        })
      );
      await attachPlanContextToOpenPlan(openPlan.idPlan);
      setStudentWorkEditMessage("");
      setSaveMessage("Student work updated.");
      closeStudentWorkEditModal();
    } catch (err) {
      console.error(err);
      setStudentWorkEditFieldErrors(extractFieldErrors(err));
      setStudentWorkEditMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to update student work."));
    } finally {
      setIsStudentWorkEditing(false);
    }
  };

  const handleDeleteProjectPlan = async (project) => {
    const idProject = getProjectId(project);
    if (!idProject) return;
    if (!openPlan?.idPlan) return;
    if (!window.confirm("Delete this project from plan?")) return;

    try {
      setIsProjectDeleting(true);
      await UserPlanService.deleteProjectPlan(openPlan.idPlan, idProject);
      setOpenPlanProjects((prev) =>
        prev.filter((p) => Number(getProjectId(p)) !== Number(idProject))
      );
      const nextDeleted = (() => {
        const next = new Set(locallyDeletedProjectIds.map((id) => Number(id)));
        next.add(Number(idProject));
        return Array.from(next);
      })();
      setLocallyDeletedProjectIds(nextDeleted);
      await attachPlanContextToOpenPlan(openPlan.idPlan, { deletedProjectIds: nextDeleted });
      setProjectDeleteMessage("");
      closeProjectDeleteModal();
    } catch (err) {
      console.error(err);
      setProjectDeleteMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to delete project."));
    } finally {
      setIsProjectDeleting(false);
    }
  };

  const handleEditProjectPlan = async () => {
    if (!openPlan?.idPlan) return;

    const idProject = getProjectId(projectEditTarget);
    if (!idProject) return;

    try {
      setIsProjectEditing(true);
      setProjectEditFieldErrors({});
      const dto = {
        idPlan: openPlan.idPlan,
        idProject,
        name: projectEditName.trim(),
        number: Number(projectEditNumber) || 0,
        managementId: Number(projectEditManagementId) || 0,
        startDate: projectEditStartDate || "",
        endDate: projectEditEndDate || "",
        acronym: projectEditAcronym.trim(),
        tasks: projectEditTasks.trim(),
        workDone: projectEditWorkDone.trim()
      };
      await UserPlanService.updateProjectPlan(dto);
      setOpenPlanProjects((prev) =>
        prev.map((p) => {
          if (Number(getProjectId(p)) !== Number(idProject)) return p;
          return { ...p, ...dto };
        })
      );
      await attachPlanContextToOpenPlan(openPlan.idPlan);
      setProjectEditMessage("");
      setSaveMessage("Project updated.");
      closeProjectEditModal();
    } catch (err) {
      console.error(err);
      setProjectEditFieldErrors(extractFieldErrors(err));
      setProjectEditMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to update project."));
    } finally {
      setIsProjectEditing(false);
    }
  };

  const formatProjectText = (project) => {
    const labels = {
      name: "Name",
      number: "Number",
      managementId: "Management ID",
      startDate: "Start date",
      endDate: "End date",
      acronym: "Acronym",
      tasks: "Tasks",
      workDone: "Work done"
    };
    const hiddenKeys = new Set([
      "idPlan",
      "idProject",
      "projectId",
      "idProjectPlan",
      "projectPlanId",
      "idProjectPlanDTO"
    ]);

    const normalized = {
      ...project,
      managementId:
        project?.managementId ??
        project?.projectManagementId ??
        project?.idProjectManag ??
        project?.projectManagement?.idProjectManag ??
        project?.project_management_id ??
        project?.management_id ??
        "",
      startDate: toDateInputValue(project?.startDate ?? project?.start_date),
      endDate: toDateInputValue(project?.endDate ?? project?.end_date),
      tasks: project?.tasks ?? project?.projectTasks ?? project?.project_tasks ?? "",
      workDone: project?.workDone ?? project?.work_done ?? ""
    };

    return Object.entries(normalized || {})
      .filter(([key, value]) =>
        !hiddenKeys.has(key) &&
        value !== null &&
        value !== undefined &&
        value !== "" &&
        typeof value !== "object"
      )
      .map(([key, value]) => `${labels[key] || key}: ${value}`)
      .join(" | ");
  };

  const formatCourseText = (course) => {
    const labels = {
      name: "Name",
      ectsCredits: "ECTS",
      semester: "Semester",
      faculty: "Faculty",
      workDone: "Work done"
    };
    const hiddenKeys = new Set([
      "idPlan",
      "idCourse",
      "courseId",
      "idCoursePlan",
      "coursePlanId",
      "idCoursePlanDTO"
    ]);

    return Object.entries(course || {})
      .filter(([key, value]) =>
        !hiddenKeys.has(key) &&
        value !== null &&
        value !== undefined &&
        value !== "" &&
        typeof value !== "object"
      )
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

  const getStudentWorkId = (work) =>
    work?.idStudWork ?? work?.idStudentWork ?? work?.studentWorkId ?? null;

  const getCoursePlanId = (course) =>
    course?.idCoursePlan ??
    course?.coursePlanId ??
    course?.idCoursePlanDTO ??
    course?.coursePlan?.idCoursePlan ??
    course?.coursePlan?.idCoursePlanDTO ??
    course?.planCourseId ??
    null;

  const getArticleId = (article) =>
    article?.idArticle ??
    article?.idScientificArticles ??
    article?.idScientificArticle ??
    article?.articleId ??
    null;

  const getArticlePlanId = (article) =>
    article?.idArticlePlan ??
    article?.articlePlanId ??
    article?.idArticlePlanDTO ??
    article?.planArticleId ??
    null;

  const getArticleComments = (article) =>
    article?.articleComments ??
    article?.articleComment ??
    article?.comments ??
    article?.article_comments ??
    article?.articlePlan?.articleComments ??
    article?.articlePlan?.articleComment ??
    article?.articlePlan?.comments ??
    article?.articlePlanDTO?.articleComments ??
    article?.articlePlanDTO?.articleComment ??
    article?.articlePlanDTO?.comments ??
    "";

  const getArticleLink = (article) =>
    article?.publicationLink ??
    article?.publication_link ??
    article?.publicationUrl ??
    article?.publication_url ??
    article?.link ??
    article?.articlePlan?.publicationLink ??
    article?.articlePlan?.publication_link ??
    article?.articlePlan?.publicationUrl ??
    article?.articlePlan?.publication_url ??
    article?.articlePlan?.link ??
    article?.articlePlanDTO?.publicationLink ??
    article?.articlePlanDTO?.publication_link ??
    article?.articlePlanDTO?.publicationUrl ??
    article?.articlePlanDTO?.publication_url ??
    article?.articlePlanDTO?.link ??
    "";

  const handleDeleteCoursePlan = async (course) => {
    const idCourse =
      course?.idCourse ??
      course?.courseId ??
      course?.course?.idCourse ??
      course?.courseDTO?.idCourse ??
      null;
    if (!idCourse) {
      // setCourseDeleteMessage("Course ID not found, cannot delete.");
      return;
    }
    if (!openPlan?.idPlan) {
      // setCourseDeleteMessage("Plan is not selected.");
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
      const nextDeleted = (() => {
        const next = new Set(locallyDeletedCourseIds.map((id) => Number(id)));
        next.add(Number(idCourse));
        return Array.from(next);
      })();
      setLocallyDeletedCourseIds(nextDeleted);
      await attachPlanContextToOpenPlan(openPlan.idPlan, { deletedCourseIds: nextDeleted });
      setCourseDeleteMessage("");
      // setSaveMessage("Course deleted from plan.");
      closeCourseDeleteModal();
    } catch (err) {
      console.error(err);
      setCourseDeleteMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to delete course."));
    } finally {
      setIsCourseDeleting(false);
    }
  };

  const handleDeleteArticlePlan = async (article) => {
    const idArticle = getArticleId(article);
    if (!idArticle) {
      setArticleDeleteMessage("Article ID not found, cannot delete.");
      return;
    }
    if (!openPlan?.idPlan) {
      setArticleDeleteMessage("Plan is not selected.");
      return;
    }

    if (!window.confirm("Delete this article from plan?")) return;

    try {
      setIsArticleDeleting(true);
      await UserPlanService.deleteArticlePlan(openPlan.idPlan, idArticle);
      setOpenPlanArticles((prev) =>
        prev.filter((a) => {
          const aid = getArticleId(a);
          return Number(aid) !== Number(idArticle);
        })
      );
      setLocallyDeletedArticleIds((prev) => {
        const next = new Set(prev.map((id) => Number(id)));
        next.add(Number(idArticle));
        return Array.from(next);
      });
      const nextDeleted = (() => {
        const next = new Set(locallyDeletedArticleIds.map((id) => Number(id)));
        next.add(Number(idArticle));
        return Array.from(next);
      })();
      await attachPlanContextToOpenPlan(openPlan.idPlan, { deletedArticleIds: nextDeleted });
      setArticleDeleteMessage("");
      setSaveMessage("Article deleted from plan.");
      closeArticleDeleteModal();
    } catch (err) {
      console.error(err);
      setArticleDeleteMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to delete article."));
    } finally {
      setIsArticleDeleting(false);
    }
  };

  const handleEditCoursePlan = async () => {
    if (!courseEditTarget) return;
    if (!openPlan?.idPlan) {
      // setCourseEditMessage("Plan is not selected.");
      return;
    }
    const idCourse =
      courseEditTarget?.idCourse ??
      courseEditTarget?.courseId ??
      courseEditTarget?.course?.idCourse ??
      courseEditTarget?.courseDTO?.idCourse ??
      null;
    if (!idCourse) {
      // setCourseEditMessage("Course ID not found.");
      return;
    }
    if (!courseEditWorkDone.trim()) {
      // setCourseEditMessage("Please provide work done.");
      return;
    }

    try {
      setIsCourseEditing(true);
      setCourseEditFieldErrors({});
      const dto = {
        idPlan: openPlan.idPlan,
        idCourse,
        name: courseEditName.trim(),
        ectsCredits: Number(courseEditEcts) || 0,
        semester: courseEditSemester.trim(),
        faculty: courseEditFaculty.trim(),
        workDone: courseEditWorkDone.trim()
      };
      await UserPlanService.updateCoursePlanWorkDone(dto);
      setOpenPlanCourses((prev) =>
        prev.map((c) => {
          const cid =
            c?.idCourse ??
            c?.courseId ??
            c?.course?.idCourse ??
            c?.courseDTO?.idCourse ??
            null;
          if (Number(cid) !== Number(idCourse)) return c;
          return {
            ...c,
            name: courseEditName.trim(),
            ectsCredits: Number(courseEditEcts) || 0,
            semester: courseEditSemester.trim(),
            faculty: courseEditFaculty.trim(),
            workDone: courseEditWorkDone.trim()
          };
        })
      );
      await attachPlanContextToOpenPlan(openPlan.idPlan);
      // setSaveMessage("Course updated.");
      closeCourseEditModal();
    } catch (err) {
      console.error(err);
      setCourseEditFieldErrors(extractFieldErrors(err));
      setCourseEditMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to update course."));
    } finally {
      setIsCourseEditing(false);
    }
  };

  const handleEditArticlePlan = async () => {
    if (!articleEditTarget) return;
    if (!openPlan?.idPlan) {
      setArticleEditMessage("Plan is not selected.");
      return;
    }
    const idArticle = getArticleId(articleEditTarget);
    if (!idArticle) {
      setArticleEditMessage("Article ID not found.");
      return;
    }
    if (!articleEditComments.trim()) {
      setArticleEditMessage("Please provide article comments.");
      return;
    }
    if (!articleEditLink.trim()) {
      setArticleEditMessage("Please provide publication link.");
      return;
    }

    try {
      setIsArticleEditing(true);
      setArticleEditFieldErrors({});
      const dto = {
        idPlan: openPlan.idPlan,
        idArticle,
        name: articleEditName.trim(),
        coAuthors: articleEditCoAuthors.trim(),
        idJournal: Number(articleEditJournalId) || 0,
        articleComments: articleEditComments.trim(),
        publicationLink: articleEditLink.trim()
      };
      await UserPlanService.updateArticlePlan(dto);
      setOpenPlanArticles((prev) =>
        prev.map((a) => {
          const aid = getArticleId(a);
          if (Number(aid) !== Number(idArticle)) return a;
          return {
            ...a,
            name: articleEditName.trim(),
            coAuthors: articleEditCoAuthors.trim(),
            idJournal: Number(articleEditJournalId) || 0,
            articleComments: articleEditComments.trim(),
            publicationLink: articleEditLink.trim()
          };
        })
      );
      await attachPlanContextToOpenPlan(openPlan.idPlan);
      setSaveMessage("Article updated.");
      closeArticleEditModal();
    } catch (err) {
      console.error(err);
      setArticleEditFieldErrors(extractFieldErrors(err));
      setArticleEditMessage(normalizeMessage(err.response?.data?.message || err.response?.data || "Failed to update article."));
    } finally {
      setIsArticleEditing(false);
    }
  };

  const formatArticleText = (article) => {
    const labels = {
      name: "Name",
      title: "Name",
      coAuthors: "Co-authors",
      journalName: "Journal",
      journal: "Journal",
      articleComments: "Comments",
      publicationLink: "Link"
    };
    const hiddenKeys = new Set([
      "idPlan",
      "idArticle",
      "idScientificArticles",
      "articleId",
      "idJournal",
      "idArticlePlan",
      "articlePlanId",
      "idArticlePlanDTO"
    ]);

    const normalized = {
      ...article,
      articleComments: getArticleComments(article),
      publicationLink: getArticleLink(article)
    };

    return Object.entries(normalized || {})
      .filter(([key, value]) =>
        !hiddenKeys.has(key) &&
        value !== null &&
        value !== undefined &&
        value !== "" &&
        typeof value !== "object"
      )
      .map(([key, value]) => `${labels[key] || key}: ${value}`)
      .join(" | ");
  };

  const normalizedStatus = extractStatus(openPlan);
  const isPlannedFrozen = normalizedStatus === "planned_frozen";
  const plannedDaysLeft = getDaysUntilDate(scheduler?.plannedFreezeDate);
  const doneDaysLeft = getDaysUntilDate(scheduler?.doneFreezeDate);
  const handleOpenPlanRowAction = (rowKey, actionIndex) => {
    if (actionIndex === 0) {
      if (rowKey === "projects") return openProjectModal();
      if (rowKey === "courses") return openCourseModal();
      if (rowKey === "studentWork") return openStudentWorkModal();
      if (rowKey === "articles") return openArticleModal();
    }

    if (actionIndex === 1) {
      if (rowKey === "projects") return openProjectDeleteModal();
      if (rowKey === "courses") return openCourseDeleteModal();
      if (rowKey === "studentWork") return openStudentWorkDeleteModal();
      if (rowKey === "articles") return openArticleDeleteModal();
    }

    setSaveMessage("Action is not connected yet.");
  };

  return (
    <div className="user-dashboard-page">
      <h2>User Dashboard</h2>
      <CurrentYearPlansTable
        currentYear={currentYear}
        currentYearId={currentYearId}
        plans={plans}
        navigate={navigate}
        handleExportPlan={handleExportPlan}
        getButtonStyle={getButtonStyle}
      />

      <SchedulerDeadlineCard
        scheduler={scheduler}
        plannedDaysLeft={plannedDaysLeft}
        doneDaysLeft={doneDaysLeft}
        formatSchedulerDate={formatSchedulerDate}
        getDeadlineClassName={getDeadlineClassName}
      />

      <OpenPlanActivities
        openPlan={openPlan}
        tableRows={tableRows}
        isPlannedFrozen={isPlannedFrozen}
        handleOpenPlanNumberChange={handleOpenPlanNumberChange}
        handleOpenPlanTextChange={handleOpenPlanTextChange}
        openPlanProjects={openPlanProjects}
        openPlanCourses={openPlanCourses}
        openPlanArticles={openPlanArticles}
        openPlanStudentWorks={openPlanStudentWorks}
        getProjectPlanId={getProjectPlanId}
        getProjectId={getProjectId}
        getStudentWorkId={getStudentWorkId}
        getArticlePlanId={getArticlePlanId}
        getArticleId={getArticleId}
        formatProjectText={formatProjectText}
        formatCourseText={formatCourseText}
        formatArticleText={formatArticleText}
        openProjectEditModal={openProjectEditModal}
        openCourseEditModal={openCourseEditModal}
        openStudentWorkEditModal={openStudentWorkEditModal}
        openArticleEditModal={openArticleEditModal}
        getButtonStyle={getButtonStyle}
        onRowAction={handleOpenPlanRowAction}
        handleSaveOpenPlan={handleSaveOpenPlan}
        isSaving={isSaving}
      />
      <ProjectModals
        openPlan={openPlan}
        isProjectModalOpen={isProjectModalOpen}
        projectSearch={projectSearch}
        setProjectSearch={setProjectSearch}
        setSelectedProject={setSelectedProject}
        projectOptions={projectOptions}
        setProjectOptions={setProjectOptions}
        getProjectId={getProjectId}
        toDateInputValue={toDateInputValue}
        openPlanProjects={openPlanProjects}
        projectModalFieldErrors={projectModalFieldErrors}
        selectedProject={selectedProject}
        formatProjectText={formatProjectText}
        projectTasks={projectTasks}
        setProjectTasks={setProjectTasks}
        projectWorkDone={projectWorkDone}
        setProjectWorkDone={setProjectWorkDone}
        projectModalMessage={projectModalMessage}
        setProjectModalMessage={setProjectModalMessage}
        handleSaveProjectFromModal={handleSaveProjectFromModal}
        isProjectSaving={isProjectSaving}
        closeProjectModal={closeProjectModal}
        getButtonStyle={getButtonStyle}
        isProjectDeleteModalOpen={isProjectDeleteModalOpen}
        isProjectDeleting={isProjectDeleting}
        projectDeleteMessage={projectDeleteMessage}
        getProjectPlanId={getProjectPlanId}
        handleDeleteProjectPlan={handleDeleteProjectPlan}
        closeProjectDeleteModal={closeProjectDeleteModal}
        isProjectEditModalOpen={isProjectEditModalOpen}
        projectEditTarget={projectEditTarget}
        projectEditName={projectEditName}
        setProjectEditName={setProjectEditName}
        projectEditFieldErrors={projectEditFieldErrors}
        projectEditNumber={projectEditNumber}
        setProjectEditNumber={setProjectEditNumber}
        projectEditManagementId={projectEditManagementId}
        setProjectEditManagementId={setProjectEditManagementId}
        projectEditStartDate={projectEditStartDate}
        setProjectEditStartDate={setProjectEditStartDate}
        projectEditEndDate={projectEditEndDate}
        setProjectEditEndDate={setProjectEditEndDate}
        projectEditAcronym={projectEditAcronym}
        setProjectEditAcronym={setProjectEditAcronym}
        projectEditTasks={projectEditTasks}
        setProjectEditTasks={setProjectEditTasks}
        projectEditWorkDone={projectEditWorkDone}
        setProjectEditWorkDone={setProjectEditWorkDone}
        projectEditMessage={projectEditMessage}
        handleEditProjectPlan={handleEditProjectPlan}
        isProjectEditing={isProjectEditing}
        closeProjectEditModal={closeProjectEditModal}
      />

      <CourseModals
        openPlan={openPlan}
        isCourseModalOpen={isCourseModalOpen}
        courseMode={courseMode}
        setCourseMode={setCourseMode}
        courseSearch={courseSearch}
        setCourseSearch={setCourseSearch}
        setSelectedCourse={setSelectedCourse}
        courseOptions={courseOptions}
        setCourseOptions={setCourseOptions}
        selectedCourse={selectedCourse}
        formatCourseText={formatCourseText}
        openPlanCourses={openPlanCourses}
        getWorkDoneText={getWorkDoneText}
        newCourse={newCourse}
        setNewCourse={setNewCourse}
        courseWorkDone={courseWorkDone}
        setCourseWorkDone={setCourseWorkDone}
        courseModalFieldErrors={courseModalFieldErrors}
        handleSaveCourseFromModal={handleSaveCourseFromModal}
        isCourseSaving={isCourseSaving}
        closeCourseModal={closeCourseModal}
        getButtonStyle={getButtonStyle}
        courseModalMessage={courseModalMessage}
        isCourseDeleteModalOpen={isCourseDeleteModalOpen}
        isCourseDeleting={isCourseDeleting}
        courseDeleteMessage={courseDeleteMessage}
        getCoursePlanId={getCoursePlanId}
        handleDeleteCoursePlan={handleDeleteCoursePlan}
        closeCourseDeleteModal={closeCourseDeleteModal}
        isCourseEditModalOpen={isCourseEditModalOpen}
        courseEditTarget={courseEditTarget}
        courseEditName={courseEditName}
        setCourseEditName={setCourseEditName}
        courseEditEcts={courseEditEcts}
        setCourseEditEcts={setCourseEditEcts}
        courseEditSemester={courseEditSemester}
        setCourseEditSemester={setCourseEditSemester}
        courseEditFaculty={courseEditFaculty}
        setCourseEditFaculty={setCourseEditFaculty}
        courseEditWorkDone={courseEditWorkDone}
        setCourseEditWorkDone={setCourseEditWorkDone}
        courseEditFieldErrors={courseEditFieldErrors}
        handleEditCoursePlan={handleEditCoursePlan}
        isCourseEditing={isCourseEditing}
        closeCourseEditModal={closeCourseEditModal}
        courseEditMessage={courseEditMessage}
      />

      <ArticleModals
        openPlan={openPlan}
        isArticleModalOpen={isArticleModalOpen}
        articleMode={articleMode}
        setArticleMode={setArticleMode}
        articleSearch={articleSearch}
        setArticleSearch={setArticleSearch}
        setSelectedArticle={setSelectedArticle}
        articleOptions={articleOptions}
        setArticleOptions={setArticleOptions}
        openPlanArticles={openPlanArticles}
        getArticleId={getArticleId}
        articleModalFieldErrors={articleModalFieldErrors}
        newArticle={newArticle}
        setNewArticle={setNewArticle}
        articleJournals={articleJournals}
        newJournalName={newJournalName}
        setNewJournalName={setNewJournalName}
        handleCreateJournal={handleCreateJournal}
        isJournalCreating={isJournalCreating}
        journalMessage={journalMessage}
        articleComments={articleComments}
        setArticleComments={setArticleComments}
        articleLink={articleLink}
        setArticleLink={setArticleLink}
        handleSaveArticleFromModal={handleSaveArticleFromModal}
        isArticleSaving={isArticleSaving}
        closeArticleModal={closeArticleModal}
        getButtonStyle={getButtonStyle}
        articleModalMessage={articleModalMessage}
        isArticleDeleteModalOpen={isArticleDeleteModalOpen}
        isArticleDeleting={isArticleDeleting}
        articleDeleteMessage={articleDeleteMessage}
        getArticlePlanId={getArticlePlanId}
        formatArticleText={formatArticleText}
        handleDeleteArticlePlan={handleDeleteArticlePlan}
        closeArticleDeleteModal={closeArticleDeleteModal}
        isArticleEditModalOpen={isArticleEditModalOpen}
        articleEditTarget={articleEditTarget}
        articleEditName={articleEditName}
        setArticleEditName={setArticleEditName}
        articleEditCoAuthors={articleEditCoAuthors}
        setArticleEditCoAuthors={setArticleEditCoAuthors}
        articleEditJournalId={articleEditJournalId}
        setArticleEditJournalId={setArticleEditJournalId}
        articleEditFieldErrors={articleEditFieldErrors}
        articleEditComments={articleEditComments}
        setArticleEditComments={setArticleEditComments}
        articleEditLink={articleEditLink}
        setArticleEditLink={setArticleEditLink}
        handleEditArticlePlan={handleEditArticlePlan}
        isArticleEditing={isArticleEditing}
        closeArticleEditModal={closeArticleEditModal}
        articleEditMessage={articleEditMessage}
      />

      <StudentWorkModals
        openPlan={openPlan}
        isStudentWorkModalOpen={isStudentWorkModalOpen}
        newStudentWork={newStudentWork}
        setNewStudentWork={setNewStudentWork}
        studentWorkFieldErrors={studentWorkFieldErrors}
        degreeOptions={degreeOptions}
        handleSaveStudentWorkFromModal={handleSaveStudentWorkFromModal}
        isStudentWorkSaving={isStudentWorkSaving}
        closeStudentWorkModal={closeStudentWorkModal}
        getButtonStyle={getButtonStyle}
        studentWorkModalMessage={studentWorkModalMessage}
        isStudentWorkDeleteModalOpen={isStudentWorkDeleteModalOpen}
        openPlanStudentWorks={openPlanStudentWorks}
        getStudentWorkId={getStudentWorkId}
        handleDeleteStudentWorkPlan={handleDeleteStudentWorkPlan}
        isStudentWorkDeleting={isStudentWorkDeleting}
        closeStudentWorkDeleteModal={closeStudentWorkDeleteModal}
        studentWorkDeleteMessage={studentWorkDeleteMessage}
        isStudentWorkEditModalOpen={isStudentWorkEditModalOpen}
        studentWorkEditForm={studentWorkEditForm}
        setStudentWorkEditForm={setStudentWorkEditForm}
        studentWorkEditFieldErrors={studentWorkEditFieldErrors}
        handleEditStudentWorkPlan={handleEditStudentWorkPlan}
        isStudentWorkEditing={isStudentWorkEditing}
        closeStudentWorkEditModal={closeStudentWorkEditModal}
        studentWorkEditMessage={studentWorkEditMessage}
      />

      {saveMessage && (
        <p style={{ color: saveMessage.toLowerCase().includes("success") ? "green" : "red" }}>
          {saveMessage}
        </p>
      )}
    </div>
  );
}

