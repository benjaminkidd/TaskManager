package com.example.taskmanager;

import com.example.taskmanager.controller.TaskController;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTask_returnsCreated() throws Exception {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("desc");
        task.setStatus(TaskStatus.TODO);

        Task saved = new Task();
        saved.setId(UUID.randomUUID());
        saved.setTitle("Test Task");
        saved.setDescription("desc");
        saved.setStatus(TaskStatus.TODO);

        Mockito.when(repository.save(any(Task.class))).thenReturn(saved);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void getAllTasks_returnsList() throws Exception {
        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setTitle("Task 1");
        task.setStatus(TaskStatus.TODO);

        Mockito.when(repository.findAll()).thenReturn(List.of(task));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Task 1"));
    }

    @Test
    void updateTask_whenExists_returnsOk() throws Exception {
        UUID id = UUID.randomUUID();
        Task existing = new Task();
        existing.setId(id);
        existing.setTitle("Old");
        existing.setStatus(TaskStatus.TODO);

        Task update = new Task();
        update.setTitle("New");
        update.setDescription("desc");
        update.setStatus(TaskStatus.DONE);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(existing));
        Mockito.when(repository.save(any(Task.class))).thenReturn(existing);

        mockMvc.perform(put("/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New"));
    }

    @Test
    void updateTask_whenNotExists_returnsNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Task update = new Task();
        update.setTitle("New");

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(put("/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTask_whenExists_returnsNoContent() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(repository.existsById(id)).thenReturn(true);

        mockMvc.perform(delete("/tasks/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTask_whenNotExists_returnsNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(repository.existsById(id)).thenReturn(false);

        mockMvc.perform(delete("/tasks/" + id))
                .andExpect(status().isNotFound());
    }
}