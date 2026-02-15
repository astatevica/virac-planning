import api from "../api/api";

const BASE = "/user";

const UserPlanService = {

  getAll() {
    return api.get(`${BASE}/filter/plans/all`);
  },

  getByYear(yearId) {
    return api.get(`${BASE}/filter/plans/${yearId}`);
  },

  getByProject(projectId) {
    return api.get(`${BASE}/plans/project/${projectId}`);
  }

};

export default UserPlanService;
