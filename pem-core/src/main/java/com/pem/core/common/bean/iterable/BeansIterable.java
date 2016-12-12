package com.pem.core.common.bean.iterable;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.bean.BeanObjectBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;

public class BeansIterable<S> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeansIterable.class);

    private FluentIterable<Map.Entry<String, S>> beans;

    private BeansIterable(FluentIterable<Map.Entry<String, S>> beans) {
        this.beans = beans;
    }

    public static <E> BeansIterable<E> fromBeans(Map<String, E> beans) {
        return new BeansIterable<>(FluentIterable.from(beans.entrySet()));
    }

    public BeansIterable<S> filter(final Predicate<S> predicate) {
        Assert.notNull(predicate, "Predicate cannot be NULL.");
        return new BeansIterable<>(beans.filter(new Predicate<Map.Entry<String, S>>() {
            @Override
            public boolean apply(Map.Entry<String, S> input) {
                return predicate.apply(input.getValue());
            }
        }));
    }

    public void forEach(final Consumer<S> consumer) {
        Assert.notNull(consumer, "Consumer cannot be NULL.");
        FluentIterable iterable = beans.transform(new Function<Map.Entry<String,S>, Map.Entry<String,S>>() {
            @Override
            public Map.Entry<String,S> apply(Map.Entry<String, S> input) {
                S value = input.getValue();
                LOGGER.trace("Value: {}.", value);
                consumer.accept(value);
                return input;
            }
        });
        iterable.toList();
    }

    public Set<BeanObject> transformToBeanObjects(final Function<S, String> nameFunction) {
        Assert.notNull(nameFunction, "Name Function cannot be NULL.");
        return beans.transform(new Function<Map.Entry<String, S>, BeanObject>() {
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
