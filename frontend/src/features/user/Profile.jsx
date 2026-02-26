import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import { getProfile } from "./userApi";

export default function Profile() {
  const [data, setData] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        const res = await getProfile();
        setData(res.data);
      } catch {
        toast.error("Failed to load profile");
      }
    };
    load();
  }, []);

  if (!data)
    return <div className="text-white p-10">Loading...</div>;

  return (
    <div className="p-10">
      <div className="bg-slate-800 p-8 rounded-xl shadow-xl">
        <h2 className="text-2xl font-bold text-white mb-4">
          {data.name}
        </h2>
        <p className="text-gray-400">{data.email}</p>
        <p className="text-gray-400 mt-2">
          Role: {data.role}
        </p>
        <p className="text-gray-400">
          Status: {data.status}
        </p>
      </div>
    </div>
  );
}