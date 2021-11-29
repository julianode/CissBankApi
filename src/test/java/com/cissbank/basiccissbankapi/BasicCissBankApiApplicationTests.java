package com.cissbank.basiccissbankapi;

import com.cissbank.basiccissbankapi.common.enumeration.ActivationStatus;
import com.cissbank.basiccissbankapi.entity.client.Account;
import com.cissbank.basiccissbankapi.entity.ledger.LedgerTransaction;
import com.cissbank.basiccissbankapi.service.AccountCreationService;
import com.cissbank.basiccissbankapi.service.AccountUtilitiesService;
import com.cissbank.basiccissbankapi.service.HealthCheckService;
import com.cissbank.basiccissbankapi.vo.AccountBalance;
import com.cissbank.basiccissbankapi.vo.HealthCheck;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BasicCissBankApiApplicationTests {

	@Autowired
	private HealthCheckService healthCheckService;

	@Autowired
	private AccountCreationService accountCreationService;

	@Autowired
	private AccountUtilitiesService accountUtilitiesService;

	private String fredName = "Fred Flintstone";
	private String fredNationalRegistration = "123.456.789-10";

	@Test
	void accountCreationServiceTest() {
		HealthCheck healthCheck = healthCheckService.healthCheck();
		assertTrue(healthCheck.getContent().contains("CiSS Bank Basic API is running."));

		healthCheck = healthCheckService.healthCheck();
		assertEquals(2, healthCheck.getId());
	}

	@Test
	void applicationFullHappyPathTest() throws InterruptedException {

		Account account1 = accountCreationService.createAccount(fredName, fredNationalRegistration, false);
		assertEquals(ActivationStatus.INACTIVE, account1.getStatus());

		Account account2 = accountCreationService.createAccount(fredName, fredNationalRegistration, false);
		assertEquals(ActivationStatus.INACTIVE, account2.getStatus());

		// 2 accounts, one individual
		assertEquals(account1.getOwnerNationalRegistration(), account2.getOwnerNationalRegistration());

		int account1Number = account1.getNumber();
		int account2Number = account2.getNumber();
		accountCreationService.approveAccount(account1Number, true);
		accountCreationService.approveAccount(account2Number, true);

		Account account3 = accountCreationService.getAccount(account1Number);
		account1.setStatus(ActivationStatus.ACTIVE);
		account1.setDemonstrationAccount(true);
		assertEquals(account1, account3);

		List<Account> fredAccountList = accountCreationService.getAccounts(fredNationalRegistration);
		assertTrue(fredAccountList.contains(account3));
		assertTrue(fredAccountList.contains(accountCreationService.getAccount(account2Number)));

		AccountBalance account1Balance = accountUtilitiesService.getAccountBalance(account1Number);
		assertTrue(BigDecimal.ZERO.compareTo(account1Balance.getAmount()) == 0);

		Thread.sleep(10);
		boolean accountBalanceRequestedJustBeforeNow = account1Balance.getLocalMillisRequestedAt() < System.currentTimeMillis();
		assertTrue(accountBalanceRequestedJustBeforeNow);

		accountUtilitiesService.transfer(account1Number, account2.getNumber(), BigDecimal.TEN);
		Set<LedgerTransaction> account1Statement = accountUtilitiesService.getAccountStatement(account1Number, 0, 10);
		LedgerTransaction account1Transaction = (account1Statement.toArray(new LedgerTransaction[0]))[0];
		assertTrue(new BigDecimal("-10").compareTo(account1Transaction.getAmount()) == 0);

		accountCreationService.deleteAccount(account1Number);
		assertEquals(ActivationStatus.DEPRECATED, accountCreationService.getAccount(account1Number).getStatus());
	}
}
