import axios from "axios";

const API_URL = "http://localhost:8080/api/project";

const ProjectService = {
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

  getByNumber(number) {
    return axios.get(`${API_URL}/number/${number}`);
  },

  getByStartDate(date) {
    // date must be "YYYY-MM-DD"
    return axios.get(`${API_URL}/start-date/${date}`);
  },

  getByEndDate(date) {
    // date must be "YYYY-MM-DD"
    return axios.get(`${API_URL}/end-date/${date}`);
  }
};

export default ProjectService;
