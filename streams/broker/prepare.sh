for s in $(ls scripts); do docker cp scripts/$s broker:/home/appuser/$s; done
for d in $(ls data); do docker cp data/$d broker:/home/appuser/$d; done
