package com.pem.persistence.converter.process;

import com.pem.common.utils.ReflectiveDTOProcessor;
import com.pem.persistence.converter.common.AbstractConverter;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.common.IdentifiableEntity;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.proccess.ExecutionProcessEntity;
import com.rits.cloning.Cloner;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class FromOperationEntityToProcessConverter extends AbstractConverter<OperationEntity, ExecutionProcessEntity> {
    private Cloner cloner = new Cloner();
    private ReflectiveDTOProcessor<OperationEntity> reflectiveProcessor = new ReflectiveDTOProcessor<>();

    @Override
    public ExecutionProcessEntity convert(OperationEntity source) {
        ExecutionProcessEntity processEntity = new ExecutionProcessEntity();
        processEntity.setName(source.getName());

        GenerateIdAction generateIdAction = new GenerateIdAction();

        OperationEntity executionOperation = reflectiveProcessor
                                    .setSource(cloner.deepClone(source))
                                    .setAction(generateIdAction)
                                    .process();

        processEntity.setExecutionOperation(executionOperation);
        return processEntity;
    }

    private class GenerateIdAction implements ReflectiveDTOProcessor.HandleAction {

        private Random random = new Random();
        private List<BigInteger> generatedIds = new LinkedList<>();

        @Override
        public void execute(Object object) {
            if ( !(object instanceof IdentifiableEntity)) {
                return;
            }
            IdentifiableEntity entity = (IdentifiableEntity) object;
            BigInteger newId = new BigInteger(95, random);
            generatedIds.add(newId);

            entity.setId(newId);
        }
    }
}
