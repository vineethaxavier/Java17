package com.jdk17.basic;

import com.jdk17.vulnerable.Command;
import com.jdk17.vulnerable.Gadget;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GadgetTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String url = "https://www.mongodb.com/";
        Gadget gadget = new Gadget(new Command(new String[] { "open" , "-a" , "Safari", url }));
        serializeValueObj(gadget);
        ObjectInputStream in;
        try (FileInputStream fileIn = new FileInputStream("ValueObject.ser")) {
            in = new ObjectInputStream(fileIn);
            //Add filter to prevent deserialisation of malicious code/unknown object
            //filesOnlyFilter(in);
            newFilterMethods(in);
            var obj = (ValueObject)in.readObject();
            System.out.println(obj);
        }

    }

    private static void newFilterMethods(ObjectInputStream in) {
        //add in JVM arg -Djdk.serialFilterFactory=com.jdk17.factory.SimpleFilterFactory

        // Create another filter
        ObjectInputFilter valueObjFilter = ObjectInputFilter.rejectFilter(
                cl -> cl.equals(ValueObject.class), ObjectInputFilter.Status.UNDECIDED);
        in.setObjectInputFilter(valueObjFilter);

/*        // Set a filter factory
        SimpleFilterFactory contextFilterFactory = new SimpleFilterFactory();
        ObjectInputFilter.Config.setSerialFilterFactory(contextFilterFactory);*/

    }

    private static void filesOnlyFilter(ObjectInputStream in) {
        ObjectInputFilter filesOnlyFilter = ObjectInputFilter.Config.createFilter("com.jdk17.basic.ValueObject;!*");
        in.setObjectInputFilter(filesOnlyFilter);
    }

    private static void serializeValueObj(Gadget gadget) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream("Gadget.ser")) {
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gadget);
            out.close();
        }
    }
}
