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
            Project Plan
          </NavLink>
        </li>
        <li>
          <NavLink to="/student-work" style={styles.link}>
            Student Work
          </NavLink>
        </li>
        <li>
          <NavLink to="/work-plan" style={styles.link}>
            Work Plan
          </NavLink>
        </li>
        <li>
          <NavLink to="/journal" style={styles.link}>
            Journal
          </NavLink>
        </li>
        <li>
          <NavLink to="/scientific-articles" style={styles.link}>
            Scientific Articles
          </NavLink>
        </li>
        <li>
          <NavLink to="/article-plan" style={styles.link}>
            Article Plan
          </NavLink>
        </li>
        <li>
          <NavLink to="/course" style={styles.link}>
            Course
          </NavLink>
        </li>
        <li>
          <NavLink to="/course-plan" style={styles.link}>
            Course Plan
          </NavLink>
        </li>
        <li>
          <NavLink to="/year" style={styles.link}>
            Year
          </NavLink>
        </li>
        <li>
          <NavLink to="/plan" style={styles.link}>
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
