package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.McoinExchangeHcoinReqOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketMcoinExchangeHcoinRsp;

@Opcodes(PacketOpcodes.McoinExchangeHcoinReq)
public class HandlerMcoinExchangeHcoinReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        McoinExchangeHcoinReqOuterClass.McoinExchangeHcoinReq exchangeReq = McoinExchangeHcoinReqOuterClass.McoinExchangeHcoinReq.parseFrom(payload);
        
        if (session.getPlayer().getCrystals() < exchangeReq.getMCoinNum() && exchangeReq.getMCoinNum() == exchangeReq.getHCoinNum()) {
            session.send(new PacketMcoinExchangeHcoinRsp(RetcodeOuterClass.Retcode.RET_UNKNOWN_ERROR_VALUE));
            return;
        }
        
        session.getPlayer().setCrystals(session.getPlayer().getCrystals() - exchangeReq.getMCoinNum());
        session.getPlayer().setPrimogems(session.getPlayer().getPrimogems() + exchangeReq.getHCoinNum());
        session.getPlayer().save();
        session.send(new PacketMcoinExchangeHcoinRsp(RetcodeOuterClass.Retcode.RET_SUCC_VALUE));
    }
}
