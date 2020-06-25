package io.github.lrzeszotarski.accountmanager.mapper;

import io.github.lrzeszotarski.accountmanager.api.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toDto(io.github.lrzeszotarski.accountmanager.domain.entity.Account account);

    io.github.lrzeszotarski.accountmanager.domain.entity.Account toEntity(Account account);
}
