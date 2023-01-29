# About MRLS

GitLabからMergeRequestを取得して一覧表示する（
ユーザー単位でgroup byして表示）

# Get Started

## Pull from Docker

TBD

## Create Docker image and run container on your local

1. クローン

```bash
git clone https://github.com/tttol/mrls.git
cd mrls
```

2. Dockerイメージ作成 -> `./gradlew bootBuildImage`
3. コンテナ起動
   -> `docker run --name mrls --env GITLAB_PROJECT_ID=xxxx --env GITLAB_ACCESS_TOKEN=xxxx -it -p 8888:8080 mrls`

- GITLAB_PROJECT_ID
    - GitLabのプロジェクトID
- GITLAB_ACCESS_TOKEN
    - GitLabのアクセストークン
    - project access token / personal access token / group access token
    - scope=`api`
        - `/merge_requests`APIが実行できる権限であればOK
    - More info -> https://docs.gitlab.com/ee/api/rest/