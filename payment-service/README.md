# Payment Service

O **Payment Service** é um microserviço responsável pelo processamento de pagamentos dentro de uma arquitetura baseada em microsserviços, utilizando o padrão Saga Orquestrado. Ele integra-se com outros serviços para garantir a consistência e o fluxo correto das transações de pagamento.

## Funcionalidades Principais
- Processamento de pagamentos de pedidos
- Atualização do status de pagamento
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
- `POST /payments` - Processamento de um novo pagamento
- `GET /payments/{id}` - Consulta de pagamento por ID
- `GET /payments` - Listagem de pagamentos

## Variáveis de Ambiente Importantes
- `SPRING_DATASOURCE_URL` - URL do banco de dados PostgreSQL
- `KAFKA_BOOTSTRAP_SERVERS` - Endereço do broker Kafka

## Testes
Para rodar os testes automatizados:
```bash
./gradlew test
```

## Observações
- Este serviço depende de outros microserviços para o fluxo completo de pedidos e pagamentos.
- O gerenciamento de transações é feito via Saga Orquestrado, coordenado pelo Orchestrator Service.

