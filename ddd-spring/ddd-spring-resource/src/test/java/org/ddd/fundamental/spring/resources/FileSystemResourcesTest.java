package org.ddd.fundamental.spring.resources;

import org.junit.Assert;
import org.junit.Test;

public class FileSystemResourcesTest {
    @Test
    public void testFileSystemResourcesInput() {
        String path = "C:\\Users\\86135\\Desktop\\我的demo\\ddd-fundamental\\ddd-spring\\ddd-spring-resource\\myfile.txt";
        try {
            String result = FileSystemResources.input(path);
            Assert.assertEquals(result,"filename=a.txt");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
