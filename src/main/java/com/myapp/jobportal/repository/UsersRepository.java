package com.myapp.jobportal.repository;

import com.myapp.jobportal.entity.Users;
import com.myapp.jobportal.entity.UsersType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {


}
