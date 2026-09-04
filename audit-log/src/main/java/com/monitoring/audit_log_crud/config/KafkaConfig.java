package com.monitoring.audit_log_crud.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic auditEventsTopic() {
        return TopicBuilder.name("audit-events-topic").partitions(3).replicas(1).build();
    }
    @Bean
    public NewTopic auditEventsDlq() {
        return TopicBuilder.name("audit-events-dlq").partitions(1).replicas(1).build();
    }
}
