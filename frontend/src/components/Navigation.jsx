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
          <NavLink to="/departments/add" style={styles.link}>
            Add Department
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
