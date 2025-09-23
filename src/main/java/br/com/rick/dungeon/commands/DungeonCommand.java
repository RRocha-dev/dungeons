package br.com.rick.dungeon.commands;

import br.com.rick.dungeon.Dungeon;
import br.com.rick.dungeon.infra.Config;
import br.com.rick.dungeon.model.DungeonModel;
import br.com.rick.dungeon.model.DungeonSpawner;
import br.com.rick.dungeon.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

@RequiredArgsConstructor
public class DungeonCommand implements CommandExecutor, TabCompleter {

    private final Dungeon plugin;
    private final Config dungeonsConfig;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String @NotNull [] args) {
        plugin.getLogger().info(sender.getName() + " usou: " + s + Arrays.toString(args));
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas players podem utilizar este comando!");
            return true;
        }

        if (args.length == 0) {
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("dungeons.use")) {
            player.sendMessage(ChatColor.RED + "Você não tem permissão para isto!");
            return true;
        }

        switch (args[0]) {
            case "give":
                if (args[1].equalsIgnoreCase("spawner")) {
                    return giveSpawnerCommand(sender, args);
                }
                break;
            case "create":
                return createNewDungeon(sender, args);
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

    private boolean createNewDungeon(CommandSender sender, String[] args) {
        try {
            if (isDungeonCreated(args[1])) {
                sender.sendMessage(MessageUtil.translateAlternateColorCodes("&eEsta dungeon já está cadastrada!"));
                return true;
            }

            DungeonModel dungeon = new DungeonModel();
            dungeon.setName(args[1]);
            String dungeonPath = "dungeons." + dungeon.getName();
            dungeonsConfig.createSection(dungeonPath);

            dungeonsConfig.saveConfig(); // Save the changes
            sender.sendMessage(MessageUtil.translateAlternateColorCodes("&aDungeon criada com sucesso!")); // Success message

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Ocorreu um erro ao criar uma nova dungeon!");
            sender.sendMessage(MessageUtil.translateAlternateColorCodes("&4Ocorreu um erro!"));
            e.printStackTrace();
        }
        return true;
    }

//    private void updateDungeon()

    private boolean isDungeonCreated(String dungeonName) {
        if (this.dungeonsConfig != null) {
            ConfigurationSection dungeonsSection = dungeonsConfig.getConfigurationSection("dungeons");
            if (dungeonsSection == null) return false;

            for (String id : dungeonsSection.getKeys(false)) {
                if (id.equalsIgnoreCase(dungeonName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            return Arrays.asList("give", "create", "select");
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give")) {
                return Arrays.asList("key", "spawner", "vault");
            } else if (args[0].equalsIgnoreCase("create")) {
                return Arrays.asList("name");
            } else if (args[0].equalsIgnoreCase("select")) {
                return Arrays.asList("name");
            }
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("select")) {
            return Arrays.asList("setSpawn", "setLevel");
        }

        if (args.length == 4 && args[0].equalsIgnoreCase("select")) {
            if (args[2].equalsIgnoreCase("setSpawn") || args[2].equalsIgnoreCase("setLevel")) {
                return Arrays.asList("int");
            }
        }

        return List.of();
    }

}
