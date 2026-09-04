package com.monitoring.audit_log_crud.kafka;

import com.monitoring.audit_log_crud.document.AuditLogDocument;
import com.monitoring.audit_log_crud.repository.AuditLogMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuditKafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(AuditKafkaConsumer.class);
    private final AuditLogMongoRepository mongoRepository;
    private final KafkaTemplate<String, AuditLogDocument> kafkaTemplate;

    public AuditKafkaConsumer(AuditLogMongoRepository mongoRepository, KafkaTemplate<String, AuditLogDocument> kafkaTemplate) {
        this.mongoRepository = mongoRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "audit-events-topic", groupId = "audit-group")
    public void consume(AuditLogDocument logDocument) {
        try {
            mongoRepository.save(logDocument);
            logger.info("Consumidor KAFKA: Log de auditoria processado com sucesso e salvo no MongoDB.");
        } catch (Exception e) {
            logger.error("Falha ao salvar no Mongo. Enviando para a DLQ (Dead Letter Queue).", e);
            kafkaTemplate.send("audit-events-dlq", logDocument);
        }
    }
}
