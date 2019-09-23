package io.github.thebusybiscuit.chestterminal;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.GEO.OreGenResource;
import me.mrCookieSlime.Slimefun.GEO.OreGenSystem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
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
		new Metrics(this);

		// Setting up the Auto-Updater
		Updater updater = null;

		if (getDescription().getVersion().startsWith("DEV - ")) {
			// If we are using a development build, we want to switch to our custom 
			updater = new GitHubBuildsUpdater(this, getFile(), "TheBusyBiscuit/ChestTerminal/master");
		}

		if (updater != null && cfg.getBoolean("options.auto-update")) updater.start();
		
		final Category category = new Category(new CustomItem(SlimefunItems.CHEST_TERMINAL, "&5Chest Terminal", "", "&a> Click to open"));
		
		final ItemStack quartz = new CustomItem(new ItemStack(Material.QUARTZ), "&rMilky Quartz");
		
		final ItemStack wireless_terminal16 = new CustomItem(new ItemStack(Material.ITEM_FRAME), "&3CT Wireless Access Terminal &b(16)", "&8\u21E8 &7Linked to: &cNowhere", "&8\u21E8 &7Range: &e16 Blocks", "&c&o&8\u21E8 &e\u26A1 &70 / 10 J", "", "&7If this Block is linked to an Access Terminal", "&7it will be able to remotely access that Terminal", "", "&7&eRight Click on an Access Terminal &7to link", "&7&eRight Click&7 to open the linked Terminal");
		final ItemStack wireless_terminal64 = new CustomItem(new ItemStack(Material.ITEM_FRAME), "&3CT Wireless Access Terminal &b(64)", "&8\u21E8 &7Linked to: &cNowhere", "&8\u21E8 &7Range: &e64 Blocks", "&c&o&8\u21E8 &e\u26A1 &70 / 25 J", "", "&7If this Block is linked to an Access Terminal", "&7it will be able to remotely access that Terminal", "", "&7&eRight Click on an Access Terminal &7to link", "&7&eRight Click&7 to open the linked Terminal");
		final ItemStack wireless_terminal128 = new CustomItem(new ItemStack(Material.ITEM_FRAME), "&3CT Wireless Access Terminal &b(128)", "&8\u21E8 &7Linked to: &cNowhere", "&8\u21E8 &7Range: &e128 Blocks", "&c&o&8\u21E8 &e\u26A1 &70 / 50 J", "", "&7If this Block is linked to an Access Terminal", "&7it will be able to remotely access that Terminal", "", "&7&eRight Click on an Access Terminal &7to link", "&7&eRight Click&7 to open the linked Terminal");
		final ItemStack wireless_terminalT = new CustomItem(new ItemStack(Material.ITEM_FRAME), "&3CT Wireless Access Terminal &b(Transdimensional)", "&8\u21E8 &7Linked to: &cNowhere", "&8\u21E8 &7Range: &eUnlimited", "&c&o&8\u21E8 &e\u26A1 &70 / 50 J", "", "&7If this Block is linked to an Access Terminal", "&7it will be able to remotely access that Terminal", "", "&7&eRight Click on an Access Terminal &7to link", "&7&eRight Click&7 to open the linked Terminal");
		final ItemStack drill = new CustomItem(new ItemStack(Material.IRON_BLOCK), "&3Quartz Drill", "&7Mines up Milky Quartz", "", "&c&l! &cMake sure to Geo-Scan the Chunk first");
		
		new QuartzDrill(category, drill, "QUARTZ_DRILL", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {null, SlimefunItems.POWER_CRYSTAL, null, SlimefunItems.PLASTIC_SHEET, SlimefunItems.OIL_PUMP, SlimefunItems.PLASTIC_SHEET, SlimefunItems.COBALT_INGOT, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.COBALT_INGOT}) {
			
			@Override
			public int getSpeed() {
				return 1;
			}
			
			@Override
			public int getEnergyConsumption() {
				return 60;
			}

			@Override
			public ItemStack getOutput() {
				return quartz;
			}
			
		}.registerChargeableBlock(512);

		new SlimefunItem(category, quartz, "MILKY_QUARTZ", new RecipeType(drill), new ItemStack[0]).register();
		
		new SlimefunItem(category, new CustomItem(SlimefunItems.CHEST_TERMINAL, "&3CT Illuminated Panel", "&7Crafting Component"), "CT_PANEL", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {quartz, SlimefunItems.BLISTERING_INGOT_3, quartz, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REDSTONE_ALLOY, quartz, SlimefunItems.BLISTERING_INGOT_3, quartz})
		.register();
		
		new AccessTerminal(category, SlimefunItems.CHEST_TERMINAL, "CHEST_TERMINAL", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {quartz, SlimefunItems.GPS_TRANSMITTER_3, quartz, SlimefunItems.POWER_CRYSTAL, SlimefunItem.getItem("CT_PANEL"), SlimefunItems.POWER_CRYSTAL, SlimefunItems.PLASTIC_SHEET, SlimefunItems.ENERGY_REGULATOR, SlimefunItems.PLASTIC_SHEET})
		.register();
		
		new ImportBus(category, SlimefunItems.CT_IMPORT_BUS, "CT_IMPORT_BUS", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {SlimefunItems.REDSTONE_ALLOY, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.CARGO_INPUT, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.PLASTIC_SHEET, SlimefunItems.CARGO_MOTOR, SlimefunItems.PLASTIC_SHEET})
		.register();
		
		new ExportBus(category, SlimefunItems.CT_EXPORT_BUS, "CT_EXPORT_BUS", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {null, SlimefunItems.DAMASCUS_STEEL_INGOT, null, SlimefunItems.ALUMINUM_BRONZE_INGOT, SlimefunItem.getItem("CT_IMPORT_BUS"), SlimefunItems.ALUMINUM_BRONZE_INGOT, SlimefunItems.PLASTIC_SHEET, SlimefunItems.GOLD_10K, SlimefunItems.PLASTIC_SHEET})
		.register();
		
		new WirelessTerminal(category, wireless_terminal16, "CT_WIRELESS_ACCESS_TERMINAL_16", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {quartz, SlimefunItems.GPS_TRANSMITTER, quartz, SlimefunItems.COBALT_INGOT, SlimefunItems.CHEST_TERMINAL, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 16;
			}
			
		}.register();
		
		new WirelessTerminal(category, wireless_terminal64, "CT_WIRELESS_ACCESS_TERMINAL_64", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {quartz, SlimefunItems.GPS_TRANSMITTER, quartz, SlimefunItems.COBALT_INGOT, wireless_terminal16, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 64;
			}
			
		}.register();
		
		new WirelessTerminal(category, wireless_terminal128, "CT_WIRELESS_ACCESS_TERMINAL_128", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {quartz, SlimefunItems.GPS_TRANSMITTER_2, quartz, SlimefunItems.COBALT_INGOT, wireless_terminal64, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 128;
			}
			
		}.register();
		
		new WirelessTerminal(category, wireless_terminalT, "CT_WIRELESS_ACCESS_TERMINAL_TRANSDIMENSIONAL", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {quartz, SlimefunItems.GPS_TRANSMITTER_4, quartz, SlimefunItems.COBALT_INGOT, wireless_terminal128, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return -1;
			}
			
		}.register();
		
		OreGenSystem.registerResource(new OreGenResource() {
			
			@Override
			public String getName() {
				return "Milky Quartz";
			}
			
			@Override
			public String getMeasurementUnit() {
				return "Unit(s)";
			}
			
			@Override
			public ItemStack getIcon() {
				return new ItemStack(Material.QUARTZ);
			}
			
			@Override
			public int getDefaultSupply(Biome biome) {
				return new Random().nextInt(6) + 1;
			}

			@Override
			public boolean isLiquid() {
				return false;
			}
		});
		
		CargoNet.extraChannels = true;
	}
}
