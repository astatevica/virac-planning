import api from "../api/api";

const API_URL = "/admin/department";

class DepartmentService {

  getAll() {
    return api.get(API_URL);
  }

  getById(id) {
    return api.get(`${API_URL}/${id}`);
  }

  create(department) {
    return api.post(API_URL, department);
  }

  update(id, department) {
    return api.put(`${API_URL}/${id}`, department);
  }

  delete(id) {
    return api.delete(`${API_URL}/${id}`);
  }
}

const departmentService = new DepartmentService();
export default departmentService;
