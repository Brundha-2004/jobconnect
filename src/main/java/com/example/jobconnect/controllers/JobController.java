package com.example.jobconnect.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jobconnect.model.Job;
import com.example.jobconnect.service.JobService;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;
    
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping({"", "/"})
    public String listJobs(@RequestParam(required = false) String location, Model model) {
        List<Job> jobs;
        if (location != null && !location.isBlank()) {
            jobs = jobService.searchByLocation(location);
        } else {
            jobs = jobService.findAll();
        }
        model.addAttribute("jobs", jobs);
        model.addAttribute("location", location == null ? "" : location);
        return "jobs";
    }

    @GetMapping("/post")
    public String postJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "postJob";
    }

    @PostMapping("/post")
    public String createJob(@ModelAttribute Job job, Authentication auth) {
        if (job.getDeadline() == null) {
            job.setDeadline(LocalDate.now().plusWeeks(2));
        }
        jobService.save(job);
        return "redirect:/jobs";
    }

    @GetMapping("/{id}")
    public String jobDetails(@PathVariable Long id, Model model) {
        jobService.findById(id).ifPresent(job -> model.addAttribute("job", job));
        return "jobDetails";
    }
}