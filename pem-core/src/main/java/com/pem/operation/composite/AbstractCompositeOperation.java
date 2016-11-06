package com.pem.operation.composite;

import com.pem.operation.basic.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractCompositeOperation implements CompositeOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCompositeOperation.class);

    private List<Operation> operations = new LinkedList<>();

    @Override
    public void addOperation(Operation operation){
        operations.add(operation);
    }

    protected List<Operation> getOperations(){
        return operations;
    }
}
