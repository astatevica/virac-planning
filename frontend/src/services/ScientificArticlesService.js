import api from "../api/api";

const API_URL = "/admin/scientific-articles";

const ScientificArticlesService = {
  getAll: () => api.get(API_URL),

  getById: (id) => api.get(`${API_URL}/${id}`),

  create: (data) => api.post(API_URL, data),

  update: (id, data) => api.put(`${API_URL}/${id}`, data),

  delete: (id) => api.delete(`${API_URL}/${id}`),

  filterByCoAuthor: (coAuthors) =>
    api.get(`${API_URL}/filter/${coAuthors}`)
};

export default ScientificArticlesService;
