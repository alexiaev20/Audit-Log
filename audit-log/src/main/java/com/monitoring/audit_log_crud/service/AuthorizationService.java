package com.monitoring.audit_log_crud.service;
import com.monitoring.audit_log_crud.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class AuthorizationService implements UserDetailsService {
    private final UserRepository repository;
    public AuthorizationService(UserRepository repository) { this.repository = repository; }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { return repository.findByEmail(username); }
}
