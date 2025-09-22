package br.com.rick.dungeon.model;

import br.com.rick.dungeon.util.ItemBuilder;
import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
public class DungeonSpawner {
    private Integer level;
    private Double qtdOrdas;
    private Double velocidadeSpawn;

    public void iniciarOrdas() {};

    public void proximaOrda() {};

    public void finalizarOrda() {};

    public void receberSpawner(Player player, Integer quantidade) {
        ItemStack item = ItemBuilder.of(Material.TRIAL_SPAWNER)
                .setName("&aCustom Spawner")
                .setAmount(quantidade)
                .setGlow(true)
                .setLore("&aUm spawner customizado!")
                .build();

        player.give(item);
        player.sendMessage(ChatColor.GREEN + "Você recebeu " + quantidade + " spawner(s).");
    }
}
