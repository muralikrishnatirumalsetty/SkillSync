import { Outlet, NavLink } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../../context/AuthContext";

const DashboardLayout = () => {
  const { logout } = useContext(AuthContext);

  const linkStyle =
    "block px-4 py-3 rounded-lg text-sm font-medium transition-colors";

  return (
    <div className="min-h-screen flex bg-[#0f172a]">

      {/* Sidebar */}
      <aside className="w-64 bg-[#111827] border-r border-slate-800 p-8 flex flex-col">

        <h1 className="text-xl font-semibold tracking-tight mb-10 text-white">
          SkillSync
        </h1>

        <nav className="space-y-2 flex-1">
          {[
            { to: "/", label: "Home" },
            { to: "/dashboard", label: "My Skills" },
            { to: "/dashboard/sent", label: "Sent Requests" },
            { to: "/dashboard/incoming", label: "Incoming Requests" },
            { to: "/dashboard/profile", label: "Profile" },
          ].map((item) => (
            <NavLink
              key={item.to}
              to={item.to}
              end
              className={({ isActive }) =>
                `${linkStyle} ${
                  isActive
                    ? "bg-slate-800 text-white"
                    : "text-slate-400 hover:bg-slate-800 hover:text-white"
                }`
              }
            >
              {item.label}
            </NavLink>
          ))}
        </nav>

        <button
          onClick={logout}
          className="mt-6 px-4 py-3 rounded-lg bg-red-600 text-sm font-medium hover:bg-red-700 transition"
        >
          Logout
        </button>
      </aside>

      {/* Main */}
      <main className="flex-1 p-14">
        <div className="max-w-6xl mx-auto">
          <Outlet />
        </div>
      </main>
    </div>
  );
};

export default DashboardLayout;