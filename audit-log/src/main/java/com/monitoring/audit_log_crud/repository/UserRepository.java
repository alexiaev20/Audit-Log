package com.monitoring.audit_log_crud.repository;
import com.monitoring.audit_log_crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);
}
