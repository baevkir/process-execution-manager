package com.pem.logic.bean.provider.calculator.impl;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.pem.core.calculator.Calculator;
import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.bean.iterable.BeansIterable;
import com.pem.core.common.utils.ApplicationContextWrapper;
import com.pem.logic.bean.provider.calculator.CalculatorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalculatorProviderImpl implements CalculatorProvider, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorProviderImpl.class);

    private ApplicationContext applicationContext;

    @Override
    public <C extends Calculator> C createCalculator(String beanName, Class<C> calculatorClass) {
        ApplicationContextWrapper wrapper = new ApplicationContextWrapper(applicationContext);
        return wrapper.getPrototypeBeanByType(beanName, calculatorClass);
    }

    @Override
    public <C extends Calculator> Set<BeanObject> getAllCalculatorBeanObjects(Class<C> conditionCalculatorClass) {
        ApplicationContextWrapper wrapper = new ApplicationContextWrapper(applicationContext);
        Map<String, C> beans = wrapper.findBeanByAnnotation(RegisterGlobalCalculator.class, conditionCalculatorClass);
        final String applicationId = applicationContext.getId();

        BeansIterable iterable = BeansIterable.fromBeans(beans).filter(new Predicate<C>() {
            @Override
            public boolean apply(C input) {
                Class<C> clazz = (Class<C>) AopProxyUtils.ultimateTargetClass(input);
                RegisterGlobalCalculator annotation = clazz.getAnnotation(RegisterGlobalCalculator.class);

                if (annotation.all()) {
                    return true;
                }
                List<String> executors = Arrays.asList(annotation.executors());
                return executors.contains(applicationId);
            }
        });

        return iterable.transformToBeanObjects(new Function<C, String>() {
            @Override
            public String apply(C input) {
                Class<C> clazz = (Class<C>) AopProxyUtils.ultimateTargetClass(input);
                RegisterGlobalCalculator annotation =  clazz.getAnnotation(RegisterGlobalCalculator.class);
                String name = annotation.value();
                LOGGER.trace("Presentation name for calculator {}", name);
                return name;
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
