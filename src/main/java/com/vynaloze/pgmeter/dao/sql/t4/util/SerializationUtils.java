package com.vynaloze.pgmeter.dao.sql.t4.util;

import java.io.*;

public class SerializationUtils {
    public static byte[] toByteArray(final Object object) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object toObject(final byte[] array) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(array);
             ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
