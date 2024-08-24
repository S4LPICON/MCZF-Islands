package com.s4lpicon.mczfislands.commands;

import com.s4lpicon.mczfislands.islandsmanager.IslandsManager;
import com.s4lpicon.mczfislands.utils.TPSpawn;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandsManager implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("island")) {
            if (sender instanceof Player player){

                switch (args[0]){
                    case "create":
                        IslandsManager.createNewIsland(player);
                        break;
                    case "tp":
                        TPSpawn.sendPlayerToWorld(player, "main");
                        break;
                }


                return true;
            }
        }

        return false;
    }
}
