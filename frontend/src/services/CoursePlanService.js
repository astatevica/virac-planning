import api from "../api/api";

const API_URL = "/admin/course-plan";

const CoursePlanService = {
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

  getByPlanId(planId) {
    return api.get(`${API_URL}/filter/${planId}`);
  }
};

export default CoursePlanService;
