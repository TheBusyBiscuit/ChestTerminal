package io.github.thebusybiscuit.chestterminal;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunBlockHandler;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.UnregisterReason;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.CargoNet;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullItem;

public class AccessTerminal extends SimpleSlimefunItem<BlockTicker> {
    
    private final int[] terminalSlots = { 0, 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 18, 19, 20, 21, 22, 23, 24, 27, 28, 29, 30, 31, 32, 33, 36, 37, 38, 39, 40, 41, 42 };

    public AccessTerminal(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        new BlockMenuPreset(getID(), "&3CT Access Terminal") {

            @Override
            public void init() {
                constructMenu(this);
            }

            @Override
            public void newInstance(BlockMenu menu, Block b) {
                menu.replaceExistingItem(46, new CustomItem(SkullItem.fromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjI1OTliZDk4NjY1OWI4Y2UyYzQ5ODg1MjVjOTRlMTlkZGQzOWZhZDA4YTM4Mjg0YTE5N2YxYjcwNjc1YWNjIn19fQ=="), "&7\u21E6 Previous Page", "", "&c(This may take up to a Second to update)"));
                menu.addMenuClickHandler(46, (p, slot, item, action) -> {
                    int page = Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "page")) - 1;
                    if (page > 0) {
                        BlockStorage.addBlockInfo(b, "page", String.valueOf(page));
                        newInstance(menu, b);
                    }
                    return false;
                });

                menu.replaceExistingItem(50, new CustomItem(SkullItem.fromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJmOTEwYzQ3ZGEwNDJlNGFhMjhhZjZjYzgxY2Y0OGFjNmNhZjM3ZGFiMzVmODhkYjk5M2FjY2I5ZGZlNTE2In19fQ=="), "&7Next Page \u21E8", "", "&c(This may take up to a Second to update)"));
                menu.addMenuClickHandler(50, (p, slot, item, action) -> {
                    int page = Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "page")) + 1;
                    BlockStorage.addBlockInfo(b, "page", String.valueOf(page));
                    newInstance(menu, b);
                    return false;
                });
            }

            @Override
            public boolean canOpen(Block b, Player p) {
                return SlimefunPlugin.getProtectionManager().hasPermission(p, b.getLocation(), ProtectableAction.ACCESS_INVENTORIES);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[0];
            }
        };

        registerBlockHandler(getID(), new SlimefunBlockHandler() {

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
    public BlockTicker getItemHandler() {
        return new BlockTicker() {

            private final ItemStack item = new CustomItem(Material.BARRIER, "&4No Cargo Net connected!");
            private final MenuClickHandler click = (p, slot, stack, action) -> false;

            @Override
            public void tick(Block b, SlimefunItem sf, Config data) {
                if (CargoNet.getNetworkFromLocation(b.getLocation()) == null) {
                    BlockMenu menu = BlockStorage.getInventory(b);

                    for (int slot : terminalSlots) {
                        menu.replaceExistingItem(slot, item);
                        menu.addMenuClickHandler(slot, click);
                    }
                }
            }

            @Override
            public boolean isSynchronized() {
                return true;
            }
        };
    }

}
