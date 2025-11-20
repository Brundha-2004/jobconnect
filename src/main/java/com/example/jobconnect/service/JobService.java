package com.example.jobconnect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.jobconnect.model.Job;
import com.example.jobconnect.repository.JobRepository;

@Service
public class JobService {
    private final JobRepository repo;
    
    public JobService(JobRepository repo) {
        this.repo = repo;
    }

    public Job save(@NonNull Job job) {
        return repo.save(job);
    }

    public List<Job> findAll() {
        List<Job> jobs = repo.findAll();
        return jobs != null ? jobs : List.of();
    }

    public Optional<Job> findById(@NonNull Long id) {
        return repo.findById(id);
    }

    public List<Job> searchByLocation(String location) {
        if (location == null) {
            return List.of();
        }
        List<Job> jobs = repo.findByLocationContainingIgnoreCase(location);
        return jobs != null ? jobs : List.of();
    }
}