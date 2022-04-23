package emu.grasscutter.server.game;

import java.util.Set;

import org.reflections.Reflections;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.game.GameSession.SessionState;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class GameServerPacketHandler {
	private final Int2ObjectMap<PacketHandler> handlers;
	
	public GameServerPacketHandler(Class<? extends PacketHandler> handlerClass) {
		this.handlers = new Int2ObjectOpenHashMap<>();
		
		this.registerHandlers(handlerClass);
	}
	
	public void registerHandlers(Class<? extends PacketHandler> handlerClass) {
		Reflections reflections = new Reflections("emu.grasscutter.server.packet");
		Set<?> handlerClasses = reflections.getSubTypesOf(handlerClass);
		
		for (Object obj : handlerClasses) {
			Class<?> c = (Class<?>) obj;
			
			try {
				Opcodes opcode = c.getAnnotation(Opcodes.class);
				
				if (opcode == null || opcode.disabled() || opcode.value() <= 0) {
					continue;
				}
				
				PacketHandler packetHandler = (PacketHandler) c.newInstance();
				
				this.handlers.put(opcode.value(), packetHandler);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Debug
		Grasscutter.getLogger().info("Registered " + this.handlers.size() + " " + handlerClass.getSimpleName() + "s");
	}
	
	public void handle(GameSession session, int opcode, byte[] header, byte[] payload) {
		PacketHandler handler = null;
		
		handler = this.handlers.get(opcode);
		
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
				
				// Handle
				handler.handle(session, header, payload);				
			} catch (Exception ex) {
				// TODO Remove this when no more needed
				ex.printStackTrace();
			}
			return; // Packet successfully handled
		}
		
		// Log unhandled packets
		if (Grasscutter.getConfig().getGameServerOptions().LOG_PACKETS) {
			//Grasscutter.getLogger().info("Unhandled packet (" + opcode + "): " + PacketOpcodesUtil.getOpcodeName(opcode));
		}
	}
}
