package io.github.lrzeszotarski.accountmanager.api;

import io.github.lrzeszotarski.accountmanager.api.model.Account;
import io.github.lrzeszotarski.accountmanager.api.model.Event;
import io.github.lrzeszotarski.accountmanager.api.model.EventStatistics;
import io.github.lrzeszotarski.accountmanager.domain.repository.AccountRepository;
import io.github.lrzeszotarski.accountmanager.domain.repository.EventRepository;
import io.github.lrzeszotarski.accountmanager.domain.service.EventCleanupService;
import io.github.lrzeszotarski.accountmanager.scheduler.ScheduledTasks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountApiGetAccountStatisticsTests {

    @Autowired
    private AccountApiDelegate accountApiDelegate;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCleanupService eventCleanupService;

    @MockBean
    private ScheduledTasks scheduledTasks;

    @Test
    public void testGetAccountStatistics() {
        final Account account = new Account();
        account.setName("Sample Name");

        final ResponseEntity<Account> createAccountResponseEntity = accountApiDelegate.createAccount(account);
        final Account createdAccount = createAccountResponseEntity.getBody();

        final OffsetDateTime firstDate = OffsetDateTime.now();
        final OffsetDateTime secondDate = OffsetDateTime.now().minusDays(1);

        final Event event1 = new Event();
        event1.setHappenedAt(firstDate);
        event1.setType("Some Type");

        final Event event2 = new Event();
        event2. setHappenedAt(firstDate);
        event2.setType("Some Type");

        final Event event3 = new Event();
        event3.setHappenedAt(secondDate);
        event3.setType("Some Type");

        final Event event4 = new Event();
        event4.setHappenedAt(firstDate);
        event4.setType("Some Other Type");

        accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), event1);
        accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), event2);
        accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), event3);
        accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), event4);

        getStatisticsForGivenAccountAndDay(createdAccount, firstDate);
    }

    @Test
    public void testGetAccountStatisticsAfterCleanupOutdatedEvents() {
        final Account account = new Account();
        account.setName("Sample Name");

        final ResponseEntity<Account> createAccountResponseEntity = accountApiDelegate.createAccount(account);
        final Account createdAccount = createAccountResponseEntity.getBody();

        final OffsetDateTime firstDate = OffsetDateTime.now().minusDays(60);
        final OffsetDateTime secondDate = OffsetDateTime.now().minusDays(61);

        final Event event1 = new Event();
        event1.setHappenedAt(firstDate);
        event1.setType("Some Type");

        final Event event2 = new Event();
        event2. setHappenedAt(firstDate);
        event2.setType("Some Type");

        final Event event3 = new Event();
        event3.setHappenedAt(secondDate);
        event3.setType("Some Type");

        final Event event4 = new Event();
        event4.setHappenedAt(firstDate);
        event4.setType("Some Other Type");

        accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), event1);
        accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), event2);
        accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), event3);
        accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), event4);

        getStatisticsForGivenAccountAndDay(createdAccount, firstDate);

        assertEquals(4, eventRepository.findAll().size());

        eventCleanupService.cleanupEvents();

        getStatisticsForGivenAccountAndDay(createdAccount, firstDate);

        assertEquals(0, eventRepository.findAll().size());
    }

    private void getStatisticsForGivenAccountAndDay(Account createdAccount, OffsetDateTime firstDate) {
        final ResponseEntity<List<EventStatistics>> accountStatisticsResponseEntity =
                accountApiDelegate.getAccountStatistics(
                        createdAccount.getAccountId().toString(),
                        firstDate.truncatedTo(ChronoUnit.DAYS));

        assertEquals(HttpStatus.OK, accountStatisticsResponseEntity.getStatusCode());

        final List<EventStatistics> accountStatistics = accountStatisticsResponseEntity.getBody();
        assertEquals(2, accountStatistics.size());

        final EventStatistics eventStatisticsFirst = accountStatistics.get(0);
        final EventStatistics eventStatisticsSecond = accountStatistics.get(1);

        assertEquals(2, eventStatisticsFirst.getCount().intValue());
        assertEquals(firstDate.truncatedTo(ChronoUnit.DAYS), eventStatisticsFirst.getDay());
        assertEquals("Some Type", eventStatisticsFirst.getType());

        assertEquals(1, eventStatisticsSecond.getCount().intValue());
        assertEquals(firstDate.truncatedTo(ChronoUnit.DAYS), eventStatisticsSecond.getDay());
        assertEquals("Some Other Type", eventStatisticsSecond.getType());
    }
}
