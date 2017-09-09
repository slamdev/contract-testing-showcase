package com.github.slamdev.contracts;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import io.restassured.response.Response;
import lombok.experimental.UtilityClass;
import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

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

    @UtilityClass
    public static class HeadersExcluder {

        private static final String KEY = "excludeHeadersFromVerification";

        public static void exclude(GlobalWorldFacade world, List<String> headerNames) {
            world.put(KEY, String.join(",", headerNames));
        }

        public static List<String> get(GlobalWorldFacade world) {
            return Arrays.stream(world.get(KEY).or("").split(","))
                    .map(String::trim)
                    .collect(toList());
        }
    }
}
