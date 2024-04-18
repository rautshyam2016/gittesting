package com.hbo.smart.serverapi.controller;


import com.hbo.smart.serverapi.service.APIClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * It is a spring RestController which serves the REST endpoint proxy for all API endpoints exposed by billing
 * microservice.
 */
@RestController
@RequestMapping("/api/billing-invoice/v1")
public class BillingEngineController extends ServerAPIController {

    @Autowired
    @Qualifier("billingEngineMSService")
    private APIClientService billingService;

    @PostConstruct
    public void init() {
        super.init(billingService);
    }
}