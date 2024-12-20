package com.s4lpicon.mczfislands;

import com.s4lpicon.mczfislands.commands.CommandsManager;
import com.s4lpicon.mczfislands.commands.CommandsTabCompleter;
import com.s4lpicon.mczfislands.genericlisteners.GenericListeners;
import com.s4lpicon.mczfislands.permissions.listeners.DamageEntityListener;
import com.s4lpicon.mczfislands.permissions.listeners.IslandsEventListener;
import com.s4lpicon.mczfislands.utils.IslandsUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MCZF_Islands extends JavaPlugin {

    @Override
    public void onEnable() {
        // Registro de listeners
        getServer().getPluginManager().registerEvents(new IslandsEventListener(), this);
        getServer().getPluginManager().registerEvents(new DamageEntityListener(), this);
        getServer().getPluginManager().registerEvents(new GenericListeners(), this);
        // Plugin startup logic
        Objects.requireNonNull(this.getCommand("island")).setExecutor(new CommandsManager());
        Objects.requireNonNull(this.getCommand("spawn")).setExecutor(new CommandsManager());
        Objects.requireNonNull(this.getCommand("devinfo")).setExecutor(new CommandsManager());
        // Register the TabCompleter
        Objects.requireNonNull(getCommand("island")).setTabCompleter(new CommandsTabCompleter());
        getLogger().info("Holisss");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        IslandsUtils.saveAllIslands();
    }
}
