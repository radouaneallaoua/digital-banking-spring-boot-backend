package net.allaoua.digitalbanking;

import net.allaoua.digitalbanking.entity.CurrentAccount;
import net.allaoua.digitalbanking.entity.Customer;
import net.allaoua.digitalbanking.entity.Operation;
import net.allaoua.digitalbanking.entity.SavingAccount;
import net.allaoua.digitalbanking.enums.AccountStatus;
import net.allaoua.digitalbanking.enums.OperationType;
import net.allaoua.digitalbanking.repository.AccountRepository;
import net.allaoua.digitalbanking.repository.CustomerRepository;
import net.allaoua.digitalbanking.repository.OperationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApplication.class, args);
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository, AccountRepository accountRepository, OperationRepository operationRepository) {
        return args -> {
            Stream.of("Radouane", "Hassan", "Rachid", "Mohamed").forEach(name -> {
                Customer customer = Customer.builder()
                        .name(name)
                        .email(name + "@gmail.com")
                        .build();
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(Math.random() * 9000);
                currentAccount.setBalance(20000);
                currentAccount.setCurrency("MAD");
                currentAccount.setStatus(AccountStatus.CREATED);
                accountRepository.save(currentAccount);
                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                savingAccount.setBalance(20000);
                savingAccount.setCurrency("MAD");
                savingAccount.setStatus(AccountStatus.CREATED);
                accountRepository.save(savingAccount);
            });
            accountRepository.findAll().forEach(account -> {
                Operation operation=new Operation();
                operation.setAccount(account);
                operation.setType(OperationType.DEBIT);
                operation.setAmount(Math.random() * 10000);
                operation.setDescription("une operation de debit");

                operationRepository.save(operation);
                Operation operation2=new Operation();
                operation2.setAccount(account);
                operation2.setType(OperationType.CREDIT);
                operation2.setAmount(Math.random()*2000);
                operation2.setDescription("une operation de credit");

                operationRepository.save(operation2);
            });

        };
    }
}
