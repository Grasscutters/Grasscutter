package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarTalentData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.avatar.AvatarStorage;
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
    aliases = {"setconstellation", "constellations", "setcons", "cons"},
    usage = {"[set] <constellation level>", "reset [all]", "toggle <constellation level>"},
    permission = "player.setconstellation",
    permissionTargeted = "player.setconstellation.others")
public final class SetConstCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        String action = "set";
        Integer constLevel = null;

        EntityAvatar entity = targetPlayer.getTeamManager().getCurrentAvatarEntity();
        if (entity == null) return;
        Avatar avatar = entity.getAvatar();
        String avatarName = avatar.getAvatarData().getName();

        if (args.size() == 0 || args.size() > 2) {
            sendUsageMessage(sender);
            return;
        } else {
            try {
                constLevel = Integer.parseInt(args.get(0));
            } catch (NumberFormatException ignored) {
                action = args.remove(0);
            }
        }

        switch (action) {
            case "set", "toggle" -> {
                try {
                    if (constLevel == null) constLevel = Integer.parseInt(args.get(0));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.setConst.level_error");
                    return;
                }
            }
            case "reset" -> {
                try {
                    if (args.get(0).equalsIgnoreCase("all")) {
                        resetAllConstellation(targetPlayer);
                        CommandHandler.sendTranslatedMessage(sender, "commands.setConst.reset_all_success");
                    } else {
                        sendUsageMessage(sender);
                    }
                    return;
                } catch (IndexOutOfBoundsException ignored) {
                    resetConstellation(targetPlayer, avatar, true);
                    CommandHandler.sendTranslatedMessage(sender, "commands.setConst.reset_success", avatarName);
                    return;
                }
            }
            default -> {
                CommandHandler.sendTranslatedMessage(sender, "commands.setConst.action_error");
                return;
            }
        }

        switch (action) {
            case "set" -> {
                if (constLevel < 0 || constLevel > 6) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.setConst.range_error", 0);
                    return;
                }
                setConstellation(targetPlayer, avatar, constLevel, true);
                CommandHandler.sendTranslatedMessage(sender, "commands.setConst.set_success", avatarName, constLevel);
            }
            case "toggle" -> {
                if (constLevel < 1 || constLevel > 6) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.setConst.range_error", 1);
                    return;
                }
                toggleConstellation(targetPlayer, avatar, constLevel);
                CommandHandler.sendTranslatedMessage(sender, "commands.setConst.set_success", constLevel, avatarName);
            }
        }
    }

    private boolean setConstellation(Player player, Avatar avatar, int constLevel, boolean reload) {
        Set<Integer> talentIdList = avatar.getTalentIdList();

        int previousHighestConstellationUnlocked = talentIdList.size() > 0 ? Collections.max(talentIdList) % 10 : 0;
        boolean reload_required = reload && constLevel < previousHighestConstellationUnlocked;

        talentIdList.clear();

        for(int talent = 1; talent <= constLevel; talent++) {
            unlockConstellation(player, avatar, talent);
        }

        // force player to reload scene when necessary
        if (reload_required) reloadScene(player);

        // ensure that all changes are visible to the player
        avatar.recalcConstellations();
        avatar.recalcStats(true);
        avatar.save();

        return reload_required;
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

    private boolean resetConstellation(Player player, Avatar avatar, boolean reload) {
        return setConstellation(player, avatar, 0, reload);
    }

    private void resetAllConstellation(Player player) {
        boolean reload_required = false;
        AvatarStorage avatars = player.getAvatars();

        for (Avatar avatar: avatars) {
            reload_required |= resetConstellation(player, avatar, false);
        }
        if (reload_required) reloadScene(player);
    }

    private void unlockConstellation(Player player, Avatar avatar, int talent) {
        Grasscutter.getGameServer().getInventorySystem().unlockAvatarConstellation(player, avatar.getGuid(), talent, false);
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
