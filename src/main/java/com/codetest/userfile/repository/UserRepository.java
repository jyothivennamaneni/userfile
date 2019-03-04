package com.codetest.userfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codetest.userfile.model.User;
public interface UserRepository extends JpaRepository<User, Long>{

}
