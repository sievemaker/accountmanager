package io.github.lrzeszotarski.accountmanager.domain.service

import io.github.lrzeszotarski.accountmanager.domain.entity.Account
import io.github.lrzeszotarski.accountmanager.domain.repository.AccountRepository
import spock.lang.Specification

class AccountServiceImplTest extends Specification {

    def accountRepository = Mock(AccountRepository)

    def identifierService = Mock(IdentifierService)

    def accountService = new AccountServiceImpl(accountRepository, identifierService)

    def "test createAccount"() {
        given:
        def account = new Account()
        def uuid = UUID.randomUUID()
        when:
        def createdAccount = accountService.createAccount(account)
        then:
        1 * accountRepository.save(account) >> account
        1 * identifierService.generateIdentifier() >> uuid
        account == createdAccount
        createdAccount.getAccountId() == uuid
    }

    def "test findAccount"() {
        given:
        def uuid = UUID.randomUUID()
        def account = new Account(accountId: uuid)
        when:
        def searchedAccount = accountService.findAccount(uuid)
        then:
        1 * accountRepository.findByAccountId(uuid) >> account
        account == searchedAccount
        searchedAccount.getAccountId() == uuid
    }
}
