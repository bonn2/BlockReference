package bonn2.BlockReference.math;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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
        if (commandLoc != null && blockLoc != null) { return true; }
        return false;
    }
    public void clearLocations() { commandLoc = null; blockLoc = null; }

    public void setToolMaterial(Material material) { toolMaterial = material; }
    public Material getToolMaterial() { return toolMaterial; }

    public void setSelectionMode(boolean mode) { selectionMode = mode; }
    public boolean inSelectionMode() { return selectionMode; }

    public boolean getRelative(Player player) {
        if (commandLoc == null) {
            player.sendMessage("To set command block location use /blockreference command");
            return false;
        }
        if (blockLoc == null) {
            player.sendMessage("To set reference block location use /blockreference block");
            return false;
        }
        int x, y, z;
        x = blockLoc.getBlockX() - commandLoc.getBlockX();
        y = blockLoc.getBlockY() - commandLoc.getBlockY();
        z = blockLoc.getBlockZ() - commandLoc.getBlockZ();
        player.sendMessage("The relative coordinates are: ~" + x + " ~" + y + " ~" + z);
        return true;
    }
}
