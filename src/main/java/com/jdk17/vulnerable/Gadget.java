package com.jdk17.vulnerable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Gadget implements Serializable {

        private Command command;

        public Gadget(Command command) {
            this.command = command;
        }

        private final void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            command.run();
        }

}
