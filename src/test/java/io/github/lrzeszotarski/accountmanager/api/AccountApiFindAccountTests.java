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
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountApiFindAccountTests {

    @Autowired
    private AccountApiDelegate accountApiDelegate;

    @Autowired
    private AccountRepository accountRepository;

    @MockBean
    private ScheduledTasks scheduledTasks;

    @Test
    public void testFindAccountWhenAccountExists() {
        final Account account = new Account();
        account.setName("Sample Account Name");
        final ResponseEntity<Account> createAccountResponse = accountApiDelegate.createAccount(account);
        final Account createdAccount = createAccountResponse.getBody();
        final ResponseEntity<Account> accountsEntityResponse = accountApiDelegate.getAccounts(createdAccount.getAccountId().toString());
        assertEquals(HttpStatus.OK, accountsEntityResponse.getStatusCode());
        final Account searchedAccount = accountsEntityResponse.getBody();
        assertEquals("Sample Account Name", searchedAccount.getName());
        assertEquals(createdAccount.getAccountId(), searchedAccount.getAccountId());
        List<io.github.lrzeszotarski.accountmanager.domain.entity.Account> all = accountRepository.findAll();
        assertEquals(1, all.size());
        io.github.lrzeszotarski.accountmanager.domain.entity.Account savedAccount = all.get(0);
        assertEquals("Sample Account Name", savedAccount.getName());
        assertEquals(createdAccount.getAccountId(), savedAccount.getAccountId());
    }

    @Test
    public void testFindAccountWhenAccountDoesNotExist() {
        final ResponseEntity<Account> accountsEntityResponse = accountApiDelegate.getAccounts(UUID.randomUUID().toString());
        assertEquals(HttpStatus.OK, accountsEntityResponse.getStatusCode());
        final Account searchedAccount = accountsEntityResponse.getBody();
        assertNull(searchedAccount);
    }
}
