package es.hulk.hub.managers;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Getter
@Setter
public class SpawnManager {

    private ConfigFile spawnConfig = SharkHub.getInstance().getSpawnConfig();
    private ConfigFile lang = SharkHub.getInstance().getMessagesConfig();
    private Location location;

    public SpawnManager() {
        this.location = null;
        this.loadLocation();
    }

    public void saveLocation() {
        if (this.location != null) {
            spawnConfig.getConfiguration().set("SPAWN.WORLD", location.getWorld().getName());
            spawnConfig.getConfiguration().set("SPAWN.X", location.getX());
            spawnConfig.getConfiguration().set("SPAWN.Y", location.getY());
            spawnConfig.getConfiguration().set("SPAWN.Z", location.getZ());
            spawnConfig.getConfiguration().set("SPAWN.YAW", location.getYaw());
            spawnConfig.getConfiguration().set("SPAWN.PITCH", location.getPitch());
            spawnConfig.save();
        }
    }

    public void loadLocation() {
        if (!spawnConfig.getConfiguration().getConfigurationSection("SPAWN").getKeys(false).isEmpty()) {
            World world = Bukkit.getWorld(spawnConfig.getString("SPAWN.WORLD"));
            double x = spawnConfig.getDouble("SPAWN.X");
            double y = spawnConfig.getDouble("SPAWN.Y");
            double z = spawnConfig.getDouble("SPAWN.Z");
            double yaw = spawnConfig.getDouble("SPAWN.YAW");
            double pitch = spawnConfig.getDouble("SPAWN.PITCH");
            this.location = new Location(world, x, y, z, (float) yaw, (float) pitch);
        }
    }

    public void sendToSpawn(Player player) {
        if (this.getLocation() == null) {
            player.sendMessage(CC.translate(lang.getString("SPAWN_NOT_FOUND")));
            return;
        }
        player.teleport(this.getLocation());
    }

}
