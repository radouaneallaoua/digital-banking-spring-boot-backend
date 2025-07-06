package net.allaoua.digitalbanking.repository;

import net.allaoua.digitalbanking.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Page<Customer> findByEmail(String email, Pageable pageable);
    Page<Customer> findByName(String name, Pageable pageable);
}
