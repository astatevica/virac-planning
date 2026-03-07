import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../api/api";


export default function UserPlanView() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [fullPlan, setFullPlan] = useState(null);

  useEffect(() => {
    const loadFullPlan = async () => {
      try {
        const fullPlanRes = await api.get(`/user/full-plan/${id}`);
        setFullPlan(fullPlanRes.data);
      } catch (err) {
        console.error(err);
      }
    };
    loadFullPlan();
  }, [id]);

  if (!fullPlan) return <p>Loading...</p>;

  return (
    <div style={{ maxWidth: 1100, margin: "auto" }}>
      {fullPlan && (
      <>
        <h3>Full Plan</h3>

        <table border="1" cellPadding="5">
          <thead>
            <tr>
              <th>Activity</th>
              <th>Planned</th>
              <th>Done</th>
            </tr>
          </thead>

          <tbody>

            <tr>
              <td>Projects</td>
              <td>{fullPlan.numOfProjects}</td>
              <td>
                {fullPlan.projects?.map(p => (
                  <div key={p.idProject}>{p.name}</div>
                ))}
              </td>
            </tr>

            <tr>
              <td>Articles</td>
              <td>{fullPlan.numOfArticles}</td>
              <td>
                {fullPlan.articles?.map(a => (
                  <div key={a.idScientificArticles}>{a.title}</div>
                ))}
              </td>
            </tr>

            <tr>
              <td>Courses</td>
              <td>{fullPlan.numOfCourses}</td>
              <td>
                {fullPlan.courses?.map(c => (
                  <div key={c.idCourse}>{c.name}</div>
                ))}
              </td>
            </tr>

            <tr>
              <td>Student Work</td>
              <td>{fullPlan.numOfStudWork}</td>
              <td>
                {fullPlan.studentWork?.map(sw => (
                  <div key={sw.idStudentWork}>{sw.title}</div>
                ))}
              </td>
            </tr>

            <tr>
              <td>Participation in Conferences</td>
              <td>{fullPlan.partInConf}</td>
              <td>{fullPlan.partInConfEnd}</td>
            </tr>

            <tr>
              <td>Committee Abroad Conferences</td>
              <td>{fullPlan.comAbConf}</td>
              <td>{fullPlan.comAbConfEnd}</td>
            </tr>

            <tr>
              <td>Promotion of Research</td>
              <td>{fullPlan.promoOfResearch}</td>
              <td>{fullPlan.promoOfResearchEnd}</td>
            </tr>

            <tr>
              <td>Administrative Work</td>
              <td>{fullPlan.adminWork}</td>
              <td>{fullPlan.adminWorkEnd}</td>
            </tr>

            <tr>
              <td>Project Applications Submitted</td>
              <td>{fullPlan.projApplicSub}</td>
              <td>{fullPlan.projApplicSubEnd}</td>
            </tr>

            <tr>
              <td>Skills Development</td>
              <td>{fullPlan.skillsDevelopment}</td>
              <td>{fullPlan.skillsDevelopmentEnd}</td>
            </tr>

            <tr>
              <td>Participation in Seminars</td>
              <td>{fullPlan.participationInSeminars}</td>
              <td>{fullPlan.participationInSeminarsEnd}</td>
            </tr>

            <tr>
              <td>Other Jobs</td>
              <td>{fullPlan.otherJobs}</td>
              <td>{fullPlan.otherJobsEnd}</td>
            </tr>

          </tbody>
        </table>
      </>
    )}
    
      <button onClick={() => navigate("/user/dashboard")}>
        Back to Dashboard
      </button>
      <button onClick={() => navigate("/user/plans")}>
        Back to Archive
      </button>
    </div>
  );
}
