import api from "../api/api";

const API_URL = "/admin/project";

const ProjectService = {
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

  getByNumber(number) {
    return api.get(`${API_URL}/number/${number}`);
  },

  getByStartDate(date) {
    // date must be "YYYY-MM-DD"
    return api.get(`${API_URL}/start-date/${date}`);
  },

  getByEndDate(date) {
    // date must be "YYYY-MM-DD"
    return api.get(`${API_URL}/end-date/${date}`);
  }
};

export default ProjectService;
