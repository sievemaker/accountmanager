package io.github.lrzeszotarski.accountmanager.domain.service;

import io.github.lrzeszotarski.accountmanager.domain.entity.Account;
import io.github.lrzeszotarski.accountmanager.domain.entity.Event;
import io.github.lrzeszotarski.accountmanager.domain.repository.AccountRepository;
import io.github.lrzeszotarski.accountmanager.domain.repository.EventRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final EventRepository eventRepository;

    private final IdentifierService identifierService;

    private final EventStatisticsService eventStatisticsService;

    public AccountServiceImpl(AccountRepository accountRepository, EventRepository eventRepository, IdentifierService identifierService, EventStatisticsService eventStatisticsService) {
        this.accountRepository = accountRepository;
        this.eventRepository = eventRepository;
        this.identifierService = identifierService;
        this.eventStatisticsService = eventStatisticsService;
    }

    @Override
    public Account createAccount(Account account) {
        account.setAccountId(identifierService.generateIdentifier());
        return accountRepository.save(account);
    }

    @Override
    public Account findAccount(UUID accountID) {
        return accountRepository.findByAccountId(accountID);
    }

    @Override
    public Account updateAccount(Account account) {
        final Account existingAccount = accountRepository.findByAccountId(account.getAccountId());
        existingAccount.setName(account.getName());
        return existingAccount;
    }

    @Override
    public Event createEvent(UUID accountId, Event entity) {
        final Account account = accountRepository.findByAccountId(accountId);
        entity.setEventId(identifierService.generateIdentifier());
        entity.setAccount(account);
        account.getEventList().add(entity);
        eventStatisticsService.updateStatistics(account, entity);
        return entity;
    }

    @Override
    public Event findEvent(UUID accountId, UUID eventId) {
        final Event event = eventRepository.findByEventId(eventId);
        return event.getAccount().getAccountId().equals(accountId) ? event : null;
    }
}
