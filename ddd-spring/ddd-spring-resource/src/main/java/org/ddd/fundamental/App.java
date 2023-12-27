package org.ddd.fundamental;

import org.ddd.fundamental.spring.resources.ClassPathResources;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println(ClassPathResources.input("application.properties") );
    }
}
