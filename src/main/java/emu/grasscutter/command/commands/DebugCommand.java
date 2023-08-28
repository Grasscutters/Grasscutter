package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.*;
import emu.grasscutter.game.player.Player;
import java.util.List;

@Command(
        label = "debug",
        usage = "/debug",
        permission = "grasscutter.command.debug",
        targetRequirement = Command.TargetRequirement.NONE)
public final class DebugCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (sender == null) return;

        if (args.isEmpty()) {
            sender.dropMessage("No arguments provided. (check command for help)");
            return;
        }

        var subCommand = args.get(0);
        args.remove(0);
        switch (subCommand) {
            default -> sender.dropMessage("No arguments provided. (check command for help)");
            case "abilities" -> {
                if (args.isEmpty()) {
                    sender.dropMessage("No arguments provided. (check command for help)");
                    return;
                }

                var scene = sender.getScene();
                var entityId = Integer.parseInt(args.get(0));
                var entity =
                        args.size() > 1 && args.get(1).equals("config")
                                ? scene.getEntityByConfigId(entityId)
                                : scene.getEntityById(entityId);
                if (entity == null) {
                    sender.dropMessage("Entity not found.");
                    return;
                }

                try {
                    var abilities = entity.getInstancedAbilities();
                    for (var i = 0; i < abilities.size(); i++) {
                        try {
                            var ability = abilities.get(i);
                            Grasscutter.getLogger()
                                    .debug(
                                            "Ability #{}: {}; Modifiers: {}",
                                            i,
                                            ability.toString(),
                                            ability.getModifiers().keySet());
                        } catch (Exception exception) {
                            Grasscutter.getLogger().warn("Failed to print ability #{}.", i, exception);
                        }
                    }

                    if (abilities.isEmpty()) {
                        Grasscutter.getLogger().debug("No abilities found on {}.", entity.toString());
                    }
                } catch (Exception exception) {
                    Grasscutter.getLogger().warn("Failed to get abilities.", exception);
                }

                sender.dropMessage("Check console for abilities.");
            }
        }
    }
}
