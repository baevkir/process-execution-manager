package com.pem.logic.bean.synchronizer.calculator;

import com.pem.core.calculator.BinaryCalculator;
import com.pem.core.common.event.RegisterLaunchEventHandler;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.calculator.bean.BeanCalculatorDTO;
import com.pem.model.calculator.bean.BinaryBeanCalculatorDTO;
import com.pem.model.common.bean.BeanObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

@RegisterLaunchEventHandler(facade = ServiceConstants.DATABASE_SYNCHRONIZER_FACADE_NAME)
public class BinaryBeanCalculatorDatabaseSynchronizer extends CalculatorDatabaseSynchronizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BinaryBeanCalculatorDatabaseSynchronizer.class);

    @Override
    public void handle() {
        List<BinaryBeanCalculatorDTO> calculators = getCalculatorPersistenceService().getCalculatorsByType(BinaryBeanCalculatorDTO.class);

        Set<BeanObject> beanObjects = getCalculatorProvider().getAllCalculatorBeanObjects(BinaryCalculator.class);

        for (BeanCalculatorDTO calculator : calculators) {
            if(!beanObjects.contains(calculator.getBean())) {
                deactivateCalculator(calculator);
                continue;
            }

            beanObjects.remove(calculator.getBean());
        }

        for (BeanObject beanObject : beanObjects) {
            createCalculator(new BinaryBeanCalculatorDTO(), beanObject);
        }
    }

}
