package net.allaoua.digitalbanking.repository;

import net.allaoua.digitalbanking.entity.BankAccount;
import net.allaoua.digitalbanking.entity.Operation;
import net.allaoua.digitalbanking.enums.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    Page<Operation> findByAccountId(String accountId, Pageable pageable);
    Page<Operation> findByAccountIdAndType(String accountId, OperationType type, Pageable pageable);

}
