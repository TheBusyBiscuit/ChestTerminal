package io.github.thebusybiscuit.chestterminal.items;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.NamespacedKey;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.chestterminal.ChestTerminal;
import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;

public class MilkyQuartz implements GEOResource {

    private final NamespacedKey key;
    private final ItemStack item;

    public MilkyQuartz(ChestTerminal plugin, ItemStack item) {
        this.key = new NamespacedKey(plugin, "milky_quartz");
        this.item = item;
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public int getDefaultSupply(Environment environment, Biome biome) {
        switch (environment) {
        case THE_END:
            return 0;
        case NETHER:
            return ThreadLocalRandom.current().nextInt(12);
        default:
            return ThreadLocalRandom.current().nextInt(8);
        }
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public int getMaxDeviation() {
        return 5;
    }

    @Override
    public String getName() {
        return "Milky Quartz";
    }

    @Override
    public boolean isObtainableFromGEOMiner() {
        return true;
    }

}
