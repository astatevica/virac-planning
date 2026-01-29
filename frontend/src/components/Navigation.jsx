import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import AuthService from "../auth/AuthService";

const Navigation = () => {

  const navigate = useNavigate();
  const isLoggedIn = AuthService.isAuthenticated();

  const logout = () => {
    AuthService.logout().then(() => {
      navigate("/login");
      window.location.reload();
    });
  };


  return (
    <nav style={styles.nav}>
      <h3 style={styles.logo}>VIRAC</h3>
      

      <ul style={styles.ul}>
        <li>
          {isLoggedIn && (
            <button onClick={logout}>Logout</button>
          )}
        </li>
        <li>
          <NavLink to="/" style={styles.link}>
            Home
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/departments" style={styles.link}>
            Departments
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/employee" style={styles.link}>
            Employee
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/project-management" style={styles.link}>
            Project Management
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/project" style={styles.link}>
            Project
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/project-plan" style={styles.link}>
            Project Plan
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/student-work" style={styles.link}>
            Student Work
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/work-plan" style={styles.link}>
            Work Plan
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/journal" style={styles.link}>
            Journal
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/scientific-articles" style={styles.link}>
            Scientific Articles
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/article-plan" style={styles.link}>
            Article Plan
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/course" style={styles.link}>
            Course
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/course-plan" style={styles.link}>
            Course Plan
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/year" style={styles.link}>
            Year
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/plan" style={styles.link}>
            Plan
          </NavLink>
        </li>
      </ul>
    </nav>
  );
};

const styles = {
  nav: {
    display: "flex",
    alignItems: "center",
    justifyContent: "space-between",
    padding: "10px 20px",
    background: "#282c34",
    color: "white",
  },
  logo: {
    margin: 0,
  },
  ul: {
    listStyle: "none",
    display: "flex",
    gap: "15px",
    margin: 0,
    padding: 0,
  },
  link: {
    color: "white",
    textDecoration: "none",
  },
};

export default Navigation;
