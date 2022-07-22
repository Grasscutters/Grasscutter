package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.OpenState;
import emu.grasscutter.server.packet.send.PacketOpenStateChangeNotify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "unlockall", usage = {""}, permission = "player.unlockall", permissionTargeted = "player.unlockall.others")
public final class UnlockAllCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        Map<Integer, Integer> changed = new HashMap<>();
        
        for (OpenState state : OpenState.values()) {
            if (state == OpenState.OPEN_STATE_NONE || state == OpenState.OPEN_STATE_LIMIT_REGION_GLOBAL) continue;
            
            if (targetPlayer.getOpenStateManager().getOpenStateMap().getOrDefault(state.getValue(), 0) == 0) {
                targetPlayer.getOpenStateManager().getOpenStateMap().put(state.getValue(), 1); 
                changed.put(state.getValue(), 1);
            }
        }
        
        targetPlayer.sendPacket(new PacketOpenStateChangeNotify(changed));
        
        CommandHandler.sendMessage(sender, translate(sender, "commands.unlockall.success", targetPlayer.getNickname()));
    }
}
