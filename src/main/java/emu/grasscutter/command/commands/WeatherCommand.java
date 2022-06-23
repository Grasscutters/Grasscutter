package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ClimateType;
import emu.grasscutter.game.world.Scene;

import java.util.List;

@Command(label = "weather", usage = "weather [weatherId] [climateType]", aliases = {"w"}, permission = "player.weather", permissionTargeted = "player.weather.others", description = "commands.weather.description")
public final class WeatherCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        int weatherId = targetPlayer.getWeatherId();
        ClimateType climate = ClimateType.CLIMATE_NONE;  // Sending ClimateType.CLIMATE_NONE to Scene.setWeather will use the default climate for that weather

        if (args.isEmpty()) {
            climate = targetPlayer.getClimate();
            CommandHandler.sendTranslatedMessage(sender, "commands.weather.status", Integer.toString(weatherId), climate.getShortName());
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
                    CommandHandler.sendTranslatedMessage(sender, "commands.weather.usage");
                    return;
                }
            }
        }

        targetPlayer.setWeather(weatherId, climate);
        climate = targetPlayer.getClimate();  // Might be different to what we set
        CommandHandler.sendTranslatedMessage(sender, "commands.weather.success", Integer.toString(weatherId), climate.getShortName());
    }
}
