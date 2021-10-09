#bin/bash

PG_IMAGE="ms27/book-print-demo:book-postgres"
PRINT_SVC_IMAGE="ms27/book-print-demo:print-svc-img"
BOOK_PRINT_APP_IMAGE="ms27/book-print-demo:book-print-img"

PG_CONTAINER="book-postgres"
PRINT_SVC_CONTAINER="print-svc-cont"
BOOK_PRINT_APP_CONTAINER="book-print-cont"

echo ">> Removing old containers"

for cont in $BOOK_PRINT_APP_CONTAINER $PRINT_SVC_CONTAINER $PG_CONTAINER
do
	if [ "$(docker ps -a | grep $cont)" ]; then
		echo ">>> removing existing container ($cont)"
		docker stop $cont>/dev/null
		docker rm -f $cont>/dev/null
	fi
done

# -------------------------------------------------------
echo "\n"
echo ">> Starting postgres container ($PG_CONTAINER)"
pg_container_id=$(docker run -d --name $PG_CONTAINER -p 5432:5432 $PG_IMAGE)
sleep 5
docker logs --tail 20 $pg_container_id


if [ "$(docker ps | grep $PG_CONTAINER)" ]; then
	echo ">> Started PG container ($PG_CONTAINER) with container_id $pg_container_id"
else
	echo ">> Failed to start PG container ($PG_CONTAINER)"
	exit 1
fi

postgres_ip=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $PG_CONTAINER)
echo ">> Postgres IP : $postgres_ip"

# -------------------------------------------------------
echo "\n"
echo ">> Starting print-service container ($PRINT_SVC_CONTAINER)"
print_svc_container_id=$(docker run -d --name $PRINT_SVC_CONTAINER -p 8081:8080 $PRINT_SVC_IMAGE)
sleep 5
docker logs --tail 20 $print_svc_container_id

if [ "$(docker ps | grep $PRINT_SVC_CONTAINER)" ]; then
	echo ">> Started print-service container ($PRINT_SVC_CONTAINER) with container_id $print_svc_container_id"
else
	echo ">> Failed to start print-service container ($PRINT_SVC_CONTAINER)"
	exit 1
fi

print_svc_ip=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $PRINT_SVC_CONTAINER)
echo ">> Print Service IP : $print_svc_ip"

# -------------------------------------------------------
echo "\n"
echo ">> Starting book-print-app container ($BOOK_PRINT_APP_CONTAINER)"
book_print_container_id=$(docker run -d --name $BOOK_PRINT_APP_CONTAINER -p 8080:8080 -e PG_HOST=$postgres_ip -e PRINT_SERVICE_HOST=$print_svc_ip $BOOK_PRINT_APP_IMAGE)
sleep 8
docker logs --tail 20 $book_print_container_id


if [ "$(docker ps | grep $BOOK_PRINT_APP_CONTAINER)" ]; then
	echo ">> Started book-print-app container ($BOOK_PRINT_APP_CONTAINER) with container_id $book_print_container_id"
	echo ">> Use credentials: (user/password) on -> http://localhost:8080/swagger-ui.html"
else
	echo ">> Failed to start book-print-app container ($BOOK_PRINT_APP_CONTAINER)"
	exit 1
fi
