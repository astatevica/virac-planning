import api from "../api/api";
import { jwtDecode } from "jwt-decode";

class AuthService {

  async login(email, password) {
    const res = await api.post("/auth/login", { email, password });

    localStorage.setItem("accessToken", res.data.accessToken);
    localStorage.setItem("refreshToken", res.data.refreshToken);

    return res.data;
  }

  async register(data) {
    const res = await api.post("/auth/register", data);

    localStorage.setItem("accessToken", res.data.accessToken);
    localStorage.setItem("refreshToken", res.data.refreshToken);

    return res.data;
  }

  async logout() {
    try {
      await api.post("/auth/logout");
    } finally {
      localStorage.clear();
      window.location.href = "/login";
    }
  }

  isAuthenticated() {
    return !!localStorage.getItem("accessToken");
  }

  getToken() {
    return localStorage.getItem("accessToken");
  }

  getRole() {
    const token = localStorage.getItem("accessToken");
    if (!token) return null;

    try {
      const decoded = jwtDecode(token);

      // depends how you generate JWT
      // most common:
      return decoded.role 
        || decoded.authorities?.[0]?.replace("ROLE_", "") 
        || null;

    } catch (e) {
      console.error("Invalid token", e);
      return null;
    }
  }
}

// ✅ create named instance
const authService = new AuthService();

// ✅ export named instance
export default authService;
