import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navigation from "./components/Navigation";
import DepartmentList from "./components/DepartmentList";

function App() {
  return (
    <BrowserRouter>
      <Navigation />

      <div style={{ padding: "20px" }}>
        <Routes>
          <Route path="/" element={<h2>Welcome to VIRAC</h2>} />
          <Route path="/departments" element={<DepartmentList />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
