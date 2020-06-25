package io.github.lrzeszotarski.accountmanager.api;

import io.github.lrzeszotarski.accountmanager.api.model.Account;
import io.github.lrzeszotarski.accountmanager.domain.repository.AccountRepository;
import io.github.lrzeszotarski.accountmanager.mapper.AccountMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountApiDelegateImpl implements AccountApiDelegate {

    private final AccountMapper accountMapper;

    private final AccountRepository accountRepository;

    public AccountApiDelegateImpl(AccountMapper accountMapper, AccountRepository accountRepository) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public ResponseEntity<Account> createAccount(Account body) {
        io.github.lrzeszotarski.accountmanager.domain.entity.Account entity = accountMapper.toEntity(body);
        accountRepository.save(entity);
        return ResponseEntity.ok(accountMapper.toDto(entity));
    }
}
