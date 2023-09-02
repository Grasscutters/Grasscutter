package emu.grasscutter.command.commands;

import emu.grasscutter.command.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ClimateType;
import java.util.List;

@Command(
        label = "weather",
        aliases = {"w"},
        usage = {"weather [<weatherId>] [<climateType>]"},
        permission = "player.weather",
        permissionTargeted = "player.weather.others")
public final class WeatherCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        int weatherId = targetPlayer.getWeatherId();
        ClimateType climate =
                ClimateType
                        .CLIMATE_NONE; // Sending ClimateType.CLIMATE_NONE to Scene.setWeather will use the
        // default climate for that weather

        if (args.isEmpty()) {
            climate = targetPlayer.getClimate();
            CommandHandler.sendTranslatedMessage(
                    sender, "commands.weather.status", weatherId, climate.getShortName());
            return;
        }

        for (String arg : args) {
            ClimateType c = ClimateType.getTypeByShortName(arg.toLowerCase());
            if (c != ClimateType.CLIMATE_NONE) {
                climate = c;
            } else {
                try {
                    weatherId = Integer.parseInt(arg);
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.generic.invalid.id");
                    sendUsageMessage(sender);
                    return;
                }
            }
        }

        targetPlayer.setWeather(weatherId, climate);
        climate = targetPlayer.getClimate(); // Might be different to what we set
        CommandHandler.sendTranslatedMessage(
                sender, "commands.weather.success", weatherId, climate.getShortName());
    }
}
