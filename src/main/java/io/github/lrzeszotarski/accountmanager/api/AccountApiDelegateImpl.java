package io.github.lrzeszotarski.accountmanager.api;

import io.github.lrzeszotarski.accountmanager.api.model.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountApiDelegateImpl implements AccountApiDelegate {
    @Override
    public ResponseEntity<Account> createAccount(Account body) {
        return null;
    }
}
