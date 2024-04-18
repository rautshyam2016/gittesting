package com.hbo.smart.serverapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hbo.smart.serverapi.service.APIClientService;

import javax.annotation.PostConstruct;

/**
 * It is a spring RestController which serves the REST endpoint proxy for all API endpoints exposed by admin
 * microservice.
 */
@RestController
@RequestMapping("/api/admin/v1")
public class AdminController extends ServerAPIController {

    @Autowired
    @Qualifier("adminMSService")
    private APIClientService adminService;

    @PostConstruct
    public void init() {
        super.init(adminService);
    }
}

