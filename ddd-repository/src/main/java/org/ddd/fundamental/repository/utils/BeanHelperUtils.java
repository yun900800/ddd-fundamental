package org.ddd.fundamental.repository.utils;

import java.io.*;
import java.util.List;

public final class BeanHelperUtils {

    public static <T> List<T> deepCopy(List<T> src) {
        List<T> dest;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(src);

            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream objectInputStream =new ObjectInputStream(in);
            dest = (List<T>) objectInputStream.readObject();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return dest;
    }
}
