package io.github.lrzeszotarski.accountmanager.domain.service

import io.github.lrzeszotarski.accountmanager.domain.entity.Account
import io.github.lrzeszotarski.accountmanager.domain.entity.Event
import io.github.lrzeszotarski.accountmanager.domain.repository.AccountRepository
import io.github.lrzeszotarski.accountmanager.domain.repository.EventRepository
import spock.lang.Specification

class AccountServiceImplTest extends Specification {

    def accountRepository = Mock(AccountRepository)

    def eventRepository = Mock(EventRepository)

    def identifierService = Mock(IdentifierService)

    def testedInstance = new AccountServiceImpl(accountRepository, eventRepository, identifierService)

    def "test createAccount"() {
        given:
        def account = new Account()
        def uuid = UUID.randomUUID()
        when:
        def createdAccount = testedInstance.createAccount(account)
        then:
        1 * accountRepository.save(account) >> account
        1 * identifierService.generateIdentifier() >> uuid
        account == createdAccount
        createdAccount.getAccountId() == uuid
    }

    def "test updateAccount"() {
        given:
        def uuid = UUID.randomUUID()
        def existingAccount = new Account(accountId: uuid, name: "Sample Name")
        def updatedAccount = new Account(accountId: uuid, name: "New Sample Name")
        when:
        def accountAfterUpdate = testedInstance.updateAccount(updatedAccount)
        then:
        1 * accountRepository.findByAccountId(existingAccount.getAccountId()) >> existingAccount
        accountAfterUpdate.getName() == "New Sample Name"
    }

    def "test findAccount"() {
        given:
        def uuid = UUID.randomUUID()
        def account = new Account(accountId: uuid)
        when:
        def searchedAccount = testedInstance.findAccount(uuid)
        then:
        1 * accountRepository.findByAccountId(uuid) >> account
        account == searchedAccount
        searchedAccount.getAccountId() == uuid
    }

    def "test createEvent"() {
        given:
        def accountUuid = UUID.randomUUID()
        def eventUuid = UUID.randomUUID()
        def account = new Account(accountId: accountUuid, eventList: new ArrayList<Event>())
        def event = new Event()
        when:
        def createdEvent = testedInstance.createEvent(accountUuid, event)
        then:
        1 * accountRepository.findByAccountId(accountUuid) >> account
        1 * accountRepository.save(account)
        1 * identifierService.generateIdentifier() >> eventUuid
        account.getEventList().size() == 1
        account.getEventList().get(0).getEventId() == eventUuid
        account.getEventList().get(0) == createdEvent
    }

    def "test findEvent"() {
        given:
        def uuid = UUID.randomUUID()
        def event = new Event(eventId: uuid)
        when:
        def searchedEvent = testedInstance.findEvent(uuid)
        then:
        1 * eventRepository.findByEventId(uuid) >> event
        event == searchedEvent
        searchedEvent.getEventId() == uuid
    }
}
