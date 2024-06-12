package com.javaproject.springboot.controllers;

import com.javaproject.springboot.dto.TaskDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.javaproject.springboot.repositories.TaskRepository;
import com.javaproject.springboot.services.TaskService;

@RestController
@RequestMapping(path = "/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService, TaskRepository taskRepository){
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping(value = "/{taskId}")
    public TaskDTO getTaskById(@PathVariable("taskId") Integer id){
        return taskService.getTaskById(id);
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody @Valid TaskDTO taskDTO){
        return taskService.addNewTask(taskDTO);
    }

    @PutMapping(value = "/{taskId}")
    public TaskDTO updateTaskInfo(@PathVariable("taskId") Integer id, @RequestBody @Valid TaskDTO taskDTO) {
        return taskService.updateTaskInfo(id, taskDTO);
    }

    @DeleteMapping(value = "/{taskId}")
    public void deleteTask(@PathVariable("taskId") Integer id) {
        taskService.deleteTask(id);
    }
}
