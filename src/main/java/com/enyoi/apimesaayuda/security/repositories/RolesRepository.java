package com.enyoi.apimesaayuda.security.repositories;

import com.enyoi.apimesaayuda.security.entities.Roles;
import com.enyoi.apimesaayuda.security.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(Role role);
}
