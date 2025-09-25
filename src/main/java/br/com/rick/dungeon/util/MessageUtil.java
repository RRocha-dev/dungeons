package br.com.rick.dungeon.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessageUtil {

    // Prefixo usando Adventure API (recomendado para Paper)
    private static final Component PLUGIN_PREFIX = Component.text()
            .append(Component.text("[", NamedTextColor.DARK_RED))
            .append(Component.text("Dungeons", NamedTextColor.WHITE))
            .append(Component.text("]", NamedTextColor.DARK_RED))
            .append(Component.text(": ", NamedTextColor.WHITE))
            .build();

    // Prefixo usando ChatColor (para compatibilidade legacy)
    private static final String LEGACY_PREFIX = "&4[&fDungeons&4]&f: ";

    /**
     * Aplica o prefixo usando ChatColor (legacy)
     */
    public static String translateAlternateColorCodes(String msg) {
        return ChatColor.translateAlternateColorCodes('&', LEGACY_PREFIX + msg);
    }

    /**
     * Cria uma mensagem com prefixo usando Adventure API
     */
    private static Component createPrefixedMessage(Component message) {
        return Component.text()
                .append(PLUGIN_PREFIX)
                .append(message)
                .build();
    }

    /**
     * Envia mensagem de erro com prefixo
     */
    public static void sendError(@NotNull CommandSender sender, @NotNull String message) {
        Component errorMessage = Component.text("✗ " + message, NamedTextColor.RED);
        sender.sendMessage(createPrefixedMessage(errorMessage));
    }

    /**
     * Envia mensagem de sucesso com prefixo
     */
    public static void sendSuccess(@NotNull CommandSender sender, @NotNull String message) {
        Component successMessage = Component.text("✓ " + message, NamedTextColor.GREEN);
        sender.sendMessage(createPrefixedMessage(successMessage));
    }

    /**
     * Envia mensagem de informação com prefixo
     */
    public static void sendInfo(@NotNull CommandSender sender, @NotNull String message) {
        Component infoMessage = Component.text(message, NamedTextColor.GRAY);
        sender.sendMessage(createPrefixedMessage(infoMessage));
    }

    /**
     * Envia mensagem de warning com prefixo
     */
    public static void sendWarning(@NotNull CommandSender sender, @NotNull String message) {
        Component warningMessage = Component.text("⚠ " + message, NamedTextColor.YELLOW);
        sender.sendMessage(createPrefixedMessage(warningMessage));
    }

    /**
     * Envia mensagem customizada com prefixo
     */
    public static void sendMessage(@NotNull CommandSender sender, @NotNull Component message) {
        sender.sendMessage(createPrefixedMessage(message));
    }

    /**
     * Envia mensagem customizada simples com prefixo
     */
    public static void sendMessage(@NotNull CommandSender sender, @NotNull String message, @NotNull NamedTextColor color) {
        Component textMessage = Component.text(message, color);
        sender.sendMessage(createPrefixedMessage(textMessage));
    }

    /**
     * Envia mensagem sem permissão
     */
    public static void sendNoPermission(@NotNull Player player) {
        sendError(player, "Você não tem permissão para isso!");
    }

    /**
     * Envia mensagem de spawner dado
     */
    public static void sendSpawnerGiven(@NotNull CommandSender sender, @NotNull Player target, int quantity) {
        Component message = Component.text()
                .append(Component.text("✓ Enviado ", NamedTextColor.GREEN))
                .append(Component.text(quantity, NamedTextColor.YELLOW))
                .append(Component.text(" spawner(s) para ", NamedTextColor.GREEN))
                .append(Component.text(target.getName(), NamedTextColor.YELLOW))
                .append(Component.text("!", NamedTextColor.GREEN))
                .build();

        sender.sendMessage(createPrefixedMessage(message));
    }

    /**
     * Envia mensagem de spawner recebido
     */
    public static void sendSpawnerReceived(@NotNull Player player, int quantity) {
        Component message = Component.text()
                .append(Component.text("✓ Você recebeu ", NamedTextColor.GREEN))
                .append(Component.text(quantity, NamedTextColor.YELLOW))
                .append(Component.text(" spawner(s)!", NamedTextColor.GREEN))
                .build();

        player.sendMessage(createPrefixedMessage(message));
    }

    /**
     * Cria um título com prefixo
     */
    public static Component createTitle(@NotNull String title) {
        Component titleComponent = Component.text(title, NamedTextColor.GOLD);
        return createPrefixedMessage(titleComponent);
    }

    /**
     * Cria linha de uso com prefixo
     */
    public static Component createUsageLine(@NotNull String usage, @NotNull String description) {
        Component usageLine = Component.text()
                .append(Component.text(usage, NamedTextColor.YELLOW))
                .append(Component.text(" - " + description, NamedTextColor.GRAY))
                .build();

        return createPrefixedMessage(usageLine);
    }

    /**
     * Cria mensagem de uso com prefixo
     */
    public static Component createUsage(@NotNull String usage) {
        Component usageComponent = Component.text("Uso: " + usage, NamedTextColor.YELLOW);
        return createPrefixedMessage(usageComponent);
    }

    /**
     * Envia múltiplas linhas com prefixo em cada uma
     */
    public static void sendMultilineMessage(@NotNull CommandSender sender, @NotNull String... messages) {
        for (String message : messages) {
            Component textMessage = Component.text(message, NamedTextColor.WHITE);
            sender.sendMessage(createPrefixedMessage(textMessage));
        }
    }

    /**
     * Envia múltiplas linhas com prefixo em cada uma (Components)
     */
    public static void sendMultilineMessage(@NotNull CommandSender sender, @NotNull Component... messages) {
        for (Component message : messages) {
            sender.sendMessage(createPrefixedMessage(message));
        }
    }

    /**
     * Cria separador visual com prefixo
     */
    public static Component createSeparator() {
        Component separator = Component.text("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", NamedTextColor.DARK_GRAY);
        return createPrefixedMessage(separator);
    }

    /**
     * Envia help/usage completo
     */
    public static void sendHelp(@NotNull CommandSender sender, @NotNull String title, @NotNull String... commands) {
        // Título
        sendMessage(sender, createTitle("=== " + title + " ==="));

        // Separador
        sender.sendMessage(createSeparator());

        // Comandos
        for (String command : commands) {
            String[] parts = command.split(" - ", 2);
            if (parts.length == 2) {
                sender.sendMessage(createUsageLine(parts[0], parts[1]));
            } else {
                sendInfo(sender, command);
            }
        }

        // Separador final
        sender.sendMessage(createSeparator());
    }

    /**
     * Obtém o prefixo como String (para uso com outras APIs)
     */
    public static String getPrefixAsString() {
        return ChatColor.translateAlternateColorCodes('&', LEGACY_PREFIX);
    }

    /**
     * Obtém o prefixo como Component
     */
    public static Component getPrefixAsComponent() {
        return PLUGIN_PREFIX;
    }
}