import axios from "axios";

const API_URL = "http://localhost:8080/api/journal";

const JournalService = {
  getAll: () => axios.get(API_URL),

  getById: (id) => axios.get(`${API_URL}/${id}`),

  create: (journal) => axios.post(API_URL, journal),

  update: (id, journal) => axios.put(`${API_URL}/${id}`, journal),

  delete: (id) => axios.delete(`${API_URL}/${id}`)
};

export default JournalService;
