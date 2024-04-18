package com.hbo.smart.serverapi.service;

import com.hbo.common.rest.client.config.RestClientProperties;
import com.hbo.common.rest.client.exception.RestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * Class that provides service to make GET,PUT,POST,DELETE HTTP API calls to configured REST API servers such as
 * user admin, affiliate & subscriber etc.
 */
public class APIClientService {

    private static final Logger logger
            = LoggerFactory.getLogger(APIClientService.class.getSimpleName());

    @Autowired
    private RestClientProperties restClientProperties;

    @Inject
    private OAuth2RestTemplate oAuth2RestTemplate;

    /**
     * Base URL of the API server which this service communicates with.
     */
    private String baseUrl;

    public APIClientService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Service for making POST request to API endpoints.
     *
     * @param requestData        HTTP request body
     * @param httpServletRequest request
     * @return response from API endpoint call.
     */
    public ResponseEntity<Object> post(Object requestData, HttpServletRequest httpServletRequest) {
        String uriString = this.getURIBuilder(httpServletRequest).toUriString();
        try {
            logger.info("POST method call to : "+uriString);
            return oAuth2RestTemplate.postForEntity(uriString, requestData, Object.class);
        } catch (Exception exception) {
            logger.warn("Exception while calling " + uriString , exception);
            return this.handleException(exception);
        }
    }

    /**
     * Service for making GET request to API endpoints.
     *
     * @param httpServletRequest request
     * @return response from API endpoint call.
     */
    public ResponseEntity<?> get(HttpServletRequest httpServletRequest) {
        final String uriString = this.getURIBuilder(httpServletRequest).toUriString();
        try {
            logger.info("GET method call to : "+uriString);
            if (uriString.contains("export")) {// TODO: Find better solution to avoid this if condition.
                return oAuth2RestTemplate.getForEntity(uriString, byte[].class);
            }
            return oAuth2RestTemplate.getForEntity(uriString, Object.class);
        } catch (Exception exception) {
            logger.warn("Exception while calling " + uriString , exception);
            return this.handleException(exception);
        }
    }

    /**
     * Service for making DELETE request to API endpoints.
     *
     * @param httpServletRequest request
     * @return response from API endpoint call.
     */
    public ResponseEntity<Object> delete(HttpServletRequest httpServletRequest) {
        String uriString = this.getURIBuilder(httpServletRequest).toUriString();
        try {
            logger.info("DELETE method call to : "+uriString);
            oAuth2RestTemplate.delete(uriString);
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } catch (Exception exception) {
            logger.warn("Exception while calling " + uriString , exception);
            return this.handleException(exception);
        }
    }

    /**
     * Service for making PUT request to API endpoints.
     *
     * @param requestData        HTTP request body
     * @param httpServletRequest request
     * @return response from API endpoint call.
     */
    public ResponseEntity<Object> put(Object requestData, HttpServletRequest httpServletRequest) {
        String uriString =this.getURIBuilder(httpServletRequest).toUriString();
        try {
            logger.info("PUT method call to : "+uriString);
            return oAuth2RestTemplate.exchange(uriString, HttpMethod.PUT, new HttpEntity(requestData), Object.class);
        } catch (Exception exception) {
            logger.warn("Exception while calling " + uriString , exception);
            return this.handleException(exception);
        }

    }

    /**
     * @param httpServletRequest request that needs to be delegated.
     * @return prepares HTTP parameter builder for given <code>httpServletRequest</code>
     */
    private UriComponentsBuilder getURIBuilder(HttpServletRequest httpServletRequest) {
        logger.info("Before URIBuilder : "+httpServletRequest);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.getApiUrl(httpServletRequest));
        Map<String, String[]> requestParameterMap = httpServletRequest.getParameterMap();
        requestParameterMap.forEach((k, v) ->
                Arrays.asList(v).forEach(item ->
                        builder.queryParam(k, item)
                ));
        logger.info("URIBuilder : "+httpServletRequest);
        return builder;
    }

    /**
     * Prepares required API endpoint URL based on for which server resource this service is configured for.
     *
     * @param httpServletRequest request that needs to be delegated.
     * @return API endpoint URL to be called.
     */
    private String getApiUrl(HttpServletRequest httpServletRequest) {
        String smartWebAppUrl = this.restClientProperties.getApis().get("smart-webapp").getBaseUrl();
        String apiURL = httpServletRequest.getRequestURL().toString().replace(smartWebAppUrl, this.baseUrl);
        logger.info("getApiUrl method call to : "+apiURL);
        return apiURL;
    }

    /**
     * Prepares a suitable response for given <code>exception</code>
     *
     * @param exception an exception to process
     * @return response entity representing given <code>exception</code>
     */
    private ResponseEntity<Object> handleException(Exception exception) {
        if (exception instanceof RestException) {
            RestException restException = (RestException) exception;
            return new ResponseEntity<Object>(restException, HttpStatus.valueOf(restException.getStatus()));
        }
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
