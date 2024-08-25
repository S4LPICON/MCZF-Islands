package com.s4lpicon.mczfislands.commands;

import com.s4lpicon.mczfislands.invitations.IslandsInvitationsManager;
import com.s4lpicon.mczfislands.islandsmanager.IslandsManager;
import com.s4lpicon.mczfislands.islandsmanager.IslandsPlayersManager;
import com.s4lpicon.mczfislands.utils.DevInfo;
import com.s4lpicon.mczfislands.utils.TPSpawn;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

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
                        if (args.length == 2){
                            IslandsPlayersManager.sendPlayerToIsland(player, args[1]);
                        }else {
                            IslandsPlayersManager.sendPlayerToIsland(player, player.getName());
                        }
                        break;
                    case "ban":
                        if (args.length == 2){
                            IslandsPlayersManager.banPlayerFromIsland(player, args[1], player.getUniqueId());
                        }else {
                            player.sendMessage("Escribe el nombre del jugador a banear");
                            return false;
                        }
                        break;
                    case "unban":
                        if (args.length == 2){
                            IslandsPlayersManager.unbanPlayerFromIsland(player, args[1],player.getUniqueId());
                        }else {
                            player.sendMessage("Escribe el nombre del jugador a desbanear");
                            return false;
                        }
                        break;
                    case "setspawn":
                        IslandsManager.islandSetSpawn(player);
                        break;
                    case "invite":
                        if (args.length >= 2) {
                            if (args.length >= 3) {
                                //IslandsManager.invitePlayerToIsland(args[1], player.getName(), Integer.parseInt(args[2]));
                                IslandsInvitationsManager.sendInvitation(player, args[1],Integer.parseInt(args[2]));
                            }else {
                                player.sendMessage("Escribe el nivel de permisos para el jugador");
                            }
                        } else {
                                player.sendMessage("Escribe el nombre del jugador que deseas invitar a tu isla!");
                        }
                        return true;
                    case "join":
                        if (args.length == 2){
                            IslandsInvitationsManager.acceptInvitation(player, UUID.fromString(args[1]));
                            return true;
                        }else {
                            player.sendMessage("Escribe el id de la invitación");
                        }
                        break;
                    case "remove":
                        if (args.length >= 2) {
                            IslandsPlayersManager.removePlayerFromIsland(player, args[1]);
                        } else {
                            player.sendMessage("Escribe el nombre del jugador que deseas remover de tu isla!");
                        }
                        break;
                }


                return true;
            }
        } else if (command.getName().equalsIgnoreCase("spawn")) {
            if (sender instanceof Player player) {
                TPSpawn.sendPlayerToWorld(player, "main");
            }
            return true;
        }else if (command.getName().equalsIgnoreCase("devinfo")) {
            if (sender instanceof Player player) {
                DevInfo.devinfo(player);
            }
            return true;
        }
        return false;
    }
}
