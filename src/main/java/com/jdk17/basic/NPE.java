package com.jdk17.basic;

import com.jdk17.vulnerable.Gadget;

import java.util.HashMap;
import java.util.Map;

public class NPE {

    public static void main(String[] args) {

        Map<String, Map<String, Map>> map = null;
        map.put("1", null);
        System.out.println(map.get(1).get("1"));
    }

}
