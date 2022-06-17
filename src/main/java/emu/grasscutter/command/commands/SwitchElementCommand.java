package emu.grasscutter.command.commands;

import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.PropChangeReasonOuterClass;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;

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

        for (AvatarData avatarData : GameData.getAvatarDataMap().values()) {
            //Exclude test avatar
            // Handle skill depot for traveller.
            try {
                Avatar avatar = sender.getAvatars().getAvatarById(avatarData.getId());
                if (avatar.getAvatarId() == GameConstants.MAIN_CHARACTER_MALE) {
                    sender.getAvatars().getAvatarById
                            (avatarData.getId()).setSkillDepotData(GameData.getAvatarSkillDepotDataMap().get(elementId));
                    sender.getAvatars().getAvatarById
                            (avatarData.getId()).getAsEntity().addEnergy(1000,
                            PropChangeReasonOuterClass.PropChangeReason.PROP_CHANGE_REASON_GM,true);
                    sender.getAvatars().getAvatarById
                            (avatarData.getId()).save();
                    CommandHandler.sendTranslatedMessage(sender, "commands.switchElement.success" , element);
                } else if (avatar.getAvatarId() == GameConstants.MAIN_CHARACTER_FEMALE) {
                    sender.getAvatars().getAvatarById
                            (avatarData.getId()).setSkillDepotData(GameData.getAvatarSkillDepotDataMap().get(elementId + 200));
                    sender.getAvatars().getAvatarById
                            (avatarData.getId()).getAsEntity().addEnergy(1000,
                            PropChangeReasonOuterClass.PropChangeReason.PROP_CHANGE_REASON_GM,true);
                    sender.getAvatars().getAvatarById
                            (avatarData.getId()).save();
                    CommandHandler.sendTranslatedMessage(sender, "commands.switchElement.success" , element);
                }
            } catch (Exception ignored) {}
        }

        int scene = sender.getSceneId();
        sender.getWorld().transferPlayerToScene(sender, 1, sender.getPos());
        sender.getWorld().transferPlayerToScene(sender, scene, sender.getPos());
        sender.getScene().broadcastPacket(new PacketSceneEntityAppearNotify(sender));
    }
}
