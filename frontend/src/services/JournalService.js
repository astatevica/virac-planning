import api from "../api/api";

const API_URL = "/admin/journal";

const JournalService = {
  getAll: () => api.get(`${API_URL}/all`),

  getById: (id) => api.get(`${API_URL}/${id}`),

  create: (journal) => api.post(`${API_URL}/add`, journal),

  update: (id, journal) => api.put(`${API_URL}/update/${id}`, journal),

  delete: (id) => api.delete(`${API_URL}/delete/${id}`)
};

export default JournalService;
