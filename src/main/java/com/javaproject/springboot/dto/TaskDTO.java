package com.javaproject.springboot.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public class TaskDTO {

    private Integer id;

    private String author;

    @NotBlank(message = "Topic is mandatory")
    private String topic;

    private LocalDateTime creation_date;

    @NotNull(message = "Deadline date is mandatory")
    private LocalDateTime deadline_date;

    @NotBlank(message = "Description is mandatory")
    private String description;

    public Integer getId() {
        return id;
    }

    public String getAuthor() {
        return author;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    @AssertTrue(message = "The execution time should be at least 14 days")
    private boolean isDeadlineValid() {
        if (creation_date != null && deadline_date != null) {
            long daysDifference = Duration.between(creation_date, deadline_date).toDays();
            return daysDifference >= 14;
        }
        return false;
    }
}