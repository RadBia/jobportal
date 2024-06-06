package com.myapp.jobportal.services;

import com.myapp.jobportal.entity.JobSeekerProfile;
import com.myapp.jobportal.entity.RecruiterProfile;
import com.myapp.jobportal.entity.Users;
import com.myapp.jobportal.repository.JobSeekerProfileRepository;
import com.myapp.jobportal.repository.RecruiterProfileRepository;
import com.myapp.jobportal.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;


    public UserService(UsersRepository usersRepository, RecruiterProfileRepository recruiterProfileRepository,
                       JobSeekerProfileRepository jobSeekerProfileRepository) {
        this.usersRepository = usersRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
    }

    public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        int userTypeId = users.getUserTypeId().getUserTypeId();
        Users savedUser = usersRepository.save(users);
        if(userTypeId == 1) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }

        return savedUser;
    }

}
