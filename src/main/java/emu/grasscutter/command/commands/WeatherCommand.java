package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ClimateType;
import emu.grasscutter.server.packet.send.PacketSceneAreaWeatherNotify;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "weather", usage = "weather <weatherId> [climateId]",
        description = "Changes the weather.", aliases = {"w"}, permission = "player.weather")
public final class WeatherCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate("commands.execution.need_target"));
            return;
        }

        int weatherId = 0;
        int climateId = 1;
        switch (args.size()) {
            case 2:
                try {
                    climateId = Integer.parseInt(args.get(1));
                } catch (NumberFormatException ignored) {
                        CommandHandler.sendMessage(sender, translate("commands.weather.invalid_id"));
                }
            case 1:
                try {
                    weatherId = Integer.parseInt(args.get(0));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, translate("commands.weather.invalid_id"));
                }
                break;
            default:
                CommandHandler.sendMessage(sender, translate("commands.weather.usage"));
                return;
        }

        ClimateType climate = ClimateType.getTypeByValue(climateId);

        targetPlayer.getScene().setWeather(weatherId);
        targetPlayer.getScene().setClimate(climate);
        targetPlayer.getScene().broadcastPacket(new PacketSceneAreaWeatherNotify(targetPlayer));
        CommandHandler.sendMessage(sender, translate("commands.weather.success", Integer.toString(weatherId), Integer.toString(climateId)));
    }
}
