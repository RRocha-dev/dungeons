package br.com.rick.dungeon.commands;

import br.com.rick.dungeon.Dungeon;
import br.com.rick.dungeon.commands.subcommads.SubCommand;
import br.com.rick.dungeon.commands.subcommads.dungeon.GiveSpawnerSubCommand;
import br.com.rick.dungeon.infra.Config;
import br.com.rick.dungeon.util.MessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.logging.Level;

public class DungeonCommand implements CommandExecutor, TabCompleter {

    private final String PERMISSION_USE = "dungeons.use";
    private final Dungeon plugin;
    private final Config dungeonsConfig;
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public DungeonCommand(Dungeon plugin, Config dungeonsConfig) {
        this.plugin = plugin;
        this.dungeonsConfig = dungeonsConfig;
        registerSubCommands();
    }

    public void registerSubCommands() {
        subCommands.put("give", new GiveSpawnerSubCommand(plugin, dungeonsConfig));
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        plugin.getLogger().log(Level.INFO, "{0} executou: /{1} {2}",
                new Object[]{sender.getName(), label, String.join(" ", args)});

        // Verificar se é player
        if (!(sender instanceof Player player)) {
            MessageUtil.sendError(sender, "Apenas players podem utilizar este comando!");
            return true;
        }

        // Verificar argumentos
        if (args.length == 0) {
            sendUsage(player);
            return true;
        }

        // Verifica permissão básica de uso
        if (!player.hasPermission(PERMISSION_USE)) {
            MessageUtil.sendNoPermission(player);
            return true;
        }

        // Busca os subcomandos para executar
        SubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            return subCommand.execute(player, args);
        }

        sendUsage(player);
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player) || !sender.hasPermission(PERMISSION_USE)) {
            return Collections.emptyList();
        }

        if (args.length == 1) {
            return subCommands.entrySet().stream()
                    .filter(entry -> entry.getValue().hasPermission(sender))
                    .map(Map.Entry::getKey)
                    .filter(command -> command.toLowerCase().startsWith(args[0].toLowerCase()))
                    .toList();
        }

        SubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            return subCommand.getTabCompletions(sender, args);
        }

        return Collections.emptyList();
    }

    private void sendUsage(@NotNull Player player) {
        List<Component> usage = new ArrayList<>();
        usage.add(MessageUtil.createUsage("=== Comandos Dungeon ==="));

        subCommands.entrySet().stream()
                .filter(entry -> entry.getValue().hasPermission(player))
                .forEach(entry -> {
                    SubCommand command = entry.getValue();
                    usage.add(MessageUtil.createUsageLine(command.getUsage(), command.getDescription()));
                });

        MessageUtil.sendMultilineMessage(player, usage.toArray(new Component[0]));
    }
}
