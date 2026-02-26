import axios from "../../app/axios";

export const getProfile = () =>
  axios.get("/api/user/profile");

export const updateProfile = (data) =>
  axios.put("/api/user/profile", data);

export const changePassword = (data) =>
  axios.put("/api/user/change-password", data);