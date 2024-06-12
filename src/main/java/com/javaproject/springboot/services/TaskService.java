package com.javaproject.springboot.services;

import com.javaproject.springboot.dto.TaskDTO;
import com.javaproject.springboot.entities.User;
import com.javaproject.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.javaproject.springboot.entities.Task;
import com.javaproject.springboot.repositories.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;


    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskDTO> getTasks(){
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByName(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Task> tasks;
        if(Objects.equals(currentUser.getRole(), "ADMIN")){
            tasks = taskRepository.findAll();
            return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
        }
        else{
            tasks = taskRepository.findByUserId(currentUser.getId());
        }
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    public TaskDTO getTaskById(Integer id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByName(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Task task;
        if(Objects.equals(currentUser.getRole(), "ADMIN")){
            task = taskRepository.findById(id).orElseThrow(() -> new IllegalStateException("Task with this id doesn't exist"));
            return convertToDTO(task);
        }
        else {
            task = taskRepository.findByIdAndUser(id, currentUser)
                    .orElseThrow(() -> new IllegalStateException("Task with this id doesn't exist or doesn't belong to the current user"));
        }
        return convertToDTO(task);
    }

    public TaskDTO addNewTask(TaskDTO taskDTO) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByName(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Task task = new Task();
        if(Objects.equals(currentUser.getRole(), "ADMIN")){

            User assignedUser = userRepository.findByName(taskDTO.getAuthor())
                    .orElseThrow(() -> new UsernameNotFoundException("User to assign task not found"));
            task.setUser(assignedUser);

        }
        else{
            task.setUser(currentUser);
        }
        task.setTopic(taskDTO.getTopic());
        task.setCreation_date(LocalDateTime.now());
        task.setDeadline_date(taskDTO.getDeadline_date());
        task.setDescription(taskDTO.getDescription());
        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }

    public TaskDTO updateTaskInfo(Integer id, TaskDTO taskDTO) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByName(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Task oldTask;
        Task newTask = new Task();

        if (Objects.equals(currentUser.getRole(), "ADMIN")) {

            oldTask = taskRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("Task with this id doesn't exist"));

            newTask.setCreation_date(oldTask.getCreation_date());
            taskRepository.delete(oldTask);

            User assignedUser = userRepository.findByName(taskDTO.getAuthor())
                    .orElseThrow(() -> new UsernameNotFoundException("User to assign task not found"));

            newTask.setUser(assignedUser);
        }
        else {

            oldTask = taskRepository.findByIdAndUser(id, currentUser)
                    .orElseThrow(() -> new IllegalStateException("Task with this id doesn't exist or doesn't belong to the current user"));

            newTask.setCreation_date(oldTask.getCreation_date());
            taskRepository.delete(oldTask);

            newTask.setUser(currentUser);
        }

        newTask.setTopic(taskDTO.getTopic());
        newTask.setDeadline_date(taskDTO.getDeadline_date());
        newTask.setDescription(taskDTO.getDescription());
        Task updatedTask = taskRepository.save(newTask);
        return convertToDTO(updatedTask);
    }


    public void deleteTask(Integer id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByName(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Task task;
        if(Objects.equals(currentUser.getRole(), "ADMIN")){
            task = taskRepository.findById(id).orElseThrow(() -> new IllegalStateException("Task with this id doesn't exist"));
        }
        else{
            task = taskRepository.findByIdAndUser(id, currentUser)
                    .orElseThrow(() -> new IllegalStateException("Task with this id doesn't exist or doesn't belong to the current user"));
        }

        taskRepository.delete(task);

    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setAuthor(task.getUser().getName());
        taskDTO.setTopic(task.getTopic());
        taskDTO.setCreation_date(task.getCreation_date());
        taskDTO.setDeadline_date(task.getDeadline_date());
        taskDTO.setDescription(task.getDescription());
        return taskDTO;
    }
}
