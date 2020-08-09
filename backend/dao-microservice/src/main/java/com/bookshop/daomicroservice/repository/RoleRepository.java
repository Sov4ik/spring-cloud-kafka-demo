package com.bookshop.daomicroservice.repository;

import com.bookshop.daomicroservice.dao.ERole;
import com.bookshop.daomicroservice.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
