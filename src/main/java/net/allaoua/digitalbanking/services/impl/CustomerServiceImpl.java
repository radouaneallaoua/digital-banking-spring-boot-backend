package net.allaoua.digitalbanking.services.impl;

import net.allaoua.digitalbanking.dtos.CustomerRequestDto;
import net.allaoua.digitalbanking.dtos.CustomerResponseDto;
import net.allaoua.digitalbanking.entity.Customer;
import net.allaoua.digitalbanking.exceptions.CustomerNotFoundException;
import net.allaoua.digitalbanking.repository.CustomerRepository;
import net.allaoua.digitalbanking.services.ICustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<CustomerResponseDto> addCustomer(CustomerRequestDto customer) {
        Customer entity=modelMapper.map(customer, Customer.class);
        entity=customerRepository.save(entity);
        return  ResponseEntity.ok(modelMapper.map(entity, CustomerResponseDto.class));
    }

    @Override
    public ResponseEntity<CustomerResponseDto> updateCustomer(String customerId, CustomerRequestDto customer) {
        customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        Customer entity=modelMapper.map(customer, Customer.class);
        entity.setId(customerId);
        entity=customerRepository.save(entity);
        return  ResponseEntity.ok(modelMapper.map(entity, CustomerResponseDto.class));
    }

    @Override
    public ResponseEntity<String> deleteCustomer(String customerId) {
        Customer foundCustomer=customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
        customerRepository.deleteById(foundCustomer.getId());
        return  ResponseEntity.ok("Customer deleted successfully");
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<List<CustomerResponseDto>> allCustomers(Pageable pageable) {
        Page<Customer> allCustomers = customerRepository.findAll(pageable);
        return ResponseEntity.ok(allCustomers.stream().map(c->modelMapper.map(c, CustomerResponseDto.class)).toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<List<CustomerResponseDto>> allCustomersByEmail(String email,Pageable pageable) {
        Page<Customer> allCustomers = customerRepository.findByEmail(email,pageable);
        return ResponseEntity.ok(allCustomers.stream().map(c->modelMapper.map(c, CustomerResponseDto.class)).toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<List<CustomerResponseDto>> allCustomersByName(String name,Pageable pageable) {
        Page<Customer> allCustomers = customerRepository.findByName(name,pageable);
        return ResponseEntity.ok(allCustomers.stream().map(c->modelMapper.map(c, CustomerResponseDto.class)).toList());
    }
}
