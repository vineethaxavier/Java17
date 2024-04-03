package com.jdk17.deserializationfilters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.function.BinaryOperator;

import com.jdk17.deserializationfilters.service.DeserializationService;
import com.jdk17.deserializationfilters.service.LimitedArrayService;
import com.jdk17.deserializationfilters.service.LowDepthService;
import com.jdk17.deserializationfilters.service.SmallObjectService;
import com.jdk17.deserializationfilters.utils.FilterUtils;

public class ContextSpecificDeserializationFilterFactory implements BinaryOperator<ObjectInputFilter> {

    @Override
    public ObjectInputFilter apply(ObjectInputFilter current, ObjectInputFilter next) {
        if (current == null) {
            Class<?> caller = findInStack(DeserializationService.class);

            if (caller == null) {
                current = FilterUtils.fallbackFilter();
            } else if (caller.equals(SmallObjectService.class)) {
                current = FilterUtils.safeSizeFilter(190);
            } else if (caller.equals(LowDepthService.class)) {
                current = FilterUtils.safeDepthFilter(2);
            } else if (caller.equals(LimitedArrayService.class)) {
                current = FilterUtils.safeArrayFilter(3);
            }
        }

        return ObjectInputFilter.merge(current, next);
    }

    private static Class<?> findInStack(Class<?> superType) {
        for (StackTraceElement element : Thread.currentThread()
            .getStackTrace()) {
            try {
                Class<?> subType = Class.forName(element.getClassName());
                if (superType.isAssignableFrom(subType)) {
                    return subType;
                }
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
        return null;
    }

    //Deserialize a string and date from file
    private void extractDateFromFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("tmp");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        String today = (String)objectInputStream.readObject();
        Date date = (Date) objectInputStream.readObject();
    }
}
