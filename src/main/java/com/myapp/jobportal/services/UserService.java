package com.myapp.jobportal.services;

import com.myapp.jobportal.entity.JobSeekerProfile;
import com.myapp.jobportal.entity.RecruiterProfile;
import com.myapp.jobportal.entity.Users;
import com.myapp.jobportal.repository.JobSeekerProfileRepository;
import com.myapp.jobportal.repository.RecruiterProfileRepository;
import com.myapp.jobportal.repository.UsersRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UsersRepository usersRepository, RecruiterProfileRepository recruiterProfileRepository,
                       JobSeekerProfileRepository jobSeekerProfileRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        int userTypeId = users.getUserTypeId().getUserTypeId();
        Users savedUser = usersRepository.save(users);
        if (userTypeId == 1) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        } else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }

        return savedUser;
    }

    public Object getCurrentUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));

            int userId = users.getUserId();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                RecruiterProfile recruiterProfile = recruiterProfileRepository
                        .findById(userId)
                        .orElse(new RecruiterProfile());
                return recruiterProfile;
            } else {
                JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository
                        .findById(userId)
                        .orElse(new JobSeekerProfile());
                return jobSeekerProfile;
            }
        }
        return null;
    }

    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
            return user;
        }
        return null;
    }
}