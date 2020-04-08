package bonn2.BlockReference;

import bonn2.BlockReference.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class User {
    private Location blockLoc, commandLoc;
    private Material toolMaterial;
    private boolean selectionMode;

    public User() {
        selectionMode = false;
    }

    public void setBlockLocation(Location blockLocation) { blockLoc = blockLocation; }
    public void setCommandLocation(Location commandLocation) { commandLoc = commandLocation; }
    public boolean hasBothLocations() {
        return commandLoc != null && blockLoc != null;
    }
    public void clearLocations() { commandLoc = null; blockLoc = null; }

    public void setToolMaterial(Material material) { toolMaterial = material; }
    public Material getToolMaterial() { return toolMaterial; }

    public void setSelectionMode(boolean mode) { selectionMode = mode; }
    public boolean inSelectionMode() { return selectionMode; }

    public boolean getRelative(Player player) {
        YamlConfiguration lang = Main.lang;
        if (commandLoc == null) {
            player.sendMessage(Objects.requireNonNull(lang.getString("HowSetCommandLocation")));
            return false;
        }
        if (blockLoc == null) {
            player.sendMessage(Objects.requireNonNull(lang.getString("HowSetBlockLocation")));
            return false;
        }
        int x, y, z;
        x = blockLoc.getBlockX() - commandLoc.getBlockX();
        y = blockLoc.getBlockY() - commandLoc.getBlockY();
        z = blockLoc.getBlockZ() - commandLoc.getBlockZ();
        TextComponent coords = new TextComponent("~" + x + " ~" + y + " ~" + z);
        coords.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "~" + x + " ~" + y + " ~" + z));
        coords.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(lang.getString("CopyToClipboard")).create()));
        ComponentBuilder message = new ComponentBuilder(Objects.requireNonNull(lang.getString("PrintRelativeCoords")))
                .append(coords);
        player.spigot().sendMessage(message.create());
        return true;
    }
}
