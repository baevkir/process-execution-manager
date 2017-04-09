package com.pem.model.proccess.record;

import com.pem.model.common.IdentifiableObject;
import org.joda.time.DateTime;

public class ExecutionRecordObject extends IdentifiableObject {

    private ExecutionRecordPK pk;

    private ExecutionRecordState state;

    private DateTime createdWhen;

    private DateTime modifyWhen;

    public ExecutionRecordPK getPk() {
        return pk;
    }

    public void setPk(ExecutionRecordPK pk) {
        this.pk = pk;
    }

    public ExecutionRecordState getState() {
        return state;
    }

    public void setState(ExecutionRecordState state) {
        this.state = state;
    }

    public DateTime getCreatedWhen() {
        return createdWhen;
    }

    public void setCreatedWhen(DateTime createdWhen) {
        this.createdWhen = createdWhen;
    }

    public DateTime getModifyWhen() {
        return modifyWhen;
    }

    public void setModifyWhen(DateTime modifyWhen) {
        this.modifyWhen = modifyWhen;
    }

    @Override
    public String toString() {
        return "ExecutionRecordObject{" +
                "pk=" + pk +
                ", trigger=" + state +
                ", createdWhen=" + createdWhen +
                ", modifyWhen=" + modifyWhen +
                "} " + super.toString();
    }
}
