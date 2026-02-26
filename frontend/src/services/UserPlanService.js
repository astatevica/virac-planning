// src/services/UserPlanService.js

import api from "../api/api";

const API = "/user";

const getAll = () => api.get(`${API}/filter/plans/all`);

const getByYear = (idYear) =>
  api.get(`${API}/filter/plans/${idYear}`);

const getByProject = (projectId) =>
  api.get(`${API}/plans/project/${projectId}`);

export default {
  getAll,
  getByYear,
  getByProject
};