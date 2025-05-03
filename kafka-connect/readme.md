### Run docker compose with build image

```bash
docker compose -p kafka up --build -d
```

### Kafka connect

Check supported plugins:

```bash
curl http://localhost:8083/connector-plugins | jq
```

Register mysql and elasticsearch connectors:

```
curl -X POST http://localhost:8083/connectors \
     -H "Content-Type: application/json" \
     -d @register-mysql.json
     
curl -X POST http://localhost:8083/connectors \
        -H "Content-Type: application/json" \
        -d @register-elasticsearch.json
```

