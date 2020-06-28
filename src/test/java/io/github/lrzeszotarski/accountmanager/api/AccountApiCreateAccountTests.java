package io.github.lrzeszotarski.accountmanager.api;

import io.github.lrzeszotarski.accountmanager.api.model.Account;
import io.github.lrzeszotarski.accountmanager.domain.repository.AccountRepository;
import io.github.lrzeszotarski.accountmanager.scheduler.ScheduledTasks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class AccountApiCreateAccountTests {

    @Autowired
    private AccountApiDelegate accountApiDelegate;

    @Autowired
    private AccountRepository accountRepository;

    @MockBean
    private ScheduledTasks scheduledTasks;

    @Test
    public void testCreateAccount() {
        final Account account = new Account();
        account.setName("Sample Account Name");
        final ResponseEntity<Account> createAccountResponse = accountApiDelegate.createAccount(account);
        assertEquals(HttpStatus.OK, createAccountResponse.getStatusCode());
        final Account createdAccount = createAccountResponse.getBody();
        assertEquals("Sample Account Name", createdAccount.getName());
        io.github.lrzeszotarski.accountmanager.domain.entity.Account entity = accountRepository.findByAccountId(createdAccount.getAccountId());
        assertEquals("Sample Account Name", entity.getName());
    }
}
