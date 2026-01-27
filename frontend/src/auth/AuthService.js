// src/auth/AuthService.js
import api from "../api/api";

const login = async (email, password) => {
  const res = await api.post("/auth/login", { email, password });

  localStorage.setItem("accessToken", res.data.accessToken);
  localStorage.setItem("refreshToken", res.data.refreshToken);
  localStorage.setItem("userId", res.data.userId);

  return res.data;
};

const logout = async () => {
  const userId = localStorage.getItem("userId");

  await api.post("/auth/logout", null, {
    params: { userId },
  });

  localStorage.clear();
};

const isAuthenticated = () => {
  return !!localStorage.getItem("accessToken");
};

export default {
  login,
  logout,
  isAuthenticated,
};
