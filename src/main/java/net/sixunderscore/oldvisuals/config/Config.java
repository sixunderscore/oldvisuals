package net.sixunderscore.oldvisuals.config;

import net.fabricmc.loader.api.FabricLoader;
import net.sixunderscore.oldvisuals.OldVisuals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Config {
    private static final Path CONFIG_FILE_PATH = FabricLoader.getInstance().getConfigDir().resolve("oldvisuals.conf");
    public static boolean enabledThirdPersonCrosshair;
    public static boolean enabledRedArmor;
    public static boolean enabledNoCooldownAnimation;
    public static boolean enabledOldThirdPersonTool;
    public static boolean enabledOldThirdPersonItem;
    public static boolean enabledOldFirstPersonRod;
    public static boolean enabledFlatDroppedItems;

    public static void load() {
        Set<String> loadedKeys = new HashSet<>();

        try {
            if (!Files.exists(CONFIG_FILE_PATH)) {
                OldVisuals.LOGGER.info("Creating and initializing config file");
                Files.createFile(CONFIG_FILE_PATH);
                initializeNewConfigFile();
            }

            try (BufferedReader reader = Files.newBufferedReader(CONFIG_FILE_PATH)) {
                OldVisuals.LOGGER.info("Loading config values");
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.contains("=")) {
                        String[] keyValue = line.split("=", 2);

                        loadedKeys.add(keyValue[0]);

                        applyRuntimeSetting(keyValue[0], Boolean.parseBoolean(keyValue[1]));
                    }
                }
            }

            checkForMissingSettings(loadedKeys);
        } catch (IOException e) {
            OldVisuals.LOGGER.error(e.getMessage());
        }
    }

    private static void applyRuntimeSetting(String key, boolean value) {
        switch (key) {
            case ConfigKeys.ENABLED_THIRD_PERSON_CROSSHAIR -> enabledThirdPersonCrosshair = value;
            case ConfigKeys.ENABLED_RED_ARMOR -> enabledRedArmor = value;
            case ConfigKeys.ENABLED_NO_COOLDOWN_ANIMATION -> enabledNoCooldownAnimation = value;
            case ConfigKeys.ENABLED_OLD_THIRD_PERSON_TOOL -> enabledOldThirdPersonTool = value;
            case ConfigKeys.ENABLED_OLD_THIRD_PERSON_ITEM -> enabledOldThirdPersonItem = value;
            case ConfigKeys.ENABLED_OLD_FIRST_PERSON_ROD -> enabledOldFirstPersonRod = value;
            case ConfigKeys.ENABLED_FLAT_DROPPED_ITEMS -> enabledFlatDroppedItems = value;
        }
    }

    public static void editConfigFile(Map<String, Object> changedSettings) {
        OldVisuals.LOGGER.info("Writing changes to config file");

        Thread.startVirtualThread(() -> {
            try {
                List<String> fileLines = Files.readAllLines(CONFIG_FILE_PATH);

                for (String key : changedSettings.keySet()) {
                    editSetting(fileLines, key, changedSettings.get(key));
                }

                Files.write(CONFIG_FILE_PATH, fileLines);
            } catch (IOException e) {
                OldVisuals.LOGGER.error(e.getMessage());
            }
        });
    }

    private static void editSetting(List<String> fileLines, String key, Object newValue) {
        int lineCount = fileLines.size();

        for (int i = 0; i < lineCount; ++i) {
            String line = fileLines.get(i);

            if (line.startsWith(key + "=")) {
                fileLines.set(i, key + "=" + newValue);
                break;
            }
        }
    }

    private static void initializeNewConfigFile() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_FILE_PATH)) {
            for (String key : ConfigKeys.ALL) {
                writer.write(key + "=true");
                writer.newLine();
            }
        }
    }

    private static void checkForMissingSettings(Set<String> loadedKeys) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_FILE_PATH, StandardOpenOption.APPEND)) {
            if (shouldWriteNewLine())
                writer.newLine();

            for (String key : ConfigKeys.ALL) {
                if (!loadedKeys.contains(key)) {
                    OldVisuals.LOGGER.info("Writing missing key: {} to config file", key);

                    writer.write(key + "=true");
                    writer.newLine();
                    applyRuntimeSetting(key, true);
                }
            }
        }
    }

    private static boolean shouldWriteNewLine() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(CONFIG_FILE_PATH.toFile(), "r")) {
            if (raf.length() > 0) {
                raf.seek(raf.length() - 1);
                return raf.read() != '\n';
            }
            return false;
        }
    }
}
