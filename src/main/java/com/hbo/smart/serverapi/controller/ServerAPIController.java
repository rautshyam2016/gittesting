package com.hbo.smart.serverapi.controller;

import com.hbo.smart.serverapi.service.APIClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * ServerAPIController
 * Routes the requests based on chosen ApiClientService.
 */
public class ServerAPIController {

    private APIClientService apiClientService;

    public void init(APIClientService smartapiClient) {
        this.apiClientService = smartapiClient;
    }

    @GetMapping("**")
    public ResponseEntity<?> get(HttpServletRequest httpServletRequest) {
        return apiClientService.get(httpServletRequest);
    }

    @PostMapping("**")
    public ResponseEntity<Object> post(@RequestBody Object requestData, HttpServletRequest httpServletRequest) {
        return apiClientService.post(requestData, httpServletRequest);
    }

    @DeleteMapping("**")
    public ResponseEntity<Object> delete(HttpServletRequest httpServletRequest) {
        return apiClientService.delete(httpServletRequest);
    }

    @PutMapping("**")
    public ResponseEntity<Object> put(@RequestBody Object requestData, HttpServletRequest httpServletRequest) {
        return apiClientService.put(requestData, httpServletRequest);
    }

}
