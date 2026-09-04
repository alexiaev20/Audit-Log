package com.monitoring.audit_log_crud.audit;

import com.monitoring.audit_log_crud.document.AuditLogDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuditService {
    private static final Logger logger = LoggerFactory.getLogger(AuditService.class);
    private final KafkaTemplate<String, AuditLogDocument> kafkaTemplate;

    public AuditService(KafkaTemplate<String, AuditLogDocument> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void logAction(String action, String username, String entity, String entityId, String oldState, String newState) {
        AuditLogDocument log = new AuditLogDocument();
        log.setAction(action);
        log.setUsername(username);
        log.setEntityName(entity);
        log.setEntityId(entityId);
        log.setOldState(oldState);
        log.setNewState(newState);
        log.setTimestamp(LocalDateTime.now());
        
        kafkaTemplate.send("audit-events-topic", log);
        logger.info("LOG ENVIADO AO KAFKA [Tópico: audit-events-topic]");
    }
}
