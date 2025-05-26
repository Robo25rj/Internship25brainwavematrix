package com.atm.Atm_interface_internship.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atm.Atm_interface_internship.dto.Account;
import com.atm.Atm_interface_internship.dto.Transactions;
import com.atm.Atm_interface_internship.repository.AccountRepository;
import com.atm.Atm_interface_internship.service.AtmService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AtmController {
     
	@Autowired
	private AtmService atmService;

	private Account currentAcc;
    
	@Autowired
	private AccountRepository accRepo;
	public AtmController(AtmService atmService) {
		this.atmService = atmService;
	}
	
	@PostMapping("/login")
    public boolean login(@RequestBody Map<String, String> loginData) { // we are accepting acNo & pin in the key value pairs by using map
        String acc = loginData.get("accountNo");       
        String pin = loginData.get("pin");
        
        System.out.println(acc +" "+ pin);
        Account accOR=accRepo.findById(acc)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        
        if (!accOR.getPin().equals(pin)) {
            throw new RuntimeException("Invalid PIN");
        }else {
        	 System.out.println(acc +"ac exixst "+ pin);
        	
        	 currentAcc = atmService.login(acc, pin);
        }
       
        return currentAcc != null;
    }
	
	@GetMapping("/balance")
    public Map<String, Object> balance() {
        return Map.of("balance", atmService.getBalance(currentAcc));
    }
	
	@PostMapping("/deposit")
    public Map<String, String> deposit(@RequestBody Map<String, Object> body) {
        double amt = Double.parseDouble(body.get("amount").toString());
        atmService.deposit(currentAcc, amt);
        
        return Map.of("Success-status", "Success (^.^)");
    }
    
	
	@PostMapping("/withdraw")
    public Map<String, Boolean> withdraw(@RequestBody Map<String, Object> body) {
		
        double amt = Double.parseDouble(body.get("amount").toString());
        
        boolean success = atmService.withdraw(currentAcc, amt);
        return Map.of("Success-status", success);
    }
	
	@GetMapping("/history")
    public List<String> history() {
		
        List<Transactions> txns = atmService.getTransactionHistory(currentAcc);
        
        List<String> results = new ArrayList<>();
        
        for (Transactions t : txns) {
            results.add(t.getType() + " ₹" + t.getAmount() + " on " + t.getTimestamp()+" Balance :"+ t.getBalance());
        }
        
        return results;
    }
	
	@DeleteMapping("/delete-transactions")
	public ResponseEntity<String> deleteFirstFive(@RequestBody Map<String, String> request) {
	    String accountNumber = request.get("accountNo");
	    String pin = request.get("pin");

	    atmService.deleteFirstFiveTransactions(accountNumber, pin);
	    return ResponseEntity.ok("First 5 transactions deleted successfully.");
	}


}
