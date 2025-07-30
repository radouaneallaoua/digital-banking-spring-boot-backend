package net.allaoua.digitalbanking.dtos.account_operation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.allaoua.digitalbanking.enums.OperationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountOperationRequestDto {
    @NotBlank(message = "amount is required")
    @Min(value = 0,message = "Amount can not be less that 0")
    private double amount;
    @NotNull
    private OperationType type;
    @NotNull
    private String account;
    @NotBlank
    private String description;
}
