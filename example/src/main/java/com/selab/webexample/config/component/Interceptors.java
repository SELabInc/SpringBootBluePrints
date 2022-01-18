package com.selab.webexample.config.component;

import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class Interceptors {

    private final HashSet<HandlerInterceptor> interceptors = new HashSet<>();

    public void append(HandlerInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    public void append(HandlerInterceptor... interceptors) {
        this.interceptors.addAll(Arrays.asList(interceptors.clone()));
    }

    public Iterator<HandlerInterceptor> getIterator() {
        return interceptors.stream().iterator();
    }
}
