package com.javaproject.springboot.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;

public class UserRegistrationDTO {

    @NotBlank(message = "Username is mandatory")
    @Size(min = 10, message = "Username must be at least 10 characters long")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}", message = "Email should be valid")
    private String email;

    @NotNull(message = "Birth date is mandatory")
    @Past(message = "Birth date should be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])(?=.*\\d).{8,}", message = "Password must be at least 8 characters long, contain one letter, one number, and one special character !@#$%^&*")
    private String password;

    @NotBlank(message = "Repeat password is mandatory")
    private String repeatPassword;


    public String getName() { return username; }

    public String getEmail() { return email; }

    public LocalDate getBirthDate() { return birthDate; }

    public String getPassword() { return password; }

    public String getRepeatPassword() { return repeatPassword; }


    public void setUsername(String username) { this.username = username; }

    public void setEmail(String email) { this.email = email; }

    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public void setPassword(String password) { this.password = password; }

    public void setRepeatPassword(String repeatPassword) { this.repeatPassword = repeatPassword; }

    @AssertTrue(message = "Passwords do not match")
    private boolean isPasswordsMatching() {
        return password != null && password.equals(repeatPassword);
    }

    @AssertTrue(message = "Age must be at least 18 years")
    private boolean isAgeValid() {
        return birthDate != null && Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

}
