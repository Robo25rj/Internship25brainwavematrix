package com.atm.Atm_interface_internship.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Account {
    
	@Id
	private String accountNo;
	private String pin;
	private double balance;
	
	
	@OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
	private List<Transactions> transactions=new ArrayList<>();    //to store list of transactions
	
	//getter and setter
	public String getAccountNo() {
		return accountNo;
	}
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

	//toString();
	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", pin=" + pin + ", balance=" + balance + "]";
	}
	
	
	
	
}
