package com.atm.Atm_interface_internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atm.Atm_interface_internship.dto.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

	Account findByAccountNoAndPin(String accountNo,String pin);
}
