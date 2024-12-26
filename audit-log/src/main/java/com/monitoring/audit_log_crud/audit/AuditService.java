package com.monitoring.audit_log_crud.audit;

import org.springframework.stereotype.Service;

@Service
public class AuditService {

    public void logAction(String action, String username) {
        System.out.println("Action: " + action + " | User: " + username + " | Timestamp: " + System.currentTimeMillis());
    }
}
