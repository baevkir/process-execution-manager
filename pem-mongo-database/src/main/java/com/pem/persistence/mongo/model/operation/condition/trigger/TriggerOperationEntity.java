package com.pem.persistence.mongo.model.operation.condition.trigger;

import com.pem.persistence.mongo.model.calculator.common.TriggerEntity;
import com.pem.persistence.mongo.model.operation.condition.ConditionOperationEntity;
import com.pem.persistence.mongo.model.operation.condition.state.IntegerState;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class TriggerOperationEntity extends ConditionOperationEntity<IntegerState> {

    @DBRef
    private TriggerEntity trigger;

    public TriggerEntity getTrigger() {
        return trigger;
    }

    public void setTrigger(TriggerEntity trigger) {
        this.trigger = trigger;
    }

    @Override
    public String toString() {
        return "TriggerOperationEntity{" +
                "trigger=" + trigger +
                "} " + super.toString();
    }
}
