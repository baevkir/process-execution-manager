package com.pem.model.operation.loop;

public class CounterLoopOperationDTO extends LoopOperationDTO {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CounterLoopOperationDTO{" +
                "counter=" + count +
                "} " + super.toString();
    }
}
