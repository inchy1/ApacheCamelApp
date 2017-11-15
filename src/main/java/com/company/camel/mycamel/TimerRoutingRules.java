package com.company.camel.mycamel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TimerRoutingRules extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:foo?fixedRate=true&period=10000").routeId("myTimer")
                .to("log:com.company.camel.mycamel.TimerRoutingRules?level=INFO&showException=true");
    }
}
