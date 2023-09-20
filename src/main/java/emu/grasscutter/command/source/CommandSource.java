package emu.grasscutter.command.source;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.utils.text.Text;
import emu.grasscutter.utils.text.TranslatableContent;
import emu.grasscutter.utils.text.UnityTextFormatting;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

import javax.annotation.Nullable;

public record CommandSource(CommandOutput output, GameServer server, @Nullable Player player, @Nullable Player target, String playerId, Command command, CommandHandler handler, Object2IntMap<String> targetPlayerIds) {
    public static final SimpleCommandExceptionType PERMISSION_ERROR = new SimpleCommandExceptionType(Text.translatable("commands.generic.permission_error"));
    public static final SimpleCommandExceptionType ERROR_NEED_PLAYER = new SimpleCommandExceptionType(Text.translatable("commands.execution.need_target"));

    public boolean hasPermissionTargeted(@Nullable Player target) {
        return this.player() == target || this.command.permissionTargeted().isEmpty() || this.hasPermission(this.command.permissionTargeted());
    }

    public boolean hasPermission() {
        return this.hasPermission(this.command.permission());
    }

    public boolean hasPermission(String permission) {
        return Grasscutter.getPermissionHandler().hasPermission(this.player(), permission);
    }

    public Player getPlayerOrException() throws CommandSyntaxException {
        var player = this.player();
        if (player == null) { // if source is the server.
            throw ERROR_NEED_PLAYER.create();
        }

        return player;
    }

    public Player resolveTargetOrException() throws CommandSyntaxException {
        Player result;

        if (this.target != null) {
            result = this.target;
        } else if (this.targetPlayerIds.containsKey(this.playerId)) {
            result = this.server.getPlayerByUid(this.targetPlayerIds.getInt(this.playerId), true);
        } else {
            result = this.player;
        }

        if (result == null) {
            throw ERROR_NEED_PLAYER.create();
        }

        return result;
    }

    public void sendMessage(Text text) {
        this.output.sendMessage(text);
    }

    public void sendFailure(Text text) {
        this.sendMessage(Text.empty().append(text).withFormatting(UnityTextFormatting.RED));
    }

    public void sendMessage(Message message) {
        if (message instanceof TranslatableContent translatableContent) {
            this.sendMessage(translatableContent.translate(this));
        } else {
            this.sendMessage(Text.literal(message.getString()));
        }
    }

    public void sendFailure(Message message) {
        if (message instanceof TranslatableContent translatableContent) {
            this.sendFailure(translatableContent.translate(this));
        } else {
            this.sendFailure(Text.literal(message.getString()));
        }
    }
}
