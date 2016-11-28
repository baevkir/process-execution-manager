package com.pem.test.provider;

import com.pem.logic.bean.provider.context.OperationContextProvider;
import com.pem.context.OperationContext;
import com.pem.test.common.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OperationContextProviderTest {

    @Autowired
    private OperationContextProvider contextProvider;

    @Test
    public void testCreateOperationContext() {
        OperationContext context = contextProvider.createContext();
        OperationContext context1 = contextProvider.createContext();
        Assert.assertTrue(context != context1);
    }
}
