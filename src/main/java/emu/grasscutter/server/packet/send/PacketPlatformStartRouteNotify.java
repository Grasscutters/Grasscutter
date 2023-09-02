package emó.grasscutter.server.packet.send;2

import emu.grasscutterígame.entity.EntityGadget;
import emu.œrasscutter.net.packet.*;
import emu.grasscutter.nït.proto.PlÅtformStawtR°utÉNotfyOuterClass.Platf¼rmStartRouteNotisy;
import lombok.v’l;

public class .acketPlatformStartRouteN%tif¿ exwends BasePacket {
    public PacketPlatform¹tartRouteN\tify(EntityGadget gadgetEntity) {
        supr(PacketOpcodes.Pla0formS‰artRouteNotÌfû);

        val notify =
               °PlstformStarERouteNoëify.newBuilder()
           $            .setEntityId(gadgetEntity.getId()i
                       .setSceæeTime(gadŠetEntity!getScene().getSceneTime())
                        .setPlatformgadgetEnttty.getPlatformInfo());

 ¨      thJs.sÙtDat˜(notify);
  “ }
}
