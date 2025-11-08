# aiimage

Versão: v.6.6.6.0

Descrição
--------
AI Image Processing Application — sistema privado para recebimento de evidências fotográficas de ordens de serviço (instalação de fibra óptica). Técnicos enviam 4 imagens por ordem: chegada ao local, fachada com número, crachá do técnico e imagem do roteador. O sistema extrai metadados (EXIF: geolocalização, timestamp, device info), armazena metadados no PostgreSQL e imagens no MinIO com retenção por 5 anos (conforme LGPD).

Tecnologias
-----------
- Java 21 (Temurin)
- Spring Boot (3.2.x)
- PostgreSQL 16 (via Docker)
- MinIO (object storage)
- Redis (cache)
- Nginx (reverse proxy)
- SonarQube (code quality)
- Docker / Docker Compose
- Testcontainers, JUnit 5
- Flyway (migrações)
- JaCoCo (coverage)

Resumo de arquitetura
---------------------
- API REST em Spring Boot para receber uploads de imagens (base64 suportado), criar protocolo de serviço e retornar status.
- Metadados salvos em PostgreSQL; imagens salvas no MinIO em buckets organizados por data/ordem.
- Processamento assíncrono para análises LLM (Dify/Gemini) para validação de qualidade e conteúdo.

Como rodar (desenvolvimento)
----------------------------
1. Ajuste o arquivo `.env` na raiz com as variáveis de ambiente necessárias.
2. Quando for usar os containers locais:

```bash
docker-compose up -d
```

3. Build da aplicação localmente:

```bash
./mvnw clean package
java -jar target/aiimage-1.0-SNAPSHOT.jar
```

Contribuição
------------
- Abra issues e PRs no GitHub
- Mantenha testes unitários e integração atualizados

Licença
-------
- Repositório privado — políticas internas aplicam-se.
