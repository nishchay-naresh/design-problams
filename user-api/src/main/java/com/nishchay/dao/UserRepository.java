package com.nishchay.dao;

import com.nishchay.pojo.User;import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}