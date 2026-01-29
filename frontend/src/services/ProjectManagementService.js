import api from "../api/api";

const API_URL = "/admin/project-management";

class ProjectManagementService {

  getAll() {
    return api.get(API_URL);
  }

  getById(id) {
    return api.get(`${API_URL}/${id}`);
  }

  create(data) {
    return api.post(API_URL, data);
  }

  update(id, data) {
    return api.put(`${API_URL}/${id}`, data);
  }

  delete(id) {
    return api.delete(`${API_URL}/${id}`);
  }

  getByEmployee(employeeId) {
    return api.get(`${API_URL}/employee/${employeeId}`);
  }

  getByStartDate(date) {
    return api.get(`${API_URL}/start-date/${date}`);
  }

  getByEndDate(date) {
    return api.get(`${API_URL}/end-date/${date}`);
  }
}

const projectManagementService = new ProjectManagementService();
export default projectManagementService;
