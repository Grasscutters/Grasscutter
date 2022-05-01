package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.McoinExchangeHcoinRspOuterClass;

public class PacketMcoinExchangeHcoinRsp extends BasePacket {

    public PacketMcoinExchangeHcoinRsp(int mcoin, int hcoin) {
        super(PacketOpcodes.McoinExchangeHcoinRsp);

        McoinExchangeHcoinRspOuterClass.McoinExchangeHcoinRsp mcoinExchangeHcoinRsp = McoinExchangeHcoinRspOuterClass.McoinExchangeHcoinRsp.newBuilder()
                .setMCoinNum(mcoin)
                .setHCoinNum(hcoin).build();

        this.setData(mcoinExchangeHcoinRsp);
    }
}
