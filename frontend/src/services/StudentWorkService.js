import api from "../api/api";

const API_URL = "/admin/student-work";

const StudentWorkService = {
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

  // ===== FILTER =====

  getByDegree(degree) {
    return api.get(`${API_URL}/filter/${degree}`);
  }
};

export default StudentWorkService;
