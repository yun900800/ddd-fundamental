package org.ddd.fundamental.share;

import org.ddd.fundamental.share.domain.Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class),
        value = {"org.ddd.fundamental.share"}
)
public class DddShareTestApp {
    public static void main(String[] args) {
        SpringApplication.run(DddShareTestApp.class,args);
    }
}
