// src/auth/AuthContext.jsx
import { createContext, useContext, useState } from "react";
import AuthService from "./AuthService";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuth, setIsAuth] = useState(AuthService.isAuthenticated());

  const login = async (email, password) => {
    await AuthService.login(email, password);
    setIsAuth(true);
  };

  const logout = async () => {
    await AuthService.logout();
    setIsAuth(false);
  };

  return (
    <AuthContext.Provider value={{ isAuth, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
