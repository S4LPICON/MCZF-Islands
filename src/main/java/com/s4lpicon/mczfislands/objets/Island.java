package com.s4lpicon.mczfislands.objets;

import com.s4lpicon.mczfislands.utils.Math;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class Island {

    private String islandName;
    private UUID ownerUuid;
    private HashSet<UUID> residentsPlayers;
    private HashSet<UUID> trustedPlayers;
    private HashSet<UUID> bannedPlayers;
    private final ArrayList<Double> spawnCoords; // X Y Z Yaw Pitch
    private final String type;
    private final int size;


    public Island(String name, Player player, String type, int size){
        this.islandName = name;
        this.ownerUuid = player.getUniqueId();
        this.residentsPlayers = new HashSet<>();
        this.trustedPlayers = new HashSet<>();
        this.bannedPlayers = new HashSet<>();
        this.spawnCoords = new ArrayList<>();
        randomSpawnCoords();
        this.type = type;
        this.size = size;

    }

    private void randomSpawnCoords(){
        spawnCoords.add(Math.randomNumber(-255,255));
        spawnCoords.add(0.0);
        spawnCoords.add(Math.randomNumber(-255,255));
        spawnCoords.add(0.0);
        spawnCoords.add(0.0);
    }


    // Getters & setters

    public String getIslandName() {
        return islandName;
    }

    public void setIslandName(String islandName) {
        this.islandName = islandName;
    }

    public UUID getOwnerUuid() {
        return ownerUuid;
    }

    public void setOwnerUuid(UUID ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public HashSet<UUID> getResidentsPlayers() {
        return residentsPlayers;
    }

    public void setResidentsPlayers(HashSet<UUID> residentsPlayers) {
        this.residentsPlayers = residentsPlayers;
    }

    public HashSet<UUID> getTrustedPlayers() {
        return trustedPlayers;
    }

    public void setTrustedPlayers(HashSet<UUID> trustedPlayers) {
        this.trustedPlayers = trustedPlayers;
    }

    public HashSet<UUID> getBannedPlayers() {
        return bannedPlayers;
    }

    public void setBannedPlayers(HashSet<UUID> bannedPlayers) {
        this.bannedPlayers = bannedPlayers;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }


    public void setSpawnCoordsX(double coordX){
        this.spawnCoords.set(0,coordX);
    }

    public void setSpawnCoordsY(double coordY){
        this.spawnCoords.set(1,coordY);
    }
    public void setSpawnCoordsZ(double coordZ){
        this.spawnCoords.set(2,coordZ);
    }

    public double getSpawnCoordX(){
        return this.spawnCoords.get(0);
    }
    public double getSpawnCoordY(){
        return this.spawnCoords.get(1);
    }
    public double getSpawnCoordZ(){
        return this.spawnCoords.get(2);
    }
    //faltaria agregar getters y setters de el yaw y pitch

}
