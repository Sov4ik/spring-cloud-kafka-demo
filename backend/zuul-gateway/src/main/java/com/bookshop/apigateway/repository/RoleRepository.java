package com.bookshop.apigateway.repository;

import com.bookshop.apigateway.models.ERole;
import com.bookshop.apigateway.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
