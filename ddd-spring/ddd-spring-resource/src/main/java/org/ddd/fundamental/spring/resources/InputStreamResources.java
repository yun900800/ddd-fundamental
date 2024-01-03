package org.ddd.fundamental.spring.resources;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamResources {
    public static String input(String path) throws IOException {
        InputStream isSource = new ByteArrayInputStream(path.getBytes());
        Resource resource = new InputStreamResource(isSource);
        try (InputStream is = resource.getInputStream()) {
            // 读取和处理资源内容
            String  result = new String(is.readAllBytes());
            return result;
        }
    }
}
