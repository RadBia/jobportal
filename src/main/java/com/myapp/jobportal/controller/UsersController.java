package com.myapp.jobportal.controller;

import com.myapp.jobportal.entity.Users;
import com.myapp.jobportal.entity.UsersType;
import com.myapp.jobportal.services.UserService;
import com.myapp.jobportal.services.UsersTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;
    private final UserService usersService;

    public UsersController(UsersTypeService usersTypeService, UserService userService) {
        this.usersTypeService = usersTypeService;
        this.usersService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<UsersType> usersTypes = usersTypeService.getAll();
        model.addAttribute("getAllTypes", usersTypes);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users) {
//        System.out.println("User:: " + users);
        usersService.addNew(users);
        return "dashboard";
    }
}
