import api from "../../app/axios";

export const getUsers = () =>
  api.get("/api/admin/users");