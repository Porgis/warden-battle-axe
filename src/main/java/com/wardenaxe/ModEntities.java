package com.wardenaxe;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(WardenAxeMod.MOD_ID);

    // Same hitbox as a vanilla boat.
    public static final DeferredHolder<EntityType<?>, EntityType<RowboatEntity>> ROWBOAT = ENTITY_TYPES.registerEntityType(
            "rowboat",
            RowboatEntity::new,
            MobCategory.MISC,
            builder -> builder.sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
}
