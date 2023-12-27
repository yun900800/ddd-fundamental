package org.ddd.fundamental.spring.resources;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class ClassPathResources {

    public static String input(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        try (InputStream is = resource.getInputStream()) {
            // 读取和处理资源内容
            String result = new String(is.readAllBytes());
            return result;
        }
    }
}
