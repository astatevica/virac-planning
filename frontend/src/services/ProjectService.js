import api from "../api/api";

const API_URL = "/admin/project";

const ProjectService = {

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

  getByNumber(number) {
    return api.get(`${API_URL}/number/${number}`);
  },

  getByStartDate(date) {
    return api.get(`${API_URL}/start-date/${date}`);
  },

  getByEndDate(date) {
    return api.get(`${API_URL}/end-date/${date}`);
  }
};

export default ProjectService;
