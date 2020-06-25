package io.github.lrzeszotarski.accountmanager.domain.service;

import io.github.lrzeszotarski.accountmanager.domain.entity.Account;

import java.util.UUID;

public interface AccountService {
    Account createAccount(Account account);

    Account findAccount(UUID fromString);
}
