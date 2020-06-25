package io.github.lrzeszotarski.accountmanager.api

import io.github.lrzeszotarski.accountmanager.api.model.Account
import io.github.lrzeszotarski.accountmanager.domain.repository.AccountRepository
import io.github.lrzeszotarski.accountmanager.mapper.AccountMapper
import spock.lang.Specification

class AccountApiDelegateImplTest extends Specification {

    def accountMapper  = Mock(AccountMapper)

    def accountRepository = Mock(AccountRepository)

    def testedInstance = new AccountApiDelegateImpl(accountMapper, accountRepository)

    def "CreateAccount"() {
        given:
        def account = new Account()
        def accountEntity = new io.github.lrzeszotarski.accountmanager.domain.entity.Account()
        when:
        testedInstance.createAccount(account)
        then:
        1 * accountMapper.toEntity(account) >> accountEntity
        1 * accountRepository.save(accountEntity) >> accountEntity
        1 * accountMapper.toDto(accountEntity)
    }
}
