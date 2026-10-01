package com.wardenaxe;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(WardenAxeMod.MOD_ID)
public class WardenAxeMod {
    public static final String MOD_ID = "wardenaxe";

    public WardenAxeMod(IEventBus modEventBus) {
        ModItems.ITEMS.register(modEventBus);
        modEventBus.addListener(WardenAxeMod::addCreative);
        NeoForge.EVENT_BUS.addListener(WardenDropHandler::onLivingDrops);
        NeoForge.EVENT_BUS.addListener(SheepQuestHandler::onSheepDeath);
    }

    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.WARDEN_BATTLE_AXE);
            event.accept(ModItems.SHEEP_CRACKER_9000);
        }
    }
}
