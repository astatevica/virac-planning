import React from "react";
import { NavLink } from "react-router-dom";

const Navigation = () => {
  return (
    <nav style={styles.nav}>
      <h3 style={styles.logo}>VIRAC</h3>

      <ul style={styles.ul}>
        <li>
          <NavLink to="/" style={styles.link}>
            Home
          </NavLink>
        </li>
        <li>
          <NavLink to="/departments" style={styles.link}>
            Departments
          </NavLink>
        </li>
        <li>
          <NavLink to="/employee" style={styles.link}>
            Employee
          </NavLink>
        </li>
        <li>
          <NavLink to="/project-management" style={styles.link}>
            Project Management
          </NavLink>
        </li>
        <li>
          <NavLink to="/project" style={styles.link}>
            Project
          </NavLink>
        </li>
        <li>
          <NavLink to="/project-plan" style={styles.link}>
            Project Plan List
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
