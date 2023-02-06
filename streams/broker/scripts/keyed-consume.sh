TOPIC="$1";shift;MORE="$@";kafka-console-consumer --bootstrap-server localhost:9092 --topic "$TOPIC" --property key.separator="-" --property print.key=true --property print.value=true "$MORE"
