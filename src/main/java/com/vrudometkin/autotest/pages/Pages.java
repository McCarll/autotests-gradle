package com.vrudometkin.autotest.pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsContainer;
import com.codeborne.selenide.SelenideElement;
import com.vrudometkin.autotest.config.Reflection;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vrudometkin.autotest.config.PropertyLoader.loadPropertySafe;

/**
 * Created by vrudometkin on 14/11/2017.
 */
@Slf4j
abstract public class Pages extends ElementsContainer {

    private static final String WAITING_APPEAR_TIMEOUT = "8000";

    private Map<String, Object> namedElements;
    private List<SelenideElement> primaryElements;

    @Target({ElementType.FIELD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Name {
        String value();
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface Optional {
    }

    public List<SelenideElement> getPrimaryElements() {
        if (primaryElements == null) primaryElements = readWithWrappedElements();
        return new ArrayList<>(primaryElements);
    }

    protected void isAppeared() {
        String timeout;
        try {
            Object property = loadProperty("waitingAppearTimeout");
            timeout = property.toString();
        } catch (IllegalArgumentException e) {
            timeout = WAITING_APPEAR_TIMEOUT;
        }
        String finalTimeout = timeout;
        getPrimaryElements().parallelStream().forEach(elem ->
                elem.waitUntil(Condition.appear,Integer.valueOf(finalTimeout)));
    }

    private static String loadProperty(String name) {
        String value = loadPropertySafe(name);
        if (null == value) {
            throw new IllegalArgumentException("Properties file does not contain property with key: " + name);
        }
        return value;
    }

    private List<SelenideElement> readWithWrappedElements() {
        return Arrays.stream(getClass().getDeclaredFields())
                .filter(f -> f.getDeclaredAnnotation(Optional.class) == null)
                .map(this::extractFieldValueViaReflection)
                .flatMap(v -> v instanceof List ? ((List<?>) v).stream() : Stream.of(v))
                .map(Pages::castToSelenideElement)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Object extractFieldValueViaReflection(Field f) {
        return Reflection.extractFieldValue(f, this);
    }

    private static SelenideElement castToSelenideElement(Object o) {
        if (o instanceof SelenideElement) {
            return (SelenideElement) o;
        }
        return null;
    }

}
