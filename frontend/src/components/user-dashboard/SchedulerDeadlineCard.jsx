import React from "react";

export default function SchedulerDeadlineCard({
  scheduler,
  plannedDaysLeft,
  doneDaysLeft,
  formatSchedulerDate,
  getDeadlineClassName,
}) {
  return (
    <div className="user-dashboard-scheduler-card">
      <div>
        <strong>Planned submission date:</strong>{" "}
        {formatSchedulerDate(scheduler?.plannedFreezeDate)}
      </div>
      <div>
        <strong>Days left until planned submission:</strong>{" "}
        <span className={getDeadlineClassName(plannedDaysLeft)}>
          {plannedDaysLeft === null
            ? "Not set"
            : plannedDaysLeft >= 0
              ? plannedDaysLeft
              : `Expired by ${Math.abs(plannedDaysLeft)} days`}
        </span>
      </div>
      <div className="user-dashboard-scheduler-row-gap">
        <strong>Done submission date:</strong>{" "}
        {formatSchedulerDate(scheduler?.doneFreezeDate)}
      </div>
      <div>
        <strong>Days left until done submission:</strong>{" "}
        <span className={getDeadlineClassName(doneDaysLeft)}>
          {doneDaysLeft === null
            ? "Not set"
            : doneDaysLeft >= 0
              ? doneDaysLeft
              : `Expired by ${Math.abs(doneDaysLeft)} days`}
        </span>
      </div>
    </div>
  );
}
