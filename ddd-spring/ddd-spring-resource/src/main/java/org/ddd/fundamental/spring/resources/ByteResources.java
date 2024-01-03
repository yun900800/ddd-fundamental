package org.ddd.fundamental.spring.resources;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class ByteResources {

    public static String input(byte[] input) throws IOException {
        Resource resource = new ByteArrayResource(input);
        try (InputStream is = resource.getInputStream()) {
            // 读取和处理资源内容
            String result = new String(is.readAllBytes());
            return result;
        }
    }
}
