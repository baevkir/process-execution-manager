package com.pem.database.model.operation.condition;

import com.pem.database.model.operation.condition.state.AbstarctState;
import com.pem.database.model.operation.common.OperationEntity;

import java.util.List;

public abstract class AbstractConditionOperationEntity<C extends AbstarctState<?>> extends OperationEntity {

    private List<C> states;

}
