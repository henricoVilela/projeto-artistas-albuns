# 🎵 Sistema de Gerenciamento de Artistas e Álbuns

Sistema fullstack para gerenciamento de artistas musicais e seus álbuns, com upload de capas via MinIO (S3), autenticação JWT e notificações em tempo real.

---

## Meu dados

👤 **Nome:** Henrico Tadeu Ribeiro Alves Vilela  
📞 **Telefone:** (65) 99605-6020  
📧 **E-mail:** henricovilela@gmail.com 

---

## 📋 Tecnologias

### Backend
- Java 21
- Spring Boot 3.4
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL 16
- MinIO (Object Storage)
- Flyway (Migrations)
- Swagger/OpenAPI

### Frontend
- Angular 21
- TypeScript 5.9
- Tailwind CSS 4
- RxJS

### Infraestrutura
- Docker & Docker Compose
- Nginx

---

## 🚀 Quick Start com Docker

### 1. Clone o repositório
```bash
git clone <repository-url>
cd henricotadeuribeiroalvesvilela048202
```
### 2. Inicie os containers
```bash
docker compose up -d
```

### 3. Acesse a aplicação

| Serviço | URL | Credenciais |
|---------|-----|-------------|
| **Frontend** | http://localhost:4200 | admin / admin123 |
| **Backend API** | http://localhost:8080/api/v1 | - |
| **Swagger UI** | http://localhost:8080/swagger-ui.html | - |
| **MinIO Console** | http://localhost:9001 | minioadmin / minioadmin123 |

---


## 🐳 Comandos Docker

### Iniciar todos os serviços
```bash
docker compose up -d
```

### Ver logs
```bash
# Todos os serviços
docker compose logs -f

# Serviço específico
docker compose logs -f backend
docker compose logs -f frontend
```

### Parar serviços
```bash
docker compose down
```

### Parar e remover volumes (reset completo)
```bash
docker compose down -v
```

## 🔐 Autenticação

O sistema utiliza **JWT (JSON Web Token)** para autenticação:

- **Access Token**: Expira em 5 minutos
- **Refresh Token**: Expira em 24 horas

### Usuário padrão
```
Username: admin
Password: admin123
```

---

## 🧪 Testes

### Backend
```bash
cd backend
./mvnw test
```

### Cobertura dos Testes
- ArtistaService
- AlbumService
- AuthService
- JwtService
- RateLimitService

---

## 📊 Funcionalidades

- [x] Autenticação JWT com refresh token
- [x] CRUD completo de artistas
- [x] CRUD completo de álbuns
- [x] Upload de capas (MinIO)
- [x] Rate limiting (100 req/min)
- [x] WebSocket para notificações
- [x] Paginação e filtros
- [x] Soft delete
- [x] Swagger/OpenAPI
- [x] Frontend responsivo
- [x] Testes unitários

---

## 📄 Licença

Este projeto foi desenvolvido para fins de demonstração técnica.
