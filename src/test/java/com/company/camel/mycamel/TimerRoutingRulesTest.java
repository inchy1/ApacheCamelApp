package com.company.camel.mycamel;

import com.company.camel.application.CamelApp;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = CamelApp.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisableJmx(true)
public class TimerRoutingRulesTest extends CamelTestSupport {

    private static final String MY_HEADER = "my_header";

    @Autowired
    private CamelContext camelContext;

    @Override
    protected CamelContext createCamelContext() throws Exception {
        return camelContext;
    }

    @EndpointInject(uri = "direct:myEndpoint")
    private ProducerTemplate endpoint;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RouteDefinition definition = context().getRouteDefinitions().get(0);
        definition.adviceWith(context(), new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                onException(Exception.class).maximumRedeliveries(0);
            }
        });
    }

    @Override
    public String isMockEndpointsAndSkip() {
        return "myEndpoint:put*";
    }

    @Test
    public void shouldSucceed() throws Exception {
        assertNotNull(camelContext);
        assertNotNull(endpoint);

        String expectedValue = "expectedValue";
        MockEndpoint mock = getMockEndpoint("mock:myEndpoint:put");
        mock.expectedMessageCount(1);
        mock.allMessages().body().isEqualTo(expectedValue);
        mock.allMessages().header(MY_HEADER).isEqualTo("testHeader");
        endpoint.sendBodyAndHeader("test", MY_HEADER, "testHeader");

        mock.assertIsSatisfied();
    }

}