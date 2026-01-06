import axios from "axios";

const API_URL = "http://localhost:8080/api/project-management";

class ProjectManagementService {

  getAll() {
    return axios.get(API_URL);
  }

  getById(id) {
    return axios.get(`${API_URL}/${id}`);
  }

  create(data) {
    return axios.post(API_URL, data);
  }

  update(id, data) {
    return axios.put(`${API_URL}/${id}`, data);
  }

  delete(id) {
    return axios.delete(`${API_URL}/${id}`);
  }

  getByEmployee(employeeId) {
    return axios.get(`${API_URL}/employee/${employeeId}`);
  }

  getByStartDate(date) {
    return axios.get(`${API_URL}/start-date/${date}`);
  }

  getByEndDate(date) {
    return axios.get(`${API_URL}/end-date/${date}`);
  }
}

export default new ProjectManagementService();
