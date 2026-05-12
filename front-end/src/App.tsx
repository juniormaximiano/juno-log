import { useEffect, useState } from "react";
import "./App.css";
import type { Task } from "./types/Task";
import { api } from "./services/api";

function App() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  async function loadTasks() {
    const response = await api.get("/tasks");
    setTasks(response.data);
  }

  async function createTask() {
    if (title == "" || description == "") {
      alert("Preencha todos os campos !");
    } else {
       await api.post("/tasks", { title, description });
      setDescription("");
      setTitle("");
      loadTasks();
    }
  }

  useEffect(() => {
    loadTasks();
  }, []);

  return (
    <>
      <h1>Juno Log</h1>

      <input
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        type="text"
        id="inputTitle"
      />
      <input
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        type="text"
        id="inputDescription"
      />
      <button onClick={createTask} type="submit" id="btnSubmit">
        Submit
      </button>

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
