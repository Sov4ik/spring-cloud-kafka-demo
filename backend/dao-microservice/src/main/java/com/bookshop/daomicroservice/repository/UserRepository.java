package com.bookshop.daomicroservice.repository;

import com.bookshop.daomicroservice.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
