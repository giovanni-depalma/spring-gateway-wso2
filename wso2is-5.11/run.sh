NAME=wso2is-5.11
docker stop $NAME
docker rm $NAME
docker run \
    -p 9443:9443 \
    -p 9763:9763 \
    --name $NAME \
    -d $NAME
