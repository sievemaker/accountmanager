package io.github.lrzeszotarski.accountmanager.api

import io.github.lrzeszotarski.accountmanager.api.model.Account
import io.github.lrzeszotarski.accountmanager.domain.service.AccountService
import io.github.lrzeszotarski.accountmanager.mapper.AccountMapper
import spock.lang.Specification

class AccountApiDelegateImplTest extends Specification {

    def accountMapper  = Mock(AccountMapper)

    def accountService = Mock(AccountService)

    def testedInstance = new AccountApiDelegateImpl(accountMapper, accountService)

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
}
