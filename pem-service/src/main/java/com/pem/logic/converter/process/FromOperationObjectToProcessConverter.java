package com.pem.logic.converter.process;

import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.core.common.utils.ReflectiveProcessor;
import com.pem.logic.common.ServiceConstants;
import com.pem.logic.common.utils.IdGenerator;
import com.pem.model.common.IdentifiableObject;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.model.proccess.record.ExecutionRecordObject;
import com.pem.model.proccess.record.ExecutionRecordPK;
import com.pem.model.proccess.record.ExecutionRecordState;
import com.rits.cloning.Cloner;
import org.apache.log4j.helpers.DateTimeDateFormat;

import java.math.BigInteger;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class FromOperationObjectToProcessConverter implements Converter<OperationObject, ExecutionProcessObject> {
    private Cloner cloner = new Cloner();
    private ReflectiveProcessor<OperationObject> reflectiveProcessor = new ReflectiveProcessor<>();
    private DateFormat dateFormat = DateTimeDateFormat.getDateTimeInstance();
    @Override
    public ExecutionProcessObject convert(OperationObject source) {
        ExecutionProcessObject processEntity = new ExecutionProcessObject();
        processEntity.setId(IdGenerator.generateId());
        processEntity.setName(source.getName() + " (" + dateFormat.format(new Date()) + ")");

        GenerateIdAction generateIdAction = new GenerateIdAction();

        OperationObject executionOperation = reflectiveProcessor
                .setSource(cloner.deepClone(source))
                .setPredicate(object -> !(object instanceof IdentifiableObject))
                .setAction(generateIdAction)
                .process();

        List<ExecutionRecordObject> executionRecords = createExecutionRecords(generateIdAction.getGeneratedIds(), processEntity.getId());

        processEntity.setExecutionRecords(executionRecords);
        processEntity.setExecutionOperation(executionOperation);
        return processEntity;
    }

    private List<ExecutionRecordObject> createExecutionRecords(List<BigInteger> generatedIds, BigInteger processId) {
        List<ExecutionRecordObject> result = new ArrayList<>();
        for (BigInteger id : generatedIds) {
            ExecutionRecordObject executionRecord = new ExecutionRecordObject();
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

    private class GenerateIdAction implements Consumer<Object> {

        private List<BigInteger> generatedIds = new LinkedList<>();

        private List<BigInteger> getGeneratedIds() {
            return generatedIds;
        }

        @Override
        public void accept(Object object) {
            IdentifiableObject entity = (IdentifiableObject) object;
            BigInteger newId = IdGenerator.generateId();
            generatedIds.add(newId);

            entity.setId(newId);
        }
    }
}
