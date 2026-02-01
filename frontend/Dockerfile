FROM node:22-alpine AS builder

WORKDIR /app

# Argumento para URL da API
ARG API_URL=http://localhost:8080/api/v1

# Copiar arquivos de dependências
COPY package*.json ./

# Instalar dependências
RUN npm ci

# Copiar código fonte
COPY . .

# Configurar environment de produção com API_URL
# RUN echo "export const environment = { production: true, apiUrl: '${API_URL}' };" > src/environments/environment.prod.ts

# Build de produção
RUN npm run build-prod

# -----------------------------------------------------------------------------
# Stage 2: Runtime (Nginx)
# -----------------------------------------------------------------------------
FROM nginx:alpine

# Remover configuração padrão
RUN rm -rf /usr/share/nginx/html/*

# Copiar arquivos buildados
COPY --from=builder /app/dist/front-end/browser /usr/share/nginx/html

# Copiar configuração customizada do Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expor porta
EXPOSE 80

# Healthcheck
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://localhost:80 || exit 1

# Comando padrão
CMD ["nginx", "-g", "daemon off;"]
