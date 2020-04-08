package bonn2.BlockReference.Listeners;

import bonn2.BlockReference.Main;
import bonn2.BlockReference.math.User;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CommandListener implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Main plugin = Main.plugin;
        HashMap<UUID, User> users = Main.users;
        Player player;
        UUID uuid;
        if (sender instanceof Player) {
            player = (Player) sender;
            uuid = player.getUniqueId();
        } else {
            sender.sendMessage("This command can only be run from a player!");
            return true;
        }
        if (args.length != 1) {
            return false;
        }
        if (!users.containsKey(uuid)) { users.put(uuid, new User()); }
        switch (args[0].toLowerCase()) {
            case "toggle": {
                if (users.get(uuid).inSelectionMode()) {
                    users.get(uuid).setSelectionMode(false);
                    users.get(uuid).clearLocations();
                    player.sendMessage("Disabled Selection Mode!");
                } else {
                    users.get(uuid).setSelectionMode(true);
                    users.get(uuid).setToolMaterial(player.getInventory().getItemInMainHand().getType());
                    users.get(uuid).clearLocations();
                    player.sendMessage("Enabled Selection Mode With Tool: " + users.get(uuid).getToolMaterial().name());
                }
                return true;
            }
            case "look": {
                Block lookingAt = player.getTargetBlockExact(plugin.getConfig().getInt("MaxCheckDistance"), FluidCollisionMode.NEVER);
                if (lookingAt == null) { player.sendMessage("That block is too far away!"); return true; }
                users.get(uuid).setCommandLocation(new Location(
                        player.getWorld(),
                        player.getLocation().getBlockX(),
                        player.getLocation().getBlockY() - 1,
                        player.getLocation().getBlockZ()));
                users.get(uuid).setBlockLocation(new Location(
                        player.getWorld(),
                        lookingAt.getX(),
                        lookingAt.getY(),
                        lookingAt.getZ()));
                users.get(uuid).getRelative(player);
                users.get(uuid).clearLocations();
                return true;
            }
            default: {
                return false;
            }
        }
    }
}
