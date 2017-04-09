package com.pem.logic.bean.provider.predicate;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.predicate.Predicate;

import java.util.Set;

public interface PredicateProvider {
    <P extends Predicate> P createPredicate(String beanName, Class<P> predicateClass);
    Set<BeanObject> getAllPredicateBeanObjects();
}
