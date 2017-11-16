package com.company.camel.mycamel;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class HttpRoutingRules extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:start")
            .to("http://shahmati.com/chess/util/stats").routeId("httpRout")
                .convertBodyTo(String.class)
                .process(HttpRoutingRules::processShahmatiResponse)
                .to("log:shahmati.com").routeId("logOfShahmati");


        from("timer:shahmati?period=10s").routeId("shahmatiTimer")
                .to("direct:start");
    }

    private static void processShahmatiResponse(Exchange exchange) {
        String shahmatiResponse = exchange.getIn().getBody(String.class);
        int from = shahmatiResponse.indexOf("\"onlineUsers\":");
        int to = shahmatiResponse.indexOf(",\"onlineGames\"");
        String onlineUsers = shahmatiResponse.substring(from + "\"onlineUsers\":".length(), to);
        exchange.getIn().setBody("There are currently " + onlineUsers.trim() + " players online on shahmati.com");
    }

}
