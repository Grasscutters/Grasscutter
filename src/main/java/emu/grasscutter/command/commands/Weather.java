package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.props.ClimateType;
import emu.grasscutter.server.packet.send.PacketSceneAreaWeatherNotify;

import java.util.List;

@Command(label = "weather", usage = "weather <weatherId>",
        description = "Changes the weather.", aliases = {"w"}, permission = "player.weather")
public class Weather implements CommandHandler {

    @Override
    public void onCommand(GenshinPlayer sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, "Usage: weather <weatherId>");
            return;
        }

        try {
            int weatherId = Integer.parseInt(args.get(0));

            ClimateType climate = ClimateType.getTypeByValue(weatherId);

            sender.getScene().setClimate(climate);
            sender.getScene().broadcastPacket(new PacketSceneAreaWeatherNotify(sender));
            CommandHandler.sendMessage(sender, "Changed weather to " + weatherId);
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, "Invalid weather ID.");
        }
    }
}
