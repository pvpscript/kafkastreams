# Kafka Streams: Scratching The Surface
A repository for my presentation about the basics of Kafka Streams

# 1. Setting up
In order to set up the environment and run tests, just follow the steps below:
### 1.1 Starting up the service
In order to start it up, simply run docker on the repository's root directory.
```
docker-compose up
```
### 1.2 Prepare the environment
There are two main scripts that will be used to prepare the whole environment, one for the external machine and one for the docker `broker` instance. Follow the instructions below to make it work:
1. From the repository's root directory, navigate to the `broker` folder, located at `./streams/broker`;
2. Run the `prepare.sh` script;
3. Run the `_prepare.sh` script that's inside the `broker` instance;
```
cd streams/broker
./prepare.sh
docker-compose exec broker /bin/sh -c ". /home/appuser/_prepare.sh"
```

### 1.3 Run the spring application
Navigate to the application directory located at `./streams/app/kafkastreams` and run it with gradle via CLI, or import the project to your favorite IDE and run it via the `KafkastreamsApplication.kt` file.
```
cd streams/app/kafkastreams
./gradlew bootRun
```

After doing that, you are good to play around with the application endpoints and open an interactive shell inside the `broker` instance and use the helper scripts.

# Appendix
### A. Endpoints
- **GET** `/kafka-streams/filter`: run `KafkaStreams` filter operations;
- **GET** `/kafka-streams/aggregate`: runs `KafkaStreams` aggregation operations;
- **GET** `/kafka-streams/table`: runs `KTable` operations;
- **GET** `/kafka-streams/join`: runs `KStreams` join operations.

### B. Helper scripts
Those are meant to be run inside the `broker` instance.
1. Create a topic: `./create TOPIC_NAME`;
2. Delete a topic: `./delete TOPIC_NAME`;
3. List created topic: `./list-topics.sh`;
4. Produce in a topic with a **null** key: `./produce.sh TOPIC_NAME`;
5. Consume a topic with a **null** key: `./consume.sh TOPIC_NAME [OPTIONS]`;
6. Produce in a topic with a **non-null** (formatted: `key:value`): `./keyed-produce.sh TOPIC_NAME`;
7. Consume a topic with a **non-null** key: `./keyed-consume.sh TOPIC_NAME [OPTIONS]`.
