package com.sam.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sam.model.MyUser;

public interface MyUserDao extends JpaRepository<MyUser, Long> {
	
	Optional<MyUser> findByUsername(String username);

}
