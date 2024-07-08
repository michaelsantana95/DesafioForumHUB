# Challenge Forum Hub

API Rest do Challenge Forum Hub para o programa ONE (Oracle Next Education) em parceria com Alura.

## Descrição do Projeto

Este projeto é uma API RESTful desenvolvida para um fórum de discussão, onde os usuários podem criar tópicos, responder a tópicos existentes e marcar respostas como solução. O objetivo é fornecer um ambiente colaborativo para discussões e resolução de problemas em diversas áreas de conhecimento, com foco inicial em programação.

## Funcionalidades

- **Autenticação de Usuários**: Cadastro e login de usuários com proteção JWT.
- **Gerenciamento de Tópicos**: Criação, atualização, listagem e exclusão de tópicos.
- **Gerenciamento de Respostas**: Adição, atualização, listagem e exclusão de respostas para tópicos.
- **Soluções para Respostas**: Marcar respostas como solução para um tópico.
- **Gerenciamento de Cursos**: Criação, atualização, listagem e exclusão de cursos.
- **Documentação da API**: Utilização do Swagger para documentar e testar os endpoints da API.

## Restrições e Comportamentos da API

### Usuários

- As senhas dos usuários são armazenadas de forma criptografada no banco de dados.

### Tópicos

- Não é possível adicionar tópicos e mensagens duplicadas.
- Ao enviar na requisição um curso não cadastrado, o curso é automaticamente criado.
- Ao marcar uma resposta como solução, o tópico é considerado solucionado e não permite mais alterações.
- Ao excluir um tópico, mesmo que já solucionado, todas as respostas são automaticamente excluidas.

### Respostas

- Não é possível adicionar respostas duplicadas para um mesmo tópico, para outros tópicos é permitido.
- Não é possível modificar ou excluir respostas de um tópico já solucionado.

### Cursos

- Não é possível modificar ou excluir cursos que possuem tópicos cadastrados relacionados.

## Como Utilizar

### Recomendações

- Recomenda-se o uso do **IntelliJ IDEA** para o desenvolvimento deste projeto devido ao suporte integrado para Java e facilidade de configuração.

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6.3 ou superior
- Banco de dados MySQL

### Dependências Utilizadas
As dependências abaixo são automaticamente gerenciadas pelo Maven:
- **Lombok**: Facilita a criação de getters, setters e outras funcionalidades comuns.
- **Spring Web**: Suporte para desenvolvimento de aplicações web, incluindo RESTful.
- **Spring Boot DevTools**: Ferramentas de desenvolvimento para Spring Boot.
- **Spring Data JPA**: Abstração de persistência de dados com JPA.
- **Flyway Migration**: Migração e versionamento do banco de dados.
- **MySQL Driver**: Conector JDBC para MySQL.
- **Validation**: Validação de dados de entrada.
- **Spring Security**: Autenticação e autorização de segurança.
- **Auth0**: Implementação de JWT para autenticação.
- **springdoc**: Documentação da API com OpenAPI e Swagger.

### Clone o repositório e abra na IDE.

   ```
   git clone https://github.com/jhonathan-p/challenge-forum-hub.git
   ```

### Configuração do Banco de Dados

Configure o arquivo `application.properties` com as credenciais do seu banco de dados MySQL.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/seu_banco_de_dados
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## Utilizando a Documentação Swagger
Com a aplicação em execução, acesse a documentação em http://localhost:8080/swagger-ui.html. Você pode usar esta interface para testar os endpoints da API.

### Endpoints Principais

- **Usuários**:
    - `POST /usuarios/novo`: Cadastro de novo usuário.
    - `POST /usuarios/login`: Login de usuário.


- **Tópicos**:
    - `POST /topicos`: Criação de novo tópico.
    - `GET /topicos`: Listagem de tópicos.
    - `GET /topicos/{id}`: Detalhes de um tópico específico.
    - `PUT /topicos/{id}`: Atualização de um tópico.
    - `DELETE /topicos/{id}`: Exclusão de um tópico.
  

- **Respostas**:
    - `POST /topicos/{topicoId}/respostas`: Adição de nova resposta a um tópico.
    - `GET /topicos/{topicoId}/respostas`: Listagem de respostas de um tópico.
    - `PUT /topicos/{topicoId}/respostas/{id}`: Atualização de uma resposta.
    - `DELETE /topicos/{topicoId}/respostas/{id}`: Exclusão de uma resposta.
    - `PUT /topicos/{topicoId}/respostas/{id}/solucao`: Marcar uma resposta como solução.


- **Cursos**:
    - `POST /cursos`: Criação de novo curso.
    - `GET /cursos`: Listagem de cursos.
    - `GET /cursos/{id}`: Detalhes de um curso específico.
    - `PUT /cursos/{id}`: Atualização de um curso.
    - `DELETE /cursos/{id}`: Exclusão de um curso.


> Além do Swagger, você também pode realizar testes utilizando o **Insomnia** ou **Postman** com os mesmos endpoints mencionados acima.


### Encontrando Ajuda

Para obter ajuda sobre o projeto, você pode:

- Consultar a documentação do Swagger.
- O projeto está configurado para retornar mensagens claras de erro para facilitar a identificação e resolução dos problemas.
- Enviar uma mensagem através do perfil do autor no GitHub.
