package com.wardenaxe;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(WardenAxeMod.MOD_ID)
public class WardenAxeMod {
    public static final String MOD_ID = "wardenaxe";

    public WardenAxeMod(IEventBus modEventBus) {
        ModItems.ITEMS.register(modEventBus);
        NeoForge.EVENT_BUS.addListener(WardenDropHandler::onLivingDrops);
    }
}
