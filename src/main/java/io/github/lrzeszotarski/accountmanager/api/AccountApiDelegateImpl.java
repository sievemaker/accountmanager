package io.github.lrzeszotarski.accountmanager.api;

import io.github.lrzeszotarski.accountmanager.api.model.Account;
import io.github.lrzeszotarski.accountmanager.api.model.Event;
import io.github.lrzeszotarski.accountmanager.domain.repository.AccountRepository;
import io.github.lrzeszotarski.accountmanager.domain.service.AccountService;
import io.github.lrzeszotarski.accountmanager.mapper.AccountMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountApiDelegateImpl implements AccountApiDelegate {

    private final AccountMapper accountMapper;

    private final AccountService accountService;

    public AccountApiDelegateImpl(AccountMapper accountMapper, AccountService accountService) {
        this.accountMapper = accountMapper;
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<Account> createAccount(Account body) {
        io.github.lrzeszotarski.accountmanager.domain.entity.Account entity = accountMapper.toEntity(body);
        return ResponseEntity.ok(accountMapper.toDto(accountService.createAccount(entity)));
    }

    @Override
    public ResponseEntity<Account> getAccounts(String accountId) {
        return ResponseEntity.ok(accountMapper.toDto(accountService.findAccount(UUID.fromString(accountId))));
    }

    @Override
    public ResponseEntity<Account> updateAccount(Account body) {
        io.github.lrzeszotarski.accountmanager.domain.entity.Account entity = accountMapper.toEntity(body);
        return ResponseEntity.ok(accountMapper.toDto(accountService.updateAccount(entity)));
    }

    @Override
    public ResponseEntity<Event> createEvent(String accountId, Event body) {
        return null;
    }
}
