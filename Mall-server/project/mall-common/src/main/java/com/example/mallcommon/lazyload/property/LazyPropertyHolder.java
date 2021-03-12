package com.example.mallcommon.lazyload.property;

import org.springframework.cglib.proxy.LazyLoader;

import java.lang.reflect.Field;

public interface LazyPropertyHolder<T> extends LazyLoader {

    T getProperty();

    void initialize(Object source, Field targetField);
}
