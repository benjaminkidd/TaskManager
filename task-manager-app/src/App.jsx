import { useEffect, useState } from 'react';
import { fetchTasks, createTask, updateTask, deleteTask } from './api';
import './App.css';

function App() {
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newTask, setNewTask] = useState({ title: '', description: '', status: 'TODO' });
  const [editTask, setEditTask] = useState(null);

  useEffect(() => {
    fetchTasks()
      .then(data => setTasks(data))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  const handleCreate = async (e) => {
    e.preventDefault();
    try {
      const created = await createTask(newTask);
      setTasks([...tasks, created]);
      setNewTask({ title: '', description: '', status: 'TODO' });
    } catch (err) {
      setError(err.message);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteTask(id);
      setTasks(tasks.filter(t => t.id !== id));
    } catch (err) {
      setError(err.message);
    }
  };

  const handleEdit = (task) => {
    setEditTask(task);
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      const updated = await updateTask(editTask.id, editTask);
      setTasks(tasks.map(t => (t.id === updated.id ? updated : t)));
      setEditTask(null);
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <p>Loading tasks...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <>
      <div>
        <h1>Task Manager</h1>
        <form onSubmit={editTask ? handleUpdate : handleCreate} style={{ marginBottom: 20 }}>
          <input
            type="text"
            placeholder="Title"
            value={editTask ? editTask.title : newTask.title}
            onChange={e => editTask ? setEditTask({ ...editTask, title: e.target.value }) : setNewTask({ ...newTask, title: e.target.value })}
            required
          />
          <input
            type="text"
            placeholder="Description"
            value={editTask ? editTask.description : newTask.description}
            onChange={e => editTask ? setEditTask({ ...editTask, description: e.target.value }) : setNewTask({ ...newTask, description: e.target.value })}
          />
          <select
            value={editTask ? editTask.status : newTask.status}
            onChange={e => editTask ? setEditTask({ ...editTask, status: e.target.value }) : setNewTask({ ...newTask, status: e.target.value })}
          >
            <option value="TODO">TODO</option>
            <option value="IN_PROGRESS">In Progress</option>
            <option value="DONE">Done</option>
          </select>
          <button type="submit">{editTask ? 'Update' : 'Create'} Task</button>
          {editTask && <button type="button" onClick={() => setEditTask(null)}>Cancel</button>}
        </form>
        <ul>
          {tasks.map(task => (
            <li key={task.id}>
              <strong>{task.title}</strong> - {task.description} [{task.status}]
              <button onClick={() => handleEdit(task)} style={{ marginLeft: 8 }}>Edit</button>
              <button onClick={() => handleDelete(task.id)} style={{ marginLeft: 4 }}>Delete</button>
            </li>
          ))}
        </ul>
      </div>
    </>
  );
}

export default App;
