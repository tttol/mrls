package io.github.tttol.mrls;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"GITLAB_PROJECT_ID=dummy",
    "GITLAB_ACCESS_TOKEN=dummy"})
class MrlsApplicationTests {

  @Test
  void contextLoads() {
  }

}
