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
  createCourseForPlan
};

export default UserPlanService;
