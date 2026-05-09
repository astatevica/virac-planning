// src/services/UserPlanService.js

import api from "../api/api";

const API = "/user";

const getAll = () => api.get(`${API}/filter/plans/all`);

const getByYear = (idYear) =>
  api.get(`${API}/filter/plans/${idYear}`);

const getByProject = (projectId) =>
  api.get(`${API}/plans/project/${projectId}`);

const getByPlan = (idPlan) =>
  api.get(`${API}/project-plan/filter/plan/${idPlan}`); 

const getAllProjects = () =>
  api.get(`${API}/all/projects`); 

const getPlanView = (idPlan) =>
  api.get(`${API}/plan/${idPlan}`);

const getFullPlan = (idPlan) =>
  api.get(`${API}/full-plan/${idPlan}`);

const exportPlanDocx = (idPlan) =>
  api.get(`/export/docx/${idPlan}`, { responseType: "blob" });

const exportPlanPdf = (idPlan) =>
  api.get(`/export/pdf/${idPlan}`, { responseType: "blob" });

const updatePlanForUserByOpenPlan = (planDto) =>
  api.put(`${API}/update/current-year/plan`, planDto);

const searchCoursesAutocomplete = (keyword) =>
  api.get(`${API}/courses/autocomplete/${encodeURIComponent(keyword)}`);

const searchProjectsAutocomplete = (keyword) =>
  api.get(`${API}/project/autocomplete/${encodeURIComponent(keyword)}`);

const saveCoursePlan = (dto) =>
  api.get(`${API}/courses/autocomplete`, { params: dto });

const saveProjectPlan = (dto) =>
  api.get(`${API}/project/autocomplete`, { params: dto });

const createCourseForPlan = (idPlan, courseDto) =>
  api.post(`${API}/add/course/${idPlan}`, courseDto);

const deleteCoursePlan = (idPlan, idCourse) =>
  api.delete(`${API}/delete/course-plan/${idPlan}/${idCourse}`);

const deleteProjectPlan = (idPlan, idProject) =>
  api.delete(`${API}/delete/project-plan/${idPlan}/${idProject}`);

const updateCoursePlanWorkDone = (dto) =>
  api.put(`${API}/update/course-plan`, dto);

const updateProjectPlan = (dto) =>
  api.put(`${API}/update/project-plan`, dto);

const searchArticlesAutocomplete = (keyword) =>
  api.get(`${API}/article/autocomplete/${encodeURIComponent(keyword)}`);

const saveArticlePlan = (idArticle, idPlan, dto) =>
  api.get(
    `${API}/articles/autocomplete/${idArticle}/${idPlan}`,
    { params: dto }
  );

const createArticleForPlan = (idPlan, articleDto) =>
  api.post(
    `${API}/add/article/${idPlan}`,
    articleDto
  );

const deleteArticlePlan = (idPlan, idArticle) =>
  api.delete(`${API}/delete/article-plan/${idPlan}/${idArticle}`);

const updateArticlePlan = (dto) =>
  api.put(
    `${API}/update/article-plan`,
    dto
  );

const createStudentWorkForPlan = (idPlan, dto) =>
  api.post(`${API}/add/work-plan/${idPlan}`, dto);

const deleteStudentWorkPlan = (idPlan, idStudWork) =>
  api.delete(`${API}/delete/work-plan/${idPlan}/${idStudWork}`);

const updateStudentWorkPlan = (dto) =>
  api.put(`${API}/update/work-plan`, dto);

const getDegreeValues = () =>
  api.get(`${API}/degree/values`);

const getAllJournals = () =>
  api.get(`${API}/journals/all`);

const getJournalById = (idJournal) =>
  api.get(`${API}/journals/${idJournal}`);

const createJournal = (dto) =>
  api.post(`${API}/journals/add`, dto);

const getSchedulerByYear = (idYear) => api.get(`${API}/scheduler/${idYear}`)

const UserPlanService = {
  getAll,
  getByYear,   
  getByProject,
  getByPlan,
  getAllProjects,
  getPlanView,
  getFullPlan,
  exportPlanDocx,
  exportPlanPdf,
  updatePlanForUserByOpenPlan,
  searchCoursesAutocomplete,
  searchProjectsAutocomplete,
  saveCoursePlan,
  saveProjectPlan,
  createCourseForPlan,
  deleteCoursePlan,
  deleteProjectPlan,
  updateCoursePlanWorkDone,
  updateProjectPlan,
  searchArticlesAutocomplete,
  saveArticlePlan,
  createArticleForPlan,
  deleteArticlePlan,
  updateArticlePlan,
  createStudentWorkForPlan,
  deleteStudentWorkPlan,
  updateStudentWorkPlan,
  getDegreeValues,
  getAllJournals,
  getJournalById,
  createJournal,
  getSchedulerByYear
};

export default UserPlanService;
