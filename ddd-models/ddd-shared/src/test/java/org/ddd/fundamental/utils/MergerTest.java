package org.ddd.fundamental.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class MergerTest {

    private Config createLocalConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Config config = mapper.readValue(this.getClass().getResourceAsStream("/localConfig.json"), Config.class);
        return config;
    }

    private Config createRemoteConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Config config = mapper.readValue(this.getClass().getResourceAsStream("/remoteConfig.json"), Config.class);
        return config;
    }

    @Test
    public void testMerge() throws IOException, IllegalAccessException, InstantiationException {
        Config merged = Merger.merge(createLocalConfig(), createRemoteConfig());
        log.info("merged is {}",merged);
    }
}
