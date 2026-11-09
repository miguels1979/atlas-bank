package com.atlas.bank.transaction.service.transfer;


import com.atlas.bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.account.model.Account;
import com.atlas.bank.application.port.in.TransferMoneyUseCase;
import com.atlas.bank.application.port.out.AccountRepositoryPort;
import com.atlas.bank.transaction.model.Transaction;
import com.atlas.bank.transaction.repository.TransactionRepository;
import com.atlas.bank.transaction.service.TransactionProcessor;
import com.atlas.bank.transaction.service.domain.TransferDomainService;
import com.atlas.bank.transaction.service.factory.TransactionFactory;
import com.atlas.bank.transaction.service.fee.FeeCalculator;
import com.atlas.bank.transaction.validation.chain.TransferValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService extends TransactionProcessor<TransferContext> implements ITransferService, TransferMoneyUseCase {

    private final AccountRepositoryPort accountRepository;
    private final List<FeeCalculator> feeCalculators;
    private final List<TransferValidator> validators;
    private final TransferDomainService transferDomainService;

    public TransferService(TransactionRepository transactionRepository ,
                           AccountRepositoryPort accountRepository,
                           List<FeeCalculator> feeCalculators,
                           List<TransferValidator> validators,
                           TransferDomainService transferDomainService) {
        super(transactionRepository);
        this.accountRepository = accountRepository;
        this.feeCalculators = feeCalculators;
        this.validators = validators;
        this.transferDomainService = transferDomainService;
    }


    @Transactional
    @Override
    public Transaction execute (Long fromId, Long toId, BigDecimal amount) {
        // Buscar cuentas
        Account from = accountRepository.findById(fromId).orElseThrow(()-> new AccountNotFoundException(fromId));
        Account to = accountRepository.findById(toId).orElseThrow(()-> new AccountNotFoundException(toId));

        Transaction transaction = process(new TransferContext(from, to, amount));

        transaction.executedTransfer();
        transactionRepository.save(transaction);

       return  transaction;
    }

    @Override
    protected void validate(TransferContext context) {
        validators.forEach(validator -> validator.validate(context));
    }

    @Override
    protected BigDecimal calculateFee(TransferContext context) {
        return feeCalculators.stream()
                .filter(feeCalculator -> feeCalculator.supports(context.from().getType()))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("No hay calculador para el tipo " + context.from().getType()))
                .calculate(context.amount());
    }


    @Override
    protected void updateBalances(TransferContext context, BigDecimal fee) {

        transferDomainService.transfer(context.from(), context.to(), context.amount(),fee);
        accountRepository.save(context.from());
        accountRepository.save(context.to());

    }

    @Override
    protected Transaction save(TransferContext context, BigDecimal fee) {
        Transaction transaction = TransactionFactory.createTransfer(context, fee);
        return transactionRepository.save(transaction);
    }
}
