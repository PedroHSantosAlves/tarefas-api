# Tarefas API

API REST para gerenciamento de tarefas, feita com **Java 17** e **Spring Boot**. O projeto implementa um CRUD completo, com validação de dados, tratamento global de erros e testes automatizados.

## Tecnologias

- Java 17
- Spring Boot 3 (Spring Web, Spring Data JPA, Bean Validation)
- Banco de dados H2 em memória
- Maven
- JUnit 5, Mockito e MockMvc

## Arquitetura

O código segue a divisão em camadas:

```
controller  ->  service  ->  repository  ->  banco de dados
```

| Pacote | Responsabilidade |
|--------|------------------|
| `controller` | Recebe as requisições HTTP e devolve as respostas |
| `service` | Regras de negócio |
| `repository` | Acesso ao banco com Spring Data JPA |
| `model` | Entidade `Tarefa` e enum `StatusTarefa` |
| `exception` | Exceção própria e tratamento global de erros |

## Como executar

Pré-requisitos: **JDK 17** e **Maven** (ou uma IDE como IntelliJ IDEA).

```bash
git clone https://github.com/[seu-usuario]/tarefas-api.git
cd tarefas-api
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`. Também é possível abrir o projeto na IDE e executar a classe `TarefasApplication`.

O console do banco fica em `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:tarefasdb`, usuário `sa`, senha vazia).

## Como rodar os testes

```bash
mvn test
```

## Endpoints

| Método | Rota | Descrição | Resposta |
|--------|------|-----------|----------|
| GET | `/tarefas` | Lista tarefas (filtro opcional: `?status=PENDENTE`) | 200 |
| GET | `/tarefas/{id}` | Busca uma tarefa | 200 / 404 |
| POST | `/tarefas` | Cria uma tarefa | 201 / 400 |
| PUT | `/tarefas/{id}` | Atualiza uma tarefa | 200 / 400 / 404 |
| DELETE | `/tarefas/{id}` | Remove uma tarefa | 204 / 404 |

Status possíveis: `PENDENTE`, `EM_ANDAMENTO`, `CONCLUIDA`.

## Exemplos

Criar uma tarefa:

```bash
curl -X POST http://localhost:8080/tarefas \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Estudar Spring Boot","descricao":"Fazer o módulo de JPA"}'
```

Resposta (`201 Created`):

```json
{
  "id": 1,
  "titulo": "Estudar Spring Boot",
  "descricao": "Fazer o módulo de JPA",
  "status": "PENDENTE",
  "criadaEm": "2026-10-06T14:30:00"
}
```

Erro de validação (`400 Bad Request`):

```json
{
  "titulo": "O título é obrigatório"
}
```

Tarefa não encontrada (`404 Not Found`):

```json
{
  "erro": "Tarefa não encontrada: id 999"
}
```

## Próximos passos

- Trocar o H2 por PostgreSQL
- Usar DTOs em vez de expor a entidade
- Paginação na listagem
- Documentação com Swagger/OpenAPI
- Containerização com Docker

## Autor

**Pedro com auxilio do uso de IA
www.linkedin.com/in/phsa
