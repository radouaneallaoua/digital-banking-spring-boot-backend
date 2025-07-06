package net.allaoua.digitalbanking.web;

import jakarta.validation.Valid;
import net.allaoua.digitalbanking.dtos.CustomerRequestDto;
import net.allaoua.digitalbanking.dtos.CustomerResponseDto;
import net.allaoua.digitalbanking.services.ICustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    ResponseEntity<List<CustomerResponseDto>> allCustomers(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size ) {
        return customerService.allCustomers(PageRequest.of(page, size));
    }
    @PostMapping
    ResponseEntity<CustomerResponseDto> addCustomer(@Valid @RequestBody CustomerRequestDto customer) {
        return customerService.addCustomer(customer);
    }

    @PutMapping("{customerId}")
    ResponseEntity<CustomerResponseDto> updateCustomer( @PathVariable String customerId,@Valid @RequestBody CustomerRequestDto customer) {
        return customerService.updateCustomer(customerId,customer);
    }
    @DeleteMapping("{customerId}")
    ResponseEntity<String> deleteCustomer( @PathVariable String customerId) {
        return customerService.deleteCustomer(customerId);
    }
}
