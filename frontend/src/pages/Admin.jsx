// src/pages/Admin.jsx
import { useEffect, useState } from "react";
import api from "../api/api";

const Admin = () => {
  const [data, setData] = useState("");

  useEffect(() => {
    api.get("/admin/dashboard")
      .then((res) => setData(res.data))
      .catch(() => setData("Access denied"));
  }, []);

  return (
    <div>
      <h2>Admin Panel</h2>
      <p>{data}</p>
    </div>
  );
};

export default Admin;
