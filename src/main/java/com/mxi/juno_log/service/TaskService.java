package com.mxi.juno_log.service;

import com.mxi.juno_log.domain.task.Task;
import com.mxi.juno_log.domain.task.TaskStatus;
import com.mxi.juno_log.dto.TaskCreateDTO;
import com.mxi.juno_log.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private Task findTaskOrThrow(long id){
        return taskRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(TaskCreateDTO dto) {
        Task task = new Task();
        task.setTaskName(dto.title());
        task.setDescription(dto.description());
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        var taskCreated = taskRepository.save(task);
        return taskCreated;
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) {

        return findTaskOrThrow(id);

    }

    public Task completeTask(Long id) {
        var taskSought = findTaskOrThrow(id);

        if(taskSought.getStatus() == TaskStatus.DONE){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Task already complete");
        }
            taskSought.setFinishedAt(LocalDateTime.now());
            taskSought.setStatus(TaskStatus.DONE);
            taskRepository.save(taskSought);

        return taskSought;


    }

    public void deleteTask(Long id) {

        var taskSought = findTaskOrThrow(id);

        taskRepository.deleteById(taskSought.getId());

    }
}

