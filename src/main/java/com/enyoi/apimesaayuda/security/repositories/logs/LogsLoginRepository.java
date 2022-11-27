package com.enyoi.apimesaayuda.security.repositories.logs;

import com.enyoi.apimesaayuda.security.entities.logs.LogsLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogsLoginRepository extends JpaRepository<LogsLogin, UUID> {
}
