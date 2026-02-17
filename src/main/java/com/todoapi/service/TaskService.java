package com.todoapi.service;

import com.todoapi.model.Task;
import com.todoapi.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        logger.info("TaskService initialized");
    }

    public Task createTask(Task task) {
        logger.debug("Creating task: title={}", task.getTitle());
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus("pending");
        Task savedTask = taskRepository.save(task);
        logger.debug("Task saved: id={}", savedTask.getId());
        return savedTask;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public Optional<Task> markTaskComplete(String id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setStatus("completed");
                    return taskRepository.save(task);
                });
    }

    public Optional<Task> updateTask(String id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setTitle(updatedTask.getTitle());
                    existingTask.setDescription(updatedTask.getDescription());
                    if (updatedTask.getStatus() != null) {
                        existingTask.setStatus(updatedTask.getStatus());
                    }
                    return taskRepository.save(existingTask);
                });
    }

    public boolean deleteTask(String id) {
        return taskRepository.deleteById(id);
    }
}