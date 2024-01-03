package org.ddd.fundamental;

import org.ddd.fundamental.spring.documentloader.DocumentLoaders;
import org.ddd.fundamental.spring.resources.ClassPathResources;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println(ClassPathResources.input("application.properties") );
        DocumentLoaders.load();
    }
}
