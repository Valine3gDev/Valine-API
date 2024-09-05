package io.github.valine3gdev.api.energy;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.math.BigDecimal;

@AutoRegisterCapability
public interface IValineEnergyStorage {
    BigDecimal receiveEnergy(BigDecimal maxReceive, boolean simulate);

    BigDecimal extractEnergy(BigDecimal maxExtract, boolean simulate);

    BigDecimal getEnergyStored();

    BigDecimal getMaxEnergyStored();

    boolean canExtract();

    boolean canReceive();
}
