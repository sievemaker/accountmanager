package io.github.lrzeszotarski.accountmanager.domain.service;

import io.github.lrzeszotarski.accountmanager.domain.entity.Account;
import io.github.lrzeszotarski.accountmanager.domain.entity.Event;

import java.util.UUID;

public interface AccountService {
    Account createAccount(Account account);

    Account findAccount(UUID accountId);

    Account updateAccount(Account account);

    Event createEvent(UUID accountId, Event entity);

    Event findEvent(UUID accountId, UUID eventId);
}
