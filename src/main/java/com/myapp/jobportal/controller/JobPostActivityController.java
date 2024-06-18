package com.myapp.jobportal.controller;


import com.myapp.jobportal.entity.JobPostActivity;
import com.myapp.jobportal.entity.Users;
import com.myapp.jobportal.services.JobPostActivityService;
import com.myapp.jobportal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class JobPostActivityController {

    private final UserService userService;
    private final SqlInitializationAutoConfiguration sqlInitializationAutoConfiguration;
    private final JobPostActivityService jobPostActivityService;


    @Autowired
    public JobPostActivityController(UserService userService,
                                     SqlInitializationAutoConfiguration sqlInitializationAutoConfiguration,
                                     JobPostActivityService jobPostActivityService) {
        this.userService = userService;
        this.sqlInitializationAutoConfiguration = sqlInitializationAutoConfiguration;
        this.jobPostActivityService = jobPostActivityService;
    }





    @GetMapping("/dashboard/")
    public String searchJobs(Model model) {

        Object currentUserProfile = userService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            model.addAttribute("username", currentUsername);
        }
        model.addAttribute("user", currentUserProfile);
        System.out.println("Dashboard");
        return "dashboard";
    }

    @GetMapping("/dashboard/add")
    public String addJobs(Model model) {
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "add-jobs";
    }

    @PostMapping("/dashboard/addNew")
    public String addNew(JobPostActivity jobPostActivity, Model model) {
        Users user = userService.getCurrentUser();
        if (user != null) {
            jobPostActivity.setPostedById(user);
        }
        jobPostActivity.setPostedDate(new Date());
        model.addAttribute("jobPostActivity", jobPostActivity);
        JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);
        return  "redirect:/dashboard/";
    }
}
