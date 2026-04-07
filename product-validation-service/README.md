# Product Validation Service

O **Product Validation Service** é um microserviço responsável pela validação de produtos em processos de pedidos, atuando como parte de uma arquitetura baseada em microsserviços com padrão Saga Orquestrado. Ele garante que os produtos solicitados estejam disponíveis e atendam aos critérios de validação definidos.

## Funcionalidades Principais
- Validação de produtos em pedidos
- Verificação de disponibilidade e regras de negócio
- Integração com outros microserviços via eventos (Kafka)
- Participação em fluxos de Saga para garantir consistência transacional

## Tecnologias Utilizadas
- Java
- Spring Boot
- Gradle
- Kafka
- Docker
- PostgreSQL

## Estrutura do Projeto
- `src/main/java/` - Código-fonte principal do serviço
- `src/main/resources/` - Arquivos de configuração (ex: `application.yml`)
- `build.gradle` - Configuração do build com Gradle
- `Dockerfile` - Containerização do serviço

## Como Executar Localmente
1. Certifique-se de que o Docker e o Docker Compose estão instalados.
2. Execute o comando abaixo na raiz do projeto para subir todos os serviços necessários:
   ```bash
   docker-compose up --build
   ```
3. O serviço estará disponível na porta configurada (verifique o `application.yml`).

## Endpoints Principais
- `POST /validate-product` - Validação de um produto
- `GET /products/{id}` - Consulta de produto por ID

## Variáveis de Ambiente Importantes
- `datasource_url` - URL do banco de dados PostgreSQL
- `KAFKA_BOOTSTRAP_SERVERS` - Endereço do broker Kafka

## Testes
Para rodar os testes automatizados:
```bash
./gradlew test
```

## Observações
- Este serviço depende de outros microserviços para o fluxo completo de pedidos.
- O gerenciamento de transações é feito via Saga Orquestrado, coordenado pelo Orchestrator Service.

