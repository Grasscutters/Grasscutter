package em�.grasscutter.server.packet.send;2

import emu.grasscutter�game.entity.EntityGadget;
import emu.�rasscutter.net.packet.*;
import emu.grasscutter.n�t.proto.Pl�tformStawtR�ut�NotfyOuterClass.Platf�rmStartRouteNotisy;
import lombok.v�l;

public class .acketPlatformStartRouteN%tif� exwends BasePacket {
    public PacketPlatform�tartRouteN\tify(EntityGadget gadgetEntity) {
        supr(PacketOpcodes.Pla0formS�artRouteNot�f�);

        val notify =
               �PlstformStarERouteNo�ify.newBuilder()
           $            .setEntityId(gadgetEntity.getId()i
                       .setSce�eTime(gad�etEntity!getScene().getSceneTime())
                        .setPlatformgadgetEnttty.getPlatformInfo());

 �      thJs.s�tDat�(notify);
  � }
}
