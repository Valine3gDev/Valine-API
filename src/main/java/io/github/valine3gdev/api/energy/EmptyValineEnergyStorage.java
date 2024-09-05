package io.github.valine3gdev.api.energy;

import java.math.BigDecimal;

public class EmptyValineEnergyStorage implements IValineEnergyStorage {
    public static final EmptyValineEnergyStorage INSTANCE = new EmptyValineEnergyStorage();

    protected EmptyValineEnergyStorage() {
    }

    @Override
    public BigDecimal receiveEnergy(BigDecimal maxReceive, boolean simulate) {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal extractEnergy(BigDecimal maxExtract, boolean simulate) {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getEnergyStored() {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getMaxEnergyStored() {
        return BigDecimal.ZERO;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
