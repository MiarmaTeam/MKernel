package net.miarma.mkernel.common.minecraft.events;

import net.miarma.mkernel.util.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlockEventHelper {
    private final Player player;
    private final Block block;

    private BlockEventHelper(Player player, Block block) {
        this.player = player;
        this.block = block;
    }

    public static BlockEventHelper of(Player player, Block block) {
        return new BlockEventHelper(player, block);
    }

    public void handleCrop() {
        Material type = block.getType();

        switch (type) {
            case WHEAT -> harvestAgeable("minecraft:wheat[age=0]", 7, Material.WHEAT, 2.25);
            case POTATOES -> harvestAgeable("minecraft:potatoes[age=0]", 7, Material.POTATO, 2.25);
            case CARROTS -> harvestAgeable("minecraft:carrots[age=0]", 7, Material.CARROT, 2.25);
            case BEETROOTS -> harvestAgeable("minecraft:beetroots[age=0]", 3, Material.BEETROOT, 2.75);
            case COCOA -> harvestAgeable("minecraft:cocoa[age=0]", 2, Material.COCOA_BEANS, 2.25);
            default -> {
                if (VersionUtil.isAtLeast("1.20")) {
                    if (type.name().equals("TORCHFLOWER_CROP")) {
                        harvestBreakOnly(1, VersionUtil.getSafeMaterial("TORCHFLOWER"));
                    } else if (type.name().equals("PITCHER_CROP")) {
                        harvestBreakOnly(4, VersionUtil.getSafeMaterial("PITCHER_PLANT"));
                    }
                }
            }
        }
    }

    private void harvestAgeable(String resetBlockData, int requiredAge, Material drop, double multiplier) {
        String ageString = "age=" + requiredAge;
        if (!block.getBlockData().getAsString().contains(ageString)) return;

        int amount = (int) ((Math.random() + 1) * multiplier);
        block.setBlockData(Bukkit.createBlockData(resetBlockData));
        dropItem(drop, amount);
    }

    private void harvestBreakOnly(int requiredAge, Material drop) {
        String ageString = "age=" + requiredAge;
        if (!block.getBlockData().getAsString().contains(ageString)) return;

        block.setType(Material.AIR);
        dropItem(drop, 1);
    }

    private void dropItem(Material material, int amount) {
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(material, amount));
        player.playSound(block.getLocation(), Sound.BLOCK_GRASS_BREAK, 1f, 1f);
    }
}

