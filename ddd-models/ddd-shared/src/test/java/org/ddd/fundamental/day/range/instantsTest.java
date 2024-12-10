package org.ddd.fundamental.day.range;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.day.Instants;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
public class instantsTest {

    @Test
    public void testInstant(){
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("localDateTime is {}",localDateTime);
        ZoneId zoneId = ZoneId.systemDefault(); // 获取系统默认时区
        log.info("zoneId is {}",zoneId);
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        log.info("zonedDateTime is {}",zonedDateTime);
        Instant instant = zonedDateTime.toInstant();
        String result = Instants.FORMATTER.format(instant);
        log.info("result is {}",result);
    }
}
