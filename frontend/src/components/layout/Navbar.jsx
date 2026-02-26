import { Link, useLocation } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../../context/AuthContext";

const Navbar = () => {
  const { user, logout } = useContext(AuthContext);
  const location = useLocation();

  const active = (path) =>
    location.pathname === path
      ? "text-indigo-400"
      : "text-slate-300 hover:text-white";

  return (
    <nav className="w-full bg-slate-950 border-b border-slate-800">
      <div className="max-w-7xl mx-auto px-8 py-4 flex items-center justify-between">

        {/* Logo */}
        <Link
          to="/"
          className="text-xl font-semibold text-white tracking-wide"
        >
          SkillSync
        </Link>

        {/* Nav Links */}
        <div className="flex items-center gap-8 text-sm font-medium">

          {/* 🔥 ALWAYS SHOW BROWSE */}
          <Link to="/skills" className={active("/skills")}>
            Browse
          </Link>

          {!user && (
            <>
              <Link to="/login" className={active("/login")}>
                Login
              </Link>

              <Link
                to="/register"
                className="px-4 py-2 rounded-lg bg-indigo-600 hover:bg-indigo-500 text-white transition"
              >
                Register
              </Link>
            </>
          )}

          {user && (
            <>
              <Link to="/dashboard" className={active("/dashboard")}>
                Dashboard
              </Link>

              <button
                onClick={logout}
                className="text-red-400 hover:text-red-300"
              >
                Logout
              </button>
            </>
          )}

        </div>
      </div>
    </nav>
  );
};

export default Navbar;