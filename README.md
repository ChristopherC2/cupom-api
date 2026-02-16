# 🧾 Cupom API

API REST para gerenciamento de cupons de desconto desenvolvida como parte do desafio técnico da tenda atacadao utilizando Java e Spring Boot.

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database
- Swagger (OpenAPI)
- Docker & Docker Compose
- JUnit 5
- Mockito
- Lombok

---

## 🧱 Arquitetura

A aplicação foi desenvolvida seguindo princípios de orientação a objetos e separação de responsabilidades, evitando serviços genéricos e utilizando uma abordagem baseada em casos de uso (Use Cases).

A lógica de negócio foi encapsulada diretamente na entidade de domínio `Cupom`, garantindo consistência independentemente da camada de aplicação.

Estrutura do projeto:


- **Controller**: responsável pela interface HTTP
- **UseCase**: representa as intenções do usuário (criar e deletar cupom)
- **Domain**: contém as regras de negócio
- **Repository**: persistência de dados

---

## 📋 Regras de Negócio

### Criação de Cupom

- Campos obrigatórios:
    - `code`
    - `description`
    - `discountValue`
    - `expirationDate`
- O código do cupom:
    - Pode conter caracteres especiais na entrada
    - Terá os caracteres especiais removidos antes de ser salvo
    - Deve possuir exatamente 6 caracteres alfanuméricos
    - Deve conter ao menos um caractere alfanumérico válido
- O valor mínimo de desconto é **0.5**
- A data de expiração não pode estar no passado
- O cupom é criado já publicado por padrão

### Remoção de Cupom

- O cupom é removido utilizando **soft delete**
- Não é permitido deletar um cupom já deletado

---

## 🐳 Execução com Docker

### Gerar o artefato da aplicação

```bash
mvn clean package
```
### Subir a aplicação
```bash
docker compose down
mvn clean package
docker compose build
docker compose up
```
### Cupons para Teste
```bash
{
  "code": "SAVE#5!",
  "description": "Desconto de boas-vindas",
  "discountValue": 5,
  "expirationDate": "2026-12-15"
}
```
```bash
{
  "code": "BLACK@9",
  "description": "Promoção Black Friday",
  "discountValue": 9,
  "expirationDate": "2026-11-30"
}
```
### Documentação da API
```bash
http://localhost:8080/swagger-ui.html
```
### Para Executar os Testes Unitários
```bash
mvn test
```
## 🗄️ Banco de Dados
### Acesse o console em:
```bash
http://localhost:8080/h2-console
```
### JDBC URL:
```bash
jdbc:h2:mem:cuponsdb
```
### Usuário:
```bash
sa
```
### Senha:
```bash
vazia
```
## 📌 Observações
As regras de negócio foram implementadas diretamente na entidade de domínio, garantindo que nenhuma operação viole as restrições definidas independentemente da camada de aplicação.
## 👨‍💻 Autor
Desenvolvido por Christopher Castro
