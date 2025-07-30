package net.allaoua.digitalbanking.dtos.currentaccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.allaoua.digitalbanking.dtos.customer.CustomerResponseDto;
import net.allaoua.digitalbanking.enums.AccountStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentAccountResponseDto {
    private String accountId;
    private String currency;
    private double balance;
    private AccountStatus status;
    private LocalDateTime createdAt;
    private CustomerResponseDto customer;
    private double overDraft;

}
