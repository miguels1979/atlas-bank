package com.atlas.bank.account.model;

import com.atlas.bank.domain.model.account.AccountStatus;
import com.atlas.bank.domain.model.account.AccountType;
import com.atlas.bank.domain.model.shared.Currency;
import com.atlas.bank.shared.model.Email;
import com.atlas.bank.shared.model.Money;
import com.atlas.bank.domain.exception.InsufficientFundsException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false))
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType type; //SAVINGS, CHECKING

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "balance", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "currency", nullable = false, length = 3))
    })
    private Money balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status; //ACTIVE, CLOSED, FROZEN

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "customer_id")
    private Long customerId;

    @PrePersist //Asignar valores por defecto
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        if(status == null) status = AccountStatus.ACTIVE;
        if(balance == null) balance = Money.zero(Currency.MXN);

    }

    public void deposit(Money amount){
        if(amount.isNegative()) throw new IllegalArgumentException("El monto a depositar no puede ser negativo");
        this.balance = this.balance.add(amount);
    }

    public void withdraw(Money amount){
        if(amount.isNegative()) throw new IllegalArgumentException("El monto a retirar no puede ser negativo");
        if(balance.isLessThan(amount)) throw new InsufficientFundsException(id,balance.getAmount(), amount.getAmount());
        this.balance = this.balance.subtract(amount);
    }



}
