import React, { useEffect, useState } from "react";
import CourseService from "../services/CourseService";

const CourseList = () => {
  const [courses, setCourses] = useState([]);

  // ADD
  const [name, setName] = useState("");
  const [ectsCredits, setEctsCredits] = useState("");
  const [semester, setSemester] = useState("");
  const [faculty, setFaculty] = useState("");

  // UPDATE
  const [editId, setEditId] = useState(null);

  useEffect(() => {
    loadCourses();
  }, []);

  const loadCourses = () => {
    CourseService.getAll()
      .then(res => setCourses(res.data))
      .catch(err => alert(err.response?.data || "Failed to load courses"));
  };

  // CREATE
  const addCourse = () => {
    if (!name || !ectsCredits || !semester || !faculty) {
      alert("All fields are required");
      return;
    }

    CourseService.create({
      name,
      ectsCredits: Number(ectsCredits),
      semester,
      faculty
    })
      .then(() => {
        clearForm();
        loadCourses();
      })
      .catch(err => alert(err.response?.data || "Create failed"));
  };

  // DELETE
  const deleteCourse = (id) => {
    CourseService.delete(id)
      .then(loadCourses)
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  // UPDATE
  const startEdit = (course) => {
    setEditId(course.idCourse);
    setName(course.name);
    setEctsCredits(course.ectsCredits);
    setSemester(course.semester);
    setFaculty(course.faculty);
  };

  const cancelEdit = () => {
    setEditId(null);
    clearForm();
  };

  const saveEdit = () => {
    CourseService.update(editId, {
      name,
      ectsCredits: Number(ectsCredits),
      semester,
      faculty
    })
      .then(() => {
        cancelEdit();
        loadCourses();
      })
      .catch(err => alert(err.response?.data || "Update failed"));
  };

  const clearForm = () => {
    setName("");
    setEctsCredits("");
    setSemester("");
    setFaculty("");
  };

  return (
    <div>
      <h2>Courses</h2>

      {/* ADD / UPDATE */}
      <div style={{ marginBottom: "15px" }}>
        <input
          placeholder="Course name"
          value={name}
          onChange={e => setName(e.target.value)}
        />
        <input
          type="number"
          placeholder="ECTS credits"
          value={ectsCredits}
          onChange={e => setEctsCredits(e.target.value)}
        />
        <input
          placeholder="Semester"
          value={semester}
          onChange={e => setSemester(e.target.value)}
        />
        <input
          placeholder="Faculty"
          value={faculty}
          onChange={e => setFaculty(e.target.value)}
        />

        {editId ? (
          <>
            <button onClick={saveEdit}>Save</button>
            <button onClick={cancelEdit}>Cancel</button>
          </>
        ) : (
          <button onClick={addCourse}>Add</button>
        )}
      </div>

      {/* LIST */}
      <ul>
        {courses.map(course => (
          <li key={course.idCourse}>
            <b>{course.name}</b> | ECTS: {course.ectsCredits} |
            Semester: {course.semester} | Faculty: {course.faculty}
            <button onClick={() => startEdit(course)}>Update</button>
            <button onClick={() => deleteCourse(course.idCourse)}>
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CourseList;
