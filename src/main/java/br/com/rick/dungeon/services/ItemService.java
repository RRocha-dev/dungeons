package br.com.rick.dungeon.services;

import br.com.rick.dungeon.Dungeon;
import br.com.rick.dungeon.model.DungeonSpawner;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ItemService {

    private final Dungeon plugin;

    public ItemService(Dungeon plugin) {
        this.plugin = plugin;
    }

    public boolean giveSpawner(@NotNull Player player, int quantity) {
        if (player.getInventory().firstEmpty() == -1) {
            return false;
        }

        try {
            DungeonSpawner spawner = new DungeonSpawner();
            spawner.receberSpawner(player, quantity);
            return true;
        } catch (Exception e) {
            plugin.getLogger().warning("Erro ao dar spawner: " + e.getMessage());
            return false;
        }
    }

    public boolean giveKey(@NotNull Player player, int quantity) {
        // Implementar lógica de dar chave
        // TODO: Implementar quando a classe DungeonKey estiver pronta
        return true;
    }

    public boolean giveVault(@NotNull Player player, int quantity) {
        // Implementar lógica de dar vault
        // TODO: Implementar quando a classe DungeonVault estiver pronta
        return true;
    }
}