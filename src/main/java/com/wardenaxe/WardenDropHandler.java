package com.wardenaxe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

/**
 * Drops a Warden Battle Axe when a player kills a Warden, until 3 have dropped in this world.
 * The count is saved in the world folder in "wardenaxe_dropped.txt" (delete that file to reset it).
 */
public class WardenDropHandler {
    private static final int MAX_AXES = 3;

    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getEntity().getType() != EntityType.WARDEN) return;
        if (!(event.getSource().getEntity() instanceof ServerPlayer)) return;
        if (!(event.getEntity().level() instanceof ServerLevel level)) return;

        MinecraftServer server = level.getServer();
        int dropped = readCount(server);
        if (dropped >= MAX_AXES) return;

        ItemEntity drop = new ItemEntity(level,
                event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                new ItemStack(ModItems.WARDEN_BATTLE_AXE.get()));
        event.getDrops().add(drop);

        dropped++;
        writeCount(server, dropped);
        server.getPlayerList().broadcastSystemMessage(
                Component.literal("A Warden Battle Axe has dropped! (" + dropped + "/" + MAX_AXES + ")")
                        .withStyle(ChatFormatting.DARK_AQUA),
                false);
    }

    private static Path countFile(MinecraftServer server) {
        return server.getWorldPath(LevelResource.ROOT).resolve("wardenaxe_dropped.txt");
    }

    private static int readCount(MinecraftServer server) {
        try {
            Path file = countFile(server);
            if (Files.exists(file)) {
                return Integer.parseInt(Files.readString(file, StandardCharsets.UTF_8).trim());
            }
        } catch (IOException | NumberFormatException ignored) {
        }
        return 0;
    }

    private static void writeCount(MinecraftServer server, int value) {
        try {
            Files.writeString(countFile(server), Integer.toString(value), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
