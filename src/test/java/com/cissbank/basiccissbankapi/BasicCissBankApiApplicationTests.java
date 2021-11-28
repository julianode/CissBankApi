package com.cissbank.basiccissbankapi;

import com.cissbank.basiccissbankapi.common.enumeration.ActivationStatus;
import com.cissbank.basiccissbankapi.entity.client.Account;
import com.cissbank.basiccissbankapi.service.AccountCreationService;
import com.cissbank.basiccissbankapi.service.HealthCheckService;
import com.cissbank.basiccissbankapi.vo.HealthCheck;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BasicCissBankApiApplicationTests {

	@Autowired
	private HealthCheckService healthCheckService;

	@Autowired
	private AccountCreationService accountCreationService;

	private String fredName = "Fred Flintstone";
	private String fredNationalRegistration = "123.456.789-10";

	@Test
	void accountCreationServiceTest() {
		HealthCheck healthCheck = healthCheckService.healthCheck();
		assertTrue(healthCheck.getContent().contains("CiSS Bank Basic API is running."));
	}

	@Test
	void accountCreationServiceShouldReturnInactiveAccount() {

		Account account = accountCreationService.createAccount(fredName, fredNationalRegistration, false);
		assertEquals(account.getStatus(), ActivationStatus.INACTIVE);
	}
}
