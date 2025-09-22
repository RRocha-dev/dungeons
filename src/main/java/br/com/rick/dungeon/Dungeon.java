package br.com.rick.dungeon;

import br.com.rick.dungeon.commands.SpawnerCommands;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dungeon extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin iniciado com sucesso!");

        getCommand("dungeon").setExecutor(new SpawnerCommands(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin desabilitado com sucesso!");
    }
}
