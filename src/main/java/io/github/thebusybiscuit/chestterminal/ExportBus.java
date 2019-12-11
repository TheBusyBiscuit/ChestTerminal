package io.github.thebusybiscuit.chestterminal;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunBlockHandler;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.UnregisterReason;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;

public class ExportBus extends SlimefunItem {
	
	private static final int[] border = {0, 1, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 18, 22, 24, 27, 31, 33, 34, 35, 36, 40, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 32, 23, 41};

	public ExportBus(Category category, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, id, recipeType, recipe);
		
		new BlockMenuPreset(getID(), "&3CT Export Bus") {
			
			@Override
			public void init() {
				constructMenu(this);
			}

			@Override
			public boolean canOpen(Block b, Player p) {
				return BlockStorage.getLocationInfo(b.getLocation(), "owner").equals(p.getUniqueId().toString()) || p.hasPermission("slimefun.cargo.bypass");
			}

			@Override
			public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
				return new int[0];
			}
		};
		
		registerBlockHandler(getID(), new SlimefunBlockHandler() {
			
			@Override
			public void onPlace(Player p, Block b, SlimefunItem item) {
				BlockStorage.addBlockInfo(b, "owner", p.getUniqueId().toString());
				BlockStorage.addBlockInfo(b, "index", "0");
				BlockStorage.addBlockInfo(b, "filter-type", "whitelist");
				BlockStorage.addBlockInfo(b, "filter-lore", "true");
				BlockStorage.addBlockInfo(b, "filter-durability", "true");
			}
			
			@Override
			public boolean onBreak(Player p, Block b, SlimefunItem item, UnregisterReason reason) {
				for (int slot: getInputSlots()) {
					if (BlockStorage.getInventory(b).getItemInSlot(slot) != null) b.getWorld().dropItemNaturally(b.getLocation(), BlockStorage.getInventory(b).getItemInSlot(slot));
				}
				return true;
			}
		});
	}
	
	protected void constructMenu(BlockMenuPreset preset) {
		MenuClickHandler click = (p, slot, item, action) -> false;
		
		for (int i: border) {
			preset.addItem(i, new CustomItem(Material.CYAN_STAINED_GLASS_PANE, " "), click);
		}

		preset.addItem(7, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(8, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(16, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(25, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(26, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, " "), click);

		preset.addItem(2, new CustomItem(Material.PAPER, "&3Items", "", "&bPut in all Items you want to", "&bwhitelist"), click);
	}
	
	public int[] getInputSlots() {
		return new int[] {19, 20, 21, 28, 29, 30, 37, 38, 39};
	}
}
