package com.todoapi.integration;

import com.todoapi.model.Task;
import com.todoapi.repository.TaskRepository;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    private String baseUrl;
    private RestTemplate patchRestTemplate;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/tasks";
        taskRepository.clear();

        // RestTemplate that supports PATCH
        HttpClient httpClient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        patchRestTemplate = new RestTemplate(factory);
    }

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
    }

    @Test
    void testGetAllTasks_Integration() {
        restTemplate.postForEntity(baseUrl, new Task("Task 1", "Desc 1"), Task.class);
        restTemplate.postForEntity(baseUrl, new Task("Task 2", "Desc 2"), Task.class);
        restTemplate.postForEntity(baseUrl, new Task("Task 3", "Desc 3"), Task.class);

        ResponseEntity<Task[]> response = restTemplate.getForEntity(baseUrl, Task[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().length);
    }

    @Test
    void testGetTaskById_Success_Integration() {
        ResponseEntity<Task> createResponse = restTemplate.postForEntity(
                baseUrl, new Task("Find Me", "Test"), Task.class);
        String taskId = createResponse.getBody().getId();

        ResponseEntity<Task> response = restTemplate.getForEntity(baseUrl + "/" + taskId, Task.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskId, response.getBody().getId());
        assertEquals("Find Me", response.getBody().getTitle());
    }

    @Test
    void testGetTaskById_NotFound_Integration() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                baseUrl + "/non-existent-id", Map.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Task not found", response.getBody().get("error"));
    }

    @Test
    void testMarkTaskComplete_Integration() {
        ResponseEntity<Task> createResponse = restTemplate.postForEntity(
                baseUrl, new Task("Complete Me", "Test"), Task.class);
        String taskId = createResponse.getBody().getId();

        ResponseEntity<Task> response = patchRestTemplate.exchange(
                baseUrl + "/" + taskId + "/complete",
                HttpMethod.PATCH,
                new HttpEntity<>(new HttpHeaders()),
                Task.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("completed", response.getBody().getStatus());
    }

    @Test
    void testUpdateTask_Integration() {
        ResponseEntity<Task> createResponse = restTemplate.postForEntity(
                baseUrl, new Task("Original", "Original desc"), Task.class);
        String taskId = createResponse.getBody().getId();

        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Title");
        updatedTask.setDescription("Updated desc");

        ResponseEntity<Task> response = restTemplate.exchange(
                baseUrl + "/" + taskId,
                HttpMethod.PUT,
                new HttpEntity<>(updatedTask),
                Task.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Title", response.getBody().getTitle());
    }

    @Test
    void testDeleteTask_Integration() {
        ResponseEntity<Task> createResponse = restTemplate.postForEntity(
                baseUrl, new Task("Delete Me", "Test"), Task.class);
        String taskId = createResponse.getBody().getId();

        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/" + taskId, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(taskRepository.findById(taskId).isPresent());
    }

    @Test
    void testDeleteTask_NotFound_Integration() {
        ResponseEntity<Map> response = restTemplate.exchange(
                baseUrl + "/non-existent-id", HttpMethod.DELETE, null, Map.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testHealthCheck_Integration() {
        ResponseEntity<Map> response = restTemplate.getForEntity(baseUrl + "/health", Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("healthy", response.getBody().get("status"));
    }
}