# Kafka example

The following example assumes that the kafka server is running locally on port `9092`.
If not, please replace `localhost:9092` with appropriate host and port of kafka server.

### Console Producer
```shell
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic shopping_list
```

### Console consumer
```shell
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic shopping_list
```
