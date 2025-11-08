FROM postgres:16.1-alpine

# Configurações do PostgreSQL
ENV POSTGRES_DB=aiimage
ENV POSTGRES_USER=aiimage
ENV POSTGRES_PASSWORD=aiimage

# Scripts de inicialização
COPY ./init.sql /docker-entrypoint-initdb.d/

# Expõe a porta padrão do PostgreSQL
EXPOSE 5432