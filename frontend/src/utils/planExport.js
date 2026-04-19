import UserPlanService from "../services/UserPlanService";

const downloadBlobFile = (blob, filename) => {
  const url = window.URL.createObjectURL(blob);
  const link = document.createElement("a");
  link.href = url;
  link.download = filename;
  document.body.appendChild(link);
  link.click();
  link.remove();
  window.URL.revokeObjectURL(url);
};

export const exportPlanFile = async (idPlan, type = "docx") => {
  const normalizedType = type.toLowerCase();

  try {
    const response =
      normalizedType === "pdf"
        ? await UserPlanService.exportPlanPdf(idPlan)
        : await UserPlanService.exportPlanDocx(idPlan);

    downloadBlobFile(response.data, `plan-${idPlan}.${normalizedType}`);
    return true;
  } catch (err) {
    console.error(
      `Failed to export ${normalizedType.toUpperCase()} for plan ${idPlan}`,
      err
    );
    alert(`Failed to export ${normalizedType.toUpperCase()}.`);
    return false;
  }
};
