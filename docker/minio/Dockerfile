FROM minio/minio:latest

# Configurações do MinIO
ENV MINIO_ROOT_USER=minioadmin
ENV MINIO_ROOT_PASSWORD=minioadmin

# Expõe as portas do MinIO
EXPOSE 9000 9001

# Comando para iniciar o MinIO
CMD ["server", "/data", "--console-address", ":9001"]