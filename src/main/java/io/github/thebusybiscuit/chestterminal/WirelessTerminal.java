package io.github.thebusybiscuit.chestterminal;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.energy.ItemEnergy;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;

public abstract class WirelessTerminal extends SimpleSlimefunItem<ItemUseHandler> {

    public WirelessTerminal(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    public abstract int getRange();

    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            ItemStack stack = e.getItem();
            ItemMeta im = stack.getItemMeta();
            List<String> lore = im.getLore();
            if (lore.isEmpty()) return;

            if (e.getClickedBlock().isPresent()) {
                Player p = e.getPlayer();
                Block b = e.getClickedBlock().get();

                if (BlockStorage.check(b, "CHEST_TERMINAL")) {
                    lore.set(0, ChatColors.color("&8\u21E8 &7Linked to: &8") + b.getWorld().getName() + " X: " + b.getX() + " Y: " + b.getY() + " Z: " + b.getZ());
                    p.sendMessage(ChatColors.color("&bLink established!"));
                    im.setLore(lore);
                    stack.setItemMeta(im);
                    p.getInventory().setItemInMainHand(stack);
                }
                else {
                    openRemoteTerminal(p, stack, lore.get(0), getRange());
                }

                e.cancel();
            }
            else {
                openRemoteTerminal(e.getPlayer(), stack, lore.get(0), getRange());
            }
        };
    }

    private void openRemoteTerminal(Player p, ItemStack stack, String loc, int range) {
        if (loc.equals(ChatColor.translateAlternateColorCodes('&', "&8\u21E8 &7Linked to: &cNowhere"))) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Failed &c- This Device has not been linked to a Chest Terminal!"));
            return;
        }

        loc = loc.replace(ChatColor.translateAlternateColorCodes('&', "&8\u21E8 &7Linked to: &8"), "");
        World world = Bukkit.getWorld(loc.split(" X: ")[0]);
        if (world == null) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Failed &c- The Chest Terminal that this Device has been linked to no longer exists!"));
            return;
        }
        int x = Integer.parseInt(loc.split(" X: ")[1].split(" Y: ")[0]);
        int y = Integer.parseInt(loc.split(" Y: ")[1].split(" Z: ")[0]);
        int z = Integer.parseInt(loc.split(" Z: ")[1]);

        Block block = world.getBlockAt(x, y, z);

        if (!BlockStorage.check(block, "CHEST_TERMINAL")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Failed &c- The Chest Terminal that this Device has been linked to no longer exists!"));
            return;
        }

        float charge = ItemEnergy.getStoredEnergy(stack);
        if (charge < 0.5F) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Failed &c- You are out of Energy!"));
            return;
        }

        if (range > 0 && !world.getUID().equals(p.getWorld().getUID())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Failed &c- You are out of Range!"));
            return;
        }
        if (range > 0 && block.getLocation().distance(p.getLocation()) > range) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Failed &c- You are out of Range!"));
            return;
        }

        p.getInventory().setItemInMainHand(ItemEnergy.chargeItem(stack, -0.5F));
        BlockStorage.getInventory(block).open(p);
    }

}
