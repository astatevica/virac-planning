import api from "../api/api";

const API_URL = "/admin/journal";

const JournalService = {
  getAll: () => api.get(API_URL),

  getById: (id) => api.get(`${API_URL}/${id}`),

  create: (journal) => api.post(API_URL, journal),

  update: (id, journal) => api.put(`${API_URL}/${id}`, journal),

  delete: (id) => api.delete(`${API_URL}/${id}`)
};

export default JournalService;
