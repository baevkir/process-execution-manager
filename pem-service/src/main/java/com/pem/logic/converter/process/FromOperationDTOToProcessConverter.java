package com.pem.logic.converter.process;

import com.pem.logic.common.ServiceConstants;
import com.pem.logic.common.utils.IdGenerator;
import com.pem.core.common.utils.ReflectiveProcessor;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.common.IdentifiableDTO;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.model.proccess.record.ExecutionRecordDTO;
import com.pem.model.proccess.record.ExecutionRecordPK;
import com.pem.model.proccess.record.ExecutionRecordState;
import com.rits.cloning.Cloner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class FromOperationDTOToProcessConverter implements Converter<OperationDTO, ExecutionProcessDTO> {
    private Cloner cloner = new Cloner();
    private ReflectiveProcessor<OperationDTO> reflectiveProcessor = new ReflectiveProcessor<>();

    @Override
    public ExecutionProcessDTO convert(OperationDTO source) {
        ExecutionProcessDTO processEntity = new ExecutionProcessDTO();
        processEntity.setId(IdGenerator.generateId());
        processEntity.setName(source.getName());

        GenerateIdAction generateIdAction = new GenerateIdAction();

        OperationDTO executionOperation = reflectiveProcessor
                .setSource(cloner.deepClone(source))
                .setAction(generateIdAction)
                .process();

        List<ExecutionRecordDTO> executionRecords = createExecutionRecords(generateIdAction.getGeneratedIds(), processEntity.getId());

        processEntity.setExecutionRecords(executionRecords);
        processEntity.setExecutionOperation(executionOperation);
        return processEntity;
    }

    private List<ExecutionRecordDTO> createExecutionRecords(List<BigInteger> generatedIds, BigInteger processId) {
        List<ExecutionRecordDTO> result = new ArrayList<>();
        for (BigInteger id : generatedIds) {
            ExecutionRecordDTO executionRecord = new ExecutionRecordDTO();
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

    private class GenerateIdAction implements ReflectiveProcessor.HandleAction {

        private List<BigInteger> generatedIds = new LinkedList<>();

        private List<BigInteger> getGeneratedIds() {
            return generatedIds;
        }

        @Override
        public void execute(Object object) {
            if (!(object instanceof IdentifiableDTO)) {
                return;
            }
            IdentifiableDTO entity = (IdentifiableDTO) object;
            BigInteger newId = IdGenerator.generateId();
            generatedIds.add(newId);

            entity.setId(newId);
        }
    }
}
