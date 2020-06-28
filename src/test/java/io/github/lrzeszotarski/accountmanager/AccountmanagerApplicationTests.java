package io.github.lrzeszotarski.accountmanager;

import io.github.lrzeszotarski.accountmanager.scheduler.ScheduledTasks;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AccountmanagerApplicationTests {

    @MockBean
    private ScheduledTasks scheduledTasks;

    @Test
    void contextLoads() {
    }

}
