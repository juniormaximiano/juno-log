package com.mxi.juno_log.controller;


import com.mxi.juno_log.domain.task.Task;
import com.mxi.juno_log.dto.TaskCreateDTO;
import com.mxi.juno_log.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(@RequestBody @Valid TaskCreateDTO taskCreateDTO) {
        return taskService.createTask(taskCreateDTO);
    }

    @GetMapping
    public List<Task> findAllTasks() {
        return taskService.findAllTasks();
    }

    @GetMapping("/{id}")
    public Task findTaskById(@PathVariable long id) {
        return taskService.findTaskById(id);
    }

    @PatchMapping("/{id}/done")
    public Task completeTask(@PathVariable long id) {
        return taskService.completeTask(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
    }
}
