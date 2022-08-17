package com.example.kart.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kart.dto.WalletDto;
import com.example.kart.entity.Wallet;
import com.example.kart.service.WalletService;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

	@Autowired
	WalletService walletService;

	@PutMapping("/add-amount/{customerId}")
	public ResponseEntity<Object> addMoneyToWallet(@RequestBody WalletDto walletDto, @PathVariable long customerId) {
		Wallet wallet = null;
		try {
			wallet = walletService.addMoney(walletDto, customerId);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (wallet != null)
			return new ResponseEntity<Object>(wallet, HttpStatus.OK);
		else
			return new ResponseEntity<Object>(wallet, HttpStatus.OK);
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<Object> getWalletBalance(@PathVariable long customerId) {
		Wallet wallet = null;
		try {
			wallet = walletService.geetWalletBalanc(customerId);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (wallet != null)
			return new ResponseEntity<Object>(wallet, HttpStatus.OK);
		else
			return new ResponseEntity<Object>(wallet, HttpStatus.OK);
	}

}
