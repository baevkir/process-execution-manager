package com.pem.test.persistence;

import com.pem.model.predicate.common.PredicateObject;
import com.pem.persistence.api.service.predicate.PredicatePersistenceService;
import com.pem.test.common.FongoConfig;
import com.pem.test.common.TestEntityCreator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FongoConfig.class)
public class PredicatePersistenceServiceTest {

    private TestEntityCreator creator = new TestEntityCreator();

    @Autowired
    private PredicatePersistenceService predicatePersistenceService;

    @Test
    public void testSaveToDBCalculator() {
        StepVerifier.create(createPredicate())
                .assertNext(predicate ->
                        predicatePersistenceService.getById(predicate.getId())
                                .doOnNext(queryCalculator -> Assert.assertEquals(predicate.getClass(), queryCalculator.getClass()))
                                .subscribe())
                .expectComplete()
                .verify();
    }

    @Test
    public void testDeleteOperation() {
        Mono<PredicateObject> predicateObjectMono = createPredicate()
                .map(predicateObject -> predicateObject.getId())
                .doOnNext(predicateId -> Assert.assertNotNull(predicateId))
                .doOnSuccess(predicateId -> predicatePersistenceService.delete(predicateId).subscribe())
                .flatMap(predicateId -> predicatePersistenceService.getById(predicateId))
                .single();

            StepVerifier.create(predicateObjectMono)
                    .expectNextCount(0)
                    .expectComplete()
                    .verify();

    }

    private Mono<PredicateObject> createPredicate() {
        return predicatePersistenceService.create(creator.createRandomPredicate());
    }
}
