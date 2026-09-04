package com.monitoring.audit_log_crud.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "audit_logs")
public class AuditLogDocument {
    @Id
    private String id;
    
    private String action;
    private String username;
    private String entityName;
    private String entityId;
    private String oldState;
    private String newState;
    private LocalDateTime timestamp;
}
