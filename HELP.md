# Getting Started

## Some useful kafka CLI commands

### Start kafka and creat topics

1. zookeeper-server-start.sh config/zookeeper.properties
2. kafka-server-start.sh config/server.properties
3. kafka-topics.sh --bootstrap-server localhost:9092 --list
4. kafka-topics.sh --bootstrap-server localhost:9092 --create --topic kafka-demo
5. kafka-topics.sh --bootstrap-server localhost:9092 --create --topic kafka-demo --partitions 3
6. kafka-topics.sh --bootstrap-server localhost:9092 --create --topic kafka-demo --partitions 3 --replication-factor 1
7. kafka-topics.sh --bootstrap-server localhost:9092 --describe
8. kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic kafka-demo
9. kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic kafka-demo

## Create Producers

#### Note :  If we don't have the specified topic the kafka will produce it for us automatically

1. kafka-console-producer.sh --bootstrap-server localhost:9092 --topic kafka-demo
2. kafka-console-producer.sh --bootstrap-server localhost:9092 --topic kafka-demo --producer-property acks=all
3. kafka-console-producer.sh --bootstrap-server localhost:9092 --topic kafka-demo --property parse.key=true --property
   key.separator=: (user_id_123:maziar)

## Create Consumers

1. kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic kafka-demo
2. kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic kafka-demo --from-beginning (This will read from
   the beginning and don't wait to receive the new messages)
3. kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic kafka-demo --formatter
   kafka.tools.DefaultMessageFormatter --property print.timestamp=true --property print.key=true --property
   print.value=true --from-beginning

## Consumers in Group

#### In a consumer group the messages will be balanced between consumers

#### All different consumer groups will get the messages together

1. kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic kafka-demo-three-partition --group
   my-first-consumer-group
2. kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic kafka-demo-three-partition --group
   my-first-consumer-group
3. kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic kafka-demo-three-partition --group
   my-second-consumer-group
4. kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic kafka-demo-three-partition --group
   my-second-consumer-group

## Consumer Groups command

1. kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
2. kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe
3. kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-first-consumer-group

## Reset the Offsets

#### We can not reset offset if the consumer is running

1. kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-consumer-group --reset-offsets
   --to-earliest --execute --all-topics
2. kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-consumer-group --reset-offsets
   --to-earliest --shift-by 2 --execute --all-topics
3. kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-consumer-group --reset-offsets
   --to-earliest --shift-by -2 --execute --all-topics