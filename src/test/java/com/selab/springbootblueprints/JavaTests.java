package com.selab.springbootblueprints;

import io.swagger.models.auth.In;

import java.util.function.Function;

public class JavaTests {
    public static void main(String[] args) {
        Function<String, Integer> stringIntegerFunction = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return null;
            }
        };

        stringIntegerFunction = (str) -> {

            return 0;
        };

        stringIntegerFunction = str -> 0;


    }
}
