package com.hbo.smart.serverapi.controller;


import com.hbo.smart.serverapi.service.APIClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;


/**
 * It is a spring RestController which serves the REST endpoint proxy for all API endpoints exposed by affiliate
 * subscriber microservice.
 */
@RestController
@RequestMapping("/api/affiliate-subscriber/v1")
public class AffiliateSubscriberController extends ServerAPIController {

    @Autowired
    @Qualifier("affiliateSubscriberMSService")
    private APIClientService affiliateSubscriberService;

    @PostConstruct
    public void init() {
        super.init(affiliateSubscriberService);
    }
}
