package com.todoapi.repository;

import com.todoapi.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(String id);
    List<Task> findAll();
    boolean deleteById(String id);
    void clear();
}