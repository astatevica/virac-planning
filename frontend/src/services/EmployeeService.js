import axios from "axios";

const API_URL = "http://localhost:8080/api/employee";

const getAll = () => axios.get(API_URL);
const getByDepartment = (departmentId) =>
  axios.get(`${API_URL}/filter/${departmentId}`);

const create = (data) => axios.post(API_URL, data);
const update = (id, data) => axios.put(`${API_URL}/${id}`, data);
const deleteEmployee = (id) => axios.delete(`${API_URL}/${id}`);

export default {
  getAll,
  getByDepartment,
  create,
  update,
  delete: deleteEmployee
};
