import React from "react";
import DepartmentList from "./components/DepartmentList";
import DepartmentForm from "./components/DepartmentForm";

function App() {
  return (
    <div>
      <DepartmentForm onCreated={() => window.location.reload()} />
      <DepartmentList />
    </div>
  );
}

export default App;