package com.jdk17.factory;

import java.io.ObjectInputFilter;
import java.util.function.BinaryOperator;

public class SimpleFilterFactory implements BinaryOperator<ObjectInputFilter> {

    private static final String DEFAULT_PACKAGE_PATTERN = "java.base/*;!*";
    private static final String POJO_PACKAGE = "com.jdk17.basic";


    @Override
        public ObjectInputFilter apply(ObjectInputFilter curr, ObjectInputFilter next) {

            if(curr ==  null){

                curr = maxbytesFilter("maxbytes", 190);
            }
            if(next ==  null){

                next = typeFilter();
            }


            System.out.println("Current filter: " + curr);
            System.out.println("Requested filter: " + next);
            return ObjectInputFilter.merge(curr, next);
        }

    private static ObjectInputFilter typeFilter() {

        var filter1 = ObjectInputFilter.allowFilter(cl -> cl.getPackageName().contentEquals("com.jdk17.basic"), ObjectInputFilter.Status.REJECTED);
        return filter1;
    }
    private static ObjectInputFilter maxbytesFilter(String parameter, int max) {
        return ObjectInputFilter.Config.createFilter(String.format("%s=%d;%s.**;%s", parameter, max, POJO_PACKAGE, DEFAULT_PACKAGE_PATTERN));
    }

}
