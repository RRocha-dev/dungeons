package br.com.rick.dungeon.commands;

import br.com.rick.dungeon.Dungeon;
import br.com.rick.dungeon.model.DungeonSpawner;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@RequiredArgsConstructor
public class SpawnerCommands implements CommandExecutor {

    private final Dungeon plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String @NotNull [] args) {
        plugin.getLogger().info(sender.getName() + " usou: " + s + Arrays.toString(args));
        if (!(sender instanceof Player) ) {
            sender.sendMessage(ChatColor.RED + "Apenas players podem utilizar este comando!");
            return true;
        }

        if (args.length == 0 || args.length < 4) {
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("dungeons.use")){
            player.sendMessage(ChatColor.RED + "Você não tem permissão para isto!");
            return true;
        }

            switch (args[0]) {
                case "give":
                    if (args[1].equalsIgnoreCase("spawner")) {
                        return giveSpawnerCommand(sender, args);
                    }
                    break;
                case "reload":
                    if (!player.hasPermission("dungeon.admin")) {
                        player.sendMessage(ChatColor.RED + "Você não tem permissão para isto!");
                        return true;
                    }
                    plugin.reloadConfig();
                    player.sendMessage(ChatColor.GREEN + "Plugin carregado com sucesso!");
                    break;
                default:
                    return false;
            }


        return false;
    }

    private boolean giveSpawnerCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("dungeon.admin")) {
            sender.sendMessage(ChatColor.RED + "Você não tem permissão para isto!");
            return false;
        }

        if (args.length < 3) {
            return false;
        }

        Player player = Bukkit.getPlayer(args[2]);

        if (player != null) {
            Integer quantidade = Integer.valueOf(args[3]);
            DungeonSpawner spawner = new DungeonSpawner();
            sender.sendMessage(ChatColor.GREEN + "Enviado " + quantidade + " spawner(s) para " + player.getName() + " !");
            spawner.receberSpawner(player, quantidade);
        } else {
            sender.sendMessage(ChatColor.RED + "Jogador não encontrado!");
        }

        return true;
    }
}
