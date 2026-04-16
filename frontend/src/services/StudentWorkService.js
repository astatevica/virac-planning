import api from "../api/api";

const API_URL = "/admin/student-work";

const StudentWorkService = {

  getAll() {
    return api.get(`${API_URL}/all`);
  },

  getById(id) {
    return api.get(`${API_URL}/${id}`);
  },

  create(data) {
    return api.post(`${API_URL}/add`, data);
  },

  update(id, data) {
    return api.put(`${API_URL}/update/${id}`, data);
  },

  delete(id) {
    return api.delete(`${API_URL}/delete/${id}`);
  },

  getByDegree(degree) {
    return api.get(`${API_URL}/filter/${degree}`);
  }
};

export default StudentWorkService;
