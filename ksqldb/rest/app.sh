HOSTNAME="localhost"

# CREATE STREAM lorem_stream_script (line VARCHAR) WITH(KAFKA_TOPIC='lorem-ipsum',VALUE_FORMAT='json');
# CREATE STREAM lorem_cnt_stream AS SELECT LEN(line), line FROM lorem_stream_script;
# SELECT * FROM lorem_cnt_stream;

create_stream_run=$(curl --http1.1 \
     -X "POST" "http://${HOSTNAME}:8088/ksql" \
     -H "Accept: application/vnd.ksql.v1+json" \
     -H "Content-Type: application/json" \
     -d "
	 {
		 \"ksql\": \"CREATE STREAM lorem_stream_script (line VARCHAR) WITH(KAFKA_TOPIC='lorem-ipsum',VALUE_FORMAT='json');\",
		 \"streamsProperties\": {
		 	\"ksql.streams.auto.offset.reset\": \"earliest\"
	 	 }
	 }
	 " | jq '.[].commandSequenceNumber')

echo "[BUILDING STREAM] commandSequenceNumber: $create_stream_run"

group_stream=$(curl --http1.1 \
     -X "POST" "http://${HOSTNAME}:8088/ksql" \
     -H "Accept: application/vnd.ksql.v1+json" \
     -H "Content-Type: application/json" \
     -d "
	 {
		 \"ksql\": \"CREATE STREAM lorem_cnt_stream AS SELECT LEN(line), line FROM lorem_stream_script;\",
		 \"streamsProperties\": {
		 	\"ksql.streams.auto.offset.reset\": \"earliest\"
	 	 },
		 \"commandSequenceNumber\": $create_stream_run
	 }
	 " | jq '.[].commandSequenceNumber')

echo "[GROUPING STREAM] commandSequenceNumber: $group_stream"

curl --http1.1 \
     -X "POST" "http://${HOSTNAME}:8088/query" \
     -H "Accept: application/vnd.ksql.v1+json" \
     -H "Content-Type: application/json" \
     -d "
	 {
		 \"ksql\": \"SELECT * FROM lorem_cnt_stream LIMIT 10;\",
		 \"commandSequenceNumber\": $group_stream
	 }
	 " | jq
