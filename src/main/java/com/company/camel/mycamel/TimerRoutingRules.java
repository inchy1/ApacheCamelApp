package com.company.camel.mycamel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TimerRoutingRules extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:foo?fixedRate=true&period=100000").routeId("myTimer")
                .setBody(constant("Hello from Camel"))
                .to("jms:topic:invoices");
        from("jms:topic:invoices").routeId("fileGenerator")
                .to("file:/invoices")
                .to("log:com.company.camel.mycamel.TimerRoutingRules?level=INFO&showException=true");

    }
}
