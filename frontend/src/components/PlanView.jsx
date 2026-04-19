import React, { useEffect, useState } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import PlanService from "../services/PlanService";
import api from "../api/api";
import { useAuth } from "../auth/AuthContext";
import { exportPlanFile } from "../utils/planExport";

const API = "/admin";

const hasItems = (items) => Array.isArray(items) && items.length > 0;

const formatValue = (value) => {
  if (value === null || value === undefined || value === "") return "-";
  return value;
};

const itemStyle = {
  marginBottom: "8px",
  paddingBottom: "8px",
  borderBottom: "1px solid #d6dde5",
};

export default function PlanView() {
  const { id } = useParams();
  const navigate = useNavigate();
  const location = useLocation();
  const { role } = useAuth();

  const [fullPlan, setFullPlan] = useState(null);
  const [employees, setEmployees] = useState([]);
  const [years, setYears] = useState([]);
  const [loadError, setLoadError] = useState("");

  useEffect(() => {
    const loadPlanView = async () => {
      try {
        const fullPlanRes = await PlanService.getById(id);
        setFullPlan(fullPlanRes.data);
        setLoadError("");
      } catch {
        setLoadError("Failed to load plan.");
      }

      try {
        const yearsRes = await api.get("/year/all");
        setYears(yearsRes.data);
      } catch {
        setYears([]);
      }

      if (role === "ADMIN") {
        try {
          const employeesRes = await api.get(`${API}/employee/all`);
          setEmployees(employeesRes.data);
        } catch {
          setEmployees([]);
        }
      } else if (location.state?.employee) {
        setEmployees([location.state.employee]);
      } else {
        setEmployees([]);
      }
    };

    loadPlanView();
  }, [id, location.state, role]);

  if (loadError) return <p>{loadError}</p>;
  if (!fullPlan) return <p>Loading...</p>;

  const employee = employees.find(
    (entry) => String(entry.idEmployee ?? entry.id) === String(fullPlan.idEmployee)
  );
  const year = years.find((entry) => entry.idYear === fullPlan.idYear);
  const fallbackEmployeeName =
    location.state?.employeeName || `Employee ID: ${fullPlan.idEmployee}`;
  const fallbackYearNumber = location.state?.yearNumber || fullPlan.idYear;

  const handleExportDocx = () => {
    exportPlanFile(id, "docx");
  };

  return (
    <div style={styles.page}>
      <h2>Full Plan</h2>

      <p>
        <strong>Employee:</strong>{" "}
        {employee ? `${employee.name} ${employee.surname}` : fallbackEmployeeName}
      </p>
      <p>
        <strong>Year:</strong> {year?.yearNumber || fallbackYearNumber}
      </p>

      <table style={styles.table}>
        <thead>
          <tr>
            <th style={styles.headCell}>Activity</th>
            <th style={styles.headCell}>Planned</th>
            <th style={styles.headCell}>Done</th>
          </tr>
        </thead>

        <tbody>
          <tr>
            <td style={styles.labelCell}>Projects</td>
            <td style={styles.countCell}>{formatValue(fullPlan.numOfProjects)}</td>
            <td style={styles.contentCell}>
              {hasItems(fullPlan.projects) ? (
                fullPlan.projects.map((project) => (
                  <div key={`${project.idPlan}-${project.idProject}`} style={itemStyle}>
                    <div><strong>Name:</strong> {formatValue(project.name)}</div>
                    <div><strong>Number:</strong> {formatValue(project.number)}</div>
                    <div><strong>Start date:</strong> {formatValue(project.startDate)}</div>
                    <div><strong>End date:</strong> {formatValue(project.endDate)}</div>
                    <div><strong>Acronym:</strong> {formatValue(project.acronym)}</div>
                    <div><strong>Tasks:</strong> {formatValue(project.tasks)}</div>
                    <div><strong>Work done:</strong> {formatValue(project.workDone)}</div>
                  </div>
                ))
              ) : (
                <div style={styles.emptyText}>-</div>
              )}
            </td>
          </tr>

          <tr>
            <td style={styles.labelCell}>Articles</td>
            <td style={styles.countCell}>{formatValue(fullPlan.numOfArticles)}</td>
            <td style={styles.contentCell}>
              {hasItems(fullPlan.articles) ? (
                fullPlan.articles.map((article) => (
                  <div key={`${article.idPlan}-${article.idArticle}`} style={itemStyle}>
                    <div><strong>Name:</strong> {formatValue(article.name)}</div>
                    <div><strong>Co-authors:</strong> {formatValue(article.coAuthors)}</div>
                    <div><strong>Journal ID:</strong> {formatValue(article.idJournal)}</div>
                    <div><strong>Comments:</strong> {formatValue(article.articleComments)}</div>
                    <div><strong>Publication link:</strong> {formatValue(article.publicationLink)}</div>
                  </div>
                ))
              ) : (
                <div style={styles.emptyText}>-</div>
              )}
            </td>
          </tr>

          <tr>
            <td style={styles.labelCell}>Courses</td>
            <td style={styles.countCell}>{formatValue(fullPlan.numOfCourses)}</td>
            <td style={styles.contentCell}>
              {hasItems(fullPlan.courses) ? (
                fullPlan.courses.map((course) => (
                  <div key={`${course.idPlan}-${course.idCourse}`} style={itemStyle}>
                    <div><strong>Name:</strong> {formatValue(course.name)}</div>
                    <div><strong>ECTS:</strong> {formatValue(course.ectsCredits)}</div>
                    <div><strong>Semester:</strong> {formatValue(course.semester)}</div>
                    <div><strong>Faculty:</strong> {formatValue(course.faculty)}</div>
                    <div><strong>Work done:</strong> {formatValue(course.workDone)}</div>
                  </div>
                ))
              ) : (
                <div style={styles.emptyText}>-</div>
              )}
            </td>
          </tr>

          <tr>
            <td style={styles.labelCell}>Student Work</td>
            <td style={styles.countCell}>{formatValue(fullPlan.numOfStudWork)}</td>
            <td style={styles.contentCell}>
              {hasItems(fullPlan.studentWork) ? (
                fullPlan.studentWork.map((work) => (
                  <div key={`${work.idPlan}-${work.idStudWork}`} style={itemStyle}>
                    <div><strong>Work name:</strong> {formatValue(work.name)}</div>
                    <div><strong>Student name:</strong> {formatValue(work.studentName)}</div>
                    <div><strong>Student surname:</strong> {formatValue(work.studentSurname)}</div>
                    <div><strong>Degree:</strong> {formatValue(work.degree)}</div>
                    <div><strong>Work done:</strong> {formatValue(work.workDone)}</div>
                  </div>
                ))
              ) : (
                <div style={styles.emptyText}>-</div>
              )}
            </td>
          </tr>

          <tr>
            <td style={styles.labelCell}>Participation in Conferences</td>
            <td style={styles.countCell}>{formatValue(fullPlan.partInConf)}</td>
            <td style={styles.contentCell}>{formatValue(fullPlan.partInConfEnd)}</td>
          </tr>

          <tr>
            <td style={styles.labelCell}>Committee Abroad Conferences</td>
            <td style={styles.countCell}>{formatValue(fullPlan.comAbConf)}</td>
            <td style={styles.contentCell}>{formatValue(fullPlan.comAbConfEnd)}</td>
          </tr>

          <tr>
            <td style={styles.labelCell}>Promotion of Research</td>
            <td style={styles.countCell}>{formatValue(fullPlan.promoOfResearch)}</td>
            <td style={styles.contentCell}>{formatValue(fullPlan.promoOfResearchEnd)}</td>
          </tr>

          <tr>
            <td style={styles.labelCell}>Administrative Work</td>
            <td style={styles.countCell}>{formatValue(fullPlan.adminWork)}</td>
            <td style={styles.contentCell}>{formatValue(fullPlan.adminWorkEnd)}</td>
          </tr>

          <tr>
            <td style={styles.labelCell}>Project Applications Submitted</td>
            <td style={styles.countCell}>{formatValue(fullPlan.projApplicSub)}</td>
            <td style={styles.contentCell}>{formatValue(fullPlan.projApplicSubEnd)}</td>
          </tr>

          <tr>
            <td style={styles.labelCell}>Skills Development</td>
            <td style={styles.countCell}>{formatValue(fullPlan.skillsDevelopment)}</td>
            <td style={styles.contentCell}>{formatValue(fullPlan.skillsDevelopmentEnd)}</td>
          </tr>

          <tr>
            <td style={styles.labelCell}>Participation in Seminars</td>
            <td style={styles.countCell}>{formatValue(fullPlan.participationInSeminars)}</td>
            <td style={styles.contentCell}>{formatValue(fullPlan.participationInSeminarsEnd)}</td>
          </tr>

          <tr>
            <td style={styles.labelCell}>Other Jobs</td>
            <td style={styles.countCell}>{formatValue(fullPlan.otherJobs)}</td>
            <td style={styles.contentCell}>{formatValue(fullPlan.otherJobsEnd)}</td>
          </tr>
        </tbody>
      </table>

      <div style={styles.buttonRow}>
        <button
          style={styles.secondaryButton}
          onClick={handleExportDocx}
        >
          Download DOCX
        </button>
        <button
          style={styles.primaryButton}
          onClick={() =>
            navigate(role === "USER_DEPART" ? "/admin/dashboard" : "/admin/plan")
          }
        >
          Back
        </button>
      </div>
    </div>
  );
}

const styles = {
  page: {
    maxWidth: 1100,
    margin: "0 auto",
    padding: "20px",
  },
  table: {
    width: "100%",
    borderCollapse: "collapse",
    border: "1px solid #aeb6bf",
    background: "#fff",
  },
  headCell: {
    textAlign: "left",
    padding: "10px 12px",
    background: "#f6f8fa",
    border: "1px solid #b7c0c8",
  },
  labelCell: {
    padding: "10px 12px",
    border: "1px solid #b7c0c8",
    verticalAlign: "top",
    fontWeight: 600,
    width: "22%",
  },
  countCell: {
    padding: "10px 12px",
    border: "1px solid #b7c0c8",
    verticalAlign: "top",
    width: "10%",
  },
  contentCell: {
    textAlign: "left",
    padding: "10px 12px",
    border: "1px solid #b7c0c8",
    verticalAlign: "top",
  },
  emptyText: {
    color: "#64748b",
  },
  buttonRow: {
    display: "flex",
    gap: 12,
    marginTop: 20,
    flexWrap: "wrap",
  },
  primaryButton: {
    padding: "10px 16px",
    borderRadius: 4,
    border: "1px solid #b7c0c8",
    background: "#f6f8fa",
    color: "#111827",
    cursor: "pointer",
    fontWeight: 500,
  },
  secondaryButton: {
    padding: "10px 16px",
    borderRadius: 4,
    border: "1px solid #0d6efd",
    background: "#0d6efd",
    color: "#fff",
    cursor: "pointer",
    fontWeight: 500,
  },
};
