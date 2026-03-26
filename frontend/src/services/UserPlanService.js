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

const updatePlanForUserByOpenPlan = (planDto) =>
  api.put(`${API}/update/current-year/plan`, planDto);

const searchCoursesAutocomplete = (keyword) =>
  api.get(`${API}/courses/autocomplete/${encodeURIComponent(keyword)}`);

const saveCoursePlan = (idCourse, idPlan, workDone) =>
  api.get(
    `${API}/courses/autocomplete/${idCourse}/${idPlan}/${encodeURIComponent(workDone)}`
  );

const createCourseForPlan = (idPlan, workDone, courseDto) =>
  api.post(`${API}/add/course/${idPlan}/${encodeURIComponent(workDone)}`, courseDto);

const deleteCoursePlan = (idPlan, idCourse) =>
  api.delete(`${API}/delete/course-plan/${idPlan}/${idCourse}`);

const updateCoursePlanWorkDone = (idPlan, idCourse, workDone, dto) =>
  api.put(
    `${API}/update/course-plan`,
    dto
  );

const searchArticlesAutocomplete = (keyword) =>
  api.get(`${API}/article/autocomplete/${encodeURIComponent(keyword)}`);

const saveArticlePlan = (idArticle, idPlan, comments, link) =>
  api.get(
    `${API}/articles/autocomplete/${idArticle}/${idPlan}/${encodeURIComponent(comments)}/${encodeURIComponent(link)}`
  );

const createArticleForPlan = (idPlan, comments, link, articleDto) =>
  api.post(
    `${API}/add/article/${idPlan}/${encodeURIComponent(comments)}/${encodeURIComponent(link)}`,
    articleDto
  );

const deleteArticlePlan = (idPlan, idArticle) =>
  api.delete(`${API}/delete/article-plan/${idPlan}/${idArticle}`);

const updateArticlePlan = (dto) =>
  api.put(
    `${API}/update/article-plan`,
    dto
  );

const UserPlanService = {
  getAll,
  getByYear,   
  getByProject,
  getByPlan,
  getAllProjects,
  getPlanView,
  getFullPlan,
  updatePlanForUserByOpenPlan,
  searchCoursesAutocomplete,
  saveCoursePlan,
  createCourseForPlan,
  deleteCoursePlan,
  updateCoursePlanWorkDone,
  searchArticlesAutocomplete,
  saveArticlePlan,
  createArticleForPlan,
  deleteArticlePlan,
  updateArticlePlan
};

export default UserPlanService;
