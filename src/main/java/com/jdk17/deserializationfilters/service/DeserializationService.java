package com.jdk17.deserializationfilters.service;

import java.io.InputStream;
import java.util.Set;

import com.jdk17.deserializationfilters.pojo.ContextSpecific;

public interface DeserializationService {

    Set<ContextSpecific> process(InputStream... inputStreams);
}
