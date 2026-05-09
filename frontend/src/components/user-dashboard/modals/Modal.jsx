import React from "react";

export default function Modal({ open, width = 700, children }) {
  if (!open) return null;

  return (
    <div className="user-dashboard-modal-overlay">
      <div
        className="user-dashboard-modal-content"
        style={{ width, maxWidth: "95%" }}
      >
        {children}
      </div>
    </div>
  );
}
