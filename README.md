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
