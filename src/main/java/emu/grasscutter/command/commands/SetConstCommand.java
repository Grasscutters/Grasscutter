package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarTalentData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.game.world.World;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.*;

@Command(
    label = "setConst",
    aliases = {"setconstellation", "setcons", "constellations", "cons"},
    usage = {"<constellation level>", "[(set|toggle)] <constellation level>"},
    permission = "player.setconstellation",
    permissionTargeted = "player.setconstellation.others")
public final class SetConstCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        String action = "set";
        int constLevel;

        EntityAvatar entity = targetPlayer.getTeamManager().getCurrentAvatarEntity();
        if (entity == null) return;
        Avatar avatar = entity.getAvatar();
        String avatarName = avatar.getAvatarData().getName();

        switch (args.size()) {
            case 2:
                action = args.remove(0).toLowerCase(); // fall-through
            case 1:
                try {
                    constLevel = Integer.parseInt(args.get(0));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.setConst.level_error");
                    return;
                }
                break;
            default:
                sendUsageMessage(sender);
                return;
        }

        switch (action) {
            case "set" -> {
                if (constLevel < 0 || constLevel > 6) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.setConst.range_error", 0);
                    return;
                }
                this.setConstellation(targetPlayer, avatar, constLevel);
                CommandHandler.sendTranslatedMessage(sender, "commands.setConst.set_success", avatarName, constLevel);
            }
            case "toggle" -> {
                if (constLevel < 1 || constLevel > 6) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.setConst.range_error", 1);
                    return;
                }
                this.toggleConstellation(targetPlayer, avatar, constLevel);
                CommandHandler.sendTranslatedMessage(sender, "commands.setConst.toggle_success", constLevel, avatarName);

            }
            default -> CommandHandler.sendTranslatedMessage(sender, "commands.setConst.action_error");
        }
    }

    private void setConstellation(Player player, Avatar avatar, int constLevel) {
        Set<Integer> talentIdList = avatar.getTalentIdList();

        int previousHighestConstellationUnlocked = talentIdList.size() > 0 ? Collections.max(talentIdList) % 10 : 0;

        talentIdList.clear();

        for(int talent = 1; talent <= constLevel; talent++) {
            unlockConstellation(player, avatar, talent);
        }

        // force player to reload scene when necessary
        if (constLevel < previousHighestConstellationUnlocked) reloadScene(player);

        // ensure that all changes are visible to the player
        avatar.recalcConstellations();
        avatar.recalcStats(true);
        avatar.save();
    }

    private void toggleConstellation(Player player, Avatar avatar, int constLevel) {
        Set<Integer> talentIdList = avatar.getTalentIdList();

        List<Integer> talentIds = avatar.getSkillDepot().getTalents();
        int talentId = talentIds.get(constLevel-1);

        boolean wasConstellationUnlocked = talentIdList.remove(talentId);
        if(!wasConstellationUnlocked) unlockConstellation(player, avatar, constLevel);
        else reloadScene(player);

        avatar.recalcConstellations();
        avatar.recalcStats(true);
        avatar.save();
    }

    private void unlockConstellation(Player player, Avatar avatar, int talent) {
        Grasscutter.getGameServer().getInventorySystem().unlockAvatarConstellation(player, avatar.getGuid(), talent);
    }

    private void reloadScene(Player player) {
        World world = player.getWorld();
        Scene scene = player.getScene();
        Position pos = player.getPosition();

        world.transferPlayerToScene(player, 1, pos);
        world.transferPlayerToScene(player, scene.getId(), pos);
        scene.broadcastPacket(new PacketSceneEntityAppearNotify(player));
    }
}
