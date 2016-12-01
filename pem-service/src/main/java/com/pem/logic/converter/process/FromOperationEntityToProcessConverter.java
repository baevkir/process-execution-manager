package com.pem.logic.converter.process;

import com.pem.logic.common.utils.IdGenerator;
import com.pem.logic.common.utils.ReflectiveDTOProcessor;
import com.pem.logic.converter.common.Converter;
import com.pem.logic.converter.common.RegisterInConverterFactory;
import com.pem.persistence.api.model.common.IdentifiableObject;
import com.pem.persistence.api.model.operation.common.OperationObject;
import com.pem.persistence.api.model.proccess.ExecutionProcess;
import com.pem.persistence.api.model.proccess.record.ExecutionRecord;
import com.pem.persistence.api.model.proccess.record.ExecutionRecordPK;
import com.pem.persistence.api.model.proccess.record.ExecutionRecordState;
import com.rits.cloning.Cloner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class FromOperationEntityToProcessConverter implements Converter<OperationObject, ExecutionProcess> {
    private Cloner cloner = new Cloner();
    private ReflectiveDTOProcessor<OperationObject> reflectiveProcessor = new ReflectiveDTOProcessor<>();

    @Override
    public ExecutionProcess convert(OperationObject source) {
        ExecutionProcess processEntity = new ExecutionProcess();
        processEntity.setId(IdGenerator.generateId());
        processEntity.setName(source.getName());

        GenerateIdAction generateIdAction = new GenerateIdAction();

        OperationObject executionOperation = reflectiveProcessor
                .setSource(cloner.deepClone(source))
                .setAction(generateIdAction)
                .process();

        List<ExecutionRecord> executionRecords = createExecutionRecords(generateIdAction.getGeneratedIds(), processEntity.getId());

        processEntity.setExecutionRecords(executionRecords);
        processEntity.setExecutionOperation(executionOperation);
        return processEntity;
    }

    private List<ExecutionRecord> createExecutionRecords(List<BigInteger> generatedIds, BigInteger processId) {
        List<ExecutionRecord> result = new ArrayList<>();
        for (BigInteger id : generatedIds) {
            ExecutionRecord executionRecord = new ExecutionRecord();
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
            if (!(object instanceof IdentifiableObject)) {
                return;
            }
            IdentifiableObject entity = (IdentifiableObject) object;
            BigInteger newId = IdGenerator.generateId();
            generatedIds.add(newId);

            entity.setId(newId);
        }
    }
}
