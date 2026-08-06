package com.mxi.juno_log.controller;

import com.mxi.juno_log.domain.task.TaskStatus;
import com.mxi.juno_log.dto.TaskCreateDTO;
import com.mxi.juno_log.dto.TaskResponseDTO;
import com.mxi.juno_log.dto.TaskSummaryDTO;
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
    public TaskResponseDTO createTask(@RequestBody @Valid TaskCreateDTO taskCreateDTO) {
        return taskService.createTask(taskCreateDTO);
    }

    @GetMapping
    public List<TaskResponseDTO> findAllTasks(
            @RequestParam(required = false) TaskStatus status
    ) {
        return taskService.filterByStatus(status);
    }

    @GetMapping("/{id}")
    public TaskResponseDTO findTaskById(@PathVariable long id) {
        return taskService.findTaskById(id);
    }

    @PatchMapping("/{id}/done")
    public TaskResponseDTO completeTask(@PathVariable long id) {
        return taskService.completeTask(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
    }


    @GetMapping("/tasksByCreated")
    public List<TaskResponseDTO> findAllTasksOrderByCreatedAtDesc() {
        return taskService.findAllByStatusOrderByCreatedDesc();
    }

    @GetMapping("/summary")
    public TaskSummaryDTO findTasksSummary() {
        return taskService.countAllTasks();
    }


}
