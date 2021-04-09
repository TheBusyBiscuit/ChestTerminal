package io.github.thebusybiscuit.chestterminal.items;

import org.bukkit.block.Block;

import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

class CTBlockBreakHandler extends SimpleBlockBreakHandler {

    private final int[] slots;

    CTBlockBreakHandler(int[] slots) {
        this.slots = slots;
    }

    @Override
    public void onBlockBreak(Block b) {
        BlockMenu menu = BlockStorage.getInventory(b);

        if (menu != null) {
            menu.dropItems(b.getLocation(), slots);
        }
    }

}
