import axios from "axios";

const API_URL = "http://localhost:8080/api/admin/employee";

const EmployeeService = {
  getAll() {
    return axios.get(API_URL);
  },

  getById(id) {
    return axios.get(`${API_URL}/${id}`);
  },

  create(data) {
    console.log(data);
    return axios.post(API_URL, data);
  },

  update(id, data) {
    return axios.put(`${API_URL}/${id}`, data);
  },

  delete(id) {
    return axios.delete(`${API_URL}/${id}`);
  },

  filterByDepartment(nameDepartment) {
    console.log(nameDepartment);
    return axios.get(`${API_URL}/filter/${nameDepartment}`);
  },
};

export default EmployeeService;
