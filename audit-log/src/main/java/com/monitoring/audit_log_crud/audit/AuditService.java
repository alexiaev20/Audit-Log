package com.monitoring.audit_log_crud.audit;

import com.monitoring.audit_log_crud.document.AuditLogDocument;
import com.monitoring.audit_log_crud.repository.AuditLogMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuditService {
    private static final Logger logger = LoggerFactory.getLogger(AuditService.class);
    private final AuditLogMongoRepository mongoRepository;

    public AuditService(AuditLogMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
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
        
        try {
            mongoRepository.save(log);
            logger.info("AUDIT LOG SALVO NO MONGODB: [Ação: {}] [Usuário: {}]", action, username);
        } catch (Exception e) {
            logger.error("Falha ao salvar log de auditoria no MongoDB", e);
        }
    }
}
