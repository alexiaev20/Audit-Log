package com.monitoring.audit_log_crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.monitoring.audit_log_crud.audit.AuditService;
import com.monitoring.audit_log_crud.model.User;
import com.monitoring.audit_log_crud.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditService auditService;

    // Criar usuário
    public User createUser(User user) {
        auditService.logAction("CREATE", user.getName());
        return userRepository.save(user);
    }

    // Obter todos os usuários com paginação
    public Page<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    // Obter usuário por ID
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    // Atualizar usuário
    public User updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            auditService.logAction("UPDATE", user.getName());
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    // Deletar usuário
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> auditService.logAction("DELETE", value.getName()));
        userRepository.deleteById(id);
    }
}
