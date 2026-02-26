import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import { getSent, cancelRequest } from "./exchangeApi";

export default function SentRequests() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  const load = async () => {
  try {
    const res = await getSent();

    console.log("SENT RESPONSE:", res.data);

    // ✅ Extract real array
    const list = res.data.data || [];

    setData(list);
  } catch (err) {
    toast.error("Failed to load sent requests");
  } finally {
    setLoading(false);
  }
};

  useEffect(() => {
    load();
  }, []);

  const cancel = async (id) => {
    if (!window.confirm("Cancel this request?")) return;

    try {
      await cancelRequest(id);
      toast.success("Request cancelled");
      load();
    } catch (err) {
      toast.error(err.response?.data?.message || "Cannot cancel");
    }
  };

  if (loading)
    return <div className="text-white p-10">Loading...</div>;

  if (!data || data.length === 0)
    return (
      <div className="text-gray-400 p-10 text-center">
        No sent requests yet.
      </div>
    );

  return (
    <div className="p-8 grid gap-6">
      <h1 className="text-3xl font-semibold tracking-tight mb-8 text-white">
        Sent Requests
</h1>
      {data.map((r) => (
        <div
          key={r.id}
          className="bg-slate-800 p-6 rounded-xl shadow-lg"
        >
          <h3 className="text-xl text-white font-semibold">
            {r.skillTitle}
          </h3>

          <p className="text-gray-400">
            To: {r.skillOwner}
          </p>

          <p className="text-gray-400 mb-4">
            Status: {r.status}
          </p>

          {r.status === "PENDING" && (
            <button
              onClick={() => cancel(r.id)}
              className="bg-red-500 hover:bg-red-600 transition px-4 py-2 rounded text-white"
            >
              Cancel
            </button>
          )}
        </div>
      ))}
    </div>
  );
}