package com.atlas.bank.account.dto;

import com.atlas.bank.account.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)

    Account toEntity (CreateAccountRequest request);
    AccountResponse toResponse(Account account);

}
