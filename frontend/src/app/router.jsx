import { Routes, Route } from "react-router-dom";
import Landing from "../pages/Landing";
import Login from "../features/auth/Login";
import Register from "../features/auth/Register";
import BrowseSkills from "../features/skills/BrowseSkills";
import DashboardLayout from "../components/layout/DashboardLayout";
import MySkills from "../features/skills/MySkills";
import Profile from "../features/user/Profile";
import Users from "../features/admin/Users";
import SentRequests from "../features/exchange/SentRequests";
import IncomingRequests from "../features/exchange/IncomingRequests";
import NotFound from "../pages/NotFound";
import Unauthorized from "../pages/Unauthorized";
import ProtectedRoute from "./ProtectedRoute";

const Router = () => {
  return (
    <Routes>
      <Route path="/" element={<Landing />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/skills" element={<BrowseSkills />} />
      <Route path="/unauthorized" element={<Unauthorized />} />

      <Route
        path="/dashboard"
        element={
          <ProtectedRoute role="USER">
            <DashboardLayout />
          </ProtectedRoute>
        }
      >
        <Route index element={<MySkills />} />
        <Route path="profile" element={<Profile />} />
        <Route path="sent" element={<SentRequests />} />
        <Route path="incoming" element={<IncomingRequests />} />
      </Route>

      <Route
        path="/admin"
        element={
          <ProtectedRoute role="ADMIN">
            <Users />
          </ProtectedRoute>
        }
      />

      <Route path="*" element={<NotFound />} />
    </Routes>
  );
};

export default Router;