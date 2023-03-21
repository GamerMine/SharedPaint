package fr.sae402.sharedpaint.networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class NetworkUtil {
    public static byte[] conversionByte(Object o) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream =new ObjectOutputStream(byteStream);
        objectStream.writeObject(o);

        return byteStream.toByteArray();
    }
}
