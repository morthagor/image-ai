<!-- prettier-ignore -->
# AI Image Evidence — AI Image Evidence Platform

![Status](https://img.shields.io/badge/status-active-brightgreen)
![Version](https://img.shields.io/badge/version-v.6.6.6.0-blue)
![Java](https://img.shields.io/badge/java-21-teal)
![Spring Boot](https://img.shields.io/badge/spring--boot-3.2.x-brightgreen)

Versão: **v.6.6.6.0**

Visão geral
-----------
AI Image Evidence é uma plataforma privada para ingestão, validação e armazenamento de evidências fotográficas de ordens de serviço (instalação de fibra óptica). Técnicos enviam um conjunto padronizado de fotos para comprovar chegada e conclusão do serviço. O sistema:

- Recebe imagens (base64) e metadados EXIF.
- Extrai e normaliza metadados (geolocalização, timestamp, device info).
- Analisa qualidade e conteúdo das imagens com LLMs (Dify/Gemini) para validação automatizada.
- Armazena metadados no PostgreSQL e imagens no MinIO com política de retenção configurável (p.ex. 5 anos — LGPD).

Principais benefícios
---------------------
- Automação da validação: redução de fraudes e retrabalho.
- Arquitetura desacoplada: fácil escala e substituição de componentes (LLMs, storage).
- Conformidade com LGPD: retenção e controle de acesso.

Arquitetura (resumida)
----------------------
```
Client Apps (Mobile) --> API Gateway / Nginx --> Spring Boot API
									  |-> Redis (cache)
									  |-> Postgres (metadados)
									  |-> MinIO (objetos/imagens)
									  |-> LLM Gateway (Dify/Gemini) [async]
``` 

Tecnologias
-----------
- Java 21 (Temurin)
- Spring Boot 3.2.x
- PostgreSQL 16 (metadados)
- MinIO (object storage para imagens)
- Redis (cache)
- Nginx (proxy/reverse)
- SonarQube (qualidade de código)
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
