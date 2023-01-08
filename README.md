# About MRLS

GitLabからMergeRequestを取得して一覧表示する（
ユーザー単位でgroup byして表示）

# Get Started

1. application-env.ymlをコピーしてapplication-local.yml作成

```bash
git clone https://github.com/tttol/mrls.git
cd mrls/src/main/resources/ 
cp application-env.yml application-local.yml
```

2. application-local.ymlの以下を編集

- `${app.gitlab.project.id}`
    - GitLabプロジェクトのプロジェクトID
    - Project -> Settings -> General -> Project ID
- `${app.gitlab.project.accessToken}`
    - Gitlabプロジェクトアクセストークン
    - Project -> Settings -> Access Tokens

3. アプリ起動

```bash
./gradlew bootRun -Dspring.profiles.active=local
```