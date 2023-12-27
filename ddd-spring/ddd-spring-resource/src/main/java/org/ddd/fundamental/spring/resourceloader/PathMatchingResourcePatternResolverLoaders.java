package org.ddd.fundamental.spring.resourceloader;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

public class PathMatchingResourcePatternResolverLoaders {
    public static void load() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        // 加载所有匹配的类路径资源
        Resource[] resources = resolver.getResources("classpath*:*.properties");
        for (Resource resource : resources) {
            System.out.println("Classpath = " + resource.getFilename());
        }

        // 加载文件系统中的所有匹配资源
        Resource[] fileResources = resolver.getResources("file:C:/Users/86135/Desktop/我的demo/ddd-fundamental/ddd-spring/ddd-spring-resource/*.txt");
        for (Resource resource : fileResources) {
            System.out.println("File = " + resource.getFilename());
        }
    }
}
