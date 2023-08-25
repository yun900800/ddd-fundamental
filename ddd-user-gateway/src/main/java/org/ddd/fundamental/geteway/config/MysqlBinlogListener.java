package org.ddd.fundamental.geteway.config;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MysqlBinlogListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlBinlogListener.class);

    public void init() throws IOException {
        BinaryLogClient client = new BinaryLogClient("localhost", 3306, "root", "rootpassword");
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(new BinaryLogClient.EventListener() {

            @Override
            public void onEvent(Event event) {
                LOGGER.info("binlog event:{}",event);
            }
        });
        client.connect();
    }
}
