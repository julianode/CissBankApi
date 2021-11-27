package com.cissbank.basiccissbankapi.service;

import com.cissbank.basiccissbankapi.vo.AccountBalance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountUtilitiesService {

    @GetMapping("/balance")
    public AccountBalance getAccountBalance(@RequestParam(value="accountNumber") int accountNumber) {
        // TODO: get account balance from database.
        return new AccountBalance();
    }


}
