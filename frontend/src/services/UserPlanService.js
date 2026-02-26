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

const UserPlanService = {
  getAll,
  getByYear,   
  getByProject,
  getByPlan,
  getAllProjects
};

export default UserPlanService;