package com.ee.calculator;

/**
 * Created by Silver on 1.04.2016.
 */
public class Calculations implements IEntity {
    private long id;
    private long operandId;
    private float num1;
    private float num2;
    private float res;
    private long timestamp;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOperationId() {
        return this.operandId;
    }

    public void setOperationId(long operationdId) {
        this.operandId = operationdId;
    }

    public float getNum1() {
        return this.num1;
    }

    public void setNum1(float num1) {
        this.num1 = num1;
    }

    public float getNum2() {
        return this.num2;
    }

    public float getRes() {
        return this.res;
    }

    public void setRes(float res) {
        this.res = res;
    }

    public void setNum2(float num2) {
        this.num2 = num2;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOperation() {
        return String.format("%s %s", num1, num2);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", operandId, num1, num2, res, timestamp);
    }
}