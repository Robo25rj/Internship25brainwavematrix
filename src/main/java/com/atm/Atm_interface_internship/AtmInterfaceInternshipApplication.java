package com.atm.Atm_interface_internship;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.atm.Atm_interface_internship.dto.Account;
import com.atm.Atm_interface_internship.repository.AccountRepository;

@SpringBootApplication
public class AtmInterfaceInternshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmInterfaceInternshipApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(AccountRepository accountRepo) {
	    return args -> {
	        if (!accountRepo.existsById("12345678")) {
	            Account acc = new Account();
	            acc.setAccountNo("12345678");
	            acc.setPin("1234");
	            acc.setBalance(5000.0);
	            accountRepo.save(acc);
	        }
	    };
	}
}
