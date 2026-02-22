# Spring Cloud Microservice Architecture

Config Server, Eureka, API Gateway, User Service, Order Service.

## Prerequisites

- JDK 17+
- Maven 3.6+

## Build

From the project root:

```bash
mvn clean install
```

## Getting Started

### Option 1: Run with Maven (local)

Start services **in this order** (each in its own terminal):

1. **Eureka Server** (required first)
   ```bash
   cd eureka-server && mvn spring-boot:run
   ```
   Dashboard: http://localhost:8761

2. **User Service**
   ```bash
   cd user-service && mvn spring-boot:run
   ```

3. **Order Service**
   ```bash
   cd order-service && mvn spring-boot:run
   ```

4. **API Gateway**
   ```bash
   cd api-gateway && mvn spring-boot:run
   ```

*(Optional)* **Config Server** — only needed if you use central config. Run first if used:
   ```bash
   cd config-server && mvn spring-boot:run
   ```
   Uses `./config-repo` by default; ensure that folder exists (see below).

**Ports:** Eureka 8761 · Gateway 8080 · User 8081 · Order 8082 · Config 8888

**Try it:**  
- Gateway: http://localhost:8080  
- Users: http://localhost:8080/api/users/  
- Orders: http://localhost:8080/api/orders/  
- Eureka: http://localhost:8761  

---

### Testing: API Gateway → Order Service → User Service (service discovery)

With all services running, you can test the full chain: **client → API Gateway → Order Service → User Service** (order-service uses **service discovery** via Eureka and **Feign** to call user-service).

1. **Create a user** (via gateway):
   ```bash
   curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d '{"name":"Jane","email":"jane@example.com"}'
   ```
   Note the returned `id` (e.g. `1`).

2. **Call order-service via gateway; order-service then calls user-service** (replace `1` with the user id):
   ```bash
   curl http://localhost:8080/api/orders/users/1
   ```
   This goes: Gateway → Order Service → (Feign/Eureka) → User Service → user data returned.

3. **Other useful calls via gateway:**
   - Order service version: `curl http://localhost:8080/api/orders/version`
   - User service version: `curl http://localhost:8080/api/users/version`
   - List users: `curl http://localhost:8080/api/users`
   - List orders: `curl http://localhost:8080/api/orders`

---

### Option 2: Run with Docker Compose

From the project root:

```bash
docker-compose up -d --build
```

Wait **1–2 minutes** for Spring Boot to start, then open http://localhost:8080 and http://localhost:8761.

Or use the helper script (starts containers, waits, then shows status):

```bash
chmod +x scripts/start-local.sh
./scripts/start-local.sh
```

---

### Connection refused? (ERR_CONNECTION_REFUSED)

Nothing is listening on the port. Do this:

1. **If using Docker:** Start the stack and confirm containers stay up:
   ```bash
   docker-compose up -d --build
   sleep 120
   docker-compose ps -a
   ```
   Every service should show **Up**. If any show **Exited (1)**, check logs:
   ```bash
   docker-compose logs config-server
   docker-compose logs eureka-server
   docker-compose logs api-gateway
   ```
2. **If using Maven:** You must start each service in a separate terminal (Eureka first, then user-service, order-service, api-gateway). If you didn’t start the API Gateway, nothing will be on port 8080.

---

## config-repo

The **config-repo** directory is the Config Server’s local “git” backend (`file:./config-repo`). It was empty; it’s now populated with optional per-service YAML under:

- `config-repo/order-service/application.yml`
- `config-repo/user-service/application.yml`
- `config-repo/api-gateway/application.yml`
- `config-repo/eureka-server/application.yml`

Services currently use their **in-module** `src/main/resources/application.yml`; they do not need the config server to run. You can add overrides in config-repo when you want central config.
