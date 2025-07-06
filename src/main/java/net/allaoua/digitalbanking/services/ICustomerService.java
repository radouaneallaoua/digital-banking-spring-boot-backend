package net.allaoua.digitalbanking.services;

import net.allaoua.digitalbanking.dtos.CustomerRequestDto;
import net.allaoua.digitalbanking.dtos.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICustomerService {
    ResponseEntity<CustomerResponseDto> addCustomer(CustomerRequestDto customer);
    ResponseEntity<CustomerResponseDto> updateCustomer(String customerId,CustomerRequestDto customer);
    ResponseEntity<String> deleteCustomer(String customerId);
    ResponseEntity<List<CustomerResponseDto>> allCustomers(Pageable pageable);
    ResponseEntity<List<CustomerResponseDto>> allCustomersByEmail(String email, Pageable pageable);
    ResponseEntity<List<CustomerResponseDto>> allCustomersByName(String name, Pageable pageable);
}
