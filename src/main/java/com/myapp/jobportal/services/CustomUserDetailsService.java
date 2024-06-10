package com.myapp.jobportal.services;

import com.myapp.jobportal.entity.Users;
import com.myapp.jobportal.repository.UsersRepository;
import com.myapp.jobportal.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not found user"));
        return new CustomUserDetails(user);
    }
}
