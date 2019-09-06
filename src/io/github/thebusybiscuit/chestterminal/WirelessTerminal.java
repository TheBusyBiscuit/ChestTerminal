package io.github.thebusybiscuit.chestterminal;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.energy.ItemEnergy;

public abstract class WirelessTerminal extends SimpleSlimefunItem<ItemInteractionHandler> {

	public WirelessTerminal(Category category, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, id, recipeType, recipe);
	}
	
	public abstract int getRange();

	@Override
	public ItemInteractionHandler getItemHandler() {
		return (e, p, stack) -> {
			if (SlimefunManager.isItemSimiliar(stack, getItem(), false)) {
				ItemMeta im = stack.getItemMeta();
				List<String> lore = im.getLore();
				if (lore.isEmpty()) return true;
				
				if (e.getClickedBlock() != null) {
					if (BlockStorage.check(e.getClickedBlock(), "CHEST_TERMINAL")) {
						lore.set(0, ChatColor.translateAlternateColorCodes('&', "&8\u21E8 &7Linked to: &8") + e.getClickedBlock().getWorld().getName() + " X: " + e.getClickedBlock().getX() + " Y: " + e.getClickedBlock().getY() + " Z: " + e.getClickedBlock().getZ());
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bLink established!"));
						im.setLore(lore);
						stack.setItemMeta(im);
						p.getInventory().setItemInMainHand(stack);
					}
					else {
						openRemoteTerminal(p, stack, lore.get(0), getRange());
					}
					
					e.setCancelled(true);
					PlayerInventory.update(p);
				}
				else {
					openRemoteTerminal(p, stack, lore.get(0), getRange());
				}
				
				return true;
			}
			else return false;
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
		PlayerInventory.update(p);
		BlockStorage.getInventory(block).open(p);
	}

}
