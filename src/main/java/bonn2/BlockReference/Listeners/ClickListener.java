package bonn2.BlockReference.Listeners;

import bonn2.BlockReference.Main;
import bonn2.BlockReference.math.User;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashMap;
import java.util.UUID;

public class ClickListener implements Listener {

    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) { return; }
        Main plugin = Main.plugin;
        HashMap<UUID, User> users = Main.users;
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!users.containsKey(uuid) || !users.get(uuid).inSelectionMode() || !player.getInventory().getItemInMainHand().getType().equals(users.get(uuid).getToolMaterial())) { return; }
        Block block = event.getClickedBlock();
        switch (event.getAction()) {
            case LEFT_CLICK_BLOCK: {
                users.get(uuid).setCommandLocation(block.getLocation());
                player.sendMessage("Set commandblock location!");
                break;
            }
            case RIGHT_CLICK_BLOCK: {
                users.get(uuid).setBlockLocation(block.getLocation());
                player.sendMessage("Set referenced block location!");
                break;
            }
        }
        if (users.get(uuid).hasBothLocations()) {
            users.get(uuid).getRelative(player);
        }
        event.setCancelled(true);
    }
}
