# Authorization in Kafka

> The following example assumed that the kafka server is running locally on port `9092`.
If not, please replace `localhost:9092` with appropriate host and port of kafka server.

Add the following configs in `server.properties` of your Kafka server if using Zookeeper:

```properties
############################# Authentication in Kafka ################################
# SASL/PLAIN should be used only with SSL as transport layer to ensure that clear passwords are
# not transmitted on the wire without encryption.
# The configs below are just for demo, and analyse your security requirements before using.
listeners=SASL_PLAINTEXT://localhost:9092,PLAINTEXT://localhost:9094
listener.security.protocol.map=SASL_PLAINTEXT:SASL_PLAINTEXT,PLAINTEXT:PLAINTEXT
inter.broker.listener.name=PLAINTEXT
sasl.mechanism.inter.broker.protocol=PLAIN
sasl.enabled.mechanisms=PLAIN
listener.name.sasl_plaintext.plain.sasl.jaas.config= \
  org.apache.kafka.common.security.plain.PlainLoginModule required \
    username="admin" \
    password="admin-secret" \
    user_admin="admin-secret" \
    user_Alice="alice-secret" \
    user_Emily="emily-secret" \
    user_Bob="bob-secret";

############################# Authorization in Kafka #################################
authorizer.class.name=kafka.security.authorizer.AclAuthorizer
super.users=User:ANONYMOUS;User:admin
allow.everyone.if.no.acl.found=false
acl.default.denied=true
```

### Console Producer
`User:Bob` is used for producer client.

```shell
kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic shopping_list \
    --producer.config client/producer.properties
```

### Console consumer
`User:Alice` is used for consumer client.

```shell
kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic shopping_list \
  --consumer.config client/consumer.properties
```

### Adding ACLs

1. Add permission for `User:Bob` to write to `shopping_cart` topic.
   If the topic shopping_list doesn't exist, make sure to provide create permission as well.
    ```shell
    kafka-acls.sh \
        --command.config client/acladmin.properties \
        --bootstrap-server localhost:9092 \
        --add \
        --allow-principal User:Bob \
        --operation Write \
        --operation Create \
        --topic shopping_cart
    ```

2. Add permission for `User:Alice` to read from `shopping_cart` topic.
    ```shell
    kafka-acls.sh \
        --command.config client/acladmin.properties \
        --bootstrap-server localhost:9092 \
        --add \
        --allow-principal User:Alice \
        --operation Read \
        --topic shopping_cart
    ```

3. Add permission for `User:Alice` to access the consumer group used to read from the topic.
   ```shell
    kafka-acls.sh \
        --command.config client/acladmin.properties \
        --bootstrap-server localhost:9092 \
        --add \
        --allow-principal User:Alice \
        --group shopping-consumer-group \
        --operation Read
    ```

4. List ACLs
   ```shell
   bin/kafka-acls.sh \
       --command-config client/acladmin.properties \
       --bootstrap-server localhost:9092 \
       --list \
       --topic shopping_cart
   ```
