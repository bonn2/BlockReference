package bonn2.BlockReference.Listeners;

import bonn2.BlockReference.Main;
import bonn2.BlockReference.User;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class CommandListener implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Main plugin = Main.plugin;
        HashMap<UUID, User> users = Main.users;
        YamlConfiguration lang = Main.lang;
        Player player;
        UUID uuid;
        if (sender instanceof Player) {
            player = (Player) sender;
            uuid = player.getUniqueId();
        } else {
            sender.sendMessage(Objects.requireNonNull(lang.getString("OnlyPlayer")));
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
                    player.sendMessage(Objects.requireNonNull(lang.getString("DisabledSelection")));
                } else {
                    users.get(uuid).setSelectionMode(true);
                    users.get(uuid).setToolMaterial(player.getInventory().getItemInMainHand().getType());
                    users.get(uuid).clearLocations();
                    player.sendMessage(Objects.requireNonNull(lang.getString("EnabledSelection")) + users.get(uuid).getToolMaterial().name());
                }
                return true;
            }
            case "look": {
                Block lookingAt = player.getTargetBlockExact(plugin.getConfig().getInt("MaxCheckDistance"), FluidCollisionMode.NEVER);
                if (lookingAt == null) { player.sendMessage(Objects.requireNonNull(lang.getString("TooFar"))); return true; }
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
            case "clear": {
                users.get(uuid).clearLocations();
                player.sendMessage(Objects.requireNonNull(lang.getString("ClearedClipboard")));
                return true;
            }
            case "reload": {
                if (sender.hasPermission("blockreference.reload")) {
                    Main.reload();
                    player.sendMessage(Objects.requireNonNull(lang.getString("FinishedReloading")));
                    return true;
                }
                return false;
            }
            default: {
                return false;
            }
        }
    }
}
