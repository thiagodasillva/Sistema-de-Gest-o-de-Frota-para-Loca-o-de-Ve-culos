# Sistema-de-Gestão-de-Frota-para-Locão-de-Veículos 🚗

##  Sistema de Gestão de Frota para Locação de Veículos
API RESTful completa desenvolvida em Java com Spring Boot para gerenciar a locação de veículos de uma frota. O sistema permite o cadastro de clientes, veículos e tipos de veículos, além de gerenciar todo o ciclo de vida de um aluguel. A API é protegida usando autenticação baseada em tokens JWT e documentada com Swagger (OpenAPI).

##  Funcionalidades
Clientes: CRUD completo e exclusão lógica (soft delete).

Veículos: CRUD completo, com associação a um tipo de veículo.

Tipos de Veículo: CRUD completo.

Aluguéis: Criação, finalização e cancelamento de aluguéis.

### Segurança: Autenticação de usuários via JWT (login com CPF e senha).
### Validações: Validação de dados de entrada, incluindo formato de CPF.
### centralized Tratamento de Exceções: Respostas de erro padronizadas para uma melhor experiência do cliente da API.
### Documentação: Documentação interativa da API com Swagger (OpenAPI).

# Como Executar o Projeto

Primeiro, clone o repositório usando o comando: git clone https://github.com/thiagodasillva/Sistema-de-Gest-o-de-Frota-para-Loca-o-de-Ve-culos.git

Abra o projeto na sua IDE. 

## Execute a aplicação:

Encontre a classe principal SistemaDeGestaoDeFrotaParaLocacaoDeVeiculosApplication.java.

Clique com o botão direito sobre ela e selecione "Run".

Verifique se a aplicação está no ar:

O servidor estará rodando em http://localhost:8080. Você verá os logs do Spring no console da sua IDE.


# Tecnologias Utilizadas
Java 17

Spring Boot 3

Spring Web: Para a construção dos endpoints REST.

Spring Data JPA: Para a persistência de dados.

Spring Security: Para a implementação de autenticação e autorização.

Hibernate: Como implementação da JPA.

Maven: Para gerenciamento de dependências.

H2 Database: Banco de dados em memória para ambiente de desenvolvimento.

JJWT (Java JWT): Para a criação e validação de tokens JWT.

ModelMapper: Para o mapeamento entre DTOs e Entidades.

Springdoc OpenAPI: Para a geração da documentação Swagger UI.

📋 Pré-requisitos
Antes de começar, você vai precisar ter instalado em sua máquina:

JDK 17 ou superior.

Maven 3.8 ou superior.

Sua IDE de preferência (ex: IntelliJ IDEA, VSCode com Java Extension Pack, Eclipse).

Um cliente de API como Postman ou Insomnia (opcional, pois o Swagger já oferece uma interface de teste).




Desenvolvido por [Thiago Raimundo da Silva]
