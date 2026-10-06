package com.wardenaxe;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import net.minecraft.world.level.Level;

/** The Rowboat: a small boat with 2 seats and a chest (27 slots). */
public class RowboatEntity extends AbstractChestBoat {
    public RowboatEntity(EntityType<? extends AbstractChestBoat> type, Level level) {
        super(type, level, () -> ModItems.ROWBOAT.get());
    }
}
