package com.pem.model.operation.loop;

public class CounterLoopOperationObject extends LoopOperationObject {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CounterLoopOperationObject{" +
                "counter=" + count +
                "} " + super.toString();
    }
}
