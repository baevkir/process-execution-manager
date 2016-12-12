package com.pem.core.common.bean;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;

public class BeanObjectIterator<S> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanObjectIterator.class);

    private FluentIterable<Map.Entry<String, S>> beans;

    private BeanObjectIterator(FluentIterable<Map.Entry<String, S>> beans){
        this.beans = beans;
    }

    public static <E> BeanObjectIterator<E> fromBeans(Map<String, E> beans) {
        return new BeanObjectIterator<>(FluentIterable.from(beans.entrySet()));
    }

    public BeanObjectIterator<S> filter(final Predicate<S> predicate) {
        Assert.notNull(predicate, "Predicate cannot be NULL.");
        return new BeanObjectIterator<>(beans.filter(new Predicate<Map.Entry<String, S>>() {
            @Override
            public boolean apply(Map.Entry<String, S> input) {
                return predicate.apply(input.getValue());
            }
        }));
    }

    public Set<BeanObject> transform(final Function<S, String> nameFunction) {
        Assert.notNull(nameFunction, "Name Function cannot be NULL.");
        return beans.transform(new Function<Map.Entry<String,S>, BeanObject>() {
            @Override
            public BeanObject apply(Map.Entry<String, S> input) {
                String beanName = input.getKey();
                LOGGER.trace("Add BeanObject with name {}", beanName);
                return BeanObjectBuilder.newInstance()
                        .setBeanName(beanName)
                        .setName(nameFunction.apply(input.getValue()))
                        .build();
            }
        }).toSet();
    }
}
