#bin/bash

PG_IMAGE="book-pg-cont"
PRINT_SVC_IMAGE="print-svc-cont"
BOOK_PRINT_APP_IMAGE="book-print-cont"

for img in $BOOK_PRINT_APP_IMAGE $PRINT_SVC_IMAGE $PG_IMAGE
do
	if [ "$(docker ps -a | grep $img)" ]; then
		echo "stopping any running container ($img)"
		docker stop $img>/dev/null
		echo "removing existing container ($img)"
		docker rm -f $img>/dev/null
	fi
done
