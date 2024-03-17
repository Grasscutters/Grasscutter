package emu.grasscutter.command.arguments.player;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import emu.grasscutter.command.source.CommandSource;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.text.Text;

public class PlayerSelector {
    private static final SimpleCommandExceptionType PLAYER_NOT_EXIST = new SimpleCommandExceptionType(Text.translatable("commands.execution.player_exist_error"));
    private final PlayerArgument.TargetType targetType;
    private final int uid;
    private final boolean senderOnly;
    private final boolean selectorAbsent;

    public PlayerSelector(PlayerArgument.TargetType targetType, int uid, boolean senderOnly, boolean selectorAbsent) {
        this.targetType = targetType;
        this.uid = uid;
        this.senderOnly = senderOnly;
        this.selectorAbsent = selectorAbsent;
    }

    public Player getPlayer(CommandSource source) throws CommandSyntaxException {
        Player out;

        if (this.selectorAbsent) {
            out = source.resolveTargetOrException();
        } else if (this.senderOnly) {
            out = source.getPlayerOrException();
        } else {
            out = source.server().getPlayerByUid(this.uid, true);
        }

        if (out == null) {
            throw PLAYER_NOT_EXIST.create();
        }

        if (!source.hasPermissionTargeted(out)) {
            throw CommandSource.PERMISSION_ERROR.create();
        }

        return this.targetType.validate(out);
    }
}
