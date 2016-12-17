package com.pem.persistence.mongo.model.proccess;

import com.pem.persistence.mongo.model.common.BaseEntity;
import com.pem.persistence.mongo.model.proccess.record.ExecutionRecordEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "pem_execution_processes")
public class ExecutionProcessEntity extends BaseEntity {

    private String executionPlan;


    private List<ExecutionRecordEntity> executionRecords;

    public String getExecutionPlan() {
        return executionPlan;
    }

    public void setExecutionPlan(String executionPlan) {
        this.executionPlan = executionPlan;
    }

    public List<ExecutionRecordEntity> getExecutionRecords() {
        return executionRecords;
    }

    public void setExecutionRecords(List<ExecutionRecordEntity> executionRecords) {
        this.executionRecords = executionRecords;
    }

    @Override
    public String toString() {
        return "ExecutionProcessEntity{" +
                "executionPlan='" + executionPlan + '\'' +
                ", executionRecords=" + executionRecords +
                "} " + super.toString();
    }
}
