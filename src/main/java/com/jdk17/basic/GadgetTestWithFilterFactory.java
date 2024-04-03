package com.jdk17.basic;

import com.jdk17.vulnerable.Command;
import com.jdk17.vulnerable.Gadget;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GadgetTestWithFilterFactory {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        var filename = "file.ser";
        var value = new TwoValue("one", "two");
        //var value = new Gadget(new Command(new String[] {"ls -l"})); //This will not be deserialized

        var filter1 = ObjectInputFilter.allowFilter(cl -> cl.getPackageName().contentEquals("com.jdk17"), ObjectInputFilter.Status.REJECTED);
        ObjectInputFilter.Config.setSerialFilter(filter1);
        ObjectInputFilter.Config.setSerialFilterFactory((f1, f2) -> ObjectInputFilter.merge(f2, f1));

        serialize(value, filename);
        deserialize(filename);
    }

    public static void serialize(Object value, String filename) throws IOException {
        System.out.println("---serialize");
        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(value);
        out.close();
        fileOut.close();
    }

    public static void deserialize(String filename) throws IOException, ClassNotFoundException {
        System.out.println("---deserialize");
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        ObjectInputFilter intFilter = ObjectInputFilter.rejectFilter(cl -> cl.equals(Gadget.class), ObjectInputFilter.Status.UNDECIDED);
        in.setObjectInputFilter(intFilter);
        TwoValue tv = (TwoValue) in.readObject();
        System.out.println(tv);

    }
}
