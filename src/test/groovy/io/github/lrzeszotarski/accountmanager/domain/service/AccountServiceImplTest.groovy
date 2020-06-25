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

    def "test updateAccount"() {
        given:
        def uuid = UUID.randomUUID()
        def existingAccount = new Account(accountId: uuid, name: "Sample Name")
        def updatedAccount = new Account(accountId: uuid, name: "New Sample Name")
        when:
        def accountAfterUpdate = accountService.updateAccount(updatedAccount)
        then:
        1 * accountRepository.findByAccountId(existingAccount.getAccountId()) >> existingAccount
        accountAfterUpdate.getName() == "New Sample Name"
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
