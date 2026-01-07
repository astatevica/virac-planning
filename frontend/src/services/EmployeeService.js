import axios from "axios";

const API_URL = "http://localhost:8080/api/employee";

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

  filterByDepartment(idDepartment) {
    return axios.get(`${API_URL}/filter/${idDepartment}`);
  },
};

export default EmployeeService;
