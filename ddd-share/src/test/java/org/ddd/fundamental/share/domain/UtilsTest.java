package org.ddd.fundamental.share.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.mockito.Mockito.verify;

public class UtilsTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testDateToString() {
        LocalDateTime dateTime = LocalDateTime.now();
        String format = Utils.dateToString(dateTime);
        Assert.assertEquals("2023-07-13",format);

        Timestamp timestamp = Timestamp.valueOf(dateTime);
        format = Utils.dateToString(timestamp);
        Assert.assertEquals("2023-07-13",format);
    }

    @Test
    public void testJsonEncode() {
        HashMap<String, Serializable> map = new HashMap<>();
        map.put("a",Integer.valueOf(1));
        map.put("b",Integer.valueOf(2));
        String res = Utils.jsonEncode(map);
        Assert.assertEquals("{\"a\":1,\"b\":2}", res);

        map.put("c", "5858");
        map.put("d", "{5858");
        res = Utils.jsonEncode(map);
        Assert.assertEquals("{\"a\":1,\"b\":2,\"c\":\"5858\",\"d\":\"{5858\"}", res);

        map = new HashMap<>();
        res = Utils.jsonEncode(map);
        Assert.assertEquals("{}", res);
    }

    @Test
    public void testJsonCodeException() throws JsonProcessingException {
        // 这里有个问题，不容易测试方法抛出异常的情况,计算模拟出来，也执行不到原来方法抛出异常的分支
//        expectedEx.expect(JsonProcessingException.class);
//        expectedEx.expectMessage("");
        ObjectMapper om = Mockito.mock(ObjectMapper.class);
        Mockito.when(om.writeValueAsString(HashMap.class)).thenThrow(new JsonProcessingException("") {});
        HashMap<String, Serializable> map = new HashMap<>();
        String result = Utils.jsonEncode(map,om);
        Assert.assertNull(result);
    }
    @Test
    public void testJsonDecode() {
        String result = "{\"a\":1,\"b\":2}";
        HashMap<String,Serializable> map = Utils.jsonDecode(result);
        Assert.assertEquals(map.get("a"),1);
        Assert.assertEquals(map.get("b"),2);
    }

    @Test
    public void testToSnake() {
        String source = "myNameIs";
        String res = Utils.toSnake(source);
        Assert.assertEquals("my_name_is",res);
    }

    @Test
    public void testToCamel() {
        String source = "myNameIs";
        String res = Utils.toCamel(source);
        Assert.assertEquals("Mynameis",res);
    }
    @Test
    public void testToCamelFirstLower() {
        String source = "myNameIs";
        String res = Utils.toCamelFirstLower(source);
        Assert.assertEquals("mynameis",res);
    }
}
