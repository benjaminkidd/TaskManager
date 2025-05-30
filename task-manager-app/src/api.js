// Centralized Axios instance and example API call
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // Change to your API base URL
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Example: Fetch tasks
export const fetchTasks = async () => {
  const response = await api.get('/tasks');
  return response.data;
};

// Create a new task
export const createTask = async (task) => {
  const response = await api.post('/tasks', task);
  return response.data;
};

// Update a task
export const updateTask = async (id, task) => {
  const response = await api.put(`/tasks/${id}`, task);
  return response.data;
};

// Delete a task
export const deleteTask = async (id) => {
  await api.delete(`/tasks/${id}`);
};

// Example: Add more API methods as needed
export default api;
