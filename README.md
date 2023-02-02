# About MRLS

GitLabからMergeRequestを取得して一覧表示する（
ユーザー単位でgroup byして表示）

![ページサンプル](src/main/resources/static/img/page-sample.png)

# Get Started

## Pull from Docker

https://hub.docker.com/r/tttol/mrls

1. pull docker image

```bash
docker pull tttol/mrls
```

2. コンテナ起動

```bash
docker run --name mrls --env GITLAB_PROJECT_ID=xxxx --env GITLAB_ACCESS_TOKEN=xxxx --env GITLAB_HOST=xxx -it -p 8888:8080 tttol/mrls:1.1.1
```

## Create Docker image and run container on your local

1. クローン

```bash
git clone https://github.com/tttol/mrls.git
cd mrls
```

2. Dockerイメージ作成 ->

```bash
./gradlew bootBuildImage
```

3. コンテナ起動

```bash
docker run --name mrls --env GITLAB_PROJECT_ID=xxxx --env GITLAB_ACCESS_TOKEN=xxxx --env GITLAB_HOST=xxx -it -p 8888:8080 tttol/mrls:1.1.1
```

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
