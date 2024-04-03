package com.jdk17.deserializationfilters.service;

import java.io.InputStream;
import java.util.Set;

import com.jdk17.deserializationfilters.pojo.ContextSpecific;
import com.jdk17.deserializationfilters.utils.DeserializationUtils;

public class LimitedArrayService implements DeserializationService {

    @Override
    public Set<ContextSpecific> process(InputStream... inputStreams) {
        return DeserializationUtils.deserializeIntoSet(inputStreams);
    }
}
