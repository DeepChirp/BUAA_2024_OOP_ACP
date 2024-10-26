package utils;

import java.io.*;

public class ObjectStreamUtil {

    public static void writeDataToFile(String filePath, boolean boolValue, byte byteValue, char charValue,
            short shortValue, int intValue, long longValue, String utfValue) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeBoolean(boolValue);
            dos.writeByte(byteValue);
            dos.writeChar(charValue);
            dos.writeShort(shortValue);
            dos.writeInt(intValue);
            dos.writeLong(longValue);
            dos.writeUTF(utfValue);
        }
    }

    public static void readDataFromFile(String filePath) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(new File(filePath)))) {
            System.out.println("readBoolean(): " + dis.readBoolean());
            System.out.println("readByte(): " + dis.readByte());
            System.out.println("readChar(): " + dis.readChar());
            System.out.println("readShort(): " + dis.readShort());
            System.out.println("readInt(): " + dis.readInt());
            System.out.println("readLong(): " + dis.readLong());
            System.out.println("readUTF(): " + dis.readUTF());
        }
    }
}