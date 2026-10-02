package com.wardenaxe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * The boss sheep: a normal sheep that is 4x size, has 4x health, and chases and "bucks" the nearest player.
 * It is recognised by its size attribute being exactly 4.
 * It drops 10 wool, 5 iron ingots and the Sheep Cracker 9000.
 */
public class SheepBossHandler {
    private static final double BOSS_SCALE = 4.0D;

    public static boolean isBoss(Entity entity) {
        if (!(entity instanceof LivingEntity living)) return false;
        var scale = living.getAttribute(Attributes.SCALE);
        return scale != null && scale.getBaseValue() == BOSS_SCALE;
    }

    /** Spawns the boss sheep at the given position. */
    public static void spawnBoss(ServerLevel level, double x, double y, double z) {
        EntityType<?> sheepType = null;
        for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
            if (BuiltInRegistries.ENTITY_TYPE.getKey(type).toString().equals("minecraft:sheep")) {
                sheepType = type;
                break;
            }
        }
        if (sheepType == null) return;

        Entity entity = sheepType.create(level, EntitySpawnReason.EVENT);
        if (!(entity instanceof Mob mob)) return;

        var scale = mob.getAttribute(Attributes.SCALE);
        if (scale != null) scale.setBaseValue(BOSS_SCALE);    // 4x size
        var maxHealth = mob.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) maxHealth.setBaseValue(32.0D); // 4x a sheep's 8 health
        mob.setHealth(32.0F);

        mob.setCustomName(Component.literal("Sheep Cracker Boss"));
        mob.setCustomNameVisible(true);
        mob.setPos(x, y, z);
        level.addFreshEntity(mob);
    }

    /** Runs every tick for every entity; only does anything for the boss sheep. */
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Mob mob)) return;
        if (!(mob.level() instanceof ServerLevel level)) return;
        if (!isBoss(mob)) return;

        // Remove the sheep's normal behaviour (fleeing, wandering, eating grass).
        if (mob.tickCount % 20 == 0) {
            mob.goalSelector.removeAllGoals(goal -> true);
        }

        ServerPlayer target = nearestPlayer(level, mob, 24.0D);
        if (target == null) return;

        if (mob.tickCount % 10 == 0) {
            mob.getNavigation().moveTo(target, 1.3D);
        }

        // Attack: close enough, once every 1.5 seconds.
        if (mob.distanceToSqr(target) < 20.0D && mob.tickCount % 30 == 0) {
            buck(level, mob, target);
        }
    }

    /** The boss hops up with a neigh, hits for 1 heart, and throws the player away. */
    private static void buck(ServerLevel level, Mob mob, ServerPlayer target) {
        mob.setDeltaMovement(mob.getDeltaMovement().add(0.0D, 0.5D, 0.0D));
        level.playSound(null, mob.getX(), mob.getY(), mob.getZ(),
                SoundEvents.HORSE_ANGRY, SoundSource.HOSTILE, 1.5F, 0.7F);
        var source = level.damageSources().mobAttack(mob);
        target.hurtServer(level, source, 2.0F); // 2 damage = 1 heart
        target.knockback(1.6D, mob.getX() - target.getX(), mob.getZ() - target.getZ(), source, 1.0F);
    }

    private static ServerPlayer nearestPlayer(ServerLevel level, Mob mob, double range) {
        ServerPlayer best = null;
        double bestDistance = range * range;
        for (ServerPlayer player : level.players()) {
            if (player.isSpectator() || player.isCreative()) continue;
            double distance = player.distanceToSqr(mob);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = player;
            }
        }
        return best;
    }

    /** Looks an item up by its id, e.g. "minecraft:white_wool". */
    private static Item findItem(String id) {
        for (Item item : BuiltInRegistries.ITEM) {
            if (BuiltInRegistries.ITEM.getKey(item).toString().equals(id)) {
                return item;
            }
        }
        return null;
    }

    /** Replaces the boss's normal drops with 10 wool, 5 iron ingots and the Sheep Cracker 9000. */
    public static void onBossDrops(LivingDropsEvent event) {
        if (!isBoss(event.getEntity())) return;
        if (!(event.getEntity().level() instanceof ServerLevel level)) return;

        double x = event.getEntity().getX();
        double y = event.getEntity().getY();
        double z = event.getEntity().getZ();

        event.getDrops().clear();
        Item wool = findItem("minecraft:white_wool");
        if (wool != null) {
            event.getDrops().add(new ItemEntity(level, x, y, z, new ItemStack(wool, 10)));
        }
        event.getDrops().add(new ItemEntity(level, x, y, z, new ItemStack(Items.IRON_INGOT, 5)));
        event.getDrops().add(new ItemEntity(level, x, y, z, new ItemStack(ModItems.SHEEP_CRACKER_9000.get())));
    }
}
