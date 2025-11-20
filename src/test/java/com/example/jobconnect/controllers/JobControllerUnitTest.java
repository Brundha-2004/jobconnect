package com.example.jobconnect.controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.example.jobconnect.model.Job;
import com.example.jobconnect.service.JobService;

@ExtendWith(MockitoExtension.class)
class JobControllerTest {

    @Mock
    private JobService jobService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JobController jobController;

    private Job testJob;
    private Job testJob2;

    @BeforeEach
    void setUp() {
        testJob = new Job();
        testJob.setId(1L);
        testJob.setTitle("Software Engineer");
        testJob.setDescription("Develop software applications");
        testJob.setLocation("New York");
        testJob.setSalary(80000.0);
        testJob.setDeadline(LocalDate.now().plusWeeks(2));

        testJob2 = new Job();
        testJob2.setId(2L);
        testJob2.setTitle("Data Scientist");
        testJob2.setDescription("Analyze data and build models");
        testJob2.setLocation("San Francisco");
        testJob2.setSalary(95000.0);
        testJob2.setDeadline(LocalDate.now().plusWeeks(3));
    }

    @Test
    void listJobs_WithoutLocation_ShouldReturnAllJobs() {
        // Arrange
        List<Job> expectedJobs = Arrays.asList(testJob, testJob2);
        when(jobService.findAll()).thenReturn(expectedJobs);

        // Act
        String viewName = jobController.listJobs(null, model);

        // Assert
        assertEquals("jobs", viewName);
        verify(jobService, times(1)).findAll();
        verify(jobService, never()).searchByLocation(anyString());
        verify(model).addAttribute("jobs", expectedJobs);
        verify(model).addAttribute("location", "");
    }

    @Test
    void listJobs_WithLocation_ShouldReturnFilteredJobs() {
        // Arrange
        String location = "New York";
        List<Job> expectedJobs = Arrays.asList(testJob);
        when(jobService.searchByLocation(location)).thenReturn(expectedJobs);

        // Act
        String viewName = jobController.listJobs(location, model);

        // Assert
        assertEquals("jobs", viewName);
        verify(jobService, times(1)).searchByLocation(location);
        verify(jobService, never()).findAll();
        verify(model).addAttribute("jobs", expectedJobs);
        verify(model).addAttribute("location", location);
    }

    @Test
    void listJobs_WithEmptyLocation_ShouldReturnAllJobs() {
        // Arrange
        List<Job> expectedJobs = Arrays.asList(testJob, testJob2);
        when(jobService.findAll()).thenReturn(expectedJobs);

        // Act
        String viewName = jobController.listJobs("", model);

        // Assert
        assertEquals("jobs", viewName);
        verify(jobService, times(1)).findAll();
        verify(jobService, never()).searchByLocation(anyString());
        verify(model).addAttribute("jobs", expectedJobs);
        verify(model).addAttribute("location", "");
    }

    @Test
    void listJobs_WithBlankLocation_ShouldReturnAllJobs() {
        // Arrange
        List<Job> expectedJobs = Arrays.asList(testJob, testJob2);
        when(jobService.findAll()).thenReturn(expectedJobs);

        // Act
        String viewName = jobController.listJobs("   ", model);

        // Assert
        assertEquals("jobs", viewName);
        verify(jobService, times(1)).findAll();
        verify(jobService, never()).searchByLocation(anyString());
        verify(model).addAttribute("jobs", expectedJobs);
        verify(model).addAttribute("location", "   ");
    }

    @Test
    void postJobForm_ShouldReturnPostJobView() {
        // Act
        String viewName = jobController.postJobForm(model);

        // Assert
        assertEquals("postJob", viewName);
        verify(model).addAttribute(eq("job"), any(Job.class));
    }

    @Test
    void createJob_WithDeadline_ShouldSaveJobWithProvidedDeadline() {
        // Arrange
        LocalDate customDeadline = LocalDate.now().plusWeeks(4);
        testJob.setDeadline(customDeadline);
        when(jobService.save(any(Job.class))).thenReturn(testJob);

        // Act
        String viewName = jobController.createJob(testJob, authentication);

        // Assert
        assertEquals("redirect:/jobs", viewName);
        verify(jobService, times(1)).save(testJob);
        assertEquals(customDeadline, testJob.getDeadline());
    }

    @Test
    void createJob_WithoutDeadline_ShouldSetDefaultDeadline() {
        // Arrange
        testJob.setDeadline(null);
        when(jobService.save(any(Job.class))).thenReturn(testJob);

        // Act
        String viewName = jobController.createJob(testJob, authentication);

        // Assert
        assertEquals("redirect:/jobs", viewName);
        verify(jobService, times(1)).save(testJob);
        assertNotNull(testJob.getDeadline());
        assertEquals(LocalDate.now().plusWeeks(2), testJob.getDeadline());
    }

    @Test
    void createJob_WithAuthentication_ShouldSaveJob() {
        // Arrange
        when(authentication.getName()).thenReturn("testuser");
        when(jobService.save(any(Job.class))).thenReturn(testJob);

        // Act
        String viewName = jobController.createJob(testJob, authentication);

        // Assert
        assertEquals("redirect:/jobs", viewName);
        verify(jobService, times(1)).save(testJob);
    }

    @Test
    void jobDetails_WhenJobExists_ShouldReturnJobDetailsView() {
        // Arrange
        when(jobService.findById(1L)).thenReturn(Optional.of(testJob));

        // Act
        String viewName = jobController.jobDetails(1L, model);

        // Assert
        assertEquals("jobDetails", viewName);
        verify(jobService, times(1)).findById(1L);
        verify(model).addAttribute("job", testJob);
    }

    @Test
    void jobDetails_WhenJobNotExists_ShouldReturnJobDetailsViewWithoutJob() {
        // Arrange
        when(jobService.findById(99L)).thenReturn(Optional.empty());

        // Act
        String viewName = jobController.jobDetails(99L, model);

        // Assert
        assertEquals("jobDetails", viewName);
        verify(jobService, times(1)).findById(99L);
        verify(model, never()).addAttribute(eq("job"), any(Job.class));
    }

    @Test
    void jobDetails_WithNullId_ShouldReturnJobDetailsView() {
        // Act
        String viewName = jobController.jobDetails(null, model);

        // Assert
        assertEquals("jobDetails", viewName);
        verify(jobService, never()).findById(anyLong());
        verify(model, never()).addAttribute(eq("job"), any(Job.class));
    }
}