package br.com.rick.dungeon.model;

import br.com.rick.dungeon.util.ItemBuilder;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

@Data
public class DungeonSpawner {
    private Integer level;
    private Integer qtdOrdas;
    private List<DungeonMob> mobs;
    private Integer ordaAtual;
    private Integer mobsSpawnadosNaOrda = 0;
    private final int LIMITE_MOBS_POR_CHUNCK = 25;
    private BukkitTask spawnTask;

    public void iniciarOrdas() {
        ordaAtual = 1;
        mobsSpawnadosNaOrda = 0;
    }


    private void spawnarMobDaOrda() {
    }

    public void proximaOrda() {
    }


    public void finalizarOrda() {
    }


    public void receberSpawner(Player player, Integer quantidade) {
        ItemStack item = ItemBuilder.of(Material.TRIAL_SPAWNER).setName("&aCustom Spawner").setAmount(quantidade).setGlow(true).setLore("&aUm spawner customizado!").build();

        player.give(item);
        player.sendMessage(ChatColor.GREEN + "Você recebeu " + quantidade + " spawner(s).");
    }
}
