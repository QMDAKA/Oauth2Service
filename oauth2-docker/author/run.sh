cd /app

echo "Sleeping: wait for database"

sleep 30

echo "Wakeup"

java -jar apifest-oauth20-parent.jar
