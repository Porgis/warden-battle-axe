package com.wardenaxe;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import net.minecraft.world.level.Level;

/** The Rowboat: a small boat with 2 seats and a chest (27 slots). */
public class RowboatEntity extends AbstractChestBoat {
    public RowboatEntity(EntityType<? extends AbstractChestBoat> type, Level level) {
        super(type, level, () -> ModItems.ROWBOAT.get());
    }

    /** How high passengers sit in the boat (same as a vanilla boat). */
    @Override
    protected double rideHeight(EntityDimensions dimensions) {
        return dimensions.height() / 3.0F;
    }
}
