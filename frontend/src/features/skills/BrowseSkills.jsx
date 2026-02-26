import { useEffect, useState, useContext } from "react";
import { getSkills } from "./skillsApi";
import { requestSkill } from "../exchange/exchangeApi";
import { AuthContext } from "../../context/AuthContext";
import Card from "../../components/ui/Card";
import Input from "../../components/ui/Input";
import Button from "../../components/ui/Button";
import Navbar from "../../components/layout/Navbar";

const BrowseSkills = () => {
  const { user } = useContext(AuthContext);

  const [skills, setSkills] = useState([]);
  const [filters, setFilters] = useState({
    keyword: "",
    category: "",
    level: "",
    page: 0,
  });

  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    fetchSkills();
  }, [filters.page]);

  const fetchSkills = async () => {
    try {
      const res = await getSkills(filters);
      setSkills(res.data.content);
      setTotalPages(res.data.totalPages);
    } catch (err) {
      console.error(err);
    }
  };

  const search = () => {
    setFilters({ ...filters, page: 0 });
    fetchSkills();
  };

  const handleRequest = async (skillId) => {
    if (!user) {
      alert("Please login to request skill");
      return;
    }

    try {
      await requestSkill(skillId);
      alert("Request sent successfully!");
    } catch (err) {
      alert(
        err.response?.data?.message ||
          "Error requesting skill"
      );
    }
  };

  return (
    <>
      <Navbar />

      <div className="p-10 space-y-8 min-h-screen bg-slate-900">

        <h2 className="text-4xl font-bold">Browse Skills</h2>

        {/* Filters */}
        <div className="flex gap-4 flex-wrap">
          <Input
            placeholder="Search..."
            onChange={(e) =>
              setFilters({ ...filters, keyword: e.target.value })
            }
          />

          <Input
            placeholder="Category"
            onChange={(e) =>
              setFilters({ ...filters, category: e.target.value })
            }
          />

          <select
            className="bg-slate-800 p-2 rounded-xl border border-slate-700"
            onChange={(e) =>
              setFilters({ ...filters, level: e.target.value })
            }
          >
            <option value="">All Levels</option>
            <option value="BEGINNER">Beginner</option>
            <option value="INTERMEDIATE">Intermediate</option>
            <option value="ADVANCED">Advanced</option>
          </select>

          <Button onClick={search}>Search</Button>
        </div>

        {/* Skills Grid */}
        <div className="grid gap-6
  sm:grid-cols-2
  lg:grid-cols-3
  xl:grid-cols-3">
          {skills.length === 0 && (
            <p className="text-slate-400">No skills found.</p>
          )}

          {skills.map((s) => (
            <Card key={s.id}>
              <h3 className="text-xl font-bold mb-2">{s.title}</h3>
              <p className="text-slate-400 mb-1">
                {s.category}
              </p>
              <p className="mb-3">{s.level}</p>

              <Button
                className="w-full"
                onClick={() => handleRequest(s.id)}
              >
                Request Skill
              </Button>
            </Card>
          ))}
        </div>

        {/* Pagination */}
        <div className="flex gap-2">
          {Array.from({ length: totalPages }).map(
            (_, i) => (
              <Button
                key={i}
                onClick={() =>
                  setFilters({ ...filters, page: i })
                }
                className={
                  filters.page === i
                    ? "bg-accent"
                    : ""
                }
              >
                {i + 1}
              </Button>
            )
          )}
        </div>

      </div>
    </>
  );
};

export default BrowseSkills;