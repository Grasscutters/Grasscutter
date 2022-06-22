package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ClimateType;
import emu.grasscutter.server.packet.send.PacketSceneAreaWeatherNotify;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "weather", usage = "weather <weather> <climate>", aliases = {"w"}, permission = "player.weather", permissionTargeted = "player.weather.others", description = "commands.weather.description")
public final class WeatherCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.weather.usage"));
            CommandHandler.sendMessage(sender, translate(sender, "commands.weather.help_message"));
            return;
        }

        int climateId = 0;
        int weatherId = 0;

        if (args.size() >= 1) {
            try {
                climateId = Integer.parseInt(args.get(0));
            } catch (NumberFormatException ignored) {
                CommandHandler.sendMessage(sender, translate(sender, "commands.weather.invalid"));
                return;
            }
        }

        if (args.size() >= 2) {
            try {
                weatherId = Integer.parseInt(args.get(1));
            } catch (NumberFormatException ignored) {
                CommandHandler.sendMessage(sender, translate(sender, "commands.weather.invalid"));
                return;
            }
        }

        ClimateType climate = ClimateType.getTypeByValue(climateId);

        targetPlayer.getScene().setClimate(climate);
        targetPlayer.getScene().setWeather(weatherId);
        targetPlayer.getScene().broadcastPacket(new PacketSceneAreaWeatherNotify(targetPlayer));
        CommandHandler.sendMessage(sender, translate(sender, "commands.weather.success", Integer.toString(climateId), Integer.toString(weatherId)));
    }
}
