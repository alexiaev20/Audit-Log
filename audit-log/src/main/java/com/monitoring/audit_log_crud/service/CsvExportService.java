package com.monitoring.audit_log_crud.service;

import com.monitoring.audit_log_crud.document.AuditLogDocument;
import com.monitoring.audit_log_crud.repository.AuditLogMongoRepository;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;

@Service
public class CsvExportService {
    private final AuditLogMongoRepository repository;

    public CsvExportService(AuditLogMongoRepository repository) {
        this.repository = repository;
    }

    public byte[] exportLogsToCsv() throws Exception {
        List<AuditLogDocument> logs = repository.findAll();
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer);

        String[] header = {"ID", "Ação", "Usuário", "Entidade", "Entidade ID", "Estado Antigo", "Novo Estado", "Data"};
        csvWriter.writeNext(header);

        for (AuditLogDocument log : logs) {
            String[] data = {
                    log.getId(),
                    log.getAction(),
                    log.getUsername(),
                    log.getEntityName(),
                    log.getEntityId(),
                    log.getOldState(),
                    log.getNewState(),
                    log.getTimestamp() != null ? log.getTimestamp().toString() : ""
            };
            csvWriter.writeNext(data);
        }

        csvWriter.close();
        return writer.toString().getBytes();
    }
}
