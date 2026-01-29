import axios from "axios";

const API_URL = "http://localhost:8080/api/admin/plan";

const PlanService = {
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

  getByEmployee(idEmployee) {
    return axios.get(`${API_URL}/filter/employee/${idEmployee}`);
  },

  getByYear(idYear) {
    return axios.get(`${API_URL}/filter/year/${idYear}`);
  },

  getByDepartment(department) {
    return axios.get(`${API_URL}/filter/department/${department}`);
  }
};

export default PlanService;
