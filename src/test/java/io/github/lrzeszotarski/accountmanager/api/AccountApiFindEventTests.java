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
import org.springframework.test.annotation.DirtiesContext;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.Assert.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountApiFindEventTests {

    @Autowired
    private AccountApiDelegate accountApiDelegate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EventRepository eventRepository;

    @MockBean
    private ScheduledTasks scheduledTasks;

    @Test
    public void testFindEventWhenEventExists() {
        final OffsetDateTime now = OffsetDateTime.now();

        final Account accountToCreate = new Account();
        accountToCreate.setName("Sample Name");

        final ResponseEntity<Account> createAccountResponse = accountApiDelegate.createAccount(accountToCreate);
        final Account createdAccount = createAccountResponse.getBody();

        final Event eventToCreate = new Event();
        eventToCreate.setType("Some Event Type");
        eventToCreate.setHappenedAt(now);
        final ResponseEntity<Event> createdEventResponse = accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), eventToCreate);
        final Event createdEvent = createdEventResponse.getBody();

        final ResponseEntity<Event> eventResponseEntity = accountApiDelegate.searchEvent(createdAccount.getAccountId().toString(), createdEvent.getEventId().toString());
        final Event searchedEvent = eventResponseEntity.getBody();

        assertEquals(HttpStatus.OK, eventResponseEntity.getStatusCode());
        assertEquals("Some Event Type", searchedEvent.getType());
        assertEquals(now, searchedEvent.getHappenedAt());
        assertEquals(createdEvent.getEventId(), searchedEvent.getEventId());
    }

    @Test
    public void testFindEventWhenEventDoesNotExist() {
        final OffsetDateTime now = OffsetDateTime.now();

        final Account accountToCreate = new Account();
        accountToCreate.setName("Sample Name");

        final ResponseEntity<Account> createAccountResponse = accountApiDelegate.createAccount(accountToCreate);
        final Account createdAccount = createAccountResponse.getBody();

        final Event eventToCreate = new Event();
        eventToCreate.setType("Some Event Type");
        eventToCreate.setHappenedAt(now);
        final ResponseEntity<Event> createdEventResponse = accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), eventToCreate);

        final ResponseEntity<Event> eventResponseEntity = accountApiDelegate.searchEvent(createdAccount.getAccountId().toString(), UUID.randomUUID().toString());
        final Event searchedEvent = eventResponseEntity.getBody();

        assertEquals(HttpStatus.OK, eventResponseEntity.getStatusCode());
        assertNull(searchedEvent);
    }

    @Test
    public void testFindEventWhenEventDoesNotMatchWithAccount() {
        final OffsetDateTime now = OffsetDateTime.now();

        final Account accountToCreate = new Account();
        accountToCreate.setName("Sample Name");
        final Account someOtherAccountToCreate = new Account();
        someOtherAccountToCreate.setName("Account Without Events");

        final ResponseEntity<Account> createAccountResponse = accountApiDelegate.createAccount(accountToCreate);
        final Account createdAccount = createAccountResponse.getBody();

        final ResponseEntity<Account> createSomeOtherAccountResponse = accountApiDelegate.createAccount(someOtherAccountToCreate);
        final Account someOtherAccount = createSomeOtherAccountResponse.getBody();

        final Event eventToCreate = new Event();
        eventToCreate.setType("Some Event Type");
        eventToCreate.setHappenedAt(now);
        final ResponseEntity<Event> createdEventResponse = accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), eventToCreate);
        final Event createdEvent = createdEventResponse.getBody();

        final ResponseEntity<Event> eventResponseEntity = accountApiDelegate.searchEvent(someOtherAccount.getAccountId().toString(), createdEvent.getEventId().toString());
        final Event searchedEvent = eventResponseEntity.getBody();

        assertEquals(HttpStatus.OK, eventResponseEntity.getStatusCode());
        assertNull(searchedEvent);
    }
}
