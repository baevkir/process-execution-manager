package com.pem.test.converter;

import com.pem.operation.basic.Operation;
import com.pem.persistence.converter.ConverterFactory;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.persistence.model.operation.basic.BeanOperationEntity;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.test.common.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ConverterFactoryTest {

    @Autowired
    private ConverterFactory converterFactory;

    private Random random = new Random();

    @Test
    public void testConverterExist() {
        OperationEntity operationEntity = createSimpleBeanOperation();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
    }

    private OperationEntity createSimpleBeanOperation() {
        BeanOperationEntity operationEntity = new BeanOperationEntity();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        BeanEntity bean = new BeanEntity();
        bean.setName("Sum Operation");
        bean.setBeanName("sumOperation");
        operationEntity.setBean(bean);

        return operationEntity;
    }
}
