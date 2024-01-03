package org.ddd.fundamental.spring.resources;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.io.InputStream;

public class UrlResources {
    public static String input(String path) throws IOException {
        path = "https://dist.apache.org/repos/dist/test/test.txt";
        Resource resource = new UrlResource(path);
        try (InputStream is = resource.getInputStream()) {
            // 读取和处理资源内容
            String result = new String(is.readAllBytes());
            return result;
        }
    }
}
