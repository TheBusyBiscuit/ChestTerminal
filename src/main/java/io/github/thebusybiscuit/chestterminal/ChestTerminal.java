package io.github.thebusybiscuit.chestterminal;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.item_transport.CargoNet;
import me.mrCookieSlime.Slimefun.bstats.bukkit.Metrics;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.Updater;

public class ChestTerminal extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		Config cfg = new Config(this);
		
		// Setting up bStats
		new Metrics(this, 5503);

		// Setting up the Auto-Updater
		Updater updater = null;

		if (getDescription().getVersion().startsWith("DEV - ")) {
			// If we are using a development build, we want to switch to our custom 
			updater = new GitHubBuildsUpdater(this, getFile(), "TheBusyBiscuit/ChestTerminal/master");
		}

		if (updater != null && cfg.getBoolean("options.auto-update")) updater.start();
		
		Category category = new Category(new NamespacedKey(this, "chest_terminal"), new CustomItem(SlimefunItems.CHEST_TERMINAL, "&5Chest Terminal", "", "&a> Click to open"));
		
		SlimefunItemStack milkyQuartz = new SlimefunItemStack("MILKY_QUARTZ", Material.QUARTZ, "&rMilky Quartz");
		
		SlimefunItemStack wirelessTerminal16 = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_16", Material.ITEM_FRAME, "&3CT Wireless Access Terminal &b(16)", "&8\u21E8 &7Linked to: &cNowhere", "&8\u21E8 &7Range: &e16 Blocks", "&c&o&8\u21E8 &e\u26A1 &70 / 10 J", "", "&7If this Block is linked to an Access Terminal", "&7it will be able to remotely access that Terminal", "", "&7&eRight Click on an Access Terminal &7to link", "&7&eRight Click&7 to open the linked Terminal");
		SlimefunItemStack wirelessTerminal64 = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_64", Material.ITEM_FRAME, "&3CT Wireless Access Terminal &b(64)", "&8\u21E8 &7Linked to: &cNowhere", "&8\u21E8 &7Range: &e64 Blocks", "&c&o&8\u21E8 &e\u26A1 &70 / 25 J", "", "&7If this Block is linked to an Access Terminal", "&7it will be able to remotely access that Terminal", "", "&7&eRight Click on an Access Terminal &7to link", "&7&eRight Click&7 to open the linked Terminal");
		SlimefunItemStack wirelessTerminal128 = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_128", Material.ITEM_FRAME, "&3CT Wireless Access Terminal &b(128)", "&8\u21E8 &7Linked to: &cNowhere", "&8\u21E8 &7Range: &e128 Blocks", "&c&o&8\u21E8 &e\u26A1 &70 / 50 J", "", "&7If this Block is linked to an Access Terminal", "&7it will be able to remotely access that Terminal", "", "&7&eRight Click on an Access Terminal &7to link", "&7&eRight Click&7 to open the linked Terminal");
		SlimefunItemStack wirelessTerminalTransdimensional = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_TRANSDIMENSIONAL", Material.ITEM_FRAME, "&3CT Wireless Access Terminal &b(Transdimensional)", "&8\u21E8 &7Linked to: &cNowhere", "&8\u21E8 &7Range: &eUnlimited", "&c&o&8\u21E8 &e\u26A1 &70 / 50 J", "", "&7If this Block is linked to an Access Terminal", "&7it will be able to remotely access that Terminal", "", "&7&eRight Click on an Access Terminal &7to link", "&7&eRight Click&7 to open the linked Terminal");
		
		new SlimefunItem(category, milkyQuartz, new RecipeType(SlimefunItems.GEO_MINER), new ItemStack[0]).register();
		
		new SlimefunItem(category, new SlimefunItemStack("CT_PANEL", SlimefunItems.CHEST_TERMINAL, "&3CT Illuminated Panel", "&7Crafting Component"), RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.BLISTERING_INGOT_3, milkyQuartz, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REDSTONE_ALLOY, milkyQuartz, SlimefunItems.BLISTERING_INGOT_3, milkyQuartz})
		.register();
		
		new AccessTerminal(category, (SlimefunItemStack) SlimefunItems.CHEST_TERMINAL, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER_3, milkyQuartz, SlimefunItems.POWER_CRYSTAL, SlimefunItem.getItem("CT_PANEL"), SlimefunItems.POWER_CRYSTAL, SlimefunItems.PLASTIC_SHEET, SlimefunItems.ENERGY_REGULATOR, SlimefunItems.PLASTIC_SHEET})
		.register();
		
		new ImportBus(category, (SlimefunItemStack) SlimefunItems.CT_IMPORT_BUS, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {SlimefunItems.REDSTONE_ALLOY, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.CARGO_INPUT, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.PLASTIC_SHEET, SlimefunItems.CARGO_MOTOR, SlimefunItems.PLASTIC_SHEET})
		.register();
		
		new ExportBus(category, (SlimefunItemStack) SlimefunItems.CT_EXPORT_BUS, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {null, SlimefunItems.DAMASCUS_STEEL_INGOT, null, SlimefunItems.ALUMINUM_BRONZE_INGOT, SlimefunItem.getItem("CT_IMPORT_BUS"), SlimefunItems.ALUMINUM_BRONZE_INGOT, SlimefunItems.PLASTIC_SHEET, SlimefunItems.GOLD_10K, SlimefunItems.PLASTIC_SHEET})
		.register();
		
		new WirelessTerminal(category, wirelessTerminal16, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER, milkyQuartz, SlimefunItems.COBALT_INGOT, SlimefunItems.CHEST_TERMINAL, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 16;
			}
			
		}.register();
		
		new WirelessTerminal(category, wirelessTerminal64, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER, milkyQuartz, SlimefunItems.COBALT_INGOT, wirelessTerminal16, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 64;
			}
			
		}.register();
		
		new WirelessTerminal(category, wirelessTerminal128, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER_2, milkyQuartz, SlimefunItems.COBALT_INGOT, wirelessTerminal64, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 128;
			}
			
		}.register();
		
		new WirelessTerminal(category, wirelessTerminalTransdimensional, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER_4, milkyQuartz, SlimefunItems.COBALT_INGOT, wirelessTerminal128, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return -1;
			}
			
		}.register();
		
		new MilkyQuartz(this, milkyQuartz).register();
		CargoNet.extraChannels = true;
	}
}
