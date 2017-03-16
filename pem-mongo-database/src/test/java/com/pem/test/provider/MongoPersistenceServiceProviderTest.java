package com.pem.test.provider;

import com.pem.model.operation.common.OperationDTO;
import com.pem.persistence.api.provider.PemPersistenceServiceProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoPersistenceServiceProviderConfig.class)
public class MongoPersistenceServiceProviderTest {

    @Autowired
    private PemPersistenceServiceProvider persistenceServiceProvider;

    @Test
    public void testPersistenceServiceProvider () {
        Assert.assertNotNull(persistenceServiceProvider.getCalculatorPersistenceService());
        Assert.assertNotNull(persistenceServiceProvider.getOperationPersistenceService());
        Assert.assertNotNull(persistenceServiceProvider.getProcessPersistenceService());
        Assert.assertNotNull(persistenceServiceProvider.getExecutionRecordPersistenceService());

        Flux<OperationDTO> operations = persistenceServiceProvider.getOperationPersistenceService().getAllOperations();

        Assert.assertNotNull(operations);
        StepVerifier.create(operations)
                .expectComplete()
                .verify();

    }
}
