package com.s4lpicon.mczfislands.islandsmanager;

import com.s4lpicon.mczfislands.objets.Island;
import com.s4lpicon.mczfislands.utils.TPSpawn;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

import static com.s4lpicon.mczfislands.islandsmanager.IslandsManager.activateIsland;
import static com.s4lpicon.mczfislands.islandsmanager.IslandsManager.activeIslands;

@SuppressWarnings("UnstableApiUsage")
public class IslandsPlayersManager {

    public static void sendPlayerToIsland(Player player, String islandOwnerName) {
        assert player != null;
        // Obtener el nombre del mundo desde el UUID del propietario
        Player ownerIsland = Bukkit.getPlayer(islandOwnerName);
        //Verificar si el dueño de la isla se encuentra conectado
        if (ownerIsland == null){
            player.sendMessage(Component.text("El dueño de esta isla se encuentra desconectado!"));
            return;
        }
        String worldName = "PlayerIslands/"+ownerIsland.getUniqueId();


        // Obtener el mundo por su nombre
        World world = Bukkit.getWorld(worldName);
        Island island = activeIslands.get(ownerIsland.getUniqueId());
        if (island == null){
            activateIsland(ownerIsland.getUniqueId());
            Bukkit.getLogger().info("Se tuvo que activar la isla antes de hacer tp al jugador");
        }
        island = activeIslands.get(ownerIsland.getUniqueId());
        if (island == null){
            player.sendMessage("no se pudo hacer tp, la isla es nula");
            return;
        }
        if (island.isBannedPlayer(player.getUniqueId())
                && !player.isOp()
                && !player.hasPermission("mczf.admin")){
            player.sendMessage("Estas baneado de esta isla!");
            return;
        }
        if (world != null) {
            if (!island.isPlayerMember(player.getUniqueId()) && !island.getOwnerUuid().equals(player.getUniqueId())){
                player.setGameMode(GameMode.ADVENTURE);
            }
            // Crear una nueva ubicación en el mundo
            double x = island.getSpawnCoordX();
            double z = island.getSpawnCoordZ();
            double y = island.getSpawnCoordY();
            if (y == -1.0){ //la primera vez que entra al mundo
                y = world.getHighestBlockYAt((int) x,(int) y)+1;
            }
            float yaw = (float) island.getSpawnYaw();   // Convertir yaw a float
            float pitch = (float) island.getSpawnPitch(); // Convertir pitch a float
            Location location = new Location(world, x, y, z, yaw, pitch);

            // Teletransportar al jugador
            player.teleport(location);

            player.sendMessage(
                    Component.text("Enviandote a la isla")
                            .color(NamedTextColor.GREEN)
                            .decorate(TextDecoration.UNDERLINED));
        } else {
            player.sendMessage(
                    Component.text("ERROR: La isla no existe!")
                            .color(NamedTextColor.RED)
                            .decorate(TextDecoration.BOLD));
        }
    }

    public static void banPlayerFromIsland(Player whoBan, String playerToBan, UUID islandUuid){
        Player playerBanned = Bukkit.getPlayer(playerToBan);
        if (playerBanned == null){
            whoBan.sendMessage("El jugador a banear no se encuentra conectado");
            //si el jugador tiene permisos se los tengo que quitar aca
            return;
        }
        if (playerBanned.getUniqueId().equals(whoBan.getUniqueId())){
            whoBan.sendMessage("No puedes banearte tu mismo!");
            return;
        }
        if (playerBanned.isOp()|| playerBanned.hasPermission("mczf.admin")){
            whoBan.sendMessage("No puedes banear a un administrador!");
            return;
        }
        if (!activeIslands.containsKey(islandUuid)){
            activateIsland(islandUuid);
        }
        Island island = activeIslands.get(islandUuid);
        if (island == null){
            whoBan.sendMessage("Hubo un problema con la isla (es nula)");
            return;
        }
        //si la isla no es nula y el jugador no está bloqueado
        if (island.isBannedPlayer(playerBanned.getUniqueId())){
            whoBan.sendMessage("El jugador ya esta baneado");
            return;
        }
        // Obtén el mundo del jugador
        World playerWorld = Objects.requireNonNull(playerBanned).getWorld();

        // Compara el nombre del mundo con el nombre que estás buscando
        if(playerWorld.getName().equals("PlayerIslands/" +whoBan.getUniqueId())){
            TPSpawn.sendPlayerToWorld(Objects.requireNonNull(playerBanned), "main");
            playerBanned.sendMessage("Te han baneado de esta isla!, enviandote al spawn!");
        }
        island.banPlayer(playerBanned.getUniqueId());
        whoBan.sendMessage("Jugador baneado correctamente");
    }

    public static void unbanPlayerFromIsland(Player whoUnban, String playerToUnban, UUID islandUuid){
        Player playerUnbanned = Bukkit.getPlayer(playerToUnban);
        if (playerUnbanned == null){
            whoUnban.sendMessage("El jugador a desbanear no se encuentra conectado");
            return;
        }
        if (playerUnbanned.getUniqueId().equals(whoUnban.getUniqueId())){
            whoUnban.sendMessage("No puedes banearte tu mismo!");
            return;
        }
        if (playerUnbanned.isOp()|| playerUnbanned.hasPermission("mczf.admin")){
            whoUnban.sendMessage("No puedes banear a un administrador!");
            return;
        }
        if (!activeIslands.containsKey(islandUuid)){
            activateIsland(islandUuid);
        }
        Island island = activeIslands.get(islandUuid);
        if (island == null){
            whoUnban.sendMessage("Hubo un problema con la isla (es nula)");
            return;
        }

        if (!island.isBannedPlayer(playerUnbanned.getUniqueId())){
            whoUnban.sendMessage("No has baneado a este jugador de tu isla!");
            return;
        }
        island.unbanPlayer(playerUnbanned.getUniqueId());
        whoUnban.sendMessage("Jugador desbaneado correctamente!");
    }

    public static void addPlayerToIsland(Player owner , Player toAdd, int levelPermission){
        if (!activeIslands.containsKey(owner.getUniqueId())){
            owner.sendMessage("La isla no esta activa");
            //IslandsManager.activateIsland(owner.getUniqueId());
            return;
        }
        Island island = activeIslands.get(owner.getUniqueId());
        if (island.isBannedPlayer(toAdd.getUniqueId())){
            owner.sendMessage("Este jugador esta baneado de tu isla!");
            return;
        }
        if (island.isPlayerMember(toAdd.getUniqueId())){
            owner.sendMessage("Este jugador ya es miembro de tu isla!");
            return;
        }
        island.addMember(toAdd.getUniqueId(), levelPermission);
        owner.sendMessage("Se agrego correctamente a "+ toAdd.getName()+ " a tu isla!");
    }

    public static void removePlayerFromIsland(Player owner, String ptoRemove){
        Player toRemove = Bukkit.getPlayer(ptoRemove);
        if (toRemove == null){
            // Debo poder eliminarlo incluso si no esta conectado
            return;
        }
        if (!activeIslands.containsKey(owner.getUniqueId())){
            //está desactivada debemos activarla
            owner.sendMessage("La isla no esta activa (es nula)");
            return;
        }
        Island island = activeIslands.get(owner.getUniqueId());
        if (island.isBannedPlayer(toRemove.getUniqueId())){
            owner.sendMessage("Este jugador esta baneado de tu isla!");
            return;
        }
        if (!island.isPlayerMember(toRemove.getUniqueId())){
            owner.sendMessage("Este jugador no es miembro de tu isla!");
            return;
        }
        island.removeMember(toRemove.getUniqueId());
        owner.sendMessage("Has eliminado correctamente a "+ toRemove.getName()+ " de tu isla!");
    }

}
