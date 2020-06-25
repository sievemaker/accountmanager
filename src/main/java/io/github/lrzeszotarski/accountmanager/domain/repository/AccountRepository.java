package io.github.lrzeszotarski.accountmanager.domain.repository;


import io.github.lrzeszotarski.accountmanager.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountId(UUID accountId);
}
