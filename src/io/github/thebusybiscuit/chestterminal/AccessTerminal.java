package io.github.thebusybiscuit.chestterminal;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunBlockHandler;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.UnregisterReason;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.CargoNet;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectionModule.Action;

public class AccessTerminal extends SlimefunItem {

	public AccessTerminal(Category category, ItemStack item, String name, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, name, recipeType, recipe);

		new BlockMenuPreset(name, ChatColor.translateAlternateColorCodes('&', "&3CT Access Terminal")) {
			
			@Override
			public void init() {
				constructMenu(this);
			}

			@Override
			public void newInstance(final BlockMenu menu, final Block b) {
				try {
					menu.replaceExistingItem(46, new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjI1OTliZDk4NjY1OWI4Y2UyYzQ5ODg1MjVjOTRlMTlkZGQzOWZhZDA4YTM4Mjg0YTE5N2YxYjcwNjc1YWNjIn19fQ=="), "&7\u21E6 Previous Page", "", "&c(This may take up to a Second to update)"));
					menu.addMenuClickHandler(46, (p, slot, item, action) -> {
						int page = Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "page")) - 1;
						if (page > 0) {
							BlockStorage.addBlockInfo(b, "page", String.valueOf(page));
							newInstance(menu, b);
						}
						return false;
					});
					
					menu.replaceExistingItem(50, new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJmOTEwYzQ3ZGEwNDJlNGFhMjhhZjZjYzgxY2Y0OGFjNmNhZjM3ZGFiMzVmODhkYjk5M2FjY2I5ZGZlNTE2In19fQ=="), "&7Next Page \u21E8", "", "&c(This may take up to a Second to update)"));
					menu.addMenuClickHandler(50, (p, slot, item, action) -> {
						int page = Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "page")) + 1;
						BlockStorage.addBlockInfo(b, "page", String.valueOf(page));
						newInstance(menu, b);
						return false;
					});
					
				} catch(Exception x) {
					x.printStackTrace();
				}
			}

			@Override
			public boolean canOpen(Block b, Player p) {
				return SlimefunPlugin.getProtectionManager().hasPermission(p, b.getLocation(), Action.ACCESS_INVENTORIES);
			}

			@Override
			public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
				return new int[0];
			}
		};
		
		registerBlockHandler(name, new SlimefunBlockHandler() {
			
			@Override
			public void onPlace(Player p, Block b, SlimefunItem item) {
				BlockStorage.addBlockInfo(b, "page", "1");
			}
			
			@Override
			public boolean onBreak(Player p, Block b, SlimefunItem item, UnregisterReason reason) {
				BlockMenu inv = BlockStorage.getInventory(b);
				if (inv != null) {
					if (inv.getItemInSlot(17) != null) b.getWorld().dropItemNaturally(b.getLocation(), inv.getItemInSlot(17));
					if (inv.getItemInSlot(44) != null) b.getWorld().dropItemNaturally(b.getLocation(), inv.getItemInSlot(44));
				}
				return true;
			}
		});
	}

	protected void constructMenu(BlockMenuPreset preset) {
		MenuClickHandler click = (p, slot, item, action) -> false;
		
		preset.addItem(45, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "), click);
		preset.addItem(47, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "), click);
		preset.addItem(48, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "), click);
		preset.addItem(49, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "), click);
		preset.addItem(51, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "), click);
		
		preset.addItem(7, new CustomItem(Material.CYAN_STAINED_GLASS_PANE, " "), click);
		preset.addItem(8, new CustomItem(Material.CYAN_STAINED_GLASS_PANE, " "), click);
		preset.addItem(16, new CustomItem(Material.CYAN_STAINED_GLASS_PANE, " "), click);
		preset.addItem(25, new CustomItem(Material.CYAN_STAINED_GLASS_PANE, " "), click);
		preset.addItem(26, new CustomItem(Material.CYAN_STAINED_GLASS_PANE, " "), click);
		
		preset.addItem(34, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(35, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(43, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(52, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(53, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);
	}
	
	@Override
	public void register(boolean slimefun) {
		addItemHandler(new BlockTicker() {
			
			private final ItemStack item = new CustomItem(Material.BARRIER, "&4No Cargo Net connected!");
			private final MenuClickHandler click = (p, slot, item, action) -> false;
			
			@Override
			public void tick(Block b, SlimefunItem sf, Config data) {
				if (!CargoNet.isConnected(b)) {
					BlockMenu menu = BlockStorage.getInventory(b);
					for (int slot: CargoNet.terminal_slots) {
						menu.replaceExistingItem(slot, item);
						menu.addMenuClickHandler(slot, click);
					}
				}
			}

			@Override
			public boolean isSynchronized() {
				return true;
			}
		});

		super.register(slimefun);
	}

}
