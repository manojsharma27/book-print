#bin/bash

PG_IMAGE="book-postgres"
PRINT_SVC_IMAGE="print-svc-img"
BOOK_PRINT_APP_IMAGE="book-print-img"

echo ">> Removing old containers"

for img in $BOOK_PRINT_APP_IMAGE $PRINT_SVC_IMAGE $PG_IMAGE
do
	if [ "$(docker ps -a | grep $img)" ]; then
		echo ">>> removing existing container ($img)"
		docker stop $img>/dev/null
		docker rm -f $img>/dev/null
	fi
done

# -------------------------------------------------------
echo "\n"
echo ">> Starting postgres container ($PG_IMAGE)"
pg_container_id=$(docker run -d --name $PG_IMAGE -p 5432:5432 $PG_IMAGE)
sleep 5
docker logs --tail 20 $pg_container_id


if [ "$(docker ps | grep $PG_IMAGE)" ]; then
	echo ">> Started PG container ($PG_IMAGE) with container_id $pg_container_id"
else
	echo ">> Failed to start PG container ($PG_IMAGE)"
	exit 1
fi

postgres_ip=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $PG_IMAGE)
echo ">> Postgres IP : $postgres_ip"

# -------------------------------------------------------
echo "\n"
echo ">> Starting print-service container ($PRINT_SVC_IMAGE)"
print_svc_container_id=$(docker run -d --name $PRINT_SVC_IMAGE -p 8081:8080 $PRINT_SVC_IMAGE)
sleep 5
docker logs --tail 20 $print_svc_container_id

if [ "$(docker ps | grep $PRINT_SVC_IMAGE)" ]; then
	echo ">> Started print-service container ($PRINT_SVC_IMAGE) with container_id $print_svc_container_id"
else
	echo ">> Failed to start print-service container ($PRINT_SVC_IMAGE)"
	exit 1
fi

print_svc_ip=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $PRINT_SVC_IMAGE)
echo ">> Print Service IP : $print_svc_ip"

# -------------------------------------------------------
echo "\n"
echo ">> Starting book-print-app container ($BOOK_PRINT_APP_IMAGE)"
book_print_container_id=$(docker run -d --name $BOOK_PRINT_APP_IMAGE -p 8080:8080 -e PG_HOST=$postgres_ip -e PRINT_SERVICE_HOST=$print_svc_ip $BOOK_PRINT_APP_IMAGE)
sleep 8
docker logs --tail 20 $book_print_container_id


if [ "$(docker ps | grep $BOOK_PRINT_APP_IMAGE)" ]; then
	echo ">> Started book-print-app container ($BOOK_PRINT_APP_IMAGE) with container_id $book_print_container_id"
	echo ">> Use credentials: (user/password) on -> http://localhost:8080/swagger-ui.html"
else
	echo ">> Failed to start book-print-app container ($BOOK_PRINT_APP_IMAGE)"
	exit 1
fi
