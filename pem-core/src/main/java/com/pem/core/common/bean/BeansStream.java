package com.pem.core.common.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BeansStream<B> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeansStream.class);

    private Stream<Entry<B>> beans;

    private BeansStream(Stream<Entry<B>> beans) {
        this.beans = beans;
    }

    public static <E> BeansStream<E> fromBeans(Map<String, E> beans) {
        Assert.notNull(beans, "Can`t create Stream beans are NULL.");
        Stream<Entry<E>> stream = beans.entrySet().stream()
                .map(entry -> new Entry<>(entry.getKey(), entry.getValue()))
                .peek(entry -> LOGGER.debug("Start to handle bean entry {}.", entry));

        return new BeansStream<>(stream);
    }

    public BeansStream<B> filterWithAnnotation(Class<? extends Annotation> beanAnnotation) {
        return new BeansStream<>(beans
                .filter(entry -> entry.getBeanAnnotation(beanAnnotation).isPresent()));
    }

    public <A extends Annotation> BeansStream<B> filterWithAnnotation(Class<A> beanAnnotation, Predicate<A> predicate) {
        return new BeansStream<>(beans
                .filter(entry -> entry.getBeanAnnotation(beanAnnotation)
                        .filter(predicate).isPresent()));
    }

    public <A extends Annotation> BeansStream<B> filterWithAnnotation(Class<A> beanAnnotation, BiPredicate<A, Entry<B>> predicate) {
        return new BeansStream<>(beans
                .filter(entry -> entry.getBeanAnnotation(beanAnnotation)
                        .filter(annotation -> predicate.test(annotation, entry)).isPresent()));
    }

    public BeansStream<B> filter(Predicate<Entry<B>> predicate) {
        Assert.notNull(predicate, "Predicate cannot be NULL.");
        return new BeansStream<>(beans.filter(predicate));
    }


    public BeansStream<B> calculateName(Function<Entry<B>, String> nameFunction) {
        Assert.notNull(nameFunction, "Consumer cannot be NULL.");
        return new BeansStream<>(beans.peek(entry -> entry.setName(nameFunction.apply(entry))));
    }

    public void forEach(Consumer<Entry<B>> consumer) {
        Assert.notNull(consumer, "Consumer cannot be NULL.");
        beans.forEach(consumer);
    }

    public <T> Stream<T> transform(Function<Entry<B>, T> function) {
        return beans.map(function);
    }

    public Stream<BeanObject> transformToBeanObject() {
        return beans.map(new BeanObjectTransformer());
    }

    public Stream<Entry<B>> stream() {
        return beans;
    }

    public static class Entry<B>{
        private String name;
        private String beanName;
        private B bean;
        private Class<B> beanClass;
        private final Map<Class<? extends Annotation>, Annotation> annotations = new HashMap<>();

        Entry(String beanName, B bean) {
            this.beanName = beanName;
            this.bean = bean;
            this.beanClass = (Class<B>) AopProxyUtils.ultimateTargetClass(bean);
        }

        public String getBeanName() {
            return beanName;
        }

        public B getBean() {
            return bean;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class<B> getBeanClass() {
            return beanClass;
        }

        public <A extends Annotation> Optional<A> getBeanAnnotation(Class<A> beanAnnotation) {
            if (annotations.containsKey(beanAnnotation)) {
                return Optional.of((A) annotations.get(beanAnnotation));
            }

            Optional<A> annotationOptional =  Optional.ofNullable(beanClass.getAnnotation(beanAnnotation));
            annotationOptional.ifPresent(annotation -> annotations.put(beanAnnotation, annotation));

            return annotationOptional;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "beanName='" + beanName + '\'' +
                    ", bean=" + bean +
                    ", beanClass=" + beanClass +
                    '}';
        }
    }

    class BeanObjectTransformer<E> implements Function<Entry<E>, BeanObject> {
        @Override
        public BeanObject apply(Entry<E> entry) {
            return BeanObjectBuilder.newInstance()
                    .setBeanName(entry.getBeanName())
                    .setName(entry.getName())
                    .build();
        }
    }
}
