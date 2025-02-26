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

    public static void load() {
        Set<String> loadedKeys = new HashSet<>();

        try {
            if (Files.notExists(CONFIG_FILE_PATH)) {
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

                        RuntimeData.applySetting(keyValue[0], keyValue[1]);
                    }
                }
            }

            checkForMissingSettings(loadedKeys);
        } catch (IOException e) {
            OldVisuals.LOGGER.error(e.getMessage());
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
            for (String key : ConfigKeys.getAllKeys()) {
                writer.write(key + "=" + ConfigKeys.getDefaultValue(key));
                writer.newLine();
            }
        }
    }

    private static void checkForMissingSettings(Set<String> loadedKeys) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_FILE_PATH, StandardOpenOption.APPEND)) {
            if (shouldWriteNewLine())
                writer.newLine();

            for (String key : ConfigKeys.getAllKeys()) {
                if (!loadedKeys.contains(key)) {
                    OldVisuals.LOGGER.info("Writing missing key: {} to config file", key);
                    Object defaultValue = ConfigKeys.getDefaultValue(key);

                    writer.write(key + "=" + defaultValue);
                    writer.newLine();
                    RuntimeData.applySetting(key, defaultValue);
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
