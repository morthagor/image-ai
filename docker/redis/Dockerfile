FROM redis:8.2.3-alpine

# Configuração do Redis
COPY redis.conf /usr/local/etc/redis/redis.conf

# Expõe a porta padrão do Redis
EXPOSE 6379

CMD ["redis-server", "/usr/local/etc/redis/redis.conf"]