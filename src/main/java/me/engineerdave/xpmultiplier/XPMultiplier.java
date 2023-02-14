package me.engineerdave.xpmultiplier;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class XPMultiplier extends JavaPlugin implements Listener {
    private int bonusMultiplier;
    private String permissionNode;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        bonusMultiplier = config.getInt("bonus-multiplier");
        permissionNode = config.getString("permission-node");

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(permissionNode)) {
            int newExp = event.getAmount() * bonusMultiplier;
            event.setAmount(newExp);
        }
    }

    @Override
    public void onDisable() {
    }
}
