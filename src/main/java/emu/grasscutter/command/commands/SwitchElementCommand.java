package emu.grasscutter.command.commands;

import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.PropChangeReasonOuterClass;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;

import java.util.ArrayList;
import java.util.List;

@Command(label = "switchelement", usage = "switchelement [White/Anemo/Geo/Electro]", aliases = {"se"}, permission = "player.switchElement", threading = true, description = "commands.switchElement.description")
public class SwitchElementCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() != 1) {
            CommandHandler.sendTranslatedMessage(sender, "commands.switchElement.usage");
            return;
        }
        if (sender == null) {
            Grasscutter.getLogger().info("SwitchElement command couldn't be called by console.");
            return;
        }

        String element = args.get(0);

        Integer elementId = switch (element.toLowerCase()) {
            case "white" -> 501;
            case "anemo" -> 504;
            case "geo" -> 506;
            case "electro" -> 507;
            default -> null;
        };

        if (elementId == null) {
            CommandHandler.sendTranslatedMessage(sender, "commands.switchElement.invalidElement");
            return;
        }

        List<Integer> id = new ArrayList<>();
        id.add(GameConstants.MAIN_CHARACTER_MALE);
        id.add(GameConstants.MAIN_CHARACTER_FEMALE);


        try {
            for (int i : id) {
                Avatar avatar
                        = sender.getAvatars().getAvatarById(i);
                if (avatar != null) {
                    avatar.setSkillDepotData(GameData.getAvatarSkillDepotDataMap().get(elementId));
                    avatar.setCurrentEnergy(1000);
                    avatar.save();
                }
            }
            CommandHandler.sendTranslatedMessage(sender, "commands.switchElement.success", element);
            int scene = sender.getSceneId();
            sender.getWorld().transferPlayerToScene(sender, 1, sender.getPos());
            sender.getWorld().transferPlayerToScene(sender, scene, sender.getPos());
            sender.getScene().broadcastPacket(new PacketSceneEntityAppearNotify(sender));
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }


    }
}
