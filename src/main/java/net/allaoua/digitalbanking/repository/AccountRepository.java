package net.allaoua.digitalbanking.repository;

import net.allaoua.digitalbanking.entity.BankAccount;
import net.allaoua.digitalbanking.entity.Customer;
import net.allaoua.digitalbanking.enums.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<BankAccount, String> {
    Page<BankAccount> findByCustomerId(String customerId, Pageable pageable);
    Page<BankAccount> findByStatus(AccountStatus status, Pageable pageable);
}
