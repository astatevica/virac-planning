import api from "../api/api";

const API = "/admin";

const adminDashboard = () => api.get(`${API}/dashboard`);

const createUser = (data) => api.post(`${API}/create-user`, data);

const getAllUsers = () => api.get(`${API}/all-users`);

const getById = (id) => api.get(`${API}/${id}`);

const updateById = (id, data) => api.put(`${API}/update/${id}`, data);

const deleteById = (id) => api.delete(`${API}/delete/${id}`);

const getSchedulerByYear = (idYear) => api.get(`${API}/scheduler/${idYear}`)

const updateScheduler = (data) => api.put(`${API}/update/scheduler`, data);

const AdminService = {
  adminDashboard,
  createUser,
  getAllUsers,
  getById,
  updateById,
  deleteById,
  getSchedulerByYear,
  updateScheduler,
};

export default AdminService;
