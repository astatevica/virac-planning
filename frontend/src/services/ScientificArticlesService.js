import api from "../api/api";

const API_URL = "/admin/scientific-articles";

const ScientificArticlesService = {
  getAll: () => api.get(`${API_URL}/all`),

  getById: (id) => api.get(`${API_URL}/${id}`),

  create: (data) => api.post(`${API_URL}/add`, data),

  update: (id, data) => api.put(`${API_URL}/update/${id}`, data),

  delete: (id) => api.delete(`${API_URL}/delete/${id}`),

  filterByCoAuthor: (coAuthors) =>
    api.get(`${API_URL}/filter/${coAuthors}`)
};

export default ScientificArticlesService;
