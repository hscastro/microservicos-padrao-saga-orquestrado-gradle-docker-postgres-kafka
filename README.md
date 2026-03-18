# 🧩 Arquitetura de Microsserviços com Saga Orquestrada

Esse projeto demonstrando a implementação de uma **Arquitetura de Microsserviços utilizando o padrão Saga Orquestrado** para garantir **consistência de dados em transações distribuídas**.

A comunicação entre os serviços é realizada de forma **assíncrona através do Apache Kafka**, com persistência de dados no **PostgreSQL** e **MongoDB**. Toda a infraestrutura é executada em **containers Docker**.

---

# 📌 Arquitetura

Este projeto implementa o padrão **Saga Orquestrada**, onde um **serviço orquestrador central** controla o fluxo da transação distribuída.

Fluxo simplificado da saga:

```
Order Created
     │
     ▼
Product Validation Service
     │
     ▼
Inventory Service
     │
     ▼
Payment Service
     │
     ▼
Saga Completed

```
  <br>
   <h2> Arqutetura dos Microserviços </h2>
<br>
  <br>

<img width="2481" height="534" alt="mermaid-diagram_arquitetura" src="https://github.com/user-attachments/assets/d8f11235-d30d-4acf-b4e3-afe4a9c5d6b5" />

<br>
  <br>
  
<img width="811" height="391" alt="arquitetura_microservices_3" src="https://github.com/user-attachments/assets/35e19ebc-ba49-4f36-8699-3f9a881d4d06" />

<br>
  <br>

---

Caso algum serviço falhe, o **Orchestrator Service** executa as **ações compensatórias**.

---

# 🧱 Microsserviços

O sistema é composto pelos seguintes serviços:

### 1️⃣ order-service

Responsável pela criação e gerenciamento de pedidos.

Funções:

* Criar pedidos
* Publicar eventos de criação no Kafka
* Atualizar status do pedido

Banco utilizado:

* MongoDB

---

### 2️⃣ product-validation-service

Responsável por validar os produtos do pedido.

Funções:

* Validar existência do produto
* Validar disponibilidade lógica

Banco utilizado:

* PostgreSQL

---

### 3️⃣ inventory-service

Responsável pelo controle de estoque.

Funções:

* Reservar estoque
* Liberar estoque em caso de rollback
* Atualizar quantidade disponível

Banco utilizado:

* PostgreSQL

---

### 4️⃣ payment-service

Responsável pelo processamento de pagamentos.

Funções:

* Processar pagamento
* Cancelar pagamento (ação compensatória)

Banco utilizado:

* PostgreSQL

---

### 5️⃣ orchestrator-service

Responsável por **orquestrar toda a saga**.

Funções:

* Consumir eventos dos microsserviços
* Controlar o fluxo da transação
* Executar compensações em caso de falha

---

# ⚙️ Tecnologias Utilizadas

| Tecnologia     | Versão | Descrição                           |
| -------------- | ------ | ----------------------------------- |
| Java           | 21     | Linguagem principal                 |
| Spring Boot    | 3.x    | Framework de desenvolvimento        |
| Apache Kafka   | -      | Mensageria e comunicação assíncrona |
| PostgreSQL     | -      | Banco relacional                    |
| MongoDB        | -      | Banco NoSQL                         |
| Docker         | -      | Containerização                     |
| Docker Compose | -      | Orquestração de containers          |

---

# 🧠 Padrão Saga Orquestrado

O padrão **Saga** é utilizado para gerenciar **transações distribuídas entre microsserviços**.

Existem dois modelos:

* **Choreography** → serviços se comunicam via eventos
* **Orchestration** → um **orquestrador central controla o fluxo**

Este projeto implementa **Orquestração**.

Benefícios:

✔ Alta escalabilidade
✔ Baixo acoplamento
✔ Tolerância a falhas
✔ Consistência eventual

---

# 📡 Comunicação entre serviços

A comunicação ocorre via **Apache Kafka** utilizando **event-driven architecture**.

Exemplo de eventos:

```
ORDER_CREATED_EVENT
PRODUCT_VALIDATED_EVENT
INVENTORY_RESERVED_EVENT
PAYMENT_PROCESSED_EVENT
SAGA_COMPLETED_EVENT
SAGA_ROLLBACK_EVENT
```

---

# 🗄️ Bancos de Dados

### PostgreSQL

Utilizado nos serviços:

* product-validation-service
* inventory-service
* payment-service

Responsável por dados **transacionais e relacionais**.

---

### MongoDB

Utilizado nos serviços:

* order-service


Responsável por:

* dados flexíveis
* dados do pedido do cliente

---

# 🐳 Executando o Projeto com Docker

### Pré-requisitos

* Docker
* Docker Compose
* Java 21
* Maven ou Gradle

---

### Subir toda a infraestrutura

```bash
docker-compose up -d
```

Isso irá iniciar:

* Kafka
* redpanda-console
* PostgreSQL
* MongoDB
* Todos os microsserviços

---

### Parar containers

```bash
docker-compose down
```

---

# 📂 Estrutura do Projeto

```
microservices-saga-orchestrated

├── order-service
├── inventory-service
├── product-validation-service
├── payment-service
├── orchestrator-service
│
├── docker-compose.yml
└── README.md
```

---

# 🔁 Fluxo da Saga

1. Cliente cria pedido
2. `order-service` publica evento **ORDER_CREATED**
3. `orchestrator-service` inicia a saga
4. `product-validation-service` valida produtos
5. `inventory-service` reserva estoque
6. `payment-service` processa pagamento
7. Saga finalizada com sucesso

Caso ocorra falha:

* Orchestrator executa **ações compensatórias**
* Estoque liberado
* Pagamento cancelado
* Pedido marcado como **FAILED**

---

# 📊 Benefícios da Arquitetura

* Escalabilidade independente dos serviços
* Alta resiliência
* Comunicação desacoplada
* Melhor gerenciamento de falhas
* Ideal para **sistemas de e-commerce e alta demanda**

---

# 🚀 Melhorias Futuras

* Implementação de **API Gateway**
* Implementação de **Service Discovery**
* Observabilidade com **Prometheus + Grafana**
* Distributed Tracing com **OpenTelemetry**
* Implementação de **Resilience4j**

---

# 👨‍💻 Autor

Projeto desenvolvido para estudo de **Arquitetura de Microsserviços, Event Driven Architecture e Saga Pattern** utilizando **Spring Boot e Apache Kafka**.



