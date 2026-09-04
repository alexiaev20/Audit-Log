package com.monitoring.audit_log_crud.controller;

import com.monitoring.audit_log_crud.service.CsvExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/audit")
public class AuditReportController {

    private final CsvExportService exportService;

    public AuditReportController(CsvExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> downloadCsv() throws Exception {
        byte[] csvData = exportService.exportLogsToCsv();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=audit_logs.csv");
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(csvData);
    }
}
