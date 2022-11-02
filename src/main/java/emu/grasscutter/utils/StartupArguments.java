package emu.grasscutter.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import emu.grasscutter.BuildConfig;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerRunMode;
import emu.grasscutter.net.packet.PacketOpcodesUtils;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Function;

import static emu.grasscutter.config.Configuration.*;

/**
 * A parser for start-up arguments.
 */
public final class StartupArguments {
    private StartupArguments() {
        // This class is not meant to be instantiated.
    }

    /* A map of parameter -> argument handler. */
    private static final Map<String, Function<String, Boolean>> argumentHandlers = Map.of(
        "-dumppacketids", parameter -> {
            PacketOpcodesUtils.dumpPacketIds(); return true;
        },
        "-version", StartupArguments::printVersion,
        "-debug", StartupArguments::enableDebug,
        "-lang", parameter -> {
            Grasscutter.setPreferredLanguage(parameter); return false;
        },"-game", parameter -> {
            Grasscutter.setRunModeOverride(ServerRunMode.GAME_ONLY); return false;
        },"-dispatch", parameter -> {
            Grasscutter.setRunModeOverride(ServerRunMode.DISPATCH_ONLY); return false;
        },

        // Aliases.
        "-v", StartupArguments::printVersion,
        "-debugall", parameter -> {
            StartupArguments.enableDebug("all"); return false;
        }
    );

    /**
     * Parses the provided start-up arguments.
     * @param args The application start-up arguments.
     * @return If the application should exit.
     */
    public static boolean parse(String[] args) {
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
     * @param parameter Additional parameters.
     * @return True to exit early.
     */
    private static boolean printVersion(String parameter) {
        System.out.println("Grasscutter version: " + BuildConfig.VERSION + "-" + BuildConfig.GIT_HASH); return true;
    }

    /**
     * Enables debug logging.
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
        }

        // Set the main logger to debug.
        Grasscutter.getLogger().setLevel(DEBUG_MODE_INFO.serverLoggerLevel);
        Grasscutter.getLogger().debug("The logger is now running in debug mode.");

        // Log level to other third-party services
        Level loggerLevel = DEBUG_MODE_INFO.servicesLoggersLevel;

        // Change loggers to debug.
        ((Logger) LoggerFactory.getLogger("io.javalin"))
            .setLevel(loggerLevel);
        ((Logger) LoggerFactory.getLogger("org.quartz"))
            .setLevel(loggerLevel);
        ((Logger) LoggerFactory.getLogger("org.reflections"))
            .setLevel(loggerLevel);
        ((Logger) LoggerFactory.getLogger("org.eclipse.jetty"))
            .setLevel(loggerLevel);
        ((Logger) LoggerFactory.getLogger("org.mongodb.driver"))
            .setLevel(loggerLevel);

        return false;
    }
}
