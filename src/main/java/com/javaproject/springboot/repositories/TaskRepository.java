package com.javaproject.springboot.repositories;

import com.javaproject.springboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.javaproject.springboot.entities.Task;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByUserId(Integer userId);
    Optional<Task> findByIdAndUser(Integer taskId, User user);
}

