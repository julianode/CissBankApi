package com.cissbank.basiccissbankapi.service;

import com.cissbank.basiccissbankapi.vo.HealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HealthCheckService {

    private static final Logger log = LoggerFactory.getLogger("HealthCheckService");
    private static final String template = "CiSS Bank Basic API is running. [Start time: %s]";
    private final AtomicLong counter = new AtomicLong();
    private final long startTime = System.currentTimeMillis();

    @GetMapping("/healthCheck")
    public HealthCheck healthCheck() {

        Timestamp startTime = new Timestamp(this.startTime);
        log.info("System healthCheck requested. [count: {}]", counter.incrementAndGet());

        return new HealthCheck(counter.get(), String.format(template, startTime));
    }
}
