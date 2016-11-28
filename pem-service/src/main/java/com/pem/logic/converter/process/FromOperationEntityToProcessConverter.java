package com.pem.logic.converter.process;

import com.pem.logic.common.utils.IdGenerator;
import com.pem.logic.common.utils.ReflectiveDTOProcessor;
import com.pem.logic.converter.common.Converter;
import com.pem.logic.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.common.IdentifiableEntity;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.model.proccess.record.ExecutionRecordEntity;
import com.pem.persistence.model.proccess.record.ExecutionRecordPK;
import com.pem.persistence.model.proccess.record.ExecutionRecordState;
import com.rits.cloning.Cloner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class FromOperationEntityToProcessConverter implements Converter<OperationEntity, ExecutionProcessEntity> {
    private Cloner cloner = new Cloner();
    private ReflectiveDTOProcessor<OperationEntity> reflectiveProcessor = new ReflectiveDTOProcessor<>();

    @Override
    public ExecutionProcessEntity convert(OperationEntity source) {
        ExecutionProcessEntity processEntity = new ExecutionProcessEntity();
        processEntity.setId(IdGenerator.generateId());
        processEntity.setName(source.getName());

        GenerateIdAction generateIdAction = new GenerateIdAction();

        OperationEntity executionOperation = reflectiveProcessor
                .setSource(cloner.deepClone(source))
                .setAction(generateIdAction)
                .process();

        List<ExecutionRecordEntity> executionRecords = createExecutionRecords(generateIdAction.getGeneratedIds(), processEntity.getId());

        processEntity.setExecutionRecords(executionRecords);
        processEntity.setExecutionOperation(executionOperation);
        return processEntity;
    }

    private List<ExecutionRecordEntity> createExecutionRecords(List<BigInteger> generatedIds, BigInteger processId) {
        List<ExecutionRecordEntity> result = new ArrayList<>();
        for (BigInteger id : generatedIds) {
            ExecutionRecordEntity executionRecord = new ExecutionRecordEntity();
            executionRecord.setId(IdGenerator.generateId());

            ExecutionRecordPK pk = new ExecutionRecordPK();
            pk.setOperationId(id);
            pk.setProcessId(processId);
            executionRecord.setPk(pk);

            executionRecord.setState(ExecutionRecordState.CREATED);
            result.add(executionRecord);
        }

        return result;
    }

    private class GenerateIdAction implements ReflectiveDTOProcessor.HandleAction {

        private List<BigInteger> generatedIds = new LinkedList<>();

        private List<BigInteger> getGeneratedIds() {
            return generatedIds;
        }

        @Override
        public void execute(Object object) {
            if (!(object instanceof IdentifiableEntity)) {
                return;
            }
            IdentifiableEntity entity = (IdentifiableEntity) object;
            BigInteger newId = IdGenerator.generateId();
            generatedIds.add(newId);

            entity.setId(newId);
        }
    }
}
