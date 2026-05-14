// src/pages/Login.jsx
import { useState } from "react";
import { useAuth } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    await login(email, password);

    const role = localStorage.getItem("role");

    if (role === "ADMIN" || role === "USER_DEPART") {
      navigate("/admin/dashboard");
    } else if (role === "USER"){
      navigate("/user/dashboard");
    }else {
      navigate("/login");
    }

  } catch {
    setError("Invalid credentials");
  }
};

  return (
    <form onSubmit={handleSubmit}>
      <h2 data-testid="cypress-title">Login</h2>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <input
        data-testid="cypress-email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />

      <input
        data-testid="cypress-password"
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <button data-testid="cypress-submit" type="submit">Login</button>
    </form>
  );
};

export default Login;
