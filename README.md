# Sistema de Gestão e Localização de Estoque de Vacinas

Este repositório contém o código-fonte de uma aplicação monolítica desenvolvida em **Java** com **Spring Boot**. O objetivo do sistema é permitir a gestão eficiente de estoques de vacinas em estabelecimentos de saúde, além de disponibilizar uma plataforma de consulta para cidadãos localizarem vacinas disponíveis.

## Sobre o Projeto

O sistema foi desenhado para atender dois perfis de usuários principais:
- **Cidadão (USER):** Pode consultar a localização e disponibilidade de vacinas.
- **Gestor (ADMIN):** Responsável pela gestão do estabelecimento de saúde, controle de estoque e registros de vacinas.

## Tecnologias Utilizadas

- **Linguagem:** Java
- **Framework:** Spring Boot 
- **Arquitetura:** Monolito

## Pré-requisitos

1. **MySQL** rodando na porta 3306
2. **Java 21** 
3. **Maven** para build

## Configuração do Banco

O sistema está configurado para conectar no MySQL com:
- **Banco**: `estoque_vacinas` (criado automaticamente)
- **Usuário**: `root`
- **Senha**: `root`
- **URL**: `localhost:3306`

## Configurações de variáveis de ambiente

- MYSQL_DATABASE: nome do banco de dados
- MYSQL_USER: nome de usuário do banco de dados
- MYSQL_PASSWORD: senha do banco de dados
- SECRET_TOKEN: token de segurança para a autenticação via JWT

## Funcionalidades Implementadas

- **Cadastro de usuário** com CPF, nome, email e senha  
- **Login** usando CPF e senha  
- **Autenticação JWT** para sessões seguras  
- **Validação completa** de dados  
- **Tratamento de erros** padronizado  
- **Persistência MySQL** com JPA/Hibernate  

## Segurança

- Senhas são criptografadas com BCrypt
- JWT com expiração de 24 horas
- Endpoints protegidos por autenticação
- Validação completa de entrada
- Tratamento seguro de erros
  
## Endpoints

### Autenticação e Segurança
- `POST /api/auth/cadastro` - Cadastrar novo usuário
- `POST /api/auth/login` - Fazer login
- `GET /api/test/all` - Endpoint público para teste
- `GET /api/test/user` - Endpoint protegido (requer autenticação)

### Estabelecimentos de Saúde (/api/health-facilities)
- `POST /api/health-facilities` - Cadastrar estabelecimento
- `DELETE /api/health-facilities/delete/{cnes}` - Remover estabelecimento pelo CNES
- `PUT /api/health-facilities/update` - Atualizar dados do estabelecimento
- `GET /api/health-facilities` - Listar todos os estabelecimentos
- `GET /api/health-facilities/cnes/{cnes}` - Buscar estabelecimento por CNES
- `GET /api/health-facilities/name/{name}` - Buscar estabelecimentos por nome
- `GET /api/health-facilities/cep/{cep}` - Buscar estabelecimentos por CEP

### Inventário de Vacinas (/api/inventories)

- `POST /api/inventories` - Criar novo inventário
- `PUT /api/inventories/{id}` - Atualizar inventário
- `DELETE /api/inventories/{id}`- Remover inventário
- `GET /api/inventories/{id}` - Buscar inventário por ID
- `GET /api/inventories` - Listar todos os inventários
- `GET /api/inventories/health-facility/{id}` - Buscar inventários de um estabelecimento
- `GET /api/inventories/vaccine/{id}` - Buscar inventários de uma vacina específica
- `GET /api/inventories/health-facility/{hfId}/vaccine/{vId}` - Buscar inventários por estabelecimento e vacina
- `GET /api/inventories/expired` - Listar inventários vencidos
- `GET /api/inventories/low-stock/{threshold}` - Listar inventários com baixo estoque (conforme limite)

### Vacinas (/api/vaccines)

- `POST /api/vaccines` - Cadastrar vacina
- `GET /api/vaccines` - Listar todas as vacinas
- `GET /api/vaccines/{id}` - Buscar vacina por ID
- `GET /api/vaccines/name/{name}` - Buscar vacina por nome
- `GET /api/vaccines/disease/{disease}` - Buscar vacinas por doença que previne
- `GET /api/vaccines/life-stage/{lifeStage}` - Buscar vacinas por estágio da vida
- `GET /api/vaccines/age-range` - Buscar vacinas por faixa etária (via body)
- `PUT /api/vaccines/{id}` - Atualizar vacina
- `DELETE,/api/vaccines/{id}` - Remover vacina

## Como Testar

### 1. Cadastrar Usuário
```bash
curl -X POST http://localhost:8080/api/auth/cadastro \
  -H "Content-Type: application/json" \
  -d '{
    "cpf": "12345678901",
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "123456"
  }'
```

**Resposta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "userId": 1,
  "nome": "João Silva",
  "cpf": "12345678901"
}
```

### 2. Fazer Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "cpf": "12345678901",
    "senha": "123456"
  }'
```

### 3. Acessar Endpoint Protegido
```bash
curl -X GET http://localhost:8080/api/test/user \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

## Coleção no Postman
Deixamos o link da coleção da API com as requisições e exemplos de cada rota implementada: https://www.postman.com/science-geologist-36836819-8391627/brenda-alexandra-souza-s-workspace

## Validações Implementadas

### CPF:
- Obrigatório
- Exatamente 11 dígitos numéricos
- Único no sistema

### Senha:
- Mínimo 6 caracteres
- Criptografada com BCrypt

### Email:
- Formato válido
- Único no sistema

### Nome:
- Entre 2 e 100 caracteres

## 🔧 Como Executar

1. **Instalar Maven** (se não tiver):
```bash
# macOS com Homebrew
brew install maven
```

2. **Instalar dependências**:
```bash
mvn clean install
```

3. **Executar aplicação**:
```bash
mvn spring-boot:run
```

4. **Aplicação estará disponível em**: `http://localhost:8080`

## Estrutura do Projeto

Parte das classes foram ocultadas nesta estrutura a seguir dado o alto volume de classes.

```
src/main/java/com/hackathon/estoque/
├── controller/
│   ├── auth/
│     └── AuthController.java
│   ├── authroles/
│     └── AdminController.java
│     └── UserController.java
│   └── HealthFacilityController.java 
│   └── InventoryController.java       
│   └── VaccineController.java        
├── dto/
├── exception/
├── mapper/
├── model/
├── repository/
├── security/
├── service/
└── EstoqueApplication.java 
```

## Autoras:
* Brenda Alexandra de Souza Silva - RM365765
* Gabriela de Sá - RM365630

Licença: 
* MIT License
