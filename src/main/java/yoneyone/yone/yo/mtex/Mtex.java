package yoneyone.yone.yo.mtex;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Mtex extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ("mtex".equals(label)) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    mtexGUIlistmenu(player);
                    return true;
                }
                try {
                    if (args[0].equals("list")) {
                        mtexGUIlistmenu(player);
                    } else {
                        short sh = Short.parseShort(args[0]);
                        mtexGUInum(player, sh);
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage("コマンドの使い方を間違えています/mtex [耐久値の数字（最小値）]（引数無しだとメニューが開きます）");
                }
            }
        }
        return true;
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        String title = e.getView().getTitle();
        if (title.equals(ChatColor.translateAlternateColorCodes('&',"&4&lmtexメニュー"))){
            e.setCancelled(true);//メニュー開いている間は、いじれない
            if (e.getClickedInventory().getName().equals(ChatColor.translateAlternateColorCodes('&',"&4&lmtexメニュー"))) {
                if (e.getSlot() <= 29) {
                    mtexGUIlist((Player) e.getView().getPlayer(), e.getSlot());
                }
            }
        }else if (title.equals(ChatColor.translateAlternateColorCodes('&',"&4&lmtexリスト"))){
            if (e.getClickedInventory().getName().equals(ChatColor.translateAlternateColorCodes('&',"&4&lmtexリスト"))) {
                if (e.getSlot() == 53) {
                    e.setCancelled(true);//メニューに戻るボタン
                    mtexGUIlistmenu((Player) e.getView().getPlayer());
                }
            }
        }
    }

    private void mtexGUInum(Player player, short sh) {
        String title = ChatColor.translateAlternateColorCodes('&',"&4&lmtex検索結果");
        Inventory inv = Bukkit.createInventory(null, 54,title);
        sh = (short) int1562(sh);

        short ysh = (short) (sh - 54);

        for (int i = 53; sh > ysh; ++ysh,--i) {
            ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
            hoe.setDurability(ysh);
            inv.setItem(i,hoe);
        }

        player.openInventory(inv);
    }
    private void mtexGUIlistmenu(Player player){
        String title = ChatColor.translateAlternateColorCodes('&',"&4&lmtexメニュー");
        Inventory inv = Bukkit.createInventory(null,36,title);

        for (int i = 0;i < 30;++i){
            ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
            ItemMeta itemMeta = hoe.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&4&l"+ (i + 1) +"番"));
            hoe.setItemMeta(itemMeta);
            inv.setItem(i,hoe);
        }

        player.openInventory(inv);
    }
    private void  mtexGUIlist(Player player,int innum){
        String title = ChatColor.translateAlternateColorCodes('&',"&4&lmtexリスト");
        Inventory inv = Bukkit.createInventory(null, 54,title);
        if (innum == 29){
            short sh = 25;

            short ysh = (short) (sh - 25);

            for (int i = 24; sh > ysh; ++ysh, --i) {
                ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
                hoe.setDurability(ysh);
                inv.setItem(i, hoe);
            }
        }else {
            short sh = (short) int1562(innum * 53);

            short ysh = (short) (sh - 53);

            for (int i = 52; sh > ysh; ++ysh, --i) {
                ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
                hoe.setDurability(ysh);
                inv.setItem(i, hoe);
            }
        }
        ItemStack apple = new ItemStack(Material.APPLE);
        ItemMeta itemMeta = apple.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&4&lメニューに戻る"));
        apple.setItemMeta(itemMeta);
        inv.setItem(53,apple);

        player.openInventory(inv);
    }
    private int int1562(int i){
        return 1562 - i;
    }
}
