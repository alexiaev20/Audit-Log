package com.monitoring.audit_log_crud.repository;
import com.monitoring.audit_log_crud.document.AuditLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface AuditLogMongoRepository extends MongoRepository<AuditLogDocument, String> {}
