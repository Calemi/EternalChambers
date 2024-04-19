package com.calemi.chambers.command;

import com.calemi.chambers.api.chamber.ChamberInstance;
import com.calemi.chambers.api.chamber.ChamberManager;
import com.calemi.chambers.api.general.TeleportHelper;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ChamberCommand {

    public static void init() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("chamber")
                .then(literal("generate")
                        .then(argument("id", IntegerArgumentType.integer(0, 63))
                                .executes(context -> {
                                    generateChamber(context.getSource().getWorld(), IntegerArgumentType.getInteger(context, "id"));
                                    context.getSource().sendFeedback(() -> Text.literal("Generating Chamber..."), false);
                                    return 1;
                                })))
                .then(literal("tp")
                        .then(argument("id", IntegerArgumentType.integer(0, 63))
                                .executes(context -> {
                                    tpToChamber(context.getSource().getPlayer(), IntegerArgumentType.getInteger(context, "id"));
                                    return 1;
                                })))));
    }

    private static void generateChamber(World world, int id) {
        ChamberInstance chamberInstance = ChamberManager.singleton.getChamberInstance(id);

        if (chamberInstance == null) {
            return;
        }

        chamberInstance.getGenerator().clear();
        chamberInstance.getGenerator().start();
    }

    private static void tpToChamber(ServerPlayerEntity player, int id) {

        ChamberInstance chamberInstance = ChamberManager.singleton.getChamberInstance(id);

        if (chamberInstance == null) {
            return;
        }

        TeleportHelper.teleportToChamber(player, chamberInstance.getGenerator().getChamberOrigin());
    }
}
