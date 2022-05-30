package com.project.wineshop;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import java.util.List;


/* EN application.properties:
    management.endpoints.web.exposure.include=*
    management.endpoints.web.base-path=/manage
    management.endpoint.health.show-components=always
    management.health.ping.enabled=false
    endpoints.trace.sensitive=false
    spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
 */

@Repository
public class CustomTraceRepository implements HttpTraceRepository {

    List<HttpTrace> lastTrace = new ArrayList<>();

    @Override
    public List<HttpTrace> findAll() {
        return lastTrace;
    }

    @Override
    public void add(HttpTrace trace) {
        if ("GET".equals(trace.getRequest().getMethod())) {
            lastTrace.add(trace);
        }
    }

}