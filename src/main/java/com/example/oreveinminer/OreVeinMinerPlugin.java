package com.example.oreveinminer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class OreVeinMinerPlugin extends JavaPlugin implements Listener {

    private static final Set<Material> ORES = EnumSet.of(
            Material.COAL_ORE,
            Material.DEEPSLATE_COAL_ORE,
            Material.IRON_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.COPPER_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.GOLD_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.REDSTONE_ORE,
            Material.DEEPSLATE_REDSTONE_ORE,
            Material.LAPIS_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DIAMOND_ORE,
            Material.DEEPSLATE_DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.DEEPSLATE_EMERALD_ORE,
            Material.NETHER_QUARTZ_ORE,
            Material.NETHER_GOLD_ORE
    );

    private static final int MAX_BLOCKS = 64;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("OreVeinMiner enabled");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block startBlock = event.getBlock();
        Material type = startBlock.getType();

        if (!ORES.contains(type)) return;

        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();

        Set<Block> vein = new HashSet<>();
        findVein(startBlock, type, vein);

        for (Block block : vein) {
            if (block.equals(startBlock)) continue;
            block.breakNaturally(tool);
        }
    }

    private void findVein(Block block, Material type, Set<Block> vein) {
        if (vein.size() >= MAX_BLOCKS) return;
        if (vein.contains(block)) return;
        if (block.getType() != type) return;

        vein.add(block);

        findVein(block.getRelative(1, 0, 0), type, vein);
        findVein(block.getRelative(-1, 0, 0), type, vein);
        findVein(block.getRelative(0, 1, 0), type, vein);
        findVein(block.getRelative(0, -1, 0), type, vein);
        findVein(block.getRelative(0, 0, 1), type, vein);
        findVein(block.getRelative(0, 0, -1), type, vein);
    }
}
