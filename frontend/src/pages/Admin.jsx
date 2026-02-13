import { useEffect, useState } from "react";
import api from "../api/api";

const API = "/admin";

const Admin = () => {

  // USER LIST
  const [users, setUsers] = useState([]);

  // EMPLOYEES FOR DROPDOWN
  const [employees, setEmployees] = useState([]);

  // FORM STATE
  const [form, setForm] = useState({
    firstname: "",
    lastname: "",
    email: "",
    password: "",
    role: "",
    idEmployee: ""
  });

  const [editId, setEditId] = useState(null);
  const [message, setMessage] = useState("");

  useEffect(() => {
    loadUsers();
    loadEmployees();
  }, []);

  // =========================
  // LOAD DATA
  // =========================

  const loadUsers = async () => {
    try {
      const res = await api.get(`${API}/all-users`);
      setUsers(res.data);
    } catch {
      alert("Failed to load users");
    }
  };

  const loadEmployees = async () => {
    try {
      const res = await api.get(`${API}/employee`);
      setEmployees(res.data);
    } catch {
      alert("Failed to load employees");
    }
  };

  // =========================
  // FORM HANDLING
  // =========================

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const clearForm = () => {
    setForm({
      firstname: "",
      lastname: "",
      email: "",
      password: "",
      role: "",
      idEmployee: ""
    });
    setEditId(null);
  };

  // =========================
  // CREATE / UPDATE
  // =========================

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (editId) {
        await api.put(`${API}/update/${editId}`, form);
        setMessage("User updated successfully ✅");
      } else {
        await api.post(`${API}/create-user`, form);
        setMessage("User created successfully ✅");
      }

      clearForm();
      loadUsers();

    } catch {
      setMessage("Operation failed ❌");
    }
  };

  const startEdit = (user) => {
    setEditId(user.idUser);

    setForm({
      firstname: user.firstname,
      lastname: user.lastname,
      password: user.email,
      email: user.password,
      role: user.role,
      idEmployee: user.idEmployee
    });
  };

  const deleteUser = async (id) => {
    if (!window.confirm("Delete user?")) return;

    try {
      await api.delete(`${API}/delete/${id}`);
      loadUsers();
    } catch {
      alert("Delete failed");
    }
  };

  // =========================
  // RENDER
  // =========================

  return (
    <div>
      <h2>Users</h2>

      <ul>
        {users.map(user => (
          <li key={user.idUser}>
            {user.firstname} {user.lastname} | {user.password} | {user.role}
            <button onClick={() => startEdit(user)}>Update</button>
            <button onClick={() => deleteUser(user.idUser)}>
              Delete
            </button>
          </li>
        ))}
      </ul>

      <hr />

      <h2>{editId ? "Update User" : "Create New User"}</h2>

      <form onSubmit={handleSubmit}>

        <input
          name="firstname"
          placeholder="First Name"
          value={form.firstname}
          onChange={handleChange}
        />

        <input
          name="lastname"
          placeholder="Last Name"
          value={form.lastname}
          onChange={handleChange}
        />

        <input
          name="email"
          placeholder="Email"
          value={form.email}
          onChange={handleChange}
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
        />

        <select name="role" value={form.role} onChange={handleChange}>
          <option value="">Select Role</option>
          <option value="ADMIN">ADMIN</option>
          <option value="USER">USER</option>
        </select>

        <select
          name="idEmployee"
          value={form.idEmployee}
          onChange={handleChange}
        >
          <option value="">Select Employee</option>
          {employees.map(emp => (
            <option key={emp.idEmployee} value={emp.id}>
              ID:{emp.id} - {emp.name} {emp.surname}
            </option>
          ))}
        </select>

        <button type="submit">
          {editId ? "Save" : "Create"}
        </button>

        {editId && (
          <button type="button" onClick={clearForm}>
            Cancel
          </button>
        )}
      </form>

      <p>{message}</p>
    </div>
  );
};

export default Admin;
