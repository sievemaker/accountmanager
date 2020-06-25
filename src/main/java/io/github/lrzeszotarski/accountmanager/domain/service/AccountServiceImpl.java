package io.github.lrzeszotarski.accountmanager.domain.service;

import io.github.lrzeszotarski.accountmanager.domain.entity.Account;
import io.github.lrzeszotarski.accountmanager.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final IdentifierService identifierService;

    public AccountServiceImpl(AccountRepository accountRepository, IdentifierService identifierService) {
        this.accountRepository = accountRepository;
        this.identifierService = identifierService;
    }

    @Override
    public Account createAccount(Account account) {
        account.setAccountId(identifierService.generateIdentifier());
        return accountRepository.save(account);
    }
}
