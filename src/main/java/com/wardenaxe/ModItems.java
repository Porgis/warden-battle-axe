package com.wardenaxe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ToolMaterial;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(WardenAxeMod.MOD_ID);

    // Damage = 1 (player base) + 4 (netherite material bonus) + 7 = 12   (vanilla netherite axe uses 5 -> 10)
    // Speed  = 4 (player base) - 3.3 = 0.7                               (vanilla netherite axe uses -3.0 -> 1.0)
    // Critical hits use the vanilla 1.5x multiplier, so a crit does 18.
    public static final DeferredItem<Item> WARDEN_BATTLE_AXE = ITEMS.registerItem(
            "warden_battle_axe",
            props -> new Item(props
                    .axe(ToolMaterial.NETHERITE, 7.0F, -3.3F)
                    .rarity(Rarity.RARE)
                    .fireResistant()));

    public static final DeferredItem<Item> SHEEP_CRACKER_9000 = ITEMS.registerItem(
            "sheep_cracker_9000",
            props -> new SheepCrackerItem(props
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)));
}
