package com.s4lpicon.mczfislands.objets;

import java.util.UUID;

public class IslandInvitation {

    private final UUID sender;
    private final UUID receiver;
    private final int permissionLevel;

    public IslandInvitation(UUID playerSender, UUID playerReceiver, int levelPermission){
        this.sender = playerSender;
        this.receiver = playerReceiver;
        this.permissionLevel = levelPermission;
    }


    public UUID getSender() {
        return sender;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }
}
