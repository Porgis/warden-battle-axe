package com.wardenaxe;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** The Rowboat: a long boat with 2 seats (driver in front, passenger behind) and a chest at the back. */
public class RowboatEntity extends AbstractChestBoat {
    // Seat positions in blocks, measured forward from the middle of the boat.
    private static final float DRIVER_SEAT = 0.7F;
    private static final float PASSENGER_SEAT = -0.2F;

    public RowboatEntity(EntityType<? extends AbstractChestBoat> type, Level level) {
        super(type, level, () -> ModItems.ROWBOAT.get());
    }

    /** How high passengers sit in the boat (same as a vanilla boat). */
    @Override
    protected double rideHeight(EntityDimensions dimensions) {
        return dimensions.height() / 3.0F;
    }

    /** Vanilla chest boats only seat 1; the Rowboat seats 2. */
    @Override
    protected int getMaxPassengers() {
        return 2;
    }

    /** The first rider sits in front; the second sits behind them, in front of the chest. */
    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float partialTick) {
        float forward = DRIVER_SEAT;
        if (this.getPassengers().size() > 1 && this.getPassengers().indexOf(passenger) != 0) {
            forward = PASSENGER_SEAT;
        }
        return new Vec3(0.0D, this.rideHeight(dimensions), forward).yRot(-this.getYRot() * ((float) Math.PI / 180.0F));
    }
}
