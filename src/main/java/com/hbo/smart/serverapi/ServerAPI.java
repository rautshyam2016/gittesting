package com.hbo.smart.serverapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * It is the main class of the microservice annotated with SpringBootApplication and responsible to start the
 * microservice. This will intern build all the controller and service. It also loads AppConfig on start which
 * creates  multiple objects of type APIClientService bean passing it RestClientProperties and identifier of module
 * which it needs to serve. Meaning there will be one service bean of type APIClientService for each controller which
 * knows which API endpoints to deal with.
 */
@Controller
@SpringBootApplication
public class ServerAPI implements ErrorController {

    public static void main(String[] args) {
        SpringApplication.run(ServerAPI.class, args);
    }

    /**
     * forward all errors to root.
     * @return string mapping.
     */
    @RequestMapping("/error")
    public String index() {
        return "forward:/";
    }

    /**
     * Error path.
     * @return error path as forward:/.
     */
    @Override
    public String getErrorPath() {
        return "forward:/";
    }
}
