package com.company.camel.mycamel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TimerRoutingRules extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:foo?fixedRate=true&period=10000").routeId("myTimer")
                .setBody(constant("Hello from Camel"))
                .to("jms:invoices");
        from("jms:invoices")
                .to("file:/invoices")
                .to("log:com.company.camel.mycamel.TimerRoutingRules?level=INFO&showException=true");

    }
}
