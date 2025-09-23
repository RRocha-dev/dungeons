package br.com.rick.dungeon;

import br.com.rick.dungeon.commands.DungeonCommand;
import br.com.rick.dungeon.infra.Config;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dungeon extends JavaPlugin {

    @Getter
    private Config dungeons;

    @Override
    public void onEnable() {
        loadConfigs();
        getCommand("dungeon").setExecutor(new DungeonCommand(this, dungeons));
        getLogger().info("Plugin iniciado com sucesso!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin desabilitado com sucesso!");
    }

    private void loadConfigs() {
        dungeons = new Config(this, "dungeons.yml");
        dungeons.reloadDefaultConfig();
    }
}
