package io.github.thebusybiscuit.chestterminal;

import org.bukkit.ChatColor;
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
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;

public class ImportBus extends SlimefunItem {
	
	private static final int[] border = {0, 1, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 18, 22, 24, 27, 31, 33, 34, 35, 36, 40, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};

	public ImportBus(Category category, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, id, recipeType, recipe);
		
		new BlockMenuPreset(getID(), ChatColor.translateAlternateColorCodes('&', "&3CT Import Bus")) {
			
			@Override
			public void init() {
				constructMenu(this);
			}
			
			@Override
			public void newInstance(final BlockMenu menu, final Block b) {
				try {
					if (!BlockStorage.hasBlockInfo(b) || BlockStorage.getLocationInfo(b.getLocation(), "filter-type") == null || BlockStorage.getLocationInfo(b.getLocation(), "filter-type").equals("whitelist")) {
						menu.replaceExistingItem(23, new CustomItem(Material.WHITE_WOOL, "&7Type: &rWhitelist", "", "&e> Click to change it to Blacklist"));
						menu.addMenuClickHandler(23, (p, slot, item, action) -> {
							BlockStorage.addBlockInfo(b, "filter-type", "blacklist");
							newInstance(menu, b);
							return false;
						});
					}
					else {
						menu.replaceExistingItem(23, new CustomItem(Material.BLACK_WOOL, "&7Type: &8Blacklist", "", "&e> Click to change it to Whitelist"));
						menu.addMenuClickHandler(23, (p, slot, item, action) -> {
							BlockStorage.addBlockInfo(b, "filter-type", "whitelist");
							newInstance(menu, b);
							return false;
						});
					}
					
					if (!BlockStorage.hasBlockInfo(b) || BlockStorage.getLocationInfo(b.getLocation(), "filter-durability") == null || BlockStorage.getLocationInfo(b.getLocation(), "filter-durability").equals("false")) {
						menu.replaceExistingItem(41, new CustomItem(Material.STONE_SWORD, "&7Include Sub-IDs/Durability: &4\u2718", "", "&e> Click to toggle whether the Durability has to match"));
						menu.addMenuClickHandler(41, (p, slot, item, action) -> {
							BlockStorage.addBlockInfo(b, "filter-durability", "true");
							newInstance(menu, b);
							return false;
						});
					}
					else {
						menu.replaceExistingItem(41, new CustomItem(Material.GOLDEN_SWORD, "&7Include Sub-IDs/Durability: &2\u2714", "", "&e> Click to toggle whether the Durability has to match"));
						menu.addMenuClickHandler(41, (p, slot, item, action) -> {
							BlockStorage.addBlockInfo(b, "filter-durability", "false");
							newInstance(menu, b);
							return false;
						});
					}
					
					if (!BlockStorage.hasBlockInfo(b) || BlockStorage.getLocationInfo(b.getLocation(), "filter-lore") == null || BlockStorage.getLocationInfo(b.getLocation(), "filter-lore").equals("true")) {
						menu.replaceExistingItem(32, new CustomItem(Material.MAP, "&7Include Lore: &2\u2714", "", "&e> Click to toggle whether the Lore has to match"));
						menu.addMenuClickHandler(32, (p, slot, item, action) -> {
							BlockStorage.addBlockInfo(b, "filter-lore", "false");
							newInstance(menu, b);
							return false;
						});
					}
					else {
						menu.replaceExistingItem(32, new CustomItem(Material.MAP, "&7Include Lore: &4\u2718", "", "&e> Click to toggle whether the Lore has to match"));
						menu.addMenuClickHandler(32, (p, slot, item, action) -> {
							BlockStorage.addBlockInfo(b, "filter-lore", "true");
							newInstance(menu, b);
							return false;
						});
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
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
				BlockStorage.addBlockInfo(b, "filter-durability", "false");
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

		preset.addItem(7, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(8, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(16, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(25, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(26, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);

		preset.addItem(2, new CustomItem(Material.PAPER, "&3Items", "", "&bPut in all Items you want to", "&bblacklist/whitelist"), click);
	}
	
	public int[] getInputSlots() {
		return new int[] {19, 20, 21, 28, 29, 30, 37, 38, 39};
	}
}
