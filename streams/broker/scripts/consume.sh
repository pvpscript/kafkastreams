TOPIC="$1";shift;MORE="$@";kafka-console-consumer --topic "$TOPIC" --bootstrap-server broker:9092 "$MORE"
