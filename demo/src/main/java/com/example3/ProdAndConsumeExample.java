package com.example3;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ProdAndConsumeExample {
    public static void main(String[] args) throws Exception {
        
        CamelContext context = new DefaultCamelContext();


        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String message = exchange.getIn().getBody(String.class);
                        message = message + "- By Jakub Gaba";

                        exchange.getOut().setBody(message);
                    }
                })
                .to("seda:end");
            }
        });

        context.start();
        ProducerTemplate prodTemplate = context.createProducerTemplate();

        prodTemplate.sendBody("direct:start", "Hello yal all");


        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        String message = consumerTemplate.receiveBody("seda:end", String.class);

        System.out.println(message);
    }
}
