package com.monitoring.audit_log_crud.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.monitoring.audit_log_crud.audit.AuditService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);
    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    public AuditAspect(AuditService auditService, ObjectMapper objectMapper) {
        this.auditService = auditService;
        this.objectMapper = objectMapper;
    }

    @Pointcut("execution(* com.monitoring.audit_log_crud.service.UserService.create*(..))")
    public void createOperation() {}

    @Pointcut("execution(* com.monitoring.audit_log_crud.service.UserService.update*(..))")
    public void updateOperation() {}

    @Pointcut("execution(* com.monitoring.audit_log_crud.service.UserService.delete*(..))")
    public void deleteOperation() {}

    private String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return auth.getName();
        }
        return "SYSTEM";
    }

    private String maskSensitiveData(Object obj) {
        if (obj == null) return null;
        try {
            String json = objectMapper.writeValueAsString(obj);
            ObjectNode node = (ObjectNode) objectMapper.readTree(json);
            if (node.has("password")) {
                node.put("password", "***MASKED***");
            }
            return node.toString();
        } catch (JsonProcessingException e) {
            logger.warn("Erro ao processar JSON para mascaramento", e);
            return obj.toString();
        }
    }

    @Around("createOperation()")
    public Object auditCreate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        String newState = maskSensitiveData(result);
        auditService.logAction("CREATE", getUsername(), "User", "N/A", null, newState);
        return result;
    }

    @Around("updateOperation()")
    public Object auditUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object arg = joinPoint.getArgs()[1]; 
        String oldState = maskSensitiveData(arg); // Simples aproximação
        Object result = joinPoint.proceed();
        String newState = maskSensitiveData(result);
        auditService.logAction("UPDATE", getUsername(), "User", joinPoint.getArgs()[0].toString(), oldState, newState);
        return result;
    }

    @Around("deleteOperation()")
    public Object auditDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        Object id = joinPoint.getArgs()[0];
        Object result = joinPoint.proceed();
        auditService.logAction("DELETE", getUsername(), "User", id.toString(), null, null);
        return result;
    }
}
