package org.ddd.fundamental.geteway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MysqlBinlogConfiguration {

    @Bean
    public MysqlBinlogListener mysqlBinlogListener() throws IOException {
        MysqlBinlogListener mysqlBinlogListener = new MysqlBinlogListener();
        mysqlBinlogListener.init();
        return mysqlBinlogListener;
    }
}
