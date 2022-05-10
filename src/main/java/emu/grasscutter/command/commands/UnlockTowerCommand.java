package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.tower.TowerLevelRecord;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "unlocktower", usage = "unlocktower", aliases = {"ut"},
        description = "commands.unlocktower.description", permission = "player.tower")
public class UnlockTowerCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        unlockFloor(sender, sender.getServer().getTowerScheduleManager()
                .getCurrentTowerScheduleData().getEntranceFloorId());

        unlockFloor(sender, sender.getServer().getTowerScheduleManager()
                .getScheduleFloors());

        CommandHandler.sendMessage(sender, translate(sender, "commands.unlocktower.success"));
    }

    public void unlockFloor(Player player, List<Integer> floors){
        floors.stream()
                .filter(id -> !player.getTowerManager().getRecordMap().containsKey(id))
                .forEach(id -> player.getTowerManager().getRecordMap().put(id, new TowerLevelRecord(id)));
    }
}
