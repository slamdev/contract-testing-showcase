package com.github.slamdev.contracts;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import io.restassured.response.Response;
import lv.ctco.cukes.core.extension.CukesInjectableModule;

import java.util.Map;

@CukesInjectableModule
public class Contracts extends AbstractModule {

    @Override
    protected void configure() {
        bind(ResponseStateSaver.class).asEagerSingleton();
        bind(BaseUrlStateSaver.class).asEagerSingleton();
    }

    public enum Contract {
        LEGACY, NEW
    }

    @Singleton
    public static class ResponseStateSaver extends ForwardingMap<Contract, Response> {

        private final Map<Contract, Response> states = Maps.newHashMap();

        @Override
        protected Map<Contract, Response> delegate() {
            return states;
        }
    }

    @Singleton
    public static class BaseUrlStateSaver extends ForwardingMap<Contract, String> {

        private final Map<Contract, String> states = Maps.newHashMap();

        @Override
        protected Map<Contract, String> delegate() {
            return states;
        }
    }
}
