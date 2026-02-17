package com.todoapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapi.model.Task;
import com.todoapi.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task();
        sampleTask.setId("123");
        sampleTask.setTitle("Buy groceries");
        sampleTask.setDescription("Milk, eggs, bread");
        sampleTask.setStatus("pending");
        sampleTask.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/tasks/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("healthy"));
    }

    @Test
    void testCreateTask_Success() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(sampleTask);
        Task newTask = new Task();
        newTask.setTitle("Buy groceries");
        newTask.setDescription("Milk, eggs, bread");
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.title").value("Buy groceries"))
                .andExpect(jsonPath("$.status").value("pending"));
    }

    @Test
    void testCreateTask_MissingTitle() throws Exception {
        Task invalidTask = new Task();
        invalidTask.setDescription("No title");
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTask)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllTasks_WithTasks() throws Exception {
        Task task2 = new Task();
        task2.setId("456");
        task2.setTitle("Finish project");
        task2.setStatus("pending");
        task2.setCreatedAt(LocalDateTime.now());
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(sampleTask, task2));
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetAllTasks_Empty() throws Exception {
        when(taskService.getAllTasks()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetTaskById_Success() throws Exception {
        when(taskService.getTaskById("123")).thenReturn(Optional.of(sampleTask));
        mockMvc.perform(get("/tasks/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.title").value("Buy groceries"));
    }

    @Test
    void testGetTaskById_NotFound() throws Exception {
        when(taskService.getTaskById("999")).thenReturn(Optional.empty());
        mockMvc.perform(get("/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Task not found"));
    }

    @Test
    void testMarkTaskComplete_Success() throws Exception {
        Task completed = new Task();
        completed.setId("123");
        completed.setTitle("Buy groceries");
        completed.setStatus("completed");
        completed.setCreatedAt(LocalDateTime.now());
        when(taskService.markTaskComplete("123")).thenReturn(Optional.of(completed));
        mockMvc.perform(patch("/tasks/123/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("completed"));
    }

    @Test
    void testMarkTaskComplete_NotFound() throws Exception {
        when(taskService.markTaskComplete("999")).thenReturn(Optional.empty());
        mockMvc.perform(patch("/tasks/999/complete"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Task not found"));
    }

    @Test
    void testUpdateTask_Success() throws Exception {
        Task updated = new Task();
        updated.setId("123");
        updated.setTitle("Updated Title");
        updated.setDescription("Updated Description");
        updated.setStatus("pending");
        updated.setCreatedAt(LocalDateTime.now());
        when(taskService.updateTask(eq("123"), any(Task.class))).thenReturn(Optional.of(updated));
        Task updateRequest = new Task();
        updateRequest.setTitle("Updated Title");
        updateRequest.setDescription("Updated Description");
        mockMvc.perform(put("/tasks/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void testUpdateTask_NotFound() throws Exception {
        when(taskService.updateTask(eq("999"), any(Task.class))).thenReturn(Optional.empty());
        Task updateRequest = new Task();
        updateRequest.setTitle("Updated Title");
        mockMvc.perform(put("/tasks/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteTask_Success() throws Exception {
        when(taskService.deleteTask("123")).thenReturn(true);
        mockMvc.perform(delete("/tasks/123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteTask_NotFound() throws Exception {
        when(taskService.deleteTask("999")).thenReturn(false);
        mockMvc.perform(delete("/tasks/999"))
                .andExpect(status().isNotFound());
    }
}