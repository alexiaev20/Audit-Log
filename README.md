# Audit Log CRUD

## Descrição
Este projeto é uma aplicação Java baseada em Spring Boot para gerenciamento de logs de auditoria e usuários. Ele implementa operações CRUD (Create, Read, Update, Delete) para armazenar, consultar e gerenciar registros de auditoria e informações de usuários em um banco de dados. O projeto também oferece suporte à containerização com Docker e documentação via Swagger.

## Tecnologias Utilizadas
- **Java 11+**
- **Spring Boot**
- **Maven** (para gerenciamento de dependências)
- **Docker** (para containerização)
- **Banco de Dados** (configuração em `application.properties`)
- **Swagger** (para documentação da API)

## Estrutura do Projeto

- `src/main/java/com/monitoring/audit_log_crud`:
  - **AuditLogCrudApplication.java**: Ponto de entrada principal da aplicação.
  - **controller/UserController.java**: Endpoints para gerenciamento de usuários.
  - **model/User.java**: Representação do modelo de dados do usuário.
  - **repository/UserRepository.java**: Interface para operações no banco de dados.
  - **service/UserService.java** e **audit/AuditService.java**: Lógica de negócios para usuários e auditoria.
  - **config/CorsConfig.java**: Configuração de CORS.
  - **config/SwaggerConfig.java**: Configuração do Swagger para documentação.

- `src/main/resources`:
  - **application.properties**: Arquivo de configuração para conexão com o banco de dados e outras definições.

- `docker-compose.yml` e `Dockerfile`: Arquivos para containerização e execução em ambientes Docker.

- `pom.xml`: Configuração do Maven para dependências e build.

## Funcionalidades
1. **Criação de Logs e Usuários:** Permite adicionar novos registros de auditoria e usuários.
2. **Consulta de Dados:** Suporte à listagem de todos os registros ou consulta de itens específicos.
3. **Atualização:** Modificação de informações existentes.
4. **Exclusão:** Remove registros de auditoria e usuários do sistema.
5. **Documentação da API:** Acesse a interface Swagger para explorar os endpoints da API.

## Como Executar

### Pré-requisitos
1. **Java 11+** instalado.
2. **Maven** instalado ou use o wrapper do Maven (`mvnw` ou `mvnw.cmd`).
3. **Docker** instalado (opcional para containerização).
4. Banco de dados configurado no arquivo `application.properties`.

### Passos
1. Clone o repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd audit-log-crud
   ```
2. Compile o projeto:
   ```bash
   ./mvnw clean install
   ```
3. Execute a aplicação localmente:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Ou execute com Docker:
   ```bash
   docker-compose up --build
   ```

5. Acesse a aplicação pelo navegador ou ferramentas como Postman em:
   ```
   http://localhost:8080
   ```

6. Acesse a documentação Swagger:
   ```
   http://localhost:8080/swagger-ui.html
   ```

## Configuração do Banco de Dados
Certifique-se de configurar o arquivo `application.properties` com as credenciais e URL do banco de dados desejado. Exemplo:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/audit_logs
spring.datasource.username=usuario
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
```

## Testes
Execute os testes para garantir o funcionamento correto da aplicação:
```bash
./mvnw test
```

## Contribuições
Contribuições são bem-vindas! Siga os passos abaixo:
1. Faça um fork do repositório.
2. Crie um branch para sua feature ou correção:
   ```bash
   git checkout -b minha-feature
   ```
3. Faça suas alterações e commit:
   ```bash
   git commit -m "Minha nova feature"
   ```
4. Envie o branch para o repositório remoto:
   ```bash
   git push origin minha-feature
   ```
5. Abra um Pull Request.

## Licença
Este projeto está licenciado sob a [MIT License](LICENSE).

