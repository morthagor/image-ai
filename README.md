<!-- prettier-ignore -->
# AI Image Evidence — AI Image Evidence Platform

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

![Status](https://img.shields.io/badge/status-active-brightgreen)

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),![Version](https://img.shields.io/badge/version-v.6.6.6.0-blue)

e este projeto adere ao [Semantic Versioning](https://semver.org/spec/v2.0.0.html).![Java](https://img.shields.io/badge/java-21-teal)

![Spring Boot](https://img.shields.io/badge/spring--boot-3.4.11-brightgreen)

## [6.6.6.1] - 2025-11-08 01:54:37 GMT-3

Versão: **v.6.6.6.1**

### Atualizado

- Spring Boot atualizado para 3.4.11 Visão geral

- Spring Cloud atualizado para 2023.0.4 (compatibilidade com Spring Boot 3.4.11)-----------

- Testcontainers atualizado para 1.19.3AI Image Evidence é uma plataforma privada para ingestão, validação e armazenamento de evidências fotográficas de ordens de serviço (instalação de fibra óptica). Técnicos enviam um conjunto padronizado de fotos para comprovar chegada e conclusão do serviço. O sistema:

- PostgreSQL downgrade para 15.14 (melhor compatibilidade com Flyway)

- Nginx atualizado para 1.28.0- Recebe imagens (base64) e metadados EXIF.

- SonarQube atualizado para 2025.5.0-developer- Extrai e normaliza metadados (geolocalização, timestamp, device info).

- Analisa qualidade e conteúdo das imagens com LLMs (Dify/Gemini) para validação automatizada.

### Build Details- Armazena metadados no PostgreSQL e imagens no MinIO com política de retenção configurável (p.ex. 5 anos — LGPD).

- Build Timestamp: 2025-11-08 01:54:37 GMT-3

- Java Version: 21.0.9Principais benefícios

- Maven Version: 3.9.5---------------------

- JaCoCo Version: 0.8.11- Automação da validação: redução de fraudes e retrabalho.

- Arquitetura desacoplada: fácil escala e substituição de componentes (LLMs, storage).

### Validações- Conformidade com LGPD: retenção e controle de acesso.

- ✅ Testes unitários executados com sucesso

- ✅ Testes de integração executados com sucessoArquitetura (resumida)

- ✅ Cobertura de código mantida acima de 80%----------------------

- ✅ Build completo sem warnings```
Client Apps (Mobile) --> API Gateway / Nginx --> Spring Boot API
									  |-> Redis (cache)
									  |-> Postgres (metadados)
									  |-> MinIO (objetos/imagens)
									  |-> LLM Gateway (Dify/Gemini) [async]
``` 

Tecnologias
-----------
- Java 21 (Temurin)
- Spring Boot 3.4.11
- PostgreSQL 15.14 (metadados)
- MinIO 8.5.7 (object storage para imagens)
- Redis (cache)
- Nginx 1.28.0 (proxy/reverse)
- SonarQube 2025.5.0 (qualidade de código)
- Docker / Docker Compose
- Testcontainers, JUnit 5, JaCoCo
- Flyway (migrações de BD)

Endpoints principais (esqueleto)
-------------------------------
- POST /api/v1/service-orders — criar ordem de serviço (gera protocolo)
- POST /api/v1/service-orders/{protocol}/arrival-image — upload imagem de chegada
- POST /api/v1/service-orders/{protocol}/completion-images — upload das 3 imagens de conclusão
- GET /api/v1/service-orders/{protocol} — consultar ordem
- GET /api/v1/service-orders/{protocol}/evidences — listar evidências

Instalação e execução (desenvolvimento)
--------------------------------------
1. Copie e ajuste o arquivo `.env` na raiz com suas credenciais/local settings.

```bash
# Subir serviços (quando for usar docker-compose):
docker-compose up -d

# Build da aplicação e execução local:
./mvnw clean package
java -jar target/aiimage-1.0-SNAPSHOT.jar
```

2. Documentação da API (Swagger/OpenAPI):

- Swagger UI: `http://localhost:8080/swagger-ui.html` (após subir a aplicação)
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Desenvolvimento e qualidade
---------------------------
- Testes unitários e de integração: JUnit 5 + Testcontainers
- Cobertura: JaCoCo (meta inicial 80%)
- Code quality: SonarQube/SonarCloud (pipeline GitHub Actions configurada)

Segurança & Compliance
----------------------
- Armazenamento: imagens em MinIO com políticas de retenção configuráveis.
- Criptografia: TLS em trânsito (configurar Nginx em produção), chaves e credenciais em secrets.
- Controle de acesso: endpoints protegidos (a implementar — JWT recomendado).

Fluxo de branches
-----------------
- `canary` — desenvolvimento contínuo; aqui fazemos commits e testes iniciais.
- `homolog` — ambiente de homologação / QA.
- `main` — produção; apenas depois de homologação e PRs aprovados.

Contribuição
------------
- Abra Issues para bugs/funcionalidades.
- Submeta PRs na branch `canary` e siga o fluxo: canary -> homolog -> main.

Contato
-------
- time-tech@yourcompany.local

Licença
-------
- Repositório privado — uso interno conforme políticas da organização.
