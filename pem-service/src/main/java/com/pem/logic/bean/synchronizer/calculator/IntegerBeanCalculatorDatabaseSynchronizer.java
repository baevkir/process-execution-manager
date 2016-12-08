package com.pem.logic.bean.synchronizer.calculator;

import com.pem.core.calculator.IntegerCalculator;
import com.pem.logic.bean.synchronizer.RegisterDatabaseSynchronizer;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.calculator.bean.BeanCalculatorDTO;
import com.pem.model.calculator.bean.IntegerBeanCalculatorDTO;
import com.pem.model.common.bean.BeanObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

@RegisterDatabaseSynchronizer(facades = ServiceConstants.DATABASE_SYNCHRONIZER_FACADE_NAME)
public class IntegerBeanCalculatorDatabaseSynchronizer extends CalculatorDatabaseSynchronizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntegerBeanCalculatorDatabaseSynchronizer.class);

    @Override
    public void synchronize() {
        List<IntegerBeanCalculatorDTO> calculators = getCalculatorPersistenceService().getCalculatorsByType(IntegerBeanCalculatorDTO.class);

        Set<BeanObject> beanObjects = getCalculatorProvider().getAllCalculatorBeanObjects(IntegerCalculator.class);

        for (BeanCalculatorDTO calculator : calculators) {
            if(!beanObjects.contains(calculator.getBean())) {
                deactivateCalculator(calculator);
                continue;
            }

            beanObjects.remove(calculator.getBean());
        }

        for (BeanObject beanObject : beanObjects) {
            createCalculator(new IntegerBeanCalculatorDTO(), beanObject);
        }
    }

}