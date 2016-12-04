package com.pem.persistence.mongo.model.operation.loop;

public class CounterLoopOperationEntity extends LoopOperationEntity {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CounterLoopOperationEntity{" +
                "counter=" + count +
                "} " + super.toString();
    }
}
