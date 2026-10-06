package com.wardenaxe;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * Client-only setup: tells the game how to draw the boats.
 * The boat texture is looked up from the layer name, so "chest_boat/rowboat" means
 * assets/wardenaxe/textures/entity/chest_boat/rowboat.png
 */
@EventBusSubscriber(modid = WardenAxeMod.MOD_ID, value = Dist.CLIENT)
public class ClientSetup {
    public static final ModelLayerLocation ROWBOAT_LAYER = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(WardenAxeMod.MOD_ID, "chest_boat/rowboat"), "main");

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ROWBOAT_LAYER, BoatModel::createChestBoatModel);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.ROWBOAT.get(), context -> new BoatRenderer(context, ROWBOAT_LAYER));
    }
}
