HOSTNAME="localhost"
QUERY="$@"

curl --http1.1 \
     -X "POST" "http://${HOSTNAME}:8088/query" \
     -H "Accept: application/vnd.ksql.v1+json" \
     -H "Content-Type: application/json" \
     -d "
	 {
		 \"ksql\": \"$QUERY\"
	 }
	 " | jq
