package com.s4lpicon.mczfislands.objets;

import com.google.gson.annotations.Expose;
import com.s4lpicon.mczfislands.utils.Math;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Island {

    @Expose
    private String islandName;
    @Expose
    private UUID ownerUuid;
    @Expose
    private final HashMap<UUID, Integer> members;
    @Expose
    private HashSet<UUID> bannedPlayers;
    @Expose
    private final ArrayList<Double> spawnCoords; // X Y Z Yaw Pitch
    @Expose
    private final String type;
    @Expose
    private final int size;


    public Island(String name, Player player, String type, int size){
        this.islandName = name;
        this.ownerUuid = player.getUniqueId();
        this.members = new HashMap<>();
        this.bannedPlayers = new HashSet<>();
        this.spawnCoords = new ArrayList<>();
        randomSpawnCoords();
        this.type = type;
        this.size = size;

    }

    private void randomSpawnCoords(){
        spawnCoords.add(Math.randomNumber(-255,255));
        spawnCoords.add(-1.0);
        spawnCoords.add(Math.randomNumber(-255,255));

        spawnCoords.add(0.0);
        spawnCoords.add(0.0);
    }

    public void banPlayer(UUID playerUuid){
        bannedPlayers.add(playerUuid);
    }

    public void unbanPlayer(UUID playerUuid){
        bannedPlayers.remove(playerUuid);
    }

    public boolean isBannedPlayer(UUID playerUuid){
        return bannedPlayers.contains(playerUuid);
    }

    public boolean isPlayerMember(UUID playerUuid){
        return members.containsKey(playerUuid);
    }

    public int getLevelPermission(UUID player){
        return this.members.get(player);
    }
    public void setSpawn(Location location){
        // Extrae las coordenadas X, Y, Z
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        // Extrae el yaw y el pitch
        double yaw = location.getYaw();
        double pitch = location.getPitch();

        this.setSpawnCoordsX(x);
        this.setSpawnCoordsY(y);
        this.setSpawnCoordsZ(z);

        this.setSpawnYaw(yaw);
        this.setSpawnPitch(pitch);
    }

    public void addMember(UUID player, int level){
        this.members.put(player,level);
    }

    public void removeMember(UUID player){
        this.members.remove(player);
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

    public HashMap<UUID,Integer> getMembers(){
        return this.members;
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

    public void setSpawnCoordsY(Double coordY){
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

    public double getSpawnYaw(){
        return this.spawnCoords.get(3);
    }
    public double getSpawnPitch(){
        return this.spawnCoords.get(4);
    }

    public void setSpawnYaw(double yaw){
        this.spawnCoords.set(3, yaw);
    }
    public void setSpawnPitch(double pitch){
        this.spawnCoords.set(4, pitch);
    }

    @Override
    public String toString() {
        return "Island{" + "\n" +
                "Nombre: " + islandName + "\n" +
                " Dueño UUID: " + ownerUuid + "\n" +
                " Miembros: " + members + "\n" +
                " Jugadores baneados: " + bannedPlayers + "\n" +
                " Coords Spawn: " + spawnCoords + "\n" +
                " Tipo: " + type + "\n" +
                " Tamaño: " + size + "\n" +
                '}';
    }
}
