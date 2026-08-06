# Juno Log

Aplicação web full stack para cadastro e acompanhamento de tarefas. O Juno Log permite registrar tarefas com título e descrição, visualizar seu estado e acompanhar, em um painel responsivo, quantas estão pendentes ou concluídas.

## Objetivo

Centralizar tarefas pessoais em uma interface simples, mantendo os dados persistidos e oferecendo uma API REST para as operações de gerenciamento.

## Tecnologias utilizadas

### Front-end

- React 19
- TypeScript 6
- Vite 8
- Axios
- CSS
- ESLint

### Back-end

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Jakarta Validation
- Springdoc OpenAPI/Swagger UI
- Maven
- MySQL Connector/J

### Banco de dados

O projeto utiliza **MySQL**. Por padrão, o arquivo de exemplo aponta para um banco chamado `juno_log`, disponível em `localhost:3306`. As tabelas são gerenciadas pelo Hibernate com `spring.jpa.hibernate.ddl-auto=update`.

## Arquitetura

O repositório é organizado como uma aplicação cliente-servidor:

```text
Navegador
   │
   │ HTTP/JSON (Axios)
   ▼
Front-end React/Vite (:5173)
   │
   │ API REST
   ▼
Controller → Service → Repository (Spring Data JPA)
                              │
                              ▼
                         MySQL (:3306)
```

No back-end, a separação segue responsabilidades em camadas:

- **Controller:** expõe os endpoints HTTP e recebe os dados da requisição.
- **Service:** concentra as regras de criação, conclusão, consulta, resumo e exclusão.
- **Repository:** executa a persistência e as consultas por meio do Spring Data JPA.
- **Domain e DTOs:** representam a entidade persistida e os contratos de entrada e saída da API.
- **Exception handler:** padroniza respostas para erros gerados como `ResponseStatusException`.

O front-end é uma SPA em React. O componente principal mantém o estado das tarefas e utiliza um serviço Axios para consumir a API.

## Estrutura de pastas

```text
juno-log/
├── back-end/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/mxi/juno_log/
│   │   │   │   ├── controller/   # Endpoints REST
│   │   │   │   ├── domain/       # Entidade Task e seus estados
│   │   │   │   ├── dto/          # Contratos de entrada, saída e erros
│   │   │   │   ├── exception/    # Tratamento global de exceções
│   │   │   │   ├── repository/   # Acesso ao MySQL via JPA
│   │   │   │   └── service/      # Regras da aplicação
│   │   │   └── resources/        # Configuração da aplicação
│   │   └── test/                  # Teste de inicialização do contexto
│   ├── mvnw / mvnw.cmd            # Maven Wrapper
│   └── pom.xml                    # Dependências do back-end
├── front-end/
│   ├── public/                    # Recursos estáticos
│   ├── src/
│   │   ├── services/              # Cliente HTTP Axios
│   │   ├── types/                 # Tipos TypeScript
│   │   ├── App.tsx                # Interface e operações de tarefas
│   │   └── main.tsx               # Ponto de entrada da SPA
│   ├── package.json               # Scripts e dependências do front-end
│   └── vite.config.ts             # Configuração do Vite
└── README.md
```

## Funcionalidades

- Cadastro de tarefas com título e descrição obrigatórios.
- Listagem de todas as tarefas.
- Consulta de uma tarefa por ID pela API.
- Filtro de tarefas por status (`PENDING` ou `DONE`) pela API.
- Marcação de uma tarefa como concluída, com registro da data de conclusão.
- Exclusão de tarefas.
- Consulta de tarefas ordenadas pela data de criação, da mais recente para a mais antiga, pela API.
- Resumo com total de tarefas pendentes, concluídas e cadastradas pela API.
- Painel no front-end com contadores calculados a partir da lista carregada.
- Identificação visual de tarefas pendentes e concluídas.
- Interface responsiva e estado vazio para listas sem tarefas.
- Validação dos campos no front-end e no back-end.

### Endpoints da API

Todos os endpoints abaixo partem de `http://localhost:8080`.

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `POST` | `/tasks` | Cria uma tarefa. |
| `GET` | `/tasks` | Lista tarefas; aceita o parâmetro opcional `status`. |
| `GET` | `/tasks/{id}` | Busca uma tarefa pelo ID. |
| `PATCH` | `/tasks/{id}/done` | Marca uma tarefa como concluída. |
| `DELETE` | `/tasks/{id}` | Exclui uma tarefa. |
| `GET` | `/tasks/tasksByCreated` | Lista tarefas por data de criação decrescente. |
| `GET` | `/tasks/summary` | Retorna as quantidades total, pendente e concluída. |

Exemplo de corpo para criação:

```json
{
  "title": "Revisar relatório",
  "description": "Conferir os dados antes do envio"
}
```

## Pré-requisitos

- Java 21
- MySQL em execução
- Node.js e npm compatíveis com as dependências declaradas no front-end

O repositório não informa uma versão mínima específica do Node.js ou do npm.

## Como executar o back-end

1. Crie o banco de dados no MySQL:

   ```sql
   CREATE DATABASE juno_log;
   ```

2. Copie o arquivo de configuração de exemplo:

   ```powershell
   Copy-Item back-end/src/main/resources/application.properties.example back-end/src/main/resources/application.properties
   ```

   Em Linux ou macOS:

   ```bash
   cp back-end/src/main/resources/application.properties.example back-end/src/main/resources/application.properties
   ```

3. Preencha usuário e senha do MySQL no novo `application.properties`.

4. Entre na pasta do back-end e inicie a aplicação:

   ```powershell
   cd back-end
   .\mvnw.cmd spring-boot:run
   ```

   Em Linux ou macOS:

   ```bash
   cd back-end
   ./mvnw spring-boot:run
   ```

A API utilizará a porta padrão do Spring Boot: `http://localhost:8080`. O projeto não define outra porta em seu arquivo de exemplo.

> **Observação:** o Maven Wrapper está versionado, mas não pôde ser iniciado durante a validação deste README no ambiente Windows utilizado. Caso o mesmo ocorra, instale o Maven e execute `mvn spring-boot:run` dentro de `back-end`.

## Como executar o front-end

Com o back-end ativo em `http://localhost:8080`, abra outro terminal:

```bash
cd front-end
npm install
npm run dev
```

Acesse `http://localhost:5173`. Essa origem está explicitamente autorizada pelo CORS do back-end, e o cliente Axios está configurado para chamar a API na porta `8080`.

Para gerar a versão de produção:

```bash
npm run build
npm run preview
```

## Configuração e variáveis de ambiente

Atualmente, o código **não lê variáveis de ambiente diretamente**. A conexão com o banco é definida em `back-end/src/main/resources/application.properties`, que não deve ser versionado. Use `application.properties.example` como modelo:

| Propriedade | Necessária | Finalidade | Valor de exemplo |
| --- | --- | --- | --- |
| `spring.datasource.url` | Sim | URL JDBC do MySQL | `jdbc:mysql://localhost:3306/juno_log?useSSL=false&serverTimezone=UTC` |
| `spring.datasource.username` | Sim | Usuário do MySQL | `SEU_USUARIO` |
| `spring.datasource.password` | Sim | Senha do MySQL | `SUA_SENHA` |
| `spring.jpa.hibernate.ddl-auto` | Não | Estratégia de atualização do esquema | `update` |
| `spring.jpa.show-sql` | Não | Exibição das consultas SQL no terminal | `true` |
| `spring.jpa.properties.hibernate.format_sql` | Não | Formatação das consultas exibidas | `true` |


