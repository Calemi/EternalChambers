package com.calemi.chambers.item;

import com.calemi.chambers.api.general.TeleportHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChamberTeleporterItem extends Item {

    public ChamberTeleporterItem() {
        super(new FabricItemSettings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getStackInHand(hand);

        if (world.isClient) {
            return TypedActionResult.success(stack);
        }

        if (TeleportHelper.isInChamber(player)) {
            TeleportHelper.teleportToChamber(player, new BlockPos(0, 100, 0));
        }

        else if (TeleportHelper.isInOverworld(player)) {
            TeleportHelper.teleportToOverworld(player, new BlockPos(0, 100, 0));
        }

        return TypedActionResult.success(stack);
    }
}
