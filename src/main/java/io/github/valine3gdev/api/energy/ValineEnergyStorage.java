package io.github.valine3gdev.api.energy;

import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.math.BigDecimal;

public class ValineEnergyStorage implements IValineEnergyStorage, INBTSerializable<Tag> {
    protected BigDecimal energy;
    protected BigDecimal capacity;
    protected BigDecimal maxReceive;
    protected BigDecimal maxExtract;

    public ValineEnergyStorage(BigDecimal capacity) {
        this(capacity, capacity, capacity, BigDecimal.ZERO);
    }

    public ValineEnergyStorage(BigDecimal capacity, BigDecimal maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, BigDecimal.ZERO);
    }

    public ValineEnergyStorage(BigDecimal capacity, BigDecimal maxReceive, BigDecimal maxExtract) {
        this(capacity, maxReceive, maxExtract, BigDecimal.ZERO);
    }

    public ValineEnergyStorage(BigDecimal capacity, BigDecimal maxReceive, BigDecimal maxExtract, BigDecimal energy) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = capacity.min(energy).max(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal receiveEnergy(BigDecimal maxReceive, boolean simulate) {
        if (!this.canReceive()) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal energyReceived = this.maxReceive.min(maxReceive).min(this.capacity.subtract(this.energy));
            if (!simulate) {
                this.energy = this.energy.add(energyReceived);
            }
            return energyReceived;
        }
    }

    @Override
    public BigDecimal extractEnergy(BigDecimal maxExtract, boolean simulate) {
        if (!this.canExtract()) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal energyExtracted = this.maxExtract.min(maxExtract).min(this.energy);
            if (!simulate) {
                this.energy = this.energy.subtract(energyExtracted);
            }

            return energyExtracted;
        }
    }

    @Override
    public BigDecimal getEnergyStored() {
        return this.energy;
    }

    @Override
    public BigDecimal getMaxEnergyStored() {
        return this.capacity;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public Tag serializeNBT() {
        return StringTag.valueOf(this.getEnergyStored().toString());
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        if (nbt instanceof StringTag stringNbt) {
            try {
                this.energy = new BigDecimal(stringNbt.getAsString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Can not deserialize from an invalid number");
            }
        } else {
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        }
    }
}
