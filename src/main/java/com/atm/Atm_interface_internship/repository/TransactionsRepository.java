package com.atm.Atm_interface_internship.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atm.Atm_interface_internship.dto.Account;
import com.atm.Atm_interface_internship.dto.Transactions;

@Repository
public interface TransactionsRepository  extends JpaRepository<Transactions, Long>{

	List<Transactions> findByAccount(Account account);

	List<Transactions> findTop5ByAccountOrderByTimestampAsc(Account acc);
}
