package com.hbo.smart.serverapi.controller;


import com.hbo.smart.serverapi.service.APIClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * It is a spring RestController which serves the REST endpoint proxy for all API endpoints exposed by deals
 * microservice.
 */
@RestController
@RequestMapping("/api/deals-accrual/v1")
@Slf4j
public class DealController extends ServerAPIController {

    @Autowired
    @Qualifier("dealMSService")
    private APIClientService dealService;

    @PostConstruct
    public void init() {
        log.info(" Initiating Deal service !! ");
        super.init(dealService);
    }
}