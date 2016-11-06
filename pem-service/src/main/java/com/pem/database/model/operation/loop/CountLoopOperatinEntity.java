package com.pem.database.model.operation.loop;

public class CountLoopOperatinEntity extends LoopOperationEntity {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CountLoopOperatinEntity{" +
                "count=" + count +
                "} " + super.toString();
    }
}
