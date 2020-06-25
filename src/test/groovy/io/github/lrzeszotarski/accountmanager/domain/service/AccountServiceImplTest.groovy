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
}
