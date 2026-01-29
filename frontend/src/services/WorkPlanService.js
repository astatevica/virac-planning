import api from "../api/api";

const API_URL = "/admin/work-plan";

const WorkPlanService = {
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

  getByStudentWork(idStudentWork) {
    return api.get(`${API_URL}/filter/student-work/${idStudentWork}`);
  },

  getByPlan(idPlan) {
    return api.get(`${API_URL}/filter/plan/${idPlan}`);
  }
};

export default WorkPlanService;
