package com.pem.test.loader;

import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.integration.loader.ProcessExecutionManagerProvider;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.service.operation.OperationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProcessExecutionManagerContextLoaderConfig.class)
public class ProcessExecutionManagerContextLoaderTest {

    @Autowired
    public ProcessExecutionManagerProvider contextLoader;

    @Test
    public void processExecutionManagerContextLoaderTest(){
        OperationService operationService = contextLoader.getOperationService();
        List<BeanEntity> operations = operationService.getAllOperationBeanEntities();
        List<BeanEntity> calculators = contextLoader.getConditionCalculatorService().getConditionCalculatorBeanEntitiesForClass(BinaryConditionCalculator.class);
    }
}
