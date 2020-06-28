package io.github.lrzeszotarski.accountmanager.api;

import io.github.lrzeszotarski.accountmanager.api.model.Account;
import io.github.lrzeszotarski.accountmanager.scheduler.ScheduledTasks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountApiUpdateAccountTests {

    @Autowired
    private AccountApiDelegate accountApiDelegate;

    @MockBean
    private ScheduledTasks scheduledTasks;

    @Test
    public void testUpdateAccountWhenAccountExists() {
        final Account account = new Account();
        account.setName("Sample Account Name");
        final ResponseEntity<Account> createAccountResponse = accountApiDelegate.createAccount(account);
        final Account createdAccount = createAccountResponse.getBody();
        final Account accountToUpdate = new Account();
        accountToUpdate.setName("Some Other Name");
        accountToUpdate.setAccountId(createdAccount.getAccountId());
        final ResponseEntity<Account> accountsEntityResponse = accountApiDelegate.updateAccount(accountToUpdate);
        assertEquals(HttpStatus.OK, accountsEntityResponse.getStatusCode());
        final Account searchedAccount = accountsEntityResponse.getBody();
        assertEquals("Some Other Name", searchedAccount.getName());
        assertEquals(createdAccount.getAccountId(), searchedAccount.getAccountId());    }

    @Test
    public void testUpdateAccountWhenAccountDoesNotExist() {
        Account account = new Account();
        account.setAccountId(UUID.randomUUID());
        account.setName("Sample Name");
        final ResponseEntity<Account> accountsEntityResponse = accountApiDelegate.updateAccount(account);
        assertEquals(HttpStatus.OK, accountsEntityResponse.getStatusCode());
        final Account searchedAccount = accountsEntityResponse.getBody();
        assertNull(searchedAccount);
    }
}
