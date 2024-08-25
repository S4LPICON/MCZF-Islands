package com.s4lpicon.mczfislands.invitations;

import com.s4lpicon.mczfislands.islandsmanager.IslandsPlayersManager;
import com.s4lpicon.mczfislands.objets.IslandInvitation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class IslandsInvitationsManager {

    public static HashMap<UUID, IslandInvitation> invitations = new HashMap<>();

    public static void sendInvitation(Player sender, String receiver, int permissionsLevel){
        Player receive = Bukkit.getPlayer(receiver);
        if (permissionsLevel < 1 || permissionsLevel > 2){
            sender.sendMessage("Ese nivel de permisos no existe!");
            return;
        }
        if (receiver.equalsIgnoreCase(sender.getName())){
            sender.sendMessage("No puedes invitarte a tu propia isla!");
            return;
        }
        if (receive == null){
            sender.sendMessage("El jugador no existe o se encuentra desconectado!");
            return;
        }
        if (invitations.containsKey(receive.getUniqueId())){
            sender.sendMessage("Ya le enviaste una invitación a este jugador!");
            return;
        }
        IslandInvitation invitation = new IslandInvitation(sender.getUniqueId(), receive.getUniqueId(), permissionsLevel);
        invitations.put(sender.getUniqueId(), invitation);
        sender.sendMessage("Invitación enviada correctamente a "+ receive.getName());
        receive.sendMessage("¡"+sender.getName()+" te ha enviado una invitación a su isla!" + " [Aceptar]");
    }

    public static void acceptInvitation(Player receiver, UUID sender) {
        if (!invitations.containsKey(sender)) {
            receiver.sendMessage("Esa invitación no existe!");
            return;
        }
        IslandInvitation invitation = invitations.get(sender);
        Player psender = Bukkit.getPlayer(sender);
        if (psender == null){
            receiver.sendMessage("El jugador que te envió la invitación se encuentra desconectado!");
            return;
        }
        IslandsPlayersManager.addPlayerToIsland(psender, receiver, invitation.getPermissionLevel());
        receiver.sendMessage("Has aceptado la invitación correctamente!");
    }

    public static List<String> getInvitationsOfPlayer(UUID player){
        List<String> list = new ArrayList<>();
        for (IslandInvitation invitation : invitations.values()){
            if (invitation.getReceiver().equals(player)){
                list.add(String.valueOf(invitation.getSender()));
            }
        }
        return list;
    }
}
