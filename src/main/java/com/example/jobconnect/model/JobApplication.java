package com.example.jobconnect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;
    private Long seekerId;

    @Column(length = 2000)
    private String coverLetter;

    private String status; // e.g., "submitted", "Review"

    // Constructors
    public JobApplication() {
    }

    public JobApplication(Long jobId, Long seekerId, String coverLetter, String status) {
        this.jobId = jobId;
        this.seekerId = seekerId;
        this.coverLetter = coverLetter;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(Long seekerId) {
        this.seekerId = seekerId;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "JobApplication{" +
                "id=" + id +
                ", jobId=" + jobId +
                ", seekerId=" + seekerId +
                ", coverLetter='" + coverLetter + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}