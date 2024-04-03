package com.jdk17.basic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ValueObjectTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ValueObject vo1 = new ValueObject("Hi");
        serializeValueObj(vo1);
        //deserializeValueObj();
    }

    private static void deserializeValueObj() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream("ValueObjectExploit.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        ValueObject vo2 = (ValueObject) in.readObject();
    }

    private static void serializeValueObj(ValueObject vo1) throws IOException {
        FileOutputStream fileOut = new FileOutputStream("ValueObject.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(vo1);
        out.close();
        fileOut.close();
    }

}
