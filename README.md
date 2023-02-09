# Kafka Streams: Scratching The Surface 
A repository containing everything I used for my Kafka Streams presentation

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

# 2. Running the spring application
Navigate to the application directory located at `./streams/app/kafkastreams` and run it with gradle via CLI, or import the project to your favorite IDE and run it via the `KafkastreamsApplication.kt` file.
```
cd streams/app/kafkastreams
./gradlew bootRun
```

After doing that, you are good to play around with the application endpoints and open an interactive shell inside the `broker` instance and use the helper scripts.

# 3. ksqlDB
The main objective of `ksqlDB` is to untie the Kafka Streams operations from a specific programming language. It creates an abstraction layer between the broker and the application by providing endpoints for the stream operations.

**The following scripts are located under the `./ksqldb/rest` directory.**

### 3.1 Helper scripts
In order to run tests on the `ksqlDB` you can use the following scripts:
- `./ksql.sh QUERY`: runs the given query on the local `/ksql` endpoint (with `auto-offset-reset` set to `earliest`);
- `./query.sh QUERY`: runs the given query on the local `/query` endpoint.

For more information about the endpoints, refer to: https://docs.ksqldb.io/en/latest/developer-guide/api/

### 3.2 Running an application example
The `app.sh` script is an example of how to execute multiple statements that depends on the previous statement's result. It does that by using the `commandSequenceNumber` value, returned by the statement executed via ksqlDB rest API.
Those are the operations that it will execute:
1. Create a stream called `lorem_stream_script` from the `lorem-ipsum` topic, whose content of each row is a lorem ipsum sentence.;
2. Create a stream called `lorem_cnt_stream`, with the sentence length and the sentence itself, pulled from the `lorem_stream_script` stream;
3. Lastly, it will run a **SELECT** statement on the `lorem_cnt_stream` stream and show its results.
```
./app.sh
```

### 3.3 Interactive shell
For testing purposes, there's an easier way to run the SQL statements, which is by running an interactive shell.
```
docker exec -it ksqldb-cli ksql http://ksqldb-server:8088
```

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
