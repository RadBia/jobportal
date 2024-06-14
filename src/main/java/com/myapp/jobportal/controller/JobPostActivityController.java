package com.myapp.jobportal.controller;


import com.myapp.jobportal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobPostActivityController {

    private final UserService userService;
    private final SqlInitializationAutoConfiguration sqlInitializationAutoConfiguration;


    @Autowired
    public JobPostActivityController(UserService userService,
                                     SqlInitializationAutoConfiguration sqlInitializationAutoConfiguration) {
        this.userService = userService;
        this.sqlInitializationAutoConfiguration = sqlInitializationAutoConfiguration;
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
}
