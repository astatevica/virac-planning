import axios from "axios";

const API_URL = "http://localhost:8080/api/departments";

class DepartmentService {

  getAll() {
    return axios.get(API_URL);
  }

  getById(id) {
    return axios.get(`${API_URL}/${id}`);
  }

  create(department) {
    return axios.post(API_URL, department);
  }

  update(id, department) {
    return axios.put(`${API_URL}/${id}`, department);
  }

  delete(id) {
    return axios.delete(`${API_URL}/${id}`);
  }
}

export default new DepartmentService();
