# Order Service

O **Order Service** é um microserviço responsável pelo gerenciamento de pedidos dentro de uma arquitetura baseada em microsserviços, utilizando o padrão Saga Orquestrado. Ele faz parte de um sistema distribuído que integra outros serviços como Inventory, Payment, Product Validation e Orchestrator.

## Funcionalidades Principais
- Criação de pedidos
- Atualização de status de pedidos
- Integração com outros microserviços via eventos (Kafka)
- Participação em fluxos de Saga para garantir consistência transacional

## Tecnologias Utilizadas
- Java
- Spring Boot
- Gradle
- Kafka
- Docker
- MongoDB

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
- `POST /orders` - Criação de um novo pedido
- `GET /orders/{id}` - Consulta de um pedido por ID
- `GET /orders` - Listagem de pedidos

## Variáveis de Ambiente Importantes
- `MONGO_DB_URI` - URL do banco de dados (noSQL) mongoDB
- `KAFKA_BOOTSTRAP_SERVERS` - Endereço do broker Kafka

## Testes
Para rodar os testes automatizados:
```bash
./gradlew test
```

## Observações
- Este serviço depende de outros microserviços para o fluxo completo de pedidos.
- O gerenciamento de transações é feito via Saga Orquestrado, coordenado pelo Orchestrator Service.

