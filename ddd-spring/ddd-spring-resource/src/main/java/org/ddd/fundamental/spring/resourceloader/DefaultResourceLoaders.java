package org.ddd.fundamental.spring.resourceloader;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

public class DefaultResourceLoaders {

    public static void load() {
        DefaultResourceLoader loader = new DefaultResourceLoader();

        // 从类路径加载资源
        Resource classpathResource = loader.getResource("classpath:application.properties");
        System.out.println("Classpath Exists= " + classpathResource.exists());

        // 加载文件系统中的资源
        Resource fileResource = loader.getResource("file:C:\\Users\\86135\\Desktop\\我的demo\\ddd-fundamental\\ddd-spring\\ddd-spring-resource\\myfile.txt");
        System.out.println("File Exists = " + fileResource.exists());
    }
}
