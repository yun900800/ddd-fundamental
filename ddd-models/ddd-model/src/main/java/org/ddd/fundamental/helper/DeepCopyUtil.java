package org.ddd.fundamental.helper;

import java.io.*;

public final class DeepCopyUtil {
    public static <T extends Serializable> T deepCopy(T object) {


        T deepCopyObject = null;


        try {


            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();


            ObjectOutputStream outStream = new ObjectOutputStream(byteOutStream);


            outStream.writeObject(object);


            outStream.flush();


            ByteArrayInputStream byteInStream = new ByteArrayInputStream(byteOutStream.toByteArray());


            ObjectInputStream inStream = new ObjectInputStream(byteInStream);


            deepCopyObject = (T) inStream.readObject();


        } catch (IOException | ClassNotFoundException e) {


            e.printStackTrace();


        }


        return deepCopyObject;


    }
}
