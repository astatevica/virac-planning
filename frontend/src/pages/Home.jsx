// src/pages/Home.jsx
import { useAuth } from "../auth/AuthContext";

const Home = () => {
  const { logout } = useAuth();

  return (
    <div>
      <h2>Home</h2>
      <button onClick={logout}>Logout</button>
    </div>
  );
};

export default Home;
