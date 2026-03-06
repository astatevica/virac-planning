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

const UserPlanService = {
  getAll,
  getByYear,   
  getByProject,
  getByPlan,
  getAllProjects,
  getPlanView,
  getFullPlan
};

export default UserPlanService;