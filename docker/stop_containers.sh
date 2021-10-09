#bin/bash

PG_IMAGE="book-postgres"
PRINT_SVC_IMAGE="print-svc-img"
BOOK_PRINT_APP_IMAGE="book-print-img"

for img in $PG_IMAGE $PRINT_SVC_IMAGE $BOOK_PRINT_APP_IMAGE
do
	if [ "$(docker ps -a | grep $img)" ]; then
		echo "stopping any running container ($img)"
		docker stop $img>/dev/null
		echo "removing existing container ($img)"
		docker rm -f $img>/dev/null
	fi
done