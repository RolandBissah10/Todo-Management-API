package com.todoapi.service;

import com.todoapi.model.Task;
import com.todoapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task("Buy groceries", "Milk, eggs, bread");
        sampleTask.setId("123");
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);
        Task result = taskService.createTask(sampleTask);
        assertNotNull(result);
        assertEquals("123", result.getId());
        assertEquals("Buy groceries", result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testGetAllTasks() {
        Task task2 = new Task("Finish project", "Complete Sprint 1");
        task2.setId("456");
        when(taskRepository.findAll()).thenReturn(Arrays.asList(sampleTask, task2));
        List<Task> results = taskService.getAllTasks();
        assertEquals(2, results.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById_Found() {
        when(taskRepository.findById("123")).thenReturn(Optional.of(sampleTask));
        Optional<Task> result = taskService.getTaskById("123");
        assertTrue(result.isPresent());
        assertEquals("123", result.get().getId());
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById("999")).thenReturn(Optional.empty());
        Optional<Task> result = taskService.getTaskById("999");
        assertFalse(result.isPresent());
    }

    @Test
    void testMarkTaskComplete_Success() {
        when(taskRepository.findById("123")).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);
        Optional<Task> result = taskService.markTaskComplete("123");
        assertTrue(result.isPresent());
        assertEquals("completed", result.get().getStatus());
    }

    @Test
    void testMarkTaskComplete_NotFound() {
        when(taskRepository.findById("999")).thenReturn(Optional.empty());
        Optional<Task> result = taskService.markTaskComplete("999");
        assertFalse(result.isPresent());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testUpdateTask_Success() {
        Task updatedData = new Task("Updated Title", "Updated Description");
        when(taskRepository.findById("123")).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);
        Optional<Task> result = taskService.updateTask("123", updatedData);
        assertTrue(result.isPresent());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testDeleteTask_Success() {
        when(taskRepository.deleteById("123")).thenReturn(true);
        assertTrue(taskService.deleteTask("123"));
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.deleteById("999")).thenReturn(false);
        assertFalse(taskService.deleteTask("999"));
    }
}