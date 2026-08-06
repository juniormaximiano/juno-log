package com.mxi.juno_log.service;

import com.mxi.juno_log.domain.task.Task;
import com.mxi.juno_log.domain.task.TaskStatus;
import com.mxi.juno_log.dto.TaskCreateDTO;
import com.mxi.juno_log.dto.TaskResponseDTO;
import com.mxi.juno_log.dto.TaskSummaryDTO;
import com.mxi.juno_log.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private Task findTaskOrThrow(long id) {
        return taskRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponseDTO createTask(TaskCreateDTO dto) {
        Task task = new Task();
        task.setTaskName(dto.title());
        task.setDescription(dto.description());
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        var taskCreated = taskRepository.save(task);
        return convertTaskToDTO(taskCreated);
    }

    public TaskResponseDTO findTaskById(Long id) {

        var taskSought = findTaskOrThrow(id);
        return convertTaskToDTO(taskSought);

    }

    public TaskResponseDTO completeTask(Long id) {
        var taskSought = findTaskOrThrow(id);

        if (taskSought.getStatus() == TaskStatus.DONE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Task already complete");
        }
        taskSought.setFinishedAt(LocalDateTime.now());
        taskSought.setStatus(TaskStatus.DONE);
        taskRepository.save(taskSought);

        return convertTaskToDTO(taskSought);

    }

    public List<TaskResponseDTO> filterByStatus(TaskStatus status) {

     if (status == null) {
         var  tasks = taskRepository.findAll();
         List<TaskResponseDTO> dtos = new ArrayList<>();
         for (Task task : tasks) {
             dtos.add(convertTaskToDTO(task));
         }
         return dtos;
     }

     return taskRepository.findAllByStatus(status);


    }

    public List<TaskResponseDTO> findAllByStatusOrderByCreatedDesc() {
        return taskRepository.findAllByOrderByCreatedAtDesc();
    }

    public void deleteTask(Long id) {

        var taskSought = findTaskOrThrow(id);

        taskRepository.deleteById(taskSought.getId());

    }

    public TaskSummaryDTO countAllTasks() {
        var pending = taskRepository.countByStatus(TaskStatus.PENDING);
        var completed = taskRepository.countByStatus(TaskStatus.DONE);
        var total = taskRepository.count();

        return new TaskSummaryDTO(total, pending, completed);

    }

    public TaskResponseDTO convertTaskToDTO(Task task) {

        return new TaskResponseDTO(
                task.getId(),
                task.getTaskName(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getFinishedAt()

        );

    }
}

