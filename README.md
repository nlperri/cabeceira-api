
# Cabeceira API - Aplicativo de Gerenciamento de Livros

## Descrição Geral

O aplicativo Cabeceira API é uma aplicação back-end desenvolvida em Spring Boot, projetada para fornecer funcionalidades de gerenciamento de biblioteca pessoal. A aplicação permite aos usuários explorar uma variedade de obras, adicioná-las a estantes específicas e organizar sua biblioteca de maneira eficiente.

### Funcionalidades Principais

- **Cadastro e Autenticação:** Os usuários podem se cadastrar e autenticar na plataforma para acessar recursos personalizados.
  
- **Perfil do Usuário:** Os usuários têm a capacidade de atualizar seus dados de cadastro, fornecendo informações como nome, email e senha.

- **Estantes de Livros:** A aplicação oferece três estantes para categorizar livros: "Lidos", "Lendo" e "Quero Ler". Os usuários podem mover livros entre essas estantes.

- **Gerenciamento de Livros:** Funcionalidades incluem busca por livros, adição, exclusão, atualização do progresso de leitura e visualização de detalhes de cada obra.

- **Segurança e Autenticação:** As senhas dos usuários são armazenadas de forma segura, utilizando a criptografia Bcrypt, e a autenticação é realizada utilizando JSON Web Tokens (JWT).

- **Persistência de Dados:** Os dados dos usuários são armazenados em um banco de dados MySQL.

### Tecnologias Utilizadas

- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **Spring Validation**
- **Spring Web**
- **Lombok**
- **MySQL**
- **Gradle**
- **Docker**
- **Springdoc OpenAPI**
- **Auth0 Java JWT**
- **Bcrypt**

### Versões das Dependências no build.gradle

- Spring Boot: 3.1.5
- Springdoc OpenAPI: 2.2.0
- Java JWT (Auth0): 2.0.1
- Bcrypt: 0.3

## Como Contribuir

1. Faça um fork do repositório.
2. Crie uma branch para a sua feature (`git checkout -b feature/nova-feature`).
3. Faça commit das suas mudanças (`git commit -m 'Adiciona nova feature'`).
4. Faça push para a branch (`git push origin feature/nova-feature`).
5. Abra um pull request.

## Ambiente de Desenvolvimento

Certifique-se de ter o Docker instalado para a execução do banco de dados MySQL.

1. Clone o repositório.
2. Execute o ambiente de desenvolvimento:
   
   ```bash
   docker-compose up app-dev


4. A aplicação estará disponível em `http://localhost:8080`.

## Testes

Para executar os testes, execute: 
```bash
docker-compose up app-test
```
  ## 
  
**Nota:** Este projeto está em constante desenvolvimento. Se encontrar algum problema ou tiver sugestões, sinta-se à vontade para contribuir ou abrir uma issue.


