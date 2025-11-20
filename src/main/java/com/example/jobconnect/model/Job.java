package com.example.jobconnect.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    @Column(length = 4000)
    private String description;

    private LocalDate deadline;
    private String location;
    private Double salary;
    private long employerId;

    // Constructors
    public Job() {}

    public Job(String title, String description, LocalDate deadline, String location, Double salary, long employerId) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.location = location;
        this.salary = salary;
        this.employerId = employerId;
    }

    // Getters and Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }

    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }

    public LocalDate getDeadline() { 
        return deadline; 
    }
    
    public void setDeadline(LocalDate deadline) { 
        this.deadline = deadline; 
    }

    public String getLocation() { 
        return location; 
    }
    
    public void setLocation(String location) { 
        this.location = location; 
    }

    public Double getSalary() { 
        return salary; 
    }
    
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public long getEmployerId() {
        return employerId;
    }
    
    public void setEmployerId(long employerId) {
        this.employerId = employerId;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", location='" + location + '\'' +
                ", salary=" + salary +
                ", employerId=" + employerId +
                '}';
    }
}