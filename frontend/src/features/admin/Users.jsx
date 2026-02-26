import { useEffect, useState } from "react";
import { getUsers } from "./adminApi";

const Users = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetch();
  }, []);

  const fetch = async () => {
    const res = await getUsers();
    setUsers(res.data);
  };

  return (
    <div className="p-10">
      <h2 className="text-3xl mb-6">Users</h2>
      {users.map((u) => (
        <div key={u.id} className="mb-4">
          {u.name} - {u.role}
        </div>
      ))}
    </div>
  );
};

export default Users;