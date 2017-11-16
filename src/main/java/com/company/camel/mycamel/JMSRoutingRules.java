package com.company.camel.mycamel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class JMSRoutingRules extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("jms:topic:invoices").routeId("jmsRouter")
                .process(this::myJmsProcessor)
                .to("log:myLogger");
    }

    private void myJmsProcessor(Exchange exchange) {
        exchange.getIn().setBody("I have got a new message from jms: " + exchange.getIn().getBody(String.class));
    }
}
