package org.ddd.fundamental;

import org.ddd.fundamental.design.chains.ChainStepFacade;
import org.ddd.fundamental.design.chains.annotation.GenericMessageAnnotation;
import org.ddd.fundamental.design.chains.annotation.MessageAnnotation;
import org.ddd.fundamental.design.chains.dto.GenericMessage;
import org.ddd.fundamental.design.chains.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import java.util.Map;


@SpringBootApplication
public class ModelApp {


    @Autowired
    private ChainStepFacade<Message> chainStepFacade;

    //@Autowired
    //private ChainStepFacade<GenericMessage> chainStepFacadeGeneric;
    public static void main(String[] args) {
        SpringApplication.run(ModelApp.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void onStart() {
        System.out.println("Result message: " + chainStepFacade.handle(
                new Message(
                        Map.of(
                                "ip", "10.10.10.10",
                                "userId", "someId",
                                "SESSIONID", "dfsjklfdjsafsdfadfa"
                        )
                )
        ));

        System.out.println("Result message generic: " + chainStepFacade.changeToGeneric().handle(
                new GenericMessage(
                        Map.of(
                                "ip", "10.10.10.10",
                                "userId", "someId",
                                "SESSIONID", "dfsjklfdjsafsdfadfa"
                        )
                )
        ));
    }

}
