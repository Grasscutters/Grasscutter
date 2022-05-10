package emu.grasscutter.command.source.impl;

import emu.grasscutter.command.handler.ContextFields;
import emu.grasscutter.command.handler.HandlerContext;
import emu.grasscutter.command.parser.annotation.Origin;
import emu.grasscutter.command.source.BaseCommandSource;
import emu.grasscutter.game.player.Player;

import java.util.concurrent.ConcurrentHashMap;

import static emu.grasscutter.command.parser.commands.TargetCommand.PERSISTED_TARGET_KEY;

public class ClientChatCommandSource extends BaseCommandSource {
    private final Player player;
    private boolean disposed = false;

    private ClientChatCommandSource(Player player) {
        this.player = player;
    }
    @Override
    public String[] getPermissions() {
        return player.getAccount().getPermissions().toArray(new String[] {});
    }

    @Override
    public Origin getOrigin() {
        return Origin.CLIENT;
    }

    @Override
    public synchronized void onMessage(String message) {
        if (!disposed) {
            player.dropMessage(message);
        }
    }

    @Override
    public synchronized void onError(String error) {
        if (!disposed) {
            player.dropMessage("Error: %s".formatted(error));
        }
    }

    @Override
    public HandlerContext buildContext(HandlerContext.HandlerContextBuilder contextBuilder) {
        HandlerContext context = contextBuilder
                .errorConsumer(e -> onError(e.toString()))
                .resultConsumer(r -> onMessage(r != null ? "Handler result: %s.".formatted(r.toString()) : "Handler completed."))
                .messageConsumer(m -> onMessage(m.toString()))
                .build();
        Integer targetUid = context.getOptional(
                ContextFields.TARGET_UID,
                getOrNull(PERSISTED_TARGET_KEY, Integer.class)
        );
        if (targetUid != null) {
            context = context.toBuilder()
                    .content(ContextFields.TARGET_UID, targetUid)
                    .content(ContextFields.SENDER_UID, player.getUid())
                    .build();
        }
        return context;
    }

    public static final ConcurrentHashMap<Integer, ClientChatCommandSource> sourceMap = new ConcurrentHashMap<>();
    public static ClientChatCommandSource getCommandSourceByPlayer(Player player) {
        if (!sourceMap.containsKey(player.getUid())) {
            ClientChatCommandSource source = new ClientChatCommandSource(player);
            sourceMap.put(player.getUid(), source);
            return source;
        }
        return sourceMap.get(player.getUid());
    }

    public static void disposeCommandSource(Player player) {
        ClientChatCommandSource source = sourceMap.remove(player.getUid());
        if (source != null) {
            source.disposed = true;
        }
    }
}
