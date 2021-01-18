package io.github.thebusybiscuit.chestterminal.items;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.events.BlockPlacerPlaceEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoNet;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuClickHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;

public class AccessTerminal extends SimpleSlimefunItem<BlockTicker> {

    private final int[] terminalSlots = { 0, 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 18, 19, 20, 21, 22, 23, 24, 27, 28, 29, 30, 31, 32, 33, 36, 37, 38, 39, 40, 41, 42 };

    public AccessTerminal(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        new BlockMenuPreset(getId(), "&3CT Access Terminal") {

            @Override
            public void init() {
                constructMenu(this);
            }

            @Override
            public void newInstance(BlockMenu menu, Block b) {
                menu.replaceExistingItem(46, new CustomItem(SlimefunUtils.getCustomHead("f2599bd986659b8ce2c4988525c94e19ddd39fad08a38284a197f1b70675acc"), "&7\u21E6 Previous Page", "", "&c(This may take up to a Second to update)"));
                menu.addMenuClickHandler(46, (p, slot, item, action) -> {
                    int page = getPage(b) - 1;
                    if (page > 0) {
                        BlockStorage.addBlockInfo(b, "page", String.valueOf(page));
                        newInstance(menu, b);
                    }
                    return false;
                });

                menu.replaceExistingItem(50, new CustomItem(SlimefunUtils.getCustomHead("c2f910c47da042e4aa28af6cc81cf48ac6caf37dab35f88db993accb9dfe516"), "&7Next Page \u21E8", "", "&c(This may take up to a Second to update)"));
                menu.addMenuClickHandler(50, (p, slot, item, action) -> {
                    int page = getPage(b) + 1;
                    BlockStorage.addBlockInfo(b, "page", String.valueOf(page));
                    newInstance(menu, b);
                    return false;
                });
            }

            private int getPage(Block b) {
                String page = BlockStorage.getLocationInfo(b.getLocation(), "page");
                return page == null ? 1 : Integer.parseInt(page);
            }

            @Override
            public boolean canOpen(Block b, Player p) {
                return SlimefunPlugin.getProtectionManager().hasPermission(p, b.getLocation(), ProtectableAction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[0];
            }
        };

        registerBlockHandler(getId(), (p, b, tool, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), 17, 44);
            }

            return true;
        });

        addItemHandler(new BlockPlaceHandler(true) {

            @Override
            public void onPlayerPlace(BlockPlaceEvent e) {
                BlockStorage.addBlockInfo(e.getBlock(), "page", "1");
            }

            @Override
            public void onBlockPlacerPlace(BlockPlacerPlaceEvent e) {
                BlockStorage.addBlockInfo(e.getBlock(), "page", "1");
            }

        });
    }

    protected void constructMenu(BlockMenuPreset preset) {
        MenuClickHandler click = (p, slot, item, action) -> false;

        preset.addItem(45, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "), click);
        preset.addItem(46, new CustomItem(Material.RED_STAINED_GLASS_PANE, "This will update shortly"));
        preset.addItem(47, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "), click);
        preset.addItem(48, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "), click);
        preset.addItem(49, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "), click);
        preset.addItem(50, new CustomItem(Material.RED_STAINED_GLASS_PANE, "This will update shortly"));
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
