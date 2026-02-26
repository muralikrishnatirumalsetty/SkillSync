import { useEffect, useState } from "react";
import {
  getMySkills,
  createSkill,
  deleteSkill,
} from "./skillsApi";
import Card from "../../components/ui/Card";
import Button from "../../components/ui/Button";
import Input from "../../components/ui/Input";

const MySkills = () => {
  const [skills, setSkills] = useState([]);
  const [form, setForm] = useState({
    title: "",
    description: "",
    category: "",
    level: "BEGINNER",
  });

  useEffect(() => {
    fetchSkills();
  }, []);

  const fetchSkills = async () => {
    const res = await getMySkills();
    const list = res.data.data || res.data.content || res.data;
    setSkills(list);
  };

  const add = async () => {
    await createSkill(form);
    setForm({
      title: "",
      description: "",
      category: "",
      level: "BEGINNER",
    });
    fetchSkills();
  };

  const remove = async (id) => {
    await deleteSkill(id);
    fetchSkills();
  };

  return (
    <div>
      <h1 className="text-3xl font-semibold tracking-tight mb-8 text-white">
        My Skills
</h1>

      <div className="grid gap-4 mb-10">
        <Input
          placeholder="Title"
          value={form.title}
          onChange={(e) =>
            setForm({ ...form, title: e.target.value })
          }
        />
        <Input
          placeholder="Description"
          value={form.description}
          onChange={(e) =>
            setForm({ ...form, description: e.target.value })
          }
        />
        <Input
          placeholder="Category"
          value={form.category}
          onChange={(e) =>
            setForm({ ...form, category: e.target.value })
          }
        />
        <Button onClick={add}>Add Skill</Button>
      </div>

      <div className="grid md:grid-cols-3 gap-6">
        {skills.map((s) => (
          <Card key={s.id}>
            <h3 className="text-xl font-semibold">{s.title}</h3>
            <p className="text-gray-400 text-sm mt-2">{s.description}</p>
            <Button
              className="bg-red-500 mt-6"
              onClick={() => remove(s.id)}
            >
              Delete
            </Button>
          </Card>
        ))}
      </div>
    </div>
  );
};

export default MySkills;