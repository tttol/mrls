package io.github.tttol.mrls;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "PROXY_HOST=dummy.com",
        "PROXY_PORT=80",
        "GITLAB_PROJECT_ID=dummy",
        "GITLAB_ACCESS_TOKEN=dummy",
        "GITLAB_HOST=dummy"})
class MrlsApplicationTests {

    @Test
    void contextLoads() {

    }

}
