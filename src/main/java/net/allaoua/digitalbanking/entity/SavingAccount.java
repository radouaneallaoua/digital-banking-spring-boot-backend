package net.allaoua.digitalbanking.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("SA")
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccount extends BankAccount {
    private double interestRate;
}
