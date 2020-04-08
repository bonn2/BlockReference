package bonn2.BlockReference;

import bonn2.BlockReference.Listeners.ClickListener;
import bonn2.BlockReference.Listeners.CommandListener;
import bonn2.BlockReference.Listeners.TabComplete;
import bonn2.BlockReference.math.User;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {
    public static Main plugin;
    public static HashMap<UUID, User> users;

    @Override
    public void onEnable() {
        plugin = this;
        users = new HashMap<>();
        setupConfig();

        this.getCommand("blockreference").setExecutor(new CommandListener());
        this.getCommand("blockreference").setTabCompleter(new TabComplete());
        getServer().getPluginManager().registerEvents(new ClickListener(), this);
    }

    public void setupConfig() {
        File configyml = new File(getDataFolder() + File.separator + "config.yml");
        if (!configyml.exists()) { // Checks if config file exists
            getLogger().warning("No config.yml found, making a new one!");
            saveResource("config.yml", false);
        }
    }
}
