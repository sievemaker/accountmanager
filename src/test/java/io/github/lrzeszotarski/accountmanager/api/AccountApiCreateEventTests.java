package io.github.lrzeszotarski.accountmanager.api;

import io.github.lrzeszotarski.accountmanager.api.model.Account;
import io.github.lrzeszotarski.accountmanager.api.model.Event;
import io.github.lrzeszotarski.accountmanager.domain.repository.AccountRepository;
import io.github.lrzeszotarski.accountmanager.domain.repository.EventRepository;
import io.github.lrzeszotarski.accountmanager.scheduler.ScheduledTasks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
public class AccountApiCreateEventTests {

    @Autowired
    private AccountApiDelegate accountApiDelegate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EventRepository eventRepository;

    @MockBean
    private ScheduledTasks scheduledTasks;

    @Test
    public void testCreateEvent() {
        final Account account = new Account();
        account.setName("Sample Account Name");
        final OffsetDateTime now = OffsetDateTime.now();
        final ResponseEntity<Account> createAccountResponse = accountApiDelegate.createAccount(account);
        final Account createdAccount = createAccountResponse.getBody();
        final Event firstEvent = new Event();
        firstEvent.setHappenedAt(now);
        firstEvent.setType("Some Type");
        final Event secondEvent = new Event();
        secondEvent.setHappenedAt(now);
        secondEvent.setType("Some Type");
        final ResponseEntity<Event> createdFirstEventResponse = accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), firstEvent);
        assertEquals(HttpStatus.OK, createdFirstEventResponse.getStatusCode());
        final Event createdFirstEvent = createdFirstEventResponse.getBody();

        final ResponseEntity<Event> createdSecondEventResponse = accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), secondEvent);
        assertEquals(HttpStatus.OK, createdSecondEventResponse.getStatusCode());

        final List<io.github.lrzeszotarski.accountmanager.domain.entity.Event> eventList = eventRepository.findAll();
        assertEquals(2, eventList.size());
        io.github.lrzeszotarski.accountmanager.domain.entity.Event firstSavedEvent = eventList.get(0);
    }
}
