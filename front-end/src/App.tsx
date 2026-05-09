import { useEffect, useState } from "react";
import "./App.css";
import type { Task } from "./types/Task";
import { api } from "./services/api";

function App() {

  const [tasks, setTasks] = useState<Task[]>([]);

  async function loadTasks() {
    const response = await api.get("/tasks");
    setTasks(response.data);
  }

  useEffect(() => {
    loadTasks();
  }, []);

    return (
    <>
      <h1>Juno Log</h1>

      {tasks.map((task) => (
        <div key={task.id}>
          <h2>{task.taskName}</h2>
          <p>{task.description}</p>
          <span>{task.status}</span>
        </div>
      ))}
    </>
  );
}

export default App;
