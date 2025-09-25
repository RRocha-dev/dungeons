package br.com.rick.dungeon.util;

import org.jetbrains.annotations.NotNull;

public class ValidationUtil {

    public static int parsePositiveInteger(@NotNull String str, int min, int max) {
        try {
            int value = Integer.parseInt(str);

            if (value < min) {
                throw new IllegalArgumentException("O valor deve ser pelo menos " + min);
            }

            if (value > max) {
                throw new IllegalArgumentException("O valor máximo é " + max);
            }

            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("'" + str + "' não é um número válido!");
        }
    }

    public static boolean isValidDungeonName(@NotNull String name) {
        return name.matches("^[a-zA-Z0-9_]+$") && name.length() >= 3 && name.length() <= 20;
    }
}
