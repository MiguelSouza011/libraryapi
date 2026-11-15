# Library API

API REST para gerenciamento de bibliotecas, desenvolvida com Java e Spring Boot. O projeto permite administrar livros, autores, usuários e clientes OAuth2, combinando persistência relacional, validações, documentação OpenAPI e autenticação segura.

## Funcionalidades

- Cadastro, consulta, atualização e exclusão de livros e autores.
- Pesquisa de livros por ISBN, título, autor, gênero e ano de publicação.
- Paginação para consultas de livros.
- Associação entre livros e autores.
- Cadastro de usuários e gerentes.
- Autenticação de usuários com emissão de tokens JWT.
- Autorização baseada em funções e endpoints protegidos.
- OAuth2 Authorization Server e Resource Server.
- Login social com Google.
- Cadastro de clientes OAuth2.
- Validação de dados e tratamento global de exceções.
- Documentação interativa com Swagger UI.
- Endpoint de monitoramento do Spring Boot Actuator.

## Tecnologias

- Java 25
- Spring Boot 4.1.1
- Spring Web, Spring Data JPA e Hibernate
- Spring Security, OAuth2 e JWT
- PostgreSQL
- MapStruct e Lombok
- Maven
- Docker
- Springdoc OpenAPI
- JUnit 5, Mockito e MockMvc
- GitHub Actions para CI/CD

## Como utilizar

### Pré-requisitos

- JDK 25
- PostgreSQL 13 ou superior
- Git
- Docker (opcional)

### Configuração

Clone o repositório e entre na pasta do projeto:

```bash
git clone https://github.com/MiguelSouza011/libraryapi.git
cd libraryapi
```

Crie um banco PostgreSQL chamado `library` e configure as variáveis de ambiente:

```bash
DB_URL=jdbc:postgresql://localhost:5432/library
DB_USERNAME=postgres
DB_PASSWORD=sua_senha
DB_DRIVER=org.postgresql.Driver
JWT_KEYSTORE_PATH=/caminho/para/keystore.p12
JWT_KEYSTORE_PASSWORD=sua_senha
GOOGLE_CLIENT_ID=seu_client_id
GOOGLE_CLIENT_SECRET=seu_client_secret
```

O script `comandos-sql.txt` contém comandos auxiliares para criação das tabelas e registros iniciais. Ajuste os valores conforme o seu ambiente e nunca publique credenciais reais.

### Execução

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

Para gerar o pacote:

```bash
./mvnw clean package
```

Também é possível construir e executar a imagem Docker:

```bash
docker build -t libraryapi .
docker run --env-file .env -p 8080:8080 -p 9090:9090 libraryapi
```

Com a aplicação em execução, os principais endereços são:

| Recurso | Endereço |
| --- | --- |
| API | `http://localhost:8080` |
| Swagger UI (documentação publicada) | [http://18.222.6.192:8080/swagger-ui/index.html#/](http://18.222.6.192:8080/swagger-ui/index.html#/) |
| Swagger UI (local) | `http://localhost:8080/swagger-ui.html` |
| OpenAPI | `http://localhost:8080/v3/api-docs` |
| Actuator | `http://localhost:9090/actuator` |

### Principais endpoints

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `POST` | `/auth/login` | Autentica um usuário e emite um JWT |
| `GET` | `/livros` | Pesquisa livros com filtros e paginação |
| `POST` | `/livros` | Cadastra um livro |
| `PUT` | `/livros/{id}` | Atualiza um livro |
| `DELETE` | `/livros/{id}` | Remove um livro |
| `GET` | `/autores` | Pesquisa autores |
| `POST` | `/autores` | Cadastra um autor |
| `POST` | `/usuarios` | Cadastra um usuário |
| `POST` | `/usuarios/gerentes` | Cadastra um usuário com função de gerente |

Para acessar endpoints protegidos, envie o token recebido na autenticação:

```http
Authorization: Bearer <seu-token>
```

Os fluxos OAuth2 também podem ser explorados pelos endpoints padrão de autorização, token, introspecção, revogação e JWKs documentados no Swagger.

## Arquitetura

O projeto está organizado em camadas:

```text
Controller → Service → Repository → PostgreSQL
```

Os DTOs, mappers, validators, handlers de exceção e componentes de segurança mantêm as responsabilidades separadas e facilitam a evolução da aplicação.

## Testes

O projeto utiliza JUnit 5, Mockito e MockMvc para testes automatizados em diferentes níveis:

| Tipo | Cobertura | Execução |
| --- | --- | --- |
| Unitários | Validadores, serviços, segurança e tratamento de exceções, com dependências isoladas por mocks | `./mvnw -B -ntp clean verify -Dskip.integration.tests=true` |
| Funcionais | Fluxos HTTP dos controllers usando MockMvc, incluindo status, headers e payloads | Executados junto com os testes unitários pelo comando acima |
| Integração | Persistência real de autores e livros no PostgreSQL | `./mvnw -B -ntp -Pintegration -Dspring.profiles.active=integration clean verify` |

Para executar apenas a suíte padrão localmente:

```bash
./mvnw test
```

No Windows, use `mvnw.cmd` no lugar de `./mvnw`. Os relatórios são gerados em `target/surefire-reports/` e `target/failsafe-reports/`.

## CI/CD com GitHub Actions

O workflow [`.github/workflows/ci.yml`](.github/workflows/ci.yml) é executado em pushes e pull requests para as branches `main`, `master` e `develop`. O pipeline:

1. Compila o projeto com Java 25 e executa os testes unitários e funcionais com Maven.
2. Inicia um serviço PostgreSQL e executa os testes de integração com o perfil `integration`.
3. Gera e publica o relatório JaCoCo e os relatórios de testes como artefatos do workflow.
4. Após a aprovação de todas as etapas, em um push para `main`, constrói e publica a imagem Docker no Docker Hub.
5. Faz o deploy da imagem no EC2 via SSH, mantendo rollback para a versão anterior caso a inicialização falhe.

Configure os seguintes secrets no repositório para habilitar o deploy:

```text
DOCKERHUB_USERNAME
DOCKERHUB_TOKEN
EC2_HOST
EC2_SSH_KEY
```

O servidor EC2 também deve possuir Docker instalado e o arquivo `/opt/libraryapi/.env` com as variáveis de ambiente da aplicação. O endereço da documentação publicada após o deploy é:

[Swagger UI](http://18.222.6.192:8080/swagger-ui/index.html#/)

## Onde encontrar ajuda

- Consulte a [documentação interativa publicada no Swagger UI](http://18.222.6.192:8080/swagger-ui/index.html#/).
- Em um ambiente local, acesse a documentação em `/swagger-ui.html`.
- Verifique os exemplos e scripts de banco no arquivo `comandos-sql.txt`.
- Abra uma [issue](https://github.com/MiguelSouza011/libraryapi/issues) para relatar problemas ou sugerir melhorias.
- Consulte o [código-fonte](https://github.com/MiguelSouza011/libraryapi) para detalhes de implementação.

## Autor

**Miguel Souza**

Desenvolvedor em formação, com foco em backend Java, Spring Boot, APIs REST, segurança, persistência de dados e arquitetura de software.

- GitHub: [@MiguelSouza011](https://github.com/MiguelSouza011)

## Status

Em desenvolvimento, com foco na evolução dos recursos de gerenciamento, segurança e qualidade da API.
