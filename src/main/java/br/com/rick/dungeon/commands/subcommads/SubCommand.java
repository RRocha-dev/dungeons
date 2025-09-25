package br.com.rick.dungeon.commands.subcommads;

import br.com.rick.dungeon.Dungeon;
import br.com.rick.dungeon.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public abstract class SubCommand {
    protected  final Dungeon plugin;

    public abstract boolean execute(@NotNull Player sender, @NotNull  String[] args);
    public abstract String getUsage();
    public abstract String getDescription();
    public abstract String getRequiredPermission();

    public boolean hasPermission(@NotNull CommandSender sender) {
        String permission = getRequiredPermission();
        return permission == null || sender.hasPermission(permission);
    }

    public @Nullable List<String> getTabCompletions(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }

    public boolean validateArgs(@NotNull String[] args, int minArgs, @NotNull Player sender) {
        if (args.length < minArgs) {
            sender.sendMessage(MessageUtil.translateAlternateColorCodes(getUsage()));
            return false;
        }
        return true;
    }

    protected Component getUsageComponent() {
        return MessageUtil.createUsage(getUsage());
    }
}
