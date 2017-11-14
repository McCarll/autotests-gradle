package com.vrudometkin.autotest.config;

import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

/**
 * Created by vrudometkin on 14/11/2017.
 */
@NoArgsConstructor
public class Reflection {

    public static Object extractFieldValue(Field f, Object owner) {
        f.setAccessible(true);
        try {
            return f.get(owner);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            f.setAccessible(false);
        }
    }
}
