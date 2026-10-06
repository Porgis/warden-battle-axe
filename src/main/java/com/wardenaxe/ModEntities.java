package com.wardenaxe;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(WardenAxeMod.MOD_ID);

    // A bit bigger than a vanilla boat (1.375), because the Rowboat is 1 block longer.
    public static final DeferredHolder<EntityType<?>, EntityType<RowboatEntity>> ROWBOAT = ENTITY_TYPES.registerEntityType(
            "rowboat",
            RowboatEntity::new,
            MobCategory.MISC,
            builder -> builder.sized(2.0F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
}
