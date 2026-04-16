import api from "../api/api";

const API_URL = "/admin/article-plan";

const ArticlePlanService = {
  getAll: () => api.get(`${API_URL}/all`),

  getById: (id) => api.get(`${API_URL}/${id}`),

  create: (data) => api.post(`${API_URL}/add`, data),

  update: (id, data) => api.put(`${API_URL}/update/${id}`, data),

  delete: (id) => api.delete(`${API_URL}/delete/${id}`),

  filterByPlan: (idPlan) =>
    api.get(`${API_URL}/filter/${idPlan}`)
};

export default ArticlePlanService;
