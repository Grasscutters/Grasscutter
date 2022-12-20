package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketSceneAreaUnlockNotify;
import emu.grasscutter.server.packet.send.PacketScenePointUnlockNotify;

import java.util.List;

@Command(label = "unlockmap", aliases = {"um"}, permission = "player.unlockmap", permissionTargeted = "player.unlockmap.others")
public final class UnlockMapCommand implements CommandHandler {
    
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (unlockMap(targetPlayer)) {
            if (targetPlayer == sender) {
                CommandHandler.sendTranslatedMessage(sender, "commands.generic.set_to", "UnlockMap", "on");
            } else {
                String uidStr = targetPlayer.getAccount().getId();
                CommandHandler.sendTranslatedMessage(sender, "commands.generic.set_for_to", "UnlockMap", uidStr, "on");
            }
        }
    }
    
    // List of map areas. Unfortunately, there is no readily available source for them in excels or bins.
    final static private List<Integer> sceneAreas = List.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,17,18,19,20,21,22,23,24,25,26,27,28,29,100,101,102,103,200,210,300,400,401,402,403);
    private boolean unlockMap(Player targetPlayer) {
        // Unlock.
        GameData.getScenePointsPerScene().forEach((sceneId, scenePoints) -> {
            // Unlock trans points.
            targetPlayer.getUnlockedScenePoints(sceneId).addAll(scenePoints);

            // Unlock map areas.
            targetPlayer.getUnlockedSceneAreas(sceneId).addAll(sceneAreas);
        });

        // Send notify.
        int playerScene = targetPlayer.getSceneId();
        targetPlayer.sendPacket(new PacketScenePointUnlockNotify(playerScene, targetPlayer.getUnlockedScenePoints(playerScene)));
        targetPlayer.sendPacket(new PacketSceneAreaUnlockNotify(playerScene, targetPlayer.getUnlockedSceneAreas(playerScene)));
        return true;
    }
}