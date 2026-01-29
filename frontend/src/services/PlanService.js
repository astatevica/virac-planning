import api from "../api/api";

const API_URL = "/admin/plan";

const PlanService = {
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

  getByEmployee(idEmployee) {
    return api.get(`${API_URL}/filter/employee/${idEmployee}`);
  },

  getByYear(idYear) {
    return api.get(`${API_URL}/filter/year/${idYear}`);
  },

  getByDepartment(department) {
    return api.get(`${API_URL}/filter/department/${department}`);
  }
};

export default PlanService;
