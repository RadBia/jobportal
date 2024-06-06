package com.myapp.jobportal.repository;

import com.myapp.jobportal.entity.JobSeekerProfile;
import com.myapp.jobportal.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Integer> {

    
}
