package io.github.lrzeszotarski.accountmanager.api

import io.github.lrzeszotarski.accountmanager.api.model.Account
import io.github.lrzeszotarski.accountmanager.api.model.Event
import io.github.lrzeszotarski.accountmanager.domain.service.AccountService
import io.github.lrzeszotarski.accountmanager.mapper.AccountMapper
import io.github.lrzeszotarski.accountmanager.mapper.EventMapper
import spock.lang.Specification

class AccountApiDelegateImplTest extends Specification {

    def accountMapper  = Mock(AccountMapper)

    def eventMapper = Mock(EventMapper)

    def accountService = Mock(AccountService)

    def testedInstance = new AccountApiDelegateImpl(accountMapper, eventMapper, accountService)

    def "CreateAccount"() {
        given:
        def account = new Account()
        def accountEntity = new io.github.lrzeszotarski.accountmanager.domain.entity.Account()
        when:
        testedInstance.createAccount(account)
        then:
        1 * accountMapper.toEntity(account) >> accountEntity
        1 * accountService.createAccount(accountEntity) >> accountEntity
        1 * accountMapper.toDto(accountEntity)
    }

    def "FindAccount"() {
        given:
        def accountId = UUID.randomUUID().toString()
        def accountEntity = new io.github.lrzeszotarski.accountmanager.domain.entity.Account()
        when:
        testedInstance.getAccounts(accountId)
        then:
        1 * accountService.findAccount(UUID.fromString(accountId)) >> accountEntity
        1 * accountMapper.toDto(accountEntity)
    }

    def "UpdateAccount"() {
        given:
        def account = new Account()
        def accountEntity = new io.github.lrzeszotarski.accountmanager.domain.entity.Account()
        def updatedEntity = new io.github.lrzeszotarski.accountmanager.domain.entity.Account()
        when:
        testedInstance.updateAccount(account)
        then:
        1 * accountMapper.toEntity(account) >> accountEntity
        1 * accountService.updateAccount(accountEntity) >> updatedEntity
        1 * accountMapper.toDto(updatedEntity)
    }

    def "CreateEvent"() {
        given:
        def event = new Event()
        def eventEntity = new io.github.lrzeszotarski.accountmanager.domain.entity.Event()
        def accountId = UUID.randomUUID().toString()
        when:
        testedInstance.createEvent(accountId, event)
        then:
        1 * eventMapper.toEntity(event) >> eventEntity
        1 * accountService.createEvent(UUID.fromString(accountId), eventEntity) >> eventEntity
        1 * eventMapper.toDto(eventEntity)
    }

    def "FindEvent"() {
        given:
        def eventId = UUID.randomUUID().toString()
        def eventEntity = new io.github.lrzeszotarski.accountmanager.domain.entity.Event()
        when:
        testedInstance.searchEvent(null, eventId)
        then:
        1 * accountService.findEvent(UUID.fromString(eventId)) >> eventEntity
        1 * eventMapper.toDto(eventEntity)
    }
}
