import api from "../api/api";

const API_URL = "/admin/department";

class DepartmentService {

  getAll() {
    return api.get(`${API_URL}/all`);
  }

  getById(id) {
    return api.get(`${API_URL}/${id}`);
  }

  create(department) {
    return api.post(`${API_URL}/add`, department);
  }

  update(id, department) {
    return api.put(`${API_URL}/update/${id}`, department);
  }

  delete(id) {
    return api.delete(`${API_URL}/delete/${id}`);
  }
}

const departmentService = new DepartmentService();
export default departmentService;
