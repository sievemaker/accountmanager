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
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.Assert.assertFalse;

@SpringBootTest
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
        final Account accountToCreate = new Account();
        accountToCreate.setName("Sample Name");
        final ResponseEntity<Account> createAccountResponse = accountApiDelegate.createAccount(accountToCreate);
        final Account createdAccount = createAccountResponse.getBody();
        final Event eventToCreate = new Event();
        eventToCreate.setType("Some Event Type");
        eventToCreate.setHappenedAt(OffsetDateTime.now());
        final ResponseEntity<Event> createdEventResponse = accountApiDelegate.createEvent(createdAccount.getAccountId().toString(), eventToCreate);
        
    }

    @Test
    public void testFindEventWhenEventDoesNotExist() {
        assertFalse(true);
    }

    @Test
    public void testFindEventWhenEventDoesNotMatchWithAccount() {
        assertFalse(true);
    }
}
