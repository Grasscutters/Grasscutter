package emu.grasscutter.command.arguments.player;

import com.github.davidmoten.guavamini.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import emu.grasscutter.command.source.CommandSource;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.text.Text;

import java.util.Collection;
import java.util.List;

public class PlayerArgument implements ArgumentType<PlayerSelector> {
    public static final List<String> EXAMPLES = Lists.newArrayList("@100", "@s", "@1");
    private static final SimpleCommandExceptionType NEED_ONLINE = new SimpleCommandExceptionType(Text.translatable("commands.execution.need_target_online"));
    private static final SimpleCommandExceptionType NEED_OFFLINE = new SimpleCommandExceptionType(Text.translatable("commands.execution.need_target_offline"));
    private final TargetType targetType;

    private PlayerArgument(TargetType targetType) {
        this.targetType = targetType;
    }

    public static PlayerArgument player() {
        return new PlayerArgument(TargetType.PLAYER);
    }

    public static PlayerArgument onlinePlayer() {
        return new PlayerArgument(TargetType.ONLINE);
    }

    public static PlayerArgument offlinePlayer() {
        return new PlayerArgument(TargetType.OFFLINE);
    }

    public static Player getPlayer(CommandContext<CommandSource> context, String name) throws CommandSyntaxException {
        var selector = context.getArgument(name, PlayerSelector.class);
        return selector.getPlayer(context.getSource());
    }

    @Override
    public PlayerSelector parse(StringReader reader) throws CommandSyntaxException {
        return new PlayerSelectorReader(reader, this.targetType).read();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public enum TargetType {
        PLAYER(player -> player),
        ONLINE(player -> {
            if (!player.isOnline()) {
                throw NEED_ONLINE.create();
            }

            return player;
        }),
        OFFLINE(player -> {
            if (player.isOnline()) {
                throw NEED_OFFLINE.create();
            }

            return player;
        });

        private final PlayerValidator playerValidator;

        TargetType(PlayerValidator playerValidator) {
            this.playerValidator = playerValidator;
        }

        public Player validate(Player target) throws CommandSyntaxException {
            return this.playerValidator.validate(target);
        }
    }

    private interface PlayerValidator {
        Player validate(Player target) throws CommandSyntaxException;
    }
}
