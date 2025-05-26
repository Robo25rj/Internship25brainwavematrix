package com.atm.Atm_interface_internship.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.Atm_interface_internship.dto.Account;
import com.atm.Atm_interface_internship.dto.Transactions;
import com.atm.Atm_interface_internship.repository.AccountRepository;
import com.atm.Atm_interface_internship.repository.TransactionsRepository;

@Service
public class AtmService {
   
	@Autowired
	private  AccountRepository accRepo;
	@Autowired
	private TransactionsRepository transRepo;
	
	public AtmService(AccountRepository accRepo, TransactionsRepository transRepo) {
	
		this.accRepo = accRepo;
		this.transRepo = transRepo;
	}
	
	//login method to  check whether user acc exist 
	public Account login(String accNum, String pin) {
        return accRepo.findByAccountNoAndPin(accNum, pin);
    }
	
	//to get balance
	public double getBalance(Account account) {
        return account.getBalance();
    }
	
	// to deposit cash
	public void deposit(Account account, double amount) {   
		
        account.setBalance(account.getBalance() + amount);
        
        //to insert every transaction after every deposit/ create new object for every transaction
        
        Transactions tran = new Transactions();
        tran.setType("DEPOSIT");
        tran.setAmount(amount);
        tran.setTimestamp(LocalDateTime.now());
        tran.setAccount(account);
        tran.setBalance(account.getBalance());

        accRepo.save(account);  //to save account repo with transaction
        transRepo.save(tran);
    }
	
	//to withdraw cash
	public boolean withdraw(Account account, double amount) {
        if (amount > account.getBalance()) {
        	
        	return false;
        }
        else {
        account.setBalance(account.getBalance() - amount);

        Transactions tran = new Transactions();
        tran.setType("WITHDRAW");
        tran.setAmount(amount);
        tran.setTimestamp(LocalDateTime.now());
        tran.setAccount(account);
        
        tran.setBalance(account.getBalance());

        accRepo.save(account);
        transRepo.save(tran);
        return true;
        }
     }
	
	//to see transaction we need to retrieve data from list of transactions from account
	
	public List<Transactions> getTransactionHistory(Account account) {  
        return transRepo.findByAccount(account);
    }
	
	//to delete old first 5 transaction
	
	public void deleteFirstFiveTransactions(String accountNumber, String pin) {
		
	    Account acc = accRepo.findById(accountNumber)
	        .orElseThrow(() -> new RuntimeException("Account not found"));

	    if (!acc.getPin().equals(pin)) {
	        throw new RuntimeException("Invalid PIN");
	    }

	    List<Transactions> firstFive = transRepo
	        .findTop5ByAccountOrderByTimestampAsc(acc);

	    transRepo.deleteAll(firstFive);
	}

}
