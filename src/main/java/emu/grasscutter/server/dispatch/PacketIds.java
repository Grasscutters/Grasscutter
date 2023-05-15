package emu.grasscutter.server.dispatch;

import emu.grasscutter.net.packet.PacketOpcodes;

/* Packet IDs for the dispatch server. */
public interface PacketIds {
    int LoginNotify = 1;
    int TokenValidateReq = 2;
    int TokenValidateRsp = 3;
    int GachaHistoryReq = 4;
    int GachaHistoryRsp = 5;
    int GmTalkReq = PacketOpcodes.GmTalkReq;
    int GmTalkRsp = PacketOpcodes.GmTalkRsp;
}
