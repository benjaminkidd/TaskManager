package com.example.taskmanager.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private List<String> tasks = new ArrayList<>();

    public List<String> getAllTasks() {
        return tasks;
    }

    public String addTask(String task) {
        tasks.add(task);
        return "Task added successfully!";
    }

    public String deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            return "Task deleted successfully!";
        } else {
            return "Invalid task index!";
        }
    }
}