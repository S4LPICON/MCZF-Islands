package com.s4lpicon.mczfislands.commands;

import com.s4lpicon.mczfislands.invitations.IslandsInvitationsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandsTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();


        // Verify that the command is /islands
        if (command.getName().equalsIgnoreCase("island")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    // Complete first level subcommands
                    completions.add("create");
                    completions.add("tp");
                    completions.add("ban");
                    completions.add("unban");
                    completions.add("setspawn");
                    completions.add("invite");
                    completions.add("remove");
                    completions.add("join");
//                    completions.add("leave");
                } else if (args.length == 2 && args[0].equalsIgnoreCase("tp")
                        || args.length == 2 && args[0].equalsIgnoreCase("ban")
                        || args.length == 2 && args[0].equalsIgnoreCase("unban")
                        || args.length == 2 && args[0].equalsIgnoreCase("invite")
                        || args.length == 2 && args[0].equalsIgnoreCase("remove")
                        || args.length == 2 && args[0].equalsIgnoreCase("leave")) {
                    List<String> playerNames = new ArrayList<>();
                    for (Player playera : Bukkit.getOnlinePlayers()) {
                        if (!playera.getName().equals(player.getName())) {
                            playerNames.add(playera.getName());
                        }
                    }
                    return playerNames; // Retorna la lista de nombres de jugadores
                } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
                    List<String> invitations = IslandsInvitationsManager.getInvitationsOfPlayer(player.getUniqueId());
                    if (invitations.isEmpty()) {
                        invitations.add("You no have invitations right now!");
                        return invitations;
                    } else {
                        return invitations;
                    }
                }
            }

        }
        return completions;
    }
}