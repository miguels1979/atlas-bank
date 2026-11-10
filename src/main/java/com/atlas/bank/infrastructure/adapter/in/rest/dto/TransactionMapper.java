package com.atlas.bank.infrastructure.adapter.in.rest.dto;

import com.atlas.bank.domain.model.transaction.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionResponse toResponse(Transaction transaction);
}
