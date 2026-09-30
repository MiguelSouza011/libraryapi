# 📚 Library API

API REST para gerenciamento de uma biblioteca, desenvolvida com **Java 25** e **Spring Boot 4.1.1**.

O projeto evoluiu de uma API CRUD para uma aplicação backend completa, trabalhando persistência de dados, relacionamentos, DTOs, MapStruct, validações, tratamento de exceções e autenticação/autorização com **Spring Security, OAuth2, JWT e login social com Google**.

---

## 🚀 Sobre o projeto

O **Library API** foi desenvolvido para colocar em prática conceitos de desenvolvimento backend utilizando Java e Spring.

A aplicação trabalha com:

- 📚 Livros
- ✍️ Autores
- 👤 Usuários
- 🔐 Clientes OAuth2

A arquitetura segue o padrão:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

---

# 🎯 Objetivos do projeto

Durante o desenvolvimento foram praticados:

- Desenvolvimento de APIs REST
- CRUD
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- DTOs
- MapStruct
- Relacionamentos entre entidades
- Validação
- Regras de negócio
- Tratamento global de exceções
- Queries personalizadas
- Query by Example
- Specifications
- Spring Security
- OAuth2
- Authorization Server
- Resource Server
- JWT
- PKCE
- Login social com Google
- Docker
- HikariCP
- Variáveis de ambiente
- Testes
- Maven
- Git
- GitHub
- Postman

---

# 🛠️ Tecnologias utilizadas

- Java 25
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- Spring Security
- Spring Authorization Server
- PostgreSQL
- JPA / Hibernate
- HikariCP
- MapStruct
- Lombok
- Maven
- Docker
- IntelliJ IDEA
- Git / GitHub
- Postman

---

# 🏗️ Arquitetura

```text
                    CLIENTE
                       │
                       ▼
              ┌─────────────────┐
              │    Controller   │
              └────────┬────────┘
                       │
                       ▼
              ┌─────────────────┐
              │     Service     │
              └────────┬────────┘
                       │
                       ▼
              ┌─────────────────┐
              │   Repository    │
              └────────┬────────┘
                       │
                       ▼
              ┌─────────────────┐
              │   PostgreSQL    │
              └─────────────────┘
```

---

# 📂 Estrutura do projeto

```text
src
└── main
    ├── java
    │   └── com.miguelsouza.libraryapi
    │       ├── config
    │       ├── controllers
    │       ├── entities
    │       ├── repositories
    │       ├── services
    │       ├── dtos
    │       ├── mappers
    │       ├── specifications
    │       ├── validators
    │       ├── exceptions
    │       └── security
    │
    └── resources
        └── application.yml
```

---

# 📖 Livros

A entidade `Livro` representa os livros cadastrados na biblioteca.

Foram trabalhadas operações de:

- Cadastro
- Consulta
- Atualização
- Exclusão
- Pesquisa
- Validação
- Associação com autores

---

# ✍️ Autores

A entidade `Autor` representa os autores cadastrados.

A API permite:

- Criar autores
- Buscar autores
- Atualizar autores
- Excluir autores
- Pesquisar autores

Os autores são relacionados aos livros utilizando JPA/Hibernate.

---

# 🔗 Relacionamento Livro e Autor

```text
Autor
  │
  │ 1
  │
  └────────── N
             │
           Livro
```

Foram praticados:

- `@ManyToOne`
- `@OneToMany`
- `FetchType.LAZY`
- `@JoinColumn`
- serialização JSON
- prevenção de referências circulares

---

# 🔄 CRUD

| Método | Operação |
|--------|----------|
| GET | Buscar |
| POST | Criar |
| PUT | Atualizar |
| DELETE | Excluir |

---

# 🔎 Pesquisas

Foram utilizados recursos do Spring Data JPA para criar pesquisas flexíveis:

- Query by Example
- ExampleMatcher
- Specifications
- Queries personalizadas
- Filtros
- Pesquisa dinâmica

---

# 🔄 DTOs

Os DTOs separam os objetos da API das entidades persistidas.

```text
JSON
 ↓
DTO
 ↓
Mapper
 ↓
Entity
 ↓
Repository
 ↓
PostgreSQL
```

E:

```text
PostgreSQL
 ↓
Entity
 ↓
Mapper
 ↓
DTO
 ↓
JSON
```

---

# 🔄 MapStruct

O projeto utiliza **MapStruct** para realizar o mapeamento entre DTOs e entidades.

```text
LivroDTO
   ↓
LivroMapper
   ↓
Livro
```

Também foram trabalhados mapeamentos envolvendo relacionamentos, como livro e autor.

---

# ⚠️ Tratamento de exceções

A aplicação possui tratamento global de exceções.

```text
Exception
    ↓
GlobalExceptionHandler
    ↓
HTTP Response
```

Foram trabalhados cenários como recursos não encontrados e erros relacionados às regras de negócio.

---

# 🧠 Regras de negócio

As regras da aplicação ficam principalmente na camada de Service.

```text
Controller
    ↓
Service
    ↓
Regra de negócio
    ↓
Repository
```

---

# 🗄️ PostgreSQL

O projeto utiliza PostgreSQL como banco de dados.

```text
Spring Boot
    ↓
HikariCP
    ↓
JPA / Hibernate
    ↓
PostgreSQL
```

Banco utilizado:

```text
library
```

---

# 🐳 Docker

O PostgreSQL foi utilizado em ambiente Docker durante o desenvolvimento.

```text
Spring Boot
     ↓
HikariCP
     ↓
localhost:5432
     ↓
Docker
     ↓
PostgreSQL
```

---

# 🔐 Spring Security

Uma das principais evoluções do projeto foi a implementação de **Spring Security**.

Foram trabalhados:

- SecurityFilterChain
- autenticação
- autorização
- OAuth2
- Authorization Server
- Resource Server
- OAuth2 Login
- JWT
- OpenID Connect
- PKCE

---

# 🔑 OAuth2 Authorization Server

O projeto possui um **OAuth2 Authorization Server**.

Fluxo:

```text
Client
   ↓
/oauth2/authorize
   ↓
Authentication
   ↓
Authorization Code
   ↓
/oauth2/token
   ↓
JWT
```

Também foi implementado um `RegisteredClientRepository` personalizado para buscar clientes OAuth2 no banco de dados.

```text
PostgreSQL
    ↓
Client
    ↓
ClientService
    ↓
CustomRegisteredClientRepository
    ↓
RegisteredClient
    ↓
Authorization Server
```

---

# 🔐 JWT

O projeto trabalha com **JSON Web Token (JWT)**.

```text
Usuário
   ↓
Autenticação
   ↓
Authorization Server
   ↓
JWT
   ↓
Cliente
   ↓
Bearer Token
   ↓
Resource Server
   ↓
API
```

Requisições protegidas utilizam:

```http
Authorization: Bearer <token>
```

---

# 🌐 OAuth2 Resource Server

O Resource Server recebe o JWT e valida o token antes de permitir acesso aos recursos protegidos.

```text
Cliente
   ↓
Bearer JWT
   ↓
Spring Security
   ↓
Resource Server
   ↓
Validação
   ↓
Endpoint protegido
```

---

# 🌎 Login social com Google

Foi implementado login social utilizando **Google OAuth2**.

```text
Usuário
   ↓
Aplicação
   ↓
Google
   ↓
Autenticação
   ↓
Callback
   ↓
Aplicação
   ↓
Usuário autenticado
```

As credenciais são configuradas através de:

```text
GOOGLE_CLIENT_ID
GOOGLE_CLIENT_SECRET
```

---

# 🔒 PKCE

Também foi trabalhado **PKCE (Proof Key for Code Exchange)** no Authorization Code Flow.

Parâmetros:

```text
code_challenge
code_challenge_method
code_verifier
```

Fluxo:

```text
Cliente
   ↓
code_challenge
   ↓
Authorization Server
   ↓
Authorization Code
   ↓
Cliente
   ↓
code_verifier
   ↓
Access Token
```

---

# 👥 Clientes OAuth2

Os clientes OAuth2 são representados por uma entidade própria.

Dados trabalhados:

```text
id
clientId
clientSecret
redirectUri
scope
```

Esses dados são utilizados para registrar os clientes no Authorization Server.

---

# ⚙️ SecurityFilterChain

O projeto trabalha diferentes cadeias de segurança para separar responsabilidades.

```text
SecurityFilterChain
       │
       ├── Authorization Server
       │
       └── Aplicação / Resource Server
```

Foram praticados:

- `@Order`
- `securityMatcher`
- Authorization Server endpoints
- autenticação
- autorização
- OAuth2 Resource Server
- OAuth2 Login

---

# 🔒 Variáveis de ambiente

As configurações sensíveis são obtidas através de variáveis de ambiente:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
DB_DRIVER

GOOGLE_CLIENT_ID
GOOGLE_CLIENT_SECRET
```

Exemplo:

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: ${DB_DRIVER}
```

---

# 📮 Testes com Postman

Foram realizados testes envolvendo:

- CRUD
- endpoints REST
- autenticação
- OAuth2
- Authorization Code
- Authorization Code + PKCE
- JWT
- redirect URI
- Authorization Server
- endpoints protegidos

Fluxo:

```text
Postman
   ↓
/oauth2/authorize
   ↓
Login
   ↓
Authorization Code
   ↓
/oauth2/token
   ↓
Access Token
   ↓
Bearer Token
   ↓
API
```

---

# 🧪 Testes

Foram trabalhados testes relacionados principalmente à persistência, incluindo:

```text
AutorRepositoryTest
LivroRepositoryTest
```

---

# 📄 Swagger / OpenAPI

A configuração contempla recursos relacionados à documentação da API:

```text
/v2/api-docs/**
/v3/api-docs/**
/swagger-resources/**
/swagger-ui.html
/swagger-ui/**
/webjars/**
```

---

# 🧠 O que foi praticado

### Java

- Orientação a objetos
- Collections
- Exceptions
- Optional
- Streams
- Generics
- Records

### Spring Boot

- Injeção de dependências
- Beans
- Controllers
- Services
- Repositories
- Configurations
- Profiles
- Configuração externa

### Spring Data JPA

- Entities
- Repositories
- Queries
- Specifications
- Query by Example
- Relacionamentos
- Hibernate
- Lazy Loading

### APIs REST

- REST
- HTTP
- JSON
- CRUD
- DTOs
- Status Codes
- ResponseEntity

### Segurança

- Spring Security
- Authentication
- Authorization
- OAuth2
- Authorization Server
- Resource Server
- OAuth2 Login
- Google Login
- JWT
- PKCE
- OpenID Connect
- Registered Clients
- SecurityFilterChain

### Banco de dados

- PostgreSQL
- SQL
- JPA
- Hibernate
- HikariCP
- Pool de conexões

### Infraestrutura

- Docker
- PostgreSQL em container
- Variáveis de ambiente
- Maven

### Ferramentas

- IntelliJ IDEA
- Git
- GitHub
- Postman

---

# 📈 Evolução do projeto

```text
API REST
   ↓
CRUD
   ↓
JPA / Hibernate
   ↓
PostgreSQL
   ↓
Relacionamentos
   ↓
DTOs
   ↓
MapStruct
   ↓
Queries
   ↓
Query by Example
   ↓
Specifications
   ↓
Validações
   ↓
Tratamento de exceções
   ↓
Spring Security
   ↓
OAuth2
   ↓
Authorization Server
   ↓
Resource Server
   ↓
JWT
   ↓
PKCE
   ↓
Login Social com Google
   ↓
API Backend completa
```

---

# 🚀 Como executar

## 1. Clonar o projeto

```bash
git clone https://github.com/MiguelSouza011/libraryapi.git
```

```bash
cd libraryapi
```

## 2. Configurar o PostgreSQL

Crie o banco:

```text
library
```

Ou utilize PostgreSQL através do Docker.

## 3. Configurar as variáveis de ambiente

```text
DB_URL=jdbc:postgresql://localhost:5432/library
DB_USERNAME=postgres
DB_PASSWORD=postgres
DB_DRIVER=org.postgresql.Driver
```

Para login social:

```text
GOOGLE_CLIENT_ID=seu-client-id
GOOGLE_CLIENT_SECRET=seu-client-secret
```

## 4. Executar

Windows:

```bash
mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Ou execute a classe principal pela IDE.

---

# 🌐 Aplicação

Por padrão:

```text
http://localhost:8080
```

---

# 👨‍💻 Autor

**Miguel Souza**

Estudante de Engenharia de Software com foco em desenvolvimento backend Java.

### Tecnologias em estudo

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Docker
- APIs REST
- OAuth2
- JWT

---

# 🔗 Repositório

[GitHub - Library API](https://github.com/MiguelSouza011/libraryapi)

---

# 📌 Status

🚧 **Em desenvolvimento**

Projeto desenvolvido como parte da minha evolução prática em desenvolvimento backend Java e Spring Boot, com foco em APIs REST, persistência, arquitetura e segurança.
