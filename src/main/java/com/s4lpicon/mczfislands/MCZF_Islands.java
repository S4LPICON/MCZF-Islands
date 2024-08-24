package com.s4lpicon.mczfislands;

import com.s4lpicon.mczfislands.commands.CommandsManager;
import com.s4lpicon.mczfislands.commands.CommandsTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MCZF_Islands extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Objects.requireNonNull(this.getCommand("island")).setExecutor(new CommandsManager());
        // Register the TabCompleter
        Objects.requireNonNull(getCommand("island")).setTabCompleter(new CommandsTabCompleter());
        getLogger().info("Holisss");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
