package com.ee.calculator;

/**
 * Created by Silver on 1.04.2016.
 */
public class CalculationStats implements IEntity {
    private long id;
    private long operandId;
    private long daystamp;
    private int dayCount;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDaystamp() {
        return this.daystamp;
    }

    public void setDaystamp(long daystamp) {
        this.daystamp = daystamp;
    }

    public long getOperandId() {
        return this.operandId;
    }

    public void setOperandId(long operandId) {
        this.operandId = operandId;
    }

    public int getDayCounter() {
        return this.dayCount;
    }

    public void setDayCounter(int dayCounter) {
        this.dayCount = dayCounter;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", operandId, daystamp, dayCount);
    }
}
