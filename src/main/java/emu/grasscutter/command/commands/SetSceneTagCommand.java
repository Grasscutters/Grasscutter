package emu.grasscutter.command.commands;

import emu.grasscutter.command.*;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.scene.SceneTagData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.*;
import lombok.val;

@Command(
        label = "setSceneTag",
        aliases = {"tag"},
        usage = {"<add|remove|unlockall> <sceneTagId>"},
        permission = "player.setscenetag",
        permissionTargeted = "player.setscenetag.others")
public final class SetSceneTagCommand implements CommandHandler {
    private final Int2ObjectMap<SceneTagData> sceneTagData = GameData.getSceneTagDataMap();

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() == 0) {
            sendUsageMessage(sender);
            return;
        }

        val actionStr = args.get(0).toLowerCase();
        var value = -1;

        if (args.size() > 1) {
            try {
                value = Integer.parseInt(args.get(1));
            } catch (NumberFormatException ignored) {
                CommandHandler.sendTranslatedMessage(sender, "commands.execution.argument_error");
                return;
            }
        } else {
            if (actionStr.equals("unlockall")) {
                unlockAllSceneTags(targetPlayer);
                return;
            } else {
                CommandHandler.sendTranslatedMessage(sender, "commands.execution.argument_error");
                return;
            }
        }

        val userVal = value;

        var sceneData =
                sceneTagData.values().stream().filter(sceneTag -> sceneTag.getId() == userVal).findFirst();
        if (sceneData == null) {
            CommandHandler.sendTranslatedMessage(sender, "commands.generic.invalid.id");
            return;
        }
        int scene = sceneData.get().getSceneId();

        switch (actionStr) {
            case "add", "set" -> addSceneTag(targetPlayer, scene, value);
            case "remove", "del" -> removeSceneTag(targetPlayer, scene, value);
            default -> CommandHandler.sendTranslatedMessage(sender, "commands.execution.argument_error");
        }

        CommandHandler.sendTranslatedMessage(sender, "commands.generic.set_to", value, actionStr);
    }

    private void addSceneTag(Player targetPlayer, int scene, int value) {
        targetPlayer.getProgressManager().addSceneTag(scene, value);
    }

    private void removeSceneTag(Player targetPlayer, int scene, int value) {
        targetPlayer.getProgressManager().delSceneTag(scene, value);
    }

    private void unlockAllSceneTags(Player targetPlayer) {
        var allData = sceneTagData.values();

        // Add all SceneTags
        allData.stream()
                .toList()
                .forEach(
                        sceneTag -> {
                            if (targetPlayer.getSceneTags().get(sceneTag.getSceneId()) == null) {
                                targetPlayer.getSceneTags().put(sceneTag.getSceneId(), new HashSet<>());
                            }
                            targetPlayer.getSceneTags().get(sceneTag.getSceneId()).add(sceneTag.getId());
                        });

        // Remove default SceneTags, as most are "before" or "locked" states
        allData.stream()
                .filter(sceneTag -> sceneTag.isDefaultValid())
                // Only remove for big world as some other scenes only have defaults
                .filter(sceneTag -> sceneTag.getSceneId() == 3)
                .forEach(
                        sceneTag -> {
                            targetPlayer.getSceneTags().get(sceneTag.getSceneId()).remove(sceneTag.getId());
                        });

        this.setSceneTags(targetPlayer);
    }

    private void setSceneTags(Player targetPlayer) {
        targetPlayer.sendPacket(new PacketPlayerWorldSceneInfoListNotify(targetPlayer));
    }
}
