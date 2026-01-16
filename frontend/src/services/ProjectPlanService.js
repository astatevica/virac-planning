import axios from "axios";

const API_URL = "http://localhost:8080/api/project-plan";

const ProjectPlanService = {
  // ===== CRUD =====

  getAll() {
    return axios.get(API_URL);
  },

  getById(id) {
    return axios.get(`${API_URL}/${id}`);
  },

  create(data) {
    return axios.post(API_URL, data);
  },

  update(id, data) {
    return axios.put(`${API_URL}/${id}`, data);
  },

  delete(id) {
    return axios.delete(`${API_URL}/${id}`);
  },

  // ===== FILTERS =====

  getByPlan(idPlan) {
    return axios.get(`${API_URL}/filter/plan/${idPlan}`);
  },

  getByProject(idProject) {
    return axios.get(`${API_URL}/filter/project/${idProject}`);
  }
};

export default ProjectPlanService;
