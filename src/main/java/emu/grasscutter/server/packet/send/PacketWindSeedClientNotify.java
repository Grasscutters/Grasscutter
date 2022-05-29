package emu.grasscutter.server.packet.send;

import com.google.protobuf.ByteString;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WindSeedClientNotifyOuterClass.*;
import emu.grasscutter.utils.FileUtils;

import java.util.Base64;

import static emu.grasscutter.Configuration.SCRIPT;


public class PacketWindSeedClientNotify extends BasePacket {

    public PacketWindSeedClientNotify(WindSeedClientNotify proto) {
        super(PacketOpcodes.WindSeedClientNotify);

        this.setData(proto);
    }
}
