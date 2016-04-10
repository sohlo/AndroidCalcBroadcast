package com.ee.calculator;

/**
 * Created by Silver on 1.04.2016.
 */
public class CalculationType implements IEntity {
    private long id;
    private String operand;
    private long lifetimeCounter;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperand() {
        return this.operand;
    }

    public void setOperand(String operation) {
        this.operand = operation.trim();
    }

    public long getLifetimeCounter() {
        return this.lifetimeCounter;
    }

    public void setLifetimeCounter(long lifetimeCounter) {
        this.lifetimeCounter = lifetimeCounter;
    }

    @Override
    public String toString() {
        return String.format("%s %d", operand, lifetimeCounter);
    }

}
