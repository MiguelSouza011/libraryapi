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
| Swagger UI | `http://localhost:8080/swagger-ui.html` |
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

Execute a suíte de testes com:

```bash
./mvnw test
```

Os testes atuais cobrem a aplicação, repositórios de autores e livros e operações transacionais.

## Onde encontrar ajuda

- Consulte a documentação interativa em `/swagger-ui.html`.
- Verifique os exemplos e scripts de banco no arquivo `comandos-sql.txt`.
- Abra uma [issue](https://github.com/MiguelSouza011/libraryapi/issues) para relatar problemas ou sugerir melhorias.
- Consulte o [código-fonte](https://github.com/MiguelSouza011/libraryapi) para detalhes de implementação.

## Autor

**Miguel Souza**

Desenvolvedor em formação, com foco em backend Java, Spring Boot, APIs REST, segurança, persistência de dados e arquitetura de software.

- GitHub: [@MiguelSouza011](https://github.com/MiguelSouza011)

## Status

Em desenvolvimento, com foco na evolução dos recursos de gerenciamento, segurança e qualidade da API.
