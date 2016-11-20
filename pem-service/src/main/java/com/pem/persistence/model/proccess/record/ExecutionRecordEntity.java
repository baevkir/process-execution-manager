package com.pem.persistence.model.proccess.record;

import com.pem.persistence.model.common.IdentifiableEntity;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "execution-record")
public class ExecutionRecordEntity extends IdentifiableEntity {

    @Indexed(unique = true)
    private ExecutionRecordPK pk;

    private ExecutionRecordState state;

    @Version
    private Long version;

    @CreatedDate
    private DateTime createdWhen;

    @LastModifiedDate
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
        return "ExecutionRecordEntity{" +
                "pk=" + pk +
                ", state=" + state +
                ", version=" + version +
                ", createdWhen=" + createdWhen +
                ", modifyWhen=" + modifyWhen +
                "} " + super.toString();
    }
}
