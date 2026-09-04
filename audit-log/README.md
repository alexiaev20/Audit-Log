# Audit-Log Corporativo (AOP, Kafka, MongoDB & ELK)

Um ecossistema complexo de auditoria de dados e gerenciamento de usuários desenhado para vagas de nível **Especialista / Arquiteto Backend**. Toda alteração no banco principal relacional gera *eventos assíncronos*, mascara informações de acordo com a LGPD e persiste evidências rastreáveis num banco NoSQL, centralizando a visualização no Kibana.

## 🚀 Arquitetura e Fluxo de Dados

1. **Spring Security (JWT):** Autenticação robusta fechando os endpoints.
2. **Interceptação Transparente (Spring AOP):** Nenhuma classe de regra de negócio foi "sujada" com código de auditoria. O AOP abraça a requisição, captura as informações via *Security Context* (Quem fez?), calcula a diferença de dados (O que fez?) e oculta senhas automaticamente (LGPD / `@SensitiveData`).
3. **Persistência Poliglota:** Usuários salvos em **MySQL** (via Flyway), enquanto os rastros infinitos de auditoria são enviados para o **MongoDB**.
4. **Mensageria com DLQ:** Ao interceptar a ação, o Log de auditoria é empurrado via produtor para um tópico no **Apache Kafka**. O Consumidor descarrega a mensagem no Mongo. Se o Mongo falhar, o Kafka redireciona o rastro para uma fila morta (Dead Letter Queue), não permitindo a perda de provas.
5. **Micrometer e Zipkin:** Rastreabilidade milissegundo a milissegundo injetando Span IDs e Trace IDs na execução, permitindo investigar engarrafamentos no ecossistema (Bottlenecks).
6. **Centralização de Logs (ELK):** O Logback envia logs via TCP puro para o **Logstash**, que indexa no **Elasticsearch**. O **Kibana** permite investigar os relatórios gráficos em tempo real.
7. **Integração de Evidências:** Extração do banco de auditoria (NoSQL) em formato planilhado (CSV) direto pela API.

## 🐳 Subindo o Ecossistema

Este projeto orquestra simultaneamente **8 microsserviços (containers)**. É recomendado possuir no mínimo 8GB de RAM disponíveis para subir a rede inteira.

```bash
docker-compose up -d --build
```

**Painéis de Comando Expostos:**
- API Principal: `http://localhost:8080` (Documentação no `/swagger-ui.html`)
- Zipkin (Tracing): `http://localhost:9411`
- Kibana (Dashboard): `http://localhost:5601`
