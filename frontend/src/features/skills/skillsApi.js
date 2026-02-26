import api from "../../app/axios";

/* PUBLIC SKILLS (Browse page) */
export const getSkills = (params) =>
  api.get("/api/skills", { params });

/* USER SKILLS */
export const getMySkills = () =>
  api.get("/api/user/skills");

export const createSkill = (data) =>
  api.post("/api/user/skills", data);

export const deleteSkill = (id) =>
  api.delete(`/api/user/skills/${id}`);