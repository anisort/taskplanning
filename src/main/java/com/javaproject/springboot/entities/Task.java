package com.javaproject.springboot.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private LocalDateTime creation_date;

    @Column(nullable = false)
    private LocalDateTime deadline_date;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Task() {
        this.creation_date = LocalDateTime.now();
    }

    public Task(String topic, LocalDateTime deadline_date, String description, User user) {
        this.topic = topic;
        this.creation_date = LocalDateTime.now();
        this.deadline_date = deadline_date;
        this.description = description;
        this.user = user;
    }


    public Integer getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public LocalDateTime getDeadline_date() {
        return deadline_date;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public void setDeadline_date(LocalDateTime deadline_date) {
        this.deadline_date = deadline_date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", creation_date=" + creation_date +
                ", deadline_date=" + deadline_date +
                ", description='" + description + '\'' +
                '}';
    }
}