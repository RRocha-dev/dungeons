package br.com.rick.dungeon.commands.subcommads.dungeon;

import br.com.rick.dungeon.Dungeon;
import br.com.rick.dungeon.commands.subcommads.SubCommand;
import br.com.rick.dungeon.infra.Config;
import br.com.rick.dungeon.services.ItemService;
import br.com.rick.dungeon.util.MessageUtil;
import br.com.rick.dungeon.util.ValidationUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class GiveSpawnerSubCommand extends SubCommand {

    private static final String PERMISSION = "dungeon.admin";
    private static final List<String> GIVE_TYPES = Arrays.asList("key", "spawner", "vault");
    private final ItemService itemService;

    public GiveSpawnerSubCommand(Dungeon plugin, Config dungeonsConfig) {
        super(plugin);
        this.itemService = new ItemService(plugin);
    }

    @Override
    public boolean execute(@NotNull Player sender, @NotNull String[] args) {
        if (!hasPermission(sender)) {
            MessageUtil.sendNoPermission(sender);
            return true;
        }

        if (!validateArgs(args, 4, sender)) {
            return true;
        }

        String itemType = args[1].toLowerCase();
        String playerName = args[2];
        String quantityStr = args[3];

        if (!GIVE_TYPES.contains(itemType)) {
            MessageUtil.sendError(sender, "Tipo inválido! Use: " + String.join(", ", GIVE_TYPES));
            return true;
        }

        try {
            // Validar quantidade
            int quantity = ValidationUtil.parsePositiveInteger(quantityStr, 1, 64);

            // Buscar jogador
            Player targetPlayer = Bukkit.getPlayer(playerName);
            if (targetPlayer == null) {
                MessageUtil.sendError(sender, "Jogador '" + playerName + "' não encontrado ou está offline!");
                return true;
            }

            // Dar item usando o service
            boolean success = switch (itemType) {
                case "spawner" -> itemService.giveSpawner(targetPlayer, quantity);
                case "key" -> itemService.giveKey(targetPlayer, quantity);
                case "vault" -> itemService.giveVault(targetPlayer, quantity);
                default -> false;
            };

            if (success) {
                MessageUtil.sendSuccess(sender,
                        String.format("Enviado %d %s(s) para %s!", quantity, itemType, targetPlayer.getName()));
                MessageUtil.sendSuccess(targetPlayer,
                        String.format("Você recebeu %d %s(s)!", quantity, itemType));

                plugin.getLogger().info(String.format("%s deu %d %s(s) para %s",
                        sender.getName(), quantity, itemType, targetPlayer.getName()));
            } else {
                MessageUtil.sendError(sender, "Não foi possível dar o item. Inventário pode estar cheio.");
            }

        } catch (IllegalArgumentException e) {
            MessageUtil.sendError(sender, e.getMessage());
        } catch (Exception e) {
            MessageUtil.sendError(sender, "Erro interno! Verifique os logs.");
            plugin.getLogger().log(Level.SEVERE, "Erro ao dar item", e);
        }

        return true;
    }

    @Override
    public String getUsage() {
        return "/dungeon give <key|spawner|vault> <jogador> <quantidade>";
    }

    @Override
    public String getDescription() {
        return "Dar itens para um jogador";
    }

    @Override
    public String getRequiredPermission() {
        return PERMISSION;
    }

    @Override
    public @Nullable List<String> getTabCompletions(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!hasPermission(sender)) {
            return Collections.emptyList();
        }

        return switch (args.length) {
            case 2 -> GIVE_TYPES.stream()
                    .filter(type -> type.startsWith(args[1].toLowerCase()))
                    .toList();
            case 3 -> Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[2].toLowerCase()))
                    .toList();
            case 4 -> Arrays.asList("1", "5", "10", "16", "32", "64");
            default -> Collections.emptyList();
        };
    }
}
