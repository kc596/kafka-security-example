# Authentication in Kafka

> The following example assumed that the kafka server is running locally on port `9092`.
If not, please replace `localhost:9092` with appropriate host and port of kafka server.

Add the following configs in `server.properties` of your Kafka server:

```properties
# Using PLAINTEXT mechanism provides no security, and should be avoided.
# The configs below are just for demo, and analyse your security requirements before using.

listeners=SASL_PLAINTEXT://localhost:9092,PLAINTEXT://localhost:9094
listener.security.protocol.map=SASL_PLAINTEXT:SASL_PLAINTEXT,PLAINTEXT:PLAINTEXT
inter.broker.listener.name=PLAINTEXT
sasl.mechanism.inter.broker.protocol=PLAIN
sasl.enabled.mechanisms=PLAIN
```

### Console Producer
```shell
kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic shopping_list \
    --producer.config client/producer.properties
```

### Console consumer
```shell
kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic shopping_list \
  --consumer.config client/consumer.properties
```
