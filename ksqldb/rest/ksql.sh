HOSTNAME="localhost"
QUERY="$@"

curl --http1.1 \
     -X "POST" "http://${HOSTNAME}:8088/ksql" \
     -H "Accept: application/vnd.ksql.v1+json" \
     -H "Content-Type: application/json" \
     -d "
	 {
		 \"ksql\": \"$QUERY\",
		 \"streamsProperties\": {
		 	\"ksql.streams.auto.offset.reset\": \"earliest\"
	 	 }
	 }
	 " | jq
