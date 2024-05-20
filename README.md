# About MRLS

GitLabからMergeRequestを取得して一覧表示する（
ユーザー単位でgroup byして表示）

![ページサンプル](src/main/resources/static/img/page-sample.png)

# Quick Start

https://hub.docker.com/r/tttol/mrls

1. pull & run docker.io/tttol/mrls:latst
```bash
docker run --name mrls --env GITLAB_PROJECT_ID=xxxx --env GITLAB_ACCESS_TOKEN=xxxx --env GITLAB_HOST=xxx --env PROXY_HOST=xxx --env PROXY_PORT=xxx -it -p 8888:8080 tttol/mrls:latest
```
> [!NOTE]
> `PROXY_HOST` and `PROXY_PORT` is optional.

## Environment variables
- GITLAB_HOST
    - GitLabのhost
- GITLAB_PROJECT_ID
    - GitLabのプロジェクトID
- GITLAB_ACCESS_TOKEN
    - GitLabのアクセストークン
    - project access token / personal access token / group access token
    - scope=`api`
        - `/merge_requests`APIが実行できる権限であればOK
    - More info -> https://docs.gitlab.com/ee/api/rest/
- PROXY_HOST (optional)
- PROXY_PORT (optioanl)

# Run container on your local(optional)

1. clone

```bash
git clone https://github.com/tttol/mrls.git
cd mrls
```

2. create docker image

```bash
./gradlew bootBuildImage
```

3. run container

```bash
docker run --name mrls --env GITLAB_PROJECT_ID=xxxx --env GITLAB_ACCESS_TOKEN=xxxx --env GITLAB_HOST=xxx --env PROXY_HOST=xxx --env PROXY_PORT=xxx -it -p 8888:8080 tttol/mrls:latest
```
