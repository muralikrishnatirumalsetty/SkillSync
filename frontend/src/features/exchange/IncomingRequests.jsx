import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import {
  getIncoming,
  acceptRequest,
  rejectRequest,
} from "./exchangeApi";

export default function IncomingRequests() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  const load = async () => {
    try {
      const res = await getIncoming();

      const list =
        res.data.data ||
        res.data.content ||
        res.data;

      setData(list);
    } catch (err) {
      toast.error("Failed to load incoming requests");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
  }, []);

  const accept = async (id) => {
    try {
      await acceptRequest(id);
      toast.success("Request accepted");
      load();
    } catch (err) {
      toast.error("Failed to accept request");
    }
  };

  const reject = async (id) => {
    try {
      await rejectRequest(id);
      toast.success("Request rejected");
      load();
    } catch (err) {
      toast.error("Failed to reject request");
    }
  };

  if (loading)
    return <div className="text-white p-10">Loading...</div>;

  if (!data.length)
    return (
      <div className="text-gray-400 p-10 text-center">
        No incoming requests yet.
      </div>
    );

  return (
    <div className="p-8 grid gap-6">
      <h1 className="text-3xl font-semibold tracking-tight mb-8 text-white">
  Incoming Requests
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
            From: {r.requesterName}
          </p>

          <p className="text-gray-400 mb-4">
            Status: {r.status}
          </p>

          {r.status === "PENDING" && (
            <div className="space-x-3">
              <button
                onClick={() => accept(r.id)}
                className="bg-green-500 px-4 py-2 rounded text-white"
              >
                Accept
              </button>

              <button
                onClick={() => reject(r.id)}
                className="bg-red-500 px-4 py-2 rounded text-white"
              >
                Reject
              </button>
            </div>
          )}
        </div>
      ))}
    </div>
  );
}