import { useEffect, useState } from "react";
import "./App.css";
import type { Task } from "./types/Task";
import { api } from "./services/api";
import type { Summary } from "./types/Summary";

function App() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  async function completeTask(id: number) {
    await api.patch(`/tasks/${id}/done`);
    loadTasks();
  }

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

  async function deleteTask(id: number) {
    await api.delete(`tasks/${id}`);
    loadTasks();
  }

  async function viewSummary(type: Summary) {
    await api.get("/summary");
    loadTasks();
  }

  useEffect(() => {
    loadTasks();
  }, []);

  return (
    <main className="app-shell">
      <header className="app-header">
        <div className="brand-mark" aria-hidden="true">
          J
        </div>
        <div>
          <p className="app-kicker">Seu painel de produtividade</p>
          <h1>Juno Log</h1>
          <p className="app-subtitle">
            Organize suas tarefas e acompanhe o que já foi concluído.
          </p>
        </div>
      </header>

      <section className="stats-grid" aria-label="Resumo das tarefas">
        <article className="stat-card stat-card--total">
          <div className="stat-icon" aria-hidden="true">≡</div>
          <div className="stat-content">
            <p className="stat-title">Total de tarefas</p>
            <strong>{tasks.length}</strong>
            <span>tarefas cadastradas</span>
          </div>
        </article>

        <article className="stat-card stat-card--pending">
          <div className="stat-icon" aria-hidden="true">◷</div>
          <div className="stat-content">
            <p className="stat-title">Pendentes</p>
            <strong>
              {tasks.filter((task) => task.status === "PENDING").length}
            </strong>
            <span>aguardando conclusão</span>
          </div>
        </article>

        <article className="stat-card stat-card--done">
          <div className="stat-icon" aria-hidden="true">✓</div>
          <div className="stat-content">
            <p className="stat-title">Concluídas</p>
            <strong>
              {tasks.filter((task) => task.status === "DONE").length}
            </strong>
            <span>tarefas finalizadas</span>
          </div>
        </article>
      </section>

      <section className="task-form" aria-labelledby="new-task-title">
        <div className="section-heading">
          <div>
            <p className="section-label">Nova tarefa</p>
            <h2 id="new-task-title">O que precisa ser feito?</h2>
          </div>
        </div>

        <div className="form-grid">
          <label className="field" htmlFor="inputTitle">
            <span>Título</span>
            <input
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              type="text"
              id="inputTitle"
              placeholder="Ex.: Revisar relatório"
            />
          </label>

          <label className="field field-description" htmlFor="inputDescription">
            <span>Descrição</span>
            <input
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              type="text"
              id="inputDescription"
              placeholder="Adicione os detalhes da tarefa"
            />
          </label>

          <button onClick={createTask} type="button" id="btnSubmit">
            Adicionar tarefa
          </button>
        </div>
      </section>

      <section className="tasks-section" aria-labelledby="tasks-title">
        <div className="section-heading tasks-heading">
          <div>
            <p className="section-label">Visão geral</p>
            <h2 id="tasks-title">Minhas tarefas</h2>
          </div>
          <span className="task-count">
            {tasks.length} {tasks.length === 1 ? "tarefa" : "tarefas"}
          </span>
        </div>

        {tasks.length === 0 ? (
          <div className="empty-state">
            <div className="empty-icon" aria-hidden="true">✓</div>
            <h3>Nenhuma tarefa por aqui</h3>
            <p>Crie uma tarefa acima para começar a organizar seu dia.</p>
          </div>
        ) : (
          <div className="task-list">
            {tasks.map((task) => (
              <article
                className={`task-card task-card--${task.status.toLowerCase()}`}
                key={task.id}
              >
                <div className="task-content">
                  <div className="task-title-row">
                    <h3>{task.taskName}</h3>
                    <span className={`status status--${task.status.toLowerCase()}`}>
                      {task.status === "DONE" ? "Concluída" : "Pendente"}
                    </span>
                  </div>
                  <p>{task.description}</p>
                </div>

                <div className="task-actions">
                  <button
                    className="button button-primary"
                    onClick={() => completeTask(task.id)}
                  >
                    Concluir
                  </button>
                  <button
                    className="button button-danger"
                    onClick={() => deleteTask(task.id)}
                  >
                    Excluir
                  </button>
                </div>
              </article>
            ))}
          </div>
        )}
      </section>

      <div className="summary-resume"></div>
    </main>
  );
}

export default App;
