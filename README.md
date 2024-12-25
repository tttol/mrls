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
> `PROXY_HOST` and `PROXY_PORT` are optional.

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

# Release
1. Update build.gradle, list.html
```diff
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.2.5'
    id 'io.spring.dependency-management' version '1.1.0'
}

group = 'io.github.tttol'
- version = "1.1.7"
+ version = "1.1.8"
```

```diff
-             <div class="appVer">1.1.7</div>
+             <div class="appVer">1.1.8</div>
        </main>
    </body>
</html>
```

2. Run the following command.
```bash
sh docker/release.sh 1.0.0 # specify app version at arg 
```

3. Create a tag in GitHub.
https://github.com/tttol/mrls/releases