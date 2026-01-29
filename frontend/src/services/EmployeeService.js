import api from "../api/api";

const API_URL = "/admin/employee";

const EmployeeService = {
  getAll() {
    return api.get(API_URL);
  },

  getById(id) {
    return api.get(`${API_URL}/${id}`);
  },

  create(data) {
    console.log(data);
    return api.post(API_URL, data);
  },

  update(id, data) {
    return api.put(`${API_URL}/${id}`, data);
  },

  delete(id) {
    return api.delete(`${API_URL}/${id}`);
  },

  filterByDepartment(nameDepartment) {
    console.log(nameDepartment);
    return api.get(`${API_URL}/filter/${nameDepartment}`);
  },
};

export default EmployeeService;
