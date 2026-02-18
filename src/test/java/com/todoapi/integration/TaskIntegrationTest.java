package com.todoapi.integration;

import com.todoapi.model.Task;
import com.todoapi.repository.TaskRepository;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskIntegrationTest {

    @TestConfiguration
    static class HttpClientConfig {
        @Bean
        RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder()
                    .requestFactory(() ->
                            new HttpComponentsClientHttpRequestFactory(
                                    HttpClients.createDefault()
                            )
                    );
        }
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/tasks";
        taskRepository.clear();
    }

    /** Create task and verify it was saved. */
    @Test
    void testCreateTask_Integration() {
        Task newTask = new Task();
        newTask.setTitle("Integration Test Task");
        newTask.setDescription("Testing full stack");

        ResponseEntity<Task> response = restTemplate.postForEntity(baseUrl, newTask, Task.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Integration Test Task", response.getBody().getTitle());
        assertNotNull(response.getBody().getId());
        assertEquals("pending", response.getBody().getStatus());
        assertTrue(taskRepository.findById(response.getBody().getId()).isPresent());
    }

    /** Create multiple tasks and retrieve all. */
    @Test
    void testGetAllTasks_Integration() {
        restTemplate.postForEntity(baseUrl, new Task("Task 1", "Description 1"), Task.class);
        restTemplate.postForEntity(baseUrl, new Task("Task 2", "Description 2"), Task.class);
        restTemplate.postForEntity(baseUrl, new Task("Task 3", "Description 3"), Task.class);

        ResponseEntity<Task[]> response = restTemplate.getForEntity(baseUrl, Task[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().length);
    }

    /** Get task by ID — success. */
    @Test
    void testGetTaskById_Success_Integration() {
        Task newTask = new Task("Find Me", "Test description");
        ResponseEntity<Task> createResponse = restTemplate.postForEntity(baseUrl, newTask, Task.class);
        String taskId = createResponse.getBody().getId();

        ResponseEntity<Task> response = restTemplate.getForEntity(baseUrl + "/" + taskId, Task.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(taskId, response.getBody().getId());
        assertEquals("Find Me", response.getBody().getTitle());
    }

    /** Get task by ID — not found. */
    @Test
    void testGetTaskById_NotFound_Integration() {
        ResponseEntity<Map> response = restTemplate.getForEntity(baseUrl + "/non-existent-id", Map.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Task not found", response.getBody().get("error"));
    }

    /** Mark task as complete via PATCH (US-005). */
    @Test
    void testMarkTaskComplete_Integration() {
        ResponseEntity<Task> createResponse = restTemplate.postForEntity(
                baseUrl, new Task("Complete Me", "Test task"), Task.class);
        String taskId = createResponse.getBody().getId();

        ResponseEntity<Task> response = restTemplate.exchange(
                baseUrl + "/" + taskId + "/complete",
                HttpMethod.PATCH,
                null,
                Task.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("completed", response.getBody().getStatus());

        Task updatedTask = taskRepository.findById(taskId).orElseThrow();
        assertEquals("completed", updatedTask.getStatus());
    }

    /** Update task via PUT (US-003). */
    @Test
    void testUpdateTask_Integration() {
        ResponseEntity<Task> createResponse = restTemplate.postForEntity(
                baseUrl, new Task("Original Title", "Original description"), Task.class);
        String taskId = createResponse.getBody().getId();

        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Title");
        updatedTask.setDescription("Updated description");

        ResponseEntity<Task> response = restTemplate.exchange(
                baseUrl + "/" + taskId,
                HttpMethod.PUT,
                new HttpEntity<>(updatedTask),
                Task.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Title", response.getBody().getTitle());
        assertEquals("Updated description", response.getBody().getDescription());

        Task persistedTask = taskRepository.findById(taskId).orElseThrow();
        assertEquals("Updated Title", persistedTask.getTitle());
    }

    /** Delete task (US-004). */
    @Test
    void testDeleteTask_Integration() {
        ResponseEntity<Task> createResponse = restTemplate.postForEntity(
                baseUrl, new Task("Delete Me", "Test task"), Task.class);
        String taskId = createResponse.getBody().getId();

        assertTrue(taskRepository.findById(taskId).isPresent());

        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/" + taskId,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(taskRepository.findById(taskId).isPresent());
    }

    /** Delete non-existent task. */
    @Test
    void testDeleteTask_NotFound_Integration() {
        ResponseEntity<Map> response = restTemplate.exchange(
                baseUrl + "/non-existent-id",
                HttpMethod.DELETE,
                null,
                Map.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /** Health check endpoint. */
    @Test
    void testHealthCheck_Integration() {
        ResponseEntity<Map> response = restTemplate.getForEntity(baseUrl + "/health", Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("healthy", response.getBody().get("status"));
    }
}