package net.sixunderscore.oldvisuals.config;

public class ConfigUtils {
    public static String getFriendlyBoolean(boolean value) {
        return value ? "On" : "Off";
    }

    public static String getFriendlyEnum(Enum<?> enumInstance) {
        String name = enumInstance.name().toLowerCase();
        int length = name.length();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; ++i) {
            char currentChar = name.charAt(i);

            if (i == 0) {
                builder.append(Character.toUpperCase(currentChar));
            } else if (currentChar == '_') {
                builder.append(" ");
            } else {
                builder.append(currentChar);
            }
        }

        return builder.toString();
    }

    public static <T extends Enum<T>> T getNextEnumValue(Enum<T> enumInstance) {
        T[] enumConstants = enumInstance.getDeclaringClass().getEnumConstants();
        int currentIndex = enumInstance.ordinal();

        // Get next index (loop back to 0 if last enum constant)
        int nextIndex = (currentIndex + 1) % enumConstants.length;

        return enumConstants[nextIndex];
    }
}
