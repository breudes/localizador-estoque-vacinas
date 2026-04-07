# Sistema de Cadastro e Login - Localizador Estoque Vacinas

## 🚀 Funcionalidades Implementadas

✅ **Cadastro de usuário** com CPF, nome, email e senha  
✅ **Login** usando CPF e senha  
✅ **Autenticação JWT** para sessões seguras  
✅ **Validação completa** de dados  
✅ **Tratamento de erros** padronizado  
✅ **Persistência MySQL** com JPA/Hibernate  

## 🏗️ Arquitetura

### Endpoints Criados:
- `POST /api/auth/cadastro` - Cadastrar novo usuário
- `POST /api/auth/login` - Fazer login
- `GET /api/test/all` - Endpoint público para teste
- `GET /api/test/user` - Endpoint protegido (requer autenticação)

## 📋 Pré-requisitos

1. **MySQL** rodando na porta 3306
2. **Java 21** 
3. **Maven** para build

## ⚙️ Configuração do Banco

O sistema está configurado para conectar no MySQL com:
- **Banco**: `estoque_vacinas` (criado automaticamente)
- **Usuário**: `root`
- **Senha**: `root`
- **URL**: `localhost:3306`

## 🧪 Como Testar

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

## 🛡️ Validações Implementadas

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

## 📝 Estrutura do Projeto

```
src/main/java/com/hackathon/estoque/
├── config/
│   ├── AuthEntryPointJwt.java      # Tratamento de erros JWT
│   ├── AuthTokenFilter.java        # Filtro de validação JWT
│   ├── JwtUtils.java               # Utilitários JWT
│   └── WebSecurityConfig.java      # Configuração Spring Security
├── controller/
│   ├── AuthController.java         # Endpoints de autenticação
│   └── TestController.java         # Endpoints de teste
├── exception/
│   ├── CpfJaCadastradoException.java
│   ├── EmailJaCadastradoException.java
│   └── GlobalExceptionHandler.java # Tratamento global de erros
├── model/
│   ├── dto/
│   │   ├── AuthResponseDTO.java    # Resposta com token
│   │   ├── CadastroRequestDTO.java # Dados para cadastro
│   │   └── LoginRequestDTO.java    # Dados para login
│   └── entity/
│       └── User.java               # Entidade usuário
├── repository/
│   └── UserRepository.java         # Repositório JPA
└── service/
    ├── AuthService.java            # Lógica de autenticação
    └── UserDetailsServiceImpl.java # Integração Spring Security
```

## 🎯 Próximos Passos

Agora você pode:
1. **Executar a aplicação** e testar os endpoints
2. **Integrar com frontend** usando os tokens JWT
3. **Adicionar mais funcionalidades** como:
   - Reset de senha
   - Perfis de usuário
   - Roles/permissões
   - Validação de CPF real

## 🔒 Segurança

- Senhas são criptografadas com BCrypt
- JWT com expiração de 24 horas
- Endpoints protegidos por autenticação
- Validação completa de entrada
- Tratamento seguro de erros

O sistema está completo e pronto para uso! 🎉
