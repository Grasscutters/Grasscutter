package emu.grasscutter.server.game;

import static emu.grasscutter.config.Configuration.GAME_INFO;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerDebugMode;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.event.game.ReceivePacketEvent;
import emu.grasscutter.server.game.GameSession.SessionState;
import it.unimi.dsi.fastutil.ints.*;

public final class GameServerPacketHandler {
    private final Int2ObjectMap<PacketHandler> handlers;

    public GameServerPacketHandler(Class<? extends PacketHandler> handlerClass) {
        this.handlers = new Int2ObjectOpenHashMap<>();

        this.registerHandlers(handlerClass);
    }

    public void registerPacketHandler(Class<? extends PacketHandler> handlerClass) {
        try {
            var opcode = handlerClass.getAnnotation(Opcodes.class);
            if (opcode == null || opcode.disabled() || opcode.value() <= 0) {
                return;
            }

            var packetHandler = handlerClass.getDeclaredConstructor().newInstance();
            this.handlers.put(opcode.value(), packetHandler);
        } catch (Exception e) {
            Grasscutter.getLogger()
                    .warn("Unable to register handler {}.", handlerClass.getSimpleName(), e);
        }
    }

    public void registerHandlers(Class<? extends PacketHandler> handlerClass) {
        var handlerClasses = Grasscutter.reflector.getSubTypesOf(handlerClass);
        for (var obj : handlerClasses) {
            this.registerPacketHandler(obj);
        }

        // Debug
        Grasscutter.getLogger()
                .debug("Registered " + this.handlers.size() + " " + handlerClass.getSimpleName() + "s");
    }

    public void handle(GameSession session, int opcode, byte[] header, byte[] payload) {
        PacketHandler handler = this.handlers.get(opcode);

        if (handler != null) {
            try {
                // Make sure session is ready for packets
                SessionState state = session.getState();

                if (opcode == PacketOpcodes.PingReq) {
                    // Always continue if packet is ping request
                } else if (opcode == PacketOpcodes.GetPlayerTokenReq) {
                    if (state != SessionState.WAITING_FOR_TOKEN) {
                        return;
                    }
                } else if (state == SessionState.ACCOUNT_BANNED) {
                    session.close();
                    return;
                } else if (opcode == PacketOpcodes.PlayerLoginReq) {
                    if (state != SessionState.WAITING_FOR_LOGIN) {
                        return;
                    }
                } else if (opcode == PacketOpcodes.SetPlayerBornDataReq) {
                    if (state != SessionState.PICKING_CHARACTER) {
                        return;
                    }
                } else {
                    if (state != SessionState.ACTIVE) {
                        return;
                    }
                }

                // Invoke event.
                ReceivePacketEvent event = new ReceivePacketEvent(session, opcode, payload);
                event.call();
                if (!event.isCanceled()) // If event is not canceled, continue.
                handler.handle(session, header, event.getPacketData());
            } catch (Exception ex) {
                // TODO Remove this when no more needed
                ex.printStackTrace();
            }
            return; // Packet successfully handled
        }

        // Log unhandled packets
        if (GAME_INFO.logPackets == ServerDebugMode.MISSING
                || GAME_INFO.logPackets == ServerDebugMode.ALL) {
            Grasscutter.getLogger()
                    .info(
                            "Unhandled packet ("
                                    + opcode
                                    + "): "
                                    + emu.grasscutter.net.packet.PacketOpcodesUtils.getOpcodeName(opcode));
        }
    }
}
