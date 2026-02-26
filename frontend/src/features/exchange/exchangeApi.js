import api from "../../app/axios";

/* ---------- REQUEST SKILL ---------- */
export const requestSkill = (skillId) =>
  api.post(`/api/user/exchange/${skillId}`);

/* ---------- GET SENT REQUESTS ---------- */
export const getSent = () =>
  api.get("/api/user/exchange/sent");

/* ---------- GET INCOMING REQUESTS ---------- */
export const getIncoming = () =>
  api.get("/api/user/exchange/incoming");

/* ---------- ACCEPT REQUEST ---------- */
export const acceptRequest = (id) =>
  api.put(`/api/user/exchange/${id}/accept`);

/* ---------- REJECT REQUEST ---------- */
export const rejectRequest = (id) =>
  api.put(`/api/user/exchange/${id}/reject`);

/* ---------- CANCEL REQUEST ---------- */
export const cancelRequest = (id) =>
  api.put(`/api/user/exchange/${id}/cancel`);