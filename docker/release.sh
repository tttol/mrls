if [ $# != 1 ]; then
    echo "引数にアプリバージョン(X.X.X)を指定してください"
    exit 1
fi

./gradlew bootBuildImage
docker tag mrls:$1 tttol/mrls:$1
docker tag mrls:$1 tttol/mrls:latest
docker push tttol/mrls:$1
docker push tttol/mrls:latest