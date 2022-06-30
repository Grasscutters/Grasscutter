package emu.grasscutter.game.managers.laylines;

import java.util.ArrayList;
import java.util.Iterator;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.gadget.GadgetWorktop;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
import emu.grasscutter.utils.Position;

public class LayLinesManager {
    private static final int LAY_LINES_BLUE_GADGET_ID = 70360057;
    private static final int LAY_LINES_GOLDEN_GADGET_ID = 70360056;
    Player player;
    ArrayList<ActiveLayLines> activeLayLines = new ArrayList<>();

    public void createLayLineGadgetEntity(){
        createLayLineGadgetEntity(player.getPos(), player.getRotation());
    }
    public synchronized void onTick(){
        Iterator<ActiveLayLines> it = activeLayLines.iterator();
        while(it.hasNext()) {
            ActiveLayLines activeLayLines = it.next();
            activeLayLines.onTick();
            if(activeLayLines.getPass()){
                it.remove();
            }
        }
    }
    public void createLayLineGadgetEntity(Position pos, Position rot){
        Scene scene = player.getScene();
        EntityGadget entityGadget = new EntityGadget(scene, LAY_LINES_GOLDEN_GADGET_ID,pos,rot);
        entityGadget.buildContent();
        GadgetWorktop gadgetWorktop = ((GadgetWorktop)entityGadget.getContent());
        gadgetWorktop.addWorktopOptions(new int[]{187});
        gadgetWorktop.setOnSelectWorktopOptionEvent((Player player, GadgetWorktop context,int option)->{
            synchronized(LayLinesManager.this){
                for(ActiveLayLines activeLayLines: activeLayLines) {
                    if(activeLayLines.gadget == entityGadget){
                        return false;
                    }
                }
                /*
                BasePacket packet = new BasePacket(PacketOpcodes.WorktopOptionNotify);
                packet.setData(WorktopOptionNotifyOuterClass.WorktopOptionNotify.newBuilder()
                    .setGadgetEntityId(entityGadget.getGadgetId()).build()
                );
                scene.broadcastPacket(packet);
                packet = new BasePacket(PacketOpcodes.WorldOwnerBlossomScheduleInfoNotify);
                packet.setData(WorldOwnerBlossomScheduleInfoNotifyOuterClass.WorldOwnerBlossomScheduleInfoNotify.newBuilder().setScheduleInfo(
                    BlossomScheduleInfoOuterClass.BlossomScheduleInfo.newBuilder()
                        .setCircleCampId(202003002)
                        .setFinishProgress(7)
                        .setRefreshId(4)
                        .setRound(1)
                        .setState(2)
                        .build()
                ).build());
                scene.broadcastPacket(packet);*/
                ActiveLayLines activeLayLine = new ActiveLayLines(entityGadget,6,-1);
                entityGadget.updateState(201);
                scene.setChallenge(activeLayLine.getChallenge());
                scene.removeEntity(entityGadget, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
                activeLayLines.add(activeLayLine);
                activeLayLine.start();
            }
            return true;
        });
        entityGadget.setState(204);
        player.getScene().addEntity(entityGadget);
    }
    public synchronized void setPlayer(Player player) {
        this.player = player;
    }
}
