package net.sixunderscore.oldvisuals.config;

import net.fabricmc.loader.api.FabricLoader;
import net.sixunderscore.oldvisuals.OldVisuals;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Config {
    private static final Path CONFIG_FILE_PATH = FabricLoader.getInstance().getConfigDir().resolve("oldvisuals.conf");

    public static void load() {
        Set<String> loadedKeys = new HashSet<>(ConfigKeys.DEFAULT_VALUES.size());

        try {
            if (Files.notExists(CONFIG_FILE_PATH)) {
                OldVisuals.LOGGER.info("Creating new default config file");
                newConfigFile();
            }

            List<String> lines = Files.readAllLines(CONFIG_FILE_PATH);

            for (String line : lines) {
                if (line.contains("=")) {
                    String[] keyValue = line.split("=", 2);
                    loadedKeys.add(keyValue[0]);
                    RuntimeConfig.applySetting(keyValue[0], keyValue[1]);
                }
            }

            checkForMissingSettings(loadedKeys);
        } catch (IOException e) {
            OldVisuals.LOGGER.error(e.toString());
        }
    }

    private static void newConfigFile() throws IOException {
        Files.createFile(CONFIG_FILE_PATH);

        try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_FILE_PATH)) {
            for (String key : ConfigKeys.DEFAULT_VALUES.keySet()) {
                writer.write(key + "=" + ConfigKeys.DEFAULT_VALUES.get(key));
                writer.newLine();
            }
        }
    }

    private static void checkForMissingSettings(Set<String> loadedKeys) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_FILE_PATH, StandardOpenOption.APPEND)) {
            if (shouldWriteNewLine()) {
                writer.newLine();
            }

            for (String key : ConfigKeys.DEFAULT_VALUES.keySet()) {
                if (!loadedKeys.contains(key)) {
                    OldVisuals.LOGGER.info("Writing missing key: {} to config file", key);
                    String defaultValue = ConfigKeys.DEFAULT_VALUES.get(key);

                    RuntimeConfig.applySetting(key, defaultValue);
                    writer.write(key + "=" + defaultValue);
                    writer.newLine();
                }
            }
        }
    }

    public static void editConfigFile(Map<String, Object> changedSettings) {
        OldVisuals.LOGGER.info("Writing changes to config file");

        Thread.startVirtualThread(() -> {
            try {
                List<String> lines = Files.readAllLines(CONFIG_FILE_PATH);

                for (int i = 0; i < lines.size(); ++i) {
                    String line = lines.get(i);

                    if (line.contains("=")) {
                        String[] parts = line.split("=", 2);
                        String key = parts[0];

                        if (changedSettings.containsKey(key)) {
                            lines.set(i, key + "=" + changedSettings.get(key));
                        }
                    }
                }

                Files.write(CONFIG_FILE_PATH, lines);
            } catch (IOException e) {
                OldVisuals.LOGGER.error(e.toString());
            }
        });
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
