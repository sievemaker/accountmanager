package io.github.lrzeszotarski.accountmanager.api

import io.github.lrzeszotarski.accountmanager.api.model.Account

class AccountApiDelegateImplTest extends spock.lang.Specification {

    def testedInstance = new AccountApiDelegateImpl()

    def "test createAccount"() {
        given:
        when:
        def responseEntity = testedInstance.createAccount(new Account(name: "Sample Account Name"))
        then:
        // TODO implement assertions
    }
}
