import api from "../api/api";

const API_URL = "/admin/project-plan";

const ProjectPlanService = {
  // ===== CRUD =====

  getAll() {
    return api.get(API_URL);
  },

  getById(id) {
    return api.get(`${API_URL}/${id}`);
  },

  create(data) {
    return api.post(API_URL, data);
  },

  update(id, data) {
    return api.put(`${API_URL}/${id}`, data);
  },

  delete(id) {
    return api.delete(`${API_URL}/${id}`);
  },

  // ===== FILTERS =====

  getByPlan(idPlan) {
    return api.get(`${API_URL}/filter/plan/${idPlan}`);
  },

  getByProject(idProject) {
    return api.get(`${API_URL}/filter/project/${idProject}`);
  }
};

export default ProjectPlanService;
