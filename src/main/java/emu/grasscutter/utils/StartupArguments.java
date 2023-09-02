package emu.grasscutter.utils;

import static emu.grasscutter.config.Configuration.*;

import ch.qos.logback.classic.*;
import emu.grasscutter.*;
import emu.grasscutter.net.packet.PacketOpcodesUtils;
import emu.grasscutter.tools.Dumpers;
import java.util.*;
import java.util.function.Function;
import org.slf4j.LoggerFactory;

/** A parser for start-up arguments. */
public interface StartupArguments {
    /* A map of parameter -> argument handler. */
    Map<String, Function<String, Boolean>> argumentHandlers =
            new HashMap<>() {
                {
                    putAll(
                            Map.of(
                                    "-dumppacketids",
                                    parameter -> {
                                        PacketOpcodesUtils.dumpPacketIds();
                                        return true;
                                    },
                                    "-version",
                                    StartupArguments::printVersion,
                                    "-debug",
                                    StartupArguments::enableDebug,
                                    "-lang",
                                    parameter -> {
                                        Grasscutter.setPreferredLanguage(parameter);
                                        return false;
                                    },
                                    "-game",
                                    parameter -> {
                                        Grasscutter.setRunModeOverride(Grasscutter.ServerRunMode.GAME_ONLY);
                                        return false;
                                    },
                                    "-dispatch",
                                    parameter -> {
                                        Grasscutter.setRunModeOverride(Grasscutter.ServerRunMode.DISPATCH_ONLY);
                                        return false;
                                    },
                                    "-noconsole",
                                    parameter -> {
                                        Grasscutter.setNoConsole(true);
                                        return false;
                                    },
                                    "-test",
                                    parameter -> {
                                        // Disable the console.
                                        SERVER.game.enableConsole = false;
                                        // Disable HTTP encryption.
                                        SERVER.http.encryption.useEncryption = false;
                                        return false;
                                    },
                                    "-dump",
                                    StartupArguments::dump,

                                    // Aliases.
                                    "-v",
                                    StartupArguments::printVersion));
                    putAll(
                            Map.of(
                                    "-debugall",
                                    parameter -> {
                                        StartupArguments.enableDebug("all");
                                        return false;
                                    }));
                }
            };

    /**
     * Parses the provided start-up arguments.
     *
     * @param args The application start-up arguments.
     * @return If the application should exit.
     */
    static boolean parse(String[] args) {
        boolean exitEarly = false;

        // Parse the arguments.
        for (var input : args) {
            var containsParameter = input.contains("=");

            var argument = containsParameter ? input.split("=")[0] : input;
            var handler = argumentHandlers.get(argument.toLowerCase());

            if (handler != null) {
                exitEarly |= handler.apply(containsParameter ? input.split("=")[1] : null);
            }
        }

        return exitEarly;
    }

    /**
     * Prints the server version.
     *
     * @param parameter Additional parameters.
     * @return True to exit early.
     */
    private static boolean printVersion(String parameter) {
        System.out.println("Grasscutter version: " + BuildConfig.VERSION + "-" + BuildConfig.GIT_HASH);
        return true;
    }

    /**
     * Enables debug logging.
     *
     * @param parameter Additional parameters.
     * @return False to continue execution.
     */
    private static boolean enableDebug(String parameter) {
        if (parameter != null && parameter.equals("all")) {
            // Override default debug configs
            GAME_INFO.isShowLoopPackets = DEBUG_MODE_INFO.isShowLoopPackets;
            GAME_INFO.isShowPacketPayload = DEBUG_MODE_INFO.isShowPacketPayload;
            GAME_INFO.logPackets = DEBUG_MODE_INFO.logPackets;
            DISPATCH_INFO.logRequests = DEBUG_MODE_INFO.logRequests;

            // Log level to other third-party services
            Level loggerLevel = DEBUG_MODE_INFO.servicesLoggersLevel;
            // Change loggers to debug.
            ((Logger) LoggerFactory.getLogger("io.javalin")).setLevel(loggerLevel);
            ((Logger) LoggerFactory.getLogger("org.quartz")).setLevel(loggerLevel);
            ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(loggerLevel);
            ((Logger) LoggerFactory.getLogger("org.eclipse.jetty")).setLevel(loggerLevel);
            ((Logger) LoggerFactory.getLogger("org.mongodb.driver")).setLevel(loggerLevel);
        }

        // Set the main logger to debug.
        Grasscutter.getLogger().setLevel(DEBUG_MODE_INFO.serverLoggerLevel);
        Grasscutter.getLogger().debug("The logger is now running in debug mode.");
        // Set the server to debug.
        GameConstants.DEBUG = true;
        return false;
    }

    /**
     * Dumps the specified information.
     *
     * @param parameter The parameter to dump.
     * @return True to exit early.
     */
    private static boolean dump(String parameter) {
        // Parse the parameter.
        if (!parameter.contains(",")) {
            Grasscutter.getLogger().error("Dumper usage: -dump=<content>,<language>");
            return true;
        }

        var split = parameter.split(",");
        var content = split[0];
        var language = split[1];

        try {
            switch (content.toLowerCase()) {
                case "commands" -> Dumpers.dumpCommands(language);
                case "avatars" -> Dumpers.dumpAvatars(language);
                case "items" -> Dumpers.dumpItems(language);
                case "scenes" -> Dumpers.dumpScenes();
                case "entities" -> Dumpers.dumpEntities(language);
                case "quests" -> Dumpers.dumpQuests(language);
                case "areas" -> Dumpers.dumpAreas(language);
            }

            Grasscutter.getLogger().info("Finished dumping.");
        } catch (Exception exception) {
            Grasscutter.getLogger().error("Unable to complete dump.", exception);
        }

        return true;
    }
}
