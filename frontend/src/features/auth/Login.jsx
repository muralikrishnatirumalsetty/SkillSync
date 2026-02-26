import { useState, useContext } from "react";
import { useNavigate, Link } from "react-router-dom";
import { login } from "./authApi";
import { AuthContext } from "../../context/AuthContext";
import toast from "react-hot-toast";

export default function Login() {
  const navigate = useNavigate();
  const { setUser } = useContext(AuthContext);

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const handleLogin = async () => {
    try {
      const res = await login(form);

      if (!res.success) {
        toast.error(res.message || "Invalid credentials");
        return;
      }

      const token = res.data?.token;

      if (!token) {
        toast.error("Token not received");
        return;
      }

      // Save token
      localStorage.setItem("token", token);

      // Decode token to update AuthContext immediately
      const payload = JSON.parse(atob(token.split(".")[1]));

      setUser({
        email: payload.sub,
        role: payload.role?.replace("ROLE_", ""),
      });

      toast.success("Login successful");

      navigate("/dashboard");

    } catch (err) {
      toast.error("Login failed");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-950 px-4">
      <div className="w-full max-w-md bg-slate-900/70 backdrop-blur-xl border border-slate-800 rounded-2xl p-8 space-y-6 shadow-xl">

        <h2 className="text-3xl font-bold text-white">
          Welcome Back
        </h2>

        <div className="space-y-4">
          <input
            type="email"
            placeholder="Email"
            value={form.email}
            onChange={(e) =>
              setForm({ ...form, email: e.target.value })
            }
            className="w-full p-3 rounded-lg bg-slate-800 text-white outline-none focus:ring-2 focus:ring-indigo-500"
          />

          <input
            type="password"
            placeholder="Password"
            value={form.password}
            onChange={(e) =>
              setForm({ ...form, password: e.target.value })
            }
            className="w-full p-3 rounded-lg bg-slate-800 text-white outline-none focus:ring-2 focus:ring-indigo-500"
          />
        </div>

        <button
          onClick={handleLogin}
          className="w-full py-3 rounded-lg bg-gradient-to-r from-indigo-500 to-purple-600 text-white font-semibold hover:opacity-90 transition"
        >
          Login
        </button>

        <p className="text-sm text-slate-400 text-center">
          New here?{" "}
          <Link
            to="/register"
            className="text-indigo-400 hover:underline"
          >
            Create an account
          </Link>
        </p>
      </div>
    </div>
  );
}