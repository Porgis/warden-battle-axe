package com.wardenaxe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * Counts how many sheep each player has killed (saved per player in the world folder, in "wardenaxe_sheep").
 * Shows "N/50 sheep killed" above the hotbar after each kill.
 * Reaching 50 spawns the boss sheep (see SheepBossHandler).
 */
public class SheepQuestHandler {
    public static final int GOAL = 50;

    public static void onSheepDeath(LivingDeathEvent event) {
        if (!BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType()).toString().equals("minecraft:sheep")) return;
        if (SheepBossHandler.isBoss(event.getEntity())) return;
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;
        if (!(event.getEntity().level() instanceof ServerLevel level)) return;

        MinecraftServer server = level.getServer();
        UUID id = player.getUUID();
        int count = readCount(server, id) + 1;

        if (count >= GOAL) {
            writeCount(server, id, 0);
<<<<<<< HEAD
            player.sendOverlayMessage(
                    Component.literal(GOAL + "/" + GOAL + " sheep killed! The boss sheep has appeared!")
                            .withStyle(ChatFormatting.GOLD));
            SheepBossHandler.spawnBoss(level,
                    event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
=======
            player.sendOverlayMessage(
                    Component.literal(GOAL + "/" + GOAL + " sheep killed!").withStyle(ChatFormatting.GOLD));
            // Stage 3: the boss sheep will spawn here.
>>>>>>> b857bd664ae2e6a4b45535ec32047b472cf5d23e
        } else {
            writeCount(server, id, count);
            player.sendOverlayMessage(
                    Component.literal(count + "/" + GOAL + " sheep killed").withStyle(ChatFormatting.YELLOW));
        }
    }

    private static Path countFile(MinecraftServer server, UUID id) throws IOException {
        Path dir = server.getWorldPath(LevelResource.ROOT).resolve("wardenaxe_sheep");
        Files.createDirectories(dir);
        return dir.resolve(id + ".txt");
    }

    private static int readCount(MinecraftServer server, UUID id) {
        try {
            Path file = countFile(server, id);
            if (Files.exists(file)) {
                return Integer.parseInt(Files.readString(file, StandardCharsets.UTF_8).trim());
            }
        } catch (IOException | NumberFormatException ignored) {
        }
        return 0;
    }

    private static void writeCount(MinecraftServer server, UUID id, int value) {
        try {
            Files.writeString(countFile(server, id), Integer.toString(value), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
