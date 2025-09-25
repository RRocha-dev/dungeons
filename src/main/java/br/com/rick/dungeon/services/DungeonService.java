package br.com.rick.dungeon.services;

import br.com.rick.dungeon.Dungeon;
import br.com.rick.dungeon.infra.Config;
import br.com.rick.dungeon.model.DungeonModel;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@RequiredArgsConstructor
public class DungeonService {

    private final Dungeon plugin;
    private final Config dungeonsConfig;

    public boolean dungeonExists(@NotNull String dungeonName) {
        ConfigurationSection dungeonsSection = dungeonsConfig.getConfigurationSection("dungeons");
        if (dungeonsSection == null) return false;

        return dungeonsSection.getKeys(false).stream()
                .anyMatch(id -> id.equalsIgnoreCase(dungeonName));
    }

    public boolean createDungeon(@NotNull String dungeonName) {
        try {
            DungeonModel dungeon = new DungeonModel();
            dungeon.setName(dungeonName);

            String dungeonPath = "dungeons." + dungeonName;
            dungeonsConfig.createSection(dungeonPath);
            dungeonsConfig.set(dungeonPath + ".name", dungeonName);
            dungeonsConfig.set(dungeonPath + ".level", 1);
            dungeonsConfig.set(dungeonPath + ".created", System.currentTimeMillis());

            dungeonsConfig.saveConfig();
            return true;
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Erro ao criar dungeon " + dungeonName, e);
            return false;
        }
    }

    public boolean setDungeonSpawn(@NotNull String dungeonName, @NotNull Location location) {
        try {
            String dungeonPath = "dungeons." + dungeonName;
            dungeonsConfig.set(dungeonPath + ".spawn.world", location.getWorld().getName());
            dungeonsConfig.set(dungeonPath + ".spawn.x", location.getX());
            dungeonsConfig.set(dungeonPath + ".spawn.y", location.getY());
            dungeonsConfig.set(dungeonPath + ".spawn.z", location.getZ());
            dungeonsConfig.set(dungeonPath + ".spawn.yaw", location.getYaw());
            dungeonsConfig.set(dungeonPath + ".spawn.pitch", location.getPitch());

            dungeonsConfig.saveConfig();
            return true;
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Erro ao definir spawn da dungeon " + dungeonName, e);
            return false;
        }
    }

    public boolean setDungeonLevel(@NotNull String dungeonName, int level) {
        try {
            String dungeonPath = "dungeons." + dungeonName + ".level";
            dungeonsConfig.set(dungeonPath, level);
            dungeonsConfig.saveConfig();
            return true;
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Erro ao definir nível da dungeon " + dungeonName, e);
            return false;
        }
    }

    public List<String> getAllDungeonNames() {
        ConfigurationSection dungeonsSection = dungeonsConfig.getConfigurationSection("dungeons");
        if (dungeonsSection == null) return new ArrayList<>();

        return new ArrayList<>(dungeonsSection.getKeys(false));
    }
}