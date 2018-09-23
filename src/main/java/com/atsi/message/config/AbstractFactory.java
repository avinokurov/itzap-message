package com.atsi.message.config;


import org.glassfish.hk2.api.Factory;

public abstract class AbstractFactory<T> implements Factory<T> {
    @Override
    public void dispose(T t) {

    }
}
