package bonn2.BlockReference;

import bonn2.BlockReference.Listeners.ClickListener;
import bonn2.BlockReference.Listeners.CommandListener;
import bonn2.BlockReference.Listeners.TabComplete;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {
    public static Main plugin;
    public static YamlConfiguration lang;
    public static HashMap<UUID, User> users;

    @Override
    public void onEnable() {
        plugin = this;
        users = new HashMap<>();
        setupConfig();
        setupLang();

        this.getCommand("blockreference").setExecutor(new CommandListener());
        this.getCommand("blockreference").setTabCompleter(new TabComplete());
        getServer().getPluginManager().registerEvents(new ClickListener(), this);
    }

    public static void setupConfig() {
        File configyml = new File(plugin.getDataFolder() + File.separator + "config.yml");
        if (!configyml.exists()) { // Checks if config file exists
            plugin.getLogger().warning("No config.yml found, making a new one!");
            plugin.saveResource("config.yml", false);
        }
    }

    public static void setupLang() {
        String selectedLang = plugin.getConfig().getString("Language");
        File langyml = new File(plugin.getDataFolder() + File.separator + selectedLang + ".yml");
        if (!langyml.exists()) {
            plugin.getLogger().warning("No " + selectedLang + ".yml found, attempting to make a new one!");
            plugin.saveResource(selectedLang + ".yml", false);
        }
        lang = YamlConfiguration.loadConfiguration(langyml);
        if (lang == null) {
            plugin.getLogger().warning("Failed to load language config file!");
        }
    }

    public static void reload() {
        setupConfig();
        plugin.reloadConfig();
        setupLang();
    }
}
