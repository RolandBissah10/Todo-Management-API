package com.todoapi.controller;

import com.todoapi.model.Task;
import com.todoapi.service.TaskService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
        logger.info("TaskController initialized");
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "healthy");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody Task task) {
        logger.info("Creating new task: title={}", task.getTitle());
        try {
            Task createdTask = taskService.createTask(task);
            logger.info("Task created successfully: id={}", createdTask.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            logger.error("Failed to create task: title={}, error={}", task.getTitle(), e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create task: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        logger.debug("Fetching all tasks");
        List<Task> tasks = taskService.getAllTasks();
        logger.info("Retrieved {} tasks", tasks.size());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id) {
        logger.debug("Fetching task by id: {}", id);
        return taskService.getTaskById(id)
                .map(task -> {
                    logger.info("Task found: id={}", id);
                    return ResponseEntity.ok((Object) task);
                })
                .orElseGet(() -> {
                    logger.warn("Task not found: id={}", id);
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Task not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                });
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> markTaskComplete(@PathVariable String id) {
        logger.info("Marking task as complete: id={}", id);
        return taskService.markTaskComplete(id)
                .map(task -> {
                    logger.info("Task marked as complete: id={}", id);
                    return ResponseEntity.ok((Object) task);
                })
                .orElseGet(() -> {
                    logger.warn("Cannot mark task as complete - not found: id={}", id);
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Task not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id, @Valid @RequestBody Task task) {
        logger.info("Updating task: id={}", id);
        return taskService.updateTask(id, task)
                .map(updatedTask -> {
                    logger.info("Task updated successfully: id={}", id);
                    return ResponseEntity.ok((Object) updatedTask);
                })
                .orElseGet(() -> {
                    logger.warn("Cannot update task - not found: id={}", id);
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Task not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        logger.info("Deleting task: id={}", id);
        boolean deleted = taskService.deleteTask(id);
        if (deleted) {
            logger.info("Task deleted successfully: id={}", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Cannot delete task - not found: id={}", id);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Task not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}