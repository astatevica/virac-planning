import api from "../api/api";

const API_URL = "/admin";

const DepartmentHeadService = {
  getDepartmentCredentials() {
    return api.get(`${API_URL}/department/credentials`);
  },

  getEmployeesByDepartment() {
    return api.get(`${API_URL}/employee/filter/department`);
  },

  getEmployeePlans(idEmployee) {
    return api.get(`${API_URL}/plan/filter/employee/${idEmployee}`);
  },

  getPlanById(idPlan) {
    return api.get(`${API_URL}/plan/${idPlan}`);
  },

  getDepartmentPlans() {
    return api.get(`${API_URL}/plan/filter/department`);
  },

  getDepartmentPlansByYear(idYear) {
    return api.get(`${API_URL}/plan/filter/department/year/${idYear}`);
  },
};

export default DepartmentHeadService;
