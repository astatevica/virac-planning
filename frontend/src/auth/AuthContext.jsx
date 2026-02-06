// src/auth/AuthContext.jsx
import { createContext, useContext, useEffect, useState } from "react";
import AuthService from "./AuthService";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuth, setIsAuth] = useState(false);
  const [role, setRole] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const tokenExists = AuthService.isAuthenticated();
    const userRole = AuthService.getRole();

    setIsAuth(tokenExists);
    setRole(userRole);
    setLoading(false);
  }, []);

  const login = async (email, password) => {
    const data = await AuthService.login(email, password);
    setIsAuth(true);
    setRole(data.role);
  };

  const logout = async () => {
    await AuthService.logout();
    setIsAuth(false);
    setRole(null);
  };

  return (
    <AuthContext.Provider value={{ isAuth, role, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
