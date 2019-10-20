package dev.kamko.lnu_ass;

import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(EmbeddedTestAggregateStoreConfiguration.class)
class LnuAssApplicationTests {

    @Test
    void contextLoads() {
    }

}
