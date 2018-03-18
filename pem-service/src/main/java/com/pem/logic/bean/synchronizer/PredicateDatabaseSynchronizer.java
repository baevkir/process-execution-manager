package com.pem.logic.bean.synchronizer;

import com.pem.core.common.applicationcontext.bean.BeanObject;
import com.pem.core.common.event.RegisterLaunchEventHandler;
import com.pem.core.predicate.Predicate;
import com.pem.logic.bean.provider.BeanProvider;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.predicate.bean.BeanPredicateObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RegisterLaunchEventHandler(facade = ServiceConstants.DATABASE_SYNCHRONIZER_FACADE_NAME)
public class PredicateDatabaseSynchronizer extends AbstractBeanDatabaseSynchronizer<BeanPredicateObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredicateDatabaseSynchronizer.class);

    private BeanProvider beanProvider;

    @Override
    public void handle() {
        Flux<BeanObject> beanObjects = Flux.fromIterable(beanProvider.getAllForType(Predicate.class));
        BeanObjectMapper<BeanPredicateObject> mapper = getMapper(getPersistenceManager().getAllByType(BeanPredicateObject.class), beanObjects);

        mapper.getForActivate()
                .flatMap(operation -> updateStatus(operation, true))
                .subscribe();

        mapper.getForDeactivate()
                .flatMap(operation -> updateStatus(operation, false))
                .subscribe();

        mapper.getForCreate()
                .flatMap(beanObject -> createPredicate(beanObject))
                .subscribe();
    }

    private Mono<Void> createPredicate(BeanObject beanObject) {
        LOGGER.debug("Create new predicateObject for {}.", beanObject);
        BeanPredicateObject predicateObject = new BeanPredicateObject();
        predicateObject.setActive(true);
        predicateObject.setName(beanObject.getName());
        predicateObject.setBean(beanObject);

        return getPersistenceManager().create(predicateObject).then();
    }

    public void setBeanProvider(BeanProvider beanProvider) {
        this.beanProvider = beanProvider;
    }

}
