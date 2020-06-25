package io.github.lrzeszotarski.accountmanager.mapper

import io.github.lrzeszotarski.accountmanager.domain.entity.Account
import spock.lang.Specification

class AccountMapperTest extends Specification {

    def testedInstance = new AccountMapperImpl()

    def "test toDto"() {
        given:
        def randomUUID = UUID.randomUUID()
        Account account = new Account(name: "sample", accountId: randomUUID, id: 10)
        when:
        def accountDto = testedInstance.toDto(account)
        then:
        accountDto.getAccountId() == randomUUID
        accountDto.getName() == "sample"
    }

    def "test toEntity"() {
        given:
        def randomUUID = UUID.randomUUID()
        io.github.lrzeszotarski.accountmanager.api.model.Account accountDto =
                new io.github.lrzeszotarski.accountmanager.api.model.Account(accountId: randomUUID, name: "sample")
        when:
        def account = testedInstance.toEntity(accountDto)
        then:
        account.getId() == null
        account.getAccountId() == randomUUID
        account.getName() == "sample"
    }
}
