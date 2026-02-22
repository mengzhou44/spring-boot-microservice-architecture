#!/usr/bin/env bash
# Start all services with Docker Compose for local development.
# Run from project root: ./scripts/start-local.sh

set -e
cd "$(dirname "$0")/.."

echo "Building and starting containers..."
docker-compose up -d --build

echo "Waiting for API Gateway to respond (up to 120 seconds)..."
MAX_WAIT=120
URL="http://localhost:8080/api/users/version"
elapsed=0
while [ $elapsed -lt $MAX_WAIT ]; do
  if curl -sf -o /dev/null "$URL" 2>/dev/null; then
    echo "Gateway is up."
    break
  fi
  sleep 5
  elapsed=$((elapsed + 5))
done
if [ $elapsed -ge $MAX_WAIT ]; then
  echo "Timeout after 120 seconds. Gateway did not respond."
  echo "Run: docker-compose ps -a    (see if any container Exited)"
  echo "Run: docker-compose logs config-server eureka-server api-gateway"
fi

echo ""
echo "Container status:"
docker-compose ps -a

echo ""
echo "If all show 'Up', try:"
echo "  Eureka:  http://localhost:8761"
echo "  Gateway: http://localhost:8080"
echo "  Users:   curl http://localhost:8080/api/users"
echo "  Orders:  curl http://localhost:8080/api/orders"
echo ""
echo "If any show 'Exited', run: docker-compose logs <service-name>"
