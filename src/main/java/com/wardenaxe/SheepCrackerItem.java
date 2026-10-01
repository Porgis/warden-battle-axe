package com.wardenaxe;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

/**
 * The Sheep Cracker 9000. Hold right-click to fire arrows from your inventory.
 * Minecraft repeats right-click every 4 ticks while the button is held (about 5 shots per second),
 * and, because this item has no "use time", it works while riding or flying with an elytra.
 */
public class SheepCrackerItem extends Item {
    // Vanilla arrow damage = speed x baseDamage. Speed 3.0 x 2.0 = 6 damage per shot.
    private static final float ARROW_SPEED = 3.0F;
    private static final double ARROW_BASE_DAMAGE = 2.0D;
    private static final float INACCURACY = 0.3F;

    public SheepCrackerItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack gun = player.getItemInHand(hand);
        boolean infinite = player.hasInfiniteMaterials();
        ItemStack ammoStack = findArrow(player);

        if (ammoStack.isEmpty() && !infinite) {
            return InteractionResult.FAIL;
        }

        if (level instanceof ServerLevel) {
            ItemStack arrowAmmo = ammoStack.isEmpty() ? new ItemStack(Items.ARROW) : ammoStack.copyWithCount(1);
            if (arrowAmmo.getItem() instanceof ArrowItem arrowItem) {
                var arrow = arrowItem.createArrow(level, arrowAmmo, player, gun);
                arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, ARROW_SPEED, INACCURACY);
                arrow.setBaseDamage(ARROW_BASE_DAMAGE);
                level.addFreshEntity(arrow);
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.6F);
                if (!infinite) {
                    ammoStack.shrink(1);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private static ItemStack findArrow(Player player) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() instanceof ArrowItem) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
