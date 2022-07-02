package emu.grasscutter.server.packet.send;

import java.util.ArrayList;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.managers.leylines.LeyLinesType;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.BlossomBriefInfoNotifyOuterClass;
import emu.grasscutter.net.proto.BlossomBriefInfoOuterClass;

public class PacketBlossomBriefInfoNotify extends BasePacket {
    public PacketBlossomBriefInfoNotify(ArrayList<EntityGadget> blooms) {
        super(PacketOpcodes.BlossomBriefInfoNotify);
        var proto
            = BlossomBriefInfoNotifyOuterClass.BlossomBriefInfoNotify.newBuilder();
        for(EntityGadget gadget : blooms){
            LeyLinesType type = LeyLinesType.valueOf(gadget.getGadgetId());
            if(type!=null) {
                var info
                    = BlossomBriefInfoOuterClass.BlossomBriefInfo.newBuilder();
                Scene scene = gadget.getScene();
                info.setSceneId(scene.getId());
                info.setPos(gadget.getPosition().toProto());
                info.setResin(20);
                info.setMonsterLevel(30);
                if(type == LeyLinesType.LEY_LINES_GOLDEN_GADGET_ID) {
                    info.setRewardId(4108);
                    info.setCircleCampId(101001001);
                    info.setRefreshId(1);
                }else if(type == LeyLinesType.LEY_LINES_BLUE_GADGET_ID) {
                    info.setRewardId(4008);
                    info.setCircleCampId(101002003);
                    info.setRefreshId(2);
                }
                proto.addBriefInfoList(info);
            }
        }
        this.setData(proto);
    }
        /*
{
  "briefInfoList": [
    {
      "circleCampId": 101001001,
      "cityId": 1,
      "monsterLevel": 78,
      "pos": {
        "x": 1392.84,
        "y": 270.231,
        "z": -1662.367
      },
      "refreshId": 1,
      "resin": 20,
      "rewardId": 4108,
      "sceneId": 3
    },
    {
      "circleCampId": 101002003,
      "cityId": 1,
      "monsterLevel": 78,
      "pos": {
        "x": 1801.796,
        "y": 243.372,
        "z": -717.566
      },
      "refreshId": 2,
      "resin": 20,
      "rewardId": 4008,
      "sceneId": 3
    },
    {
      "circleCampId": 202009001,
      "cityId": 2,
      "monsterLevel": 78,
      "pos": {
        "x": -47.678,
        "y": 260.739,
        "z": -7.075
      },
      "refreshId": 3,
      "resin": 20,
      "rewardId": 4108,
      "sceneId": 3
    },
    {
      "circleCampId": 202006001,
      "cityId": 2,
      "monsterLevel": 78,
      "pos": {
        "x": -928,
        "y": 197.6,
        "z": 773.5
      },
      "refreshId": 4,
      "resin": 20,
      "rewardId": 4008,
      "sceneId": 3
    },
    {
      "circleCampId": 101002006,
      "cityId": 1,
      "monsterLevel": 78,
      "pos": {
        "x": 2039.606,
        "y": 241.64,
        "z": -635.589
      },
      "refreshId": 9,
      "sceneId": 3
    },
    {
      "circleCampId": 101002007,
      "cityId": 1,
      "monsterLevel": 78,
      "pos": {
        "x": 1675.688,
        "y": 259.063,
        "z": -714.483
      },
      "refreshId": 9,
      "sceneId": 3
    },
    {
      "circleCampId": 101007007,
      "cityId": 1,
      "monsterLevel": 78,
      "pos": {
        "x": 2381.691,
        "y": 287.829,
        "z": -328.531
      },
      "refreshId": 9,
      "sceneId": 3
    },
    {
      "circleCampId": 101007009,
      "cityId": 1,
      "monsterLevel": 78,
      "pos": {
        "x": 2149.874,
        "y": 215.259,
        "z": -585.25
      },
      "refreshId": 10,
      "sceneId": 3
    },
    {
      "circleCampId": 101007010,
      "cityId": 1,
      "monsterLevel": 78,
      "pos": {
        "x": 2676.034,
        "y": 224.224,
        "z": -486.852
      },
      "refreshId": 10,
      "sceneId": 3
    },
    {
      "circleCampId": 101007012,
      "cityId": 1,
      "monsterLevel": 78,
      "pos": {
        "x": 2305.087,
        "y": 249.684,
        "z": -430.197
      },
      "refreshId": 10,
      "sceneId": 3
    },
    {
      "circleCampId": 202006009,
      "cityId": 2,
      "monsterLevel": 78,
      "pos": {
        "x": -669.628,
        "y": 146.75,
        "z": 877.413
      },
      "refreshId": 11,
      "sceneId": 3
    },
    {
      "circleCampId": 202006014,
      "cityId": 2,
      "monsterLevel": 78,
      "pos": {
        "x": -653.67,
        "y": 167.931,
        "z": 1088.709
      },
      "refreshId": 11,
      "sceneId": 3
    },
    {
      "circleCampId": 202010006,
      "cityId": 2,
      "monsterLevel": 78,
      "pos": {
        "x": 285.19,
        "y": 203.958,
        "z": 682.137
      },
      "refreshId": 11,
      "sceneId": 3
    },
    {
      "circleCampId": 202012001,
      "cityId": 2,
      "monsterLevel": 78,
      "pos": {
        "x": -598.318,
        "y": 350.962,
        "z": 565.044
      },
      "refreshId": 11,
      "sceneId": 3
    },
    {
      "circleCampId": 202012002,
      "cityId": 2,
      "monsterLevel": 78,
      "pos": {
        "x": -547.766,
        "y": 343.946,
        "z": 578.365
      },
      "refreshId": 11,
      "sceneId": 3
    },
    {
      "circleCampId": 202006008,
      "cityId": 2,
      "monsterLevel": 78,
      "pos": {
        "x": -874.848,
        "y": 155.793,
        "z": 1270.864
      },
      "refreshId": 12,
      "sceneId": 3
    },
    {
      "circleCampId": 202006010,
      "cityId": 2,
      "monsterLevel": 78,
      "pos": {
        "x": -266.897,
        "y": 248.732,
        "z": 704.769
      },
      "refreshId": 12,
      "sceneId": 3
    },
    {
      "circleCampId": 202006011,
      "cityId": 2,
      "monsterLevel": 78,
      "pos": {
        "x": -319.344,
        "y": 238.63,
        "z": 1031.658
      },
      "refreshId": 12,
      "sceneId": 3
    },
    {
      "circleCampId": 202006012,
      "cityId": 2,
      "monsterLevel": 78,
      "pos": {
        "x": -889.123,
        "y": 181.006,
        "z": 1031.743
      },
      "refreshId": 12,
      "sceneId": 3
    },
    {
      "circleCampId": 202006015,
      "cityId": 2,
      "monsterLevel": 78,
      "pos": {
        "x": -793.145,
        "y": 170.279,
        "z": 780.243
      },
      "refreshId": 12,
      "sceneId": 3
    },
    {
      "circleCampId": 303003004,
      "cityId": 3,
      "monsterLevel": 78,
      "pos": {
        "x": -4032.505,
        "y": 261.271,
        "z": -2211.523
      },
      "refreshId": 20,
      "resin": 20,
      "rewardId": 4108,
      "sceneId": 3
    },
    {
      "circleCampId": 303004001,
      "cityId": 3,
      "monsterLevel": 78,
      "pos": {
        "x": -3962.075,
        "y": 199.982,
        "z": -1119.923
      },
      "refreshId": 21,
      "resin": 20,
      "rewardId": 4008,
      "sceneId": 3
    },
    {
      "circleCampId": 303003011,
      "cityId": 3,
      "monsterLevel": 78,
      "pos": {
        "x": -3553.335,
        "y": 199.411,
        "z": -2752.466
      },
      "refreshId": 24,
      "sceneId": 3
    },
    {
      "circleCampId": 303003013,
      "cityId": 3,
      "monsterLevel": 78,
      "pos": {
        "x": -3765.123,
        "y": 232.244,
        "z": -2119.286
      },
      "refreshId": 24,
      "sceneId": 3
    },
    {
      "circleCampId": 303003014,
      "cityId": 3,
      "monsterLevel": 78,
      "pos": {
        "x": -4021.85,
        "y": 209.244,
        "z": -2496.617
      },
      "refreshId": 24,
      "sceneId": 3
    },
    {
      "circleCampId": 303003015,
      "cityId": 3,
      "monsterLevel": 78,
      "pos": {
        "x": -4046.39,
        "y": 263.043,
        "z": -2215.813
      },
      "refreshId": 24,
      "sceneId": 3,
      "state": 2
    },
    {
      "circleCampId": 303003012,
      "cityId": 3,
      "monsterLevel": 78,
      "pos": {
        "x": -3664.138,
        "y": 236.054,
        "z": -2392.087
      },
      "refreshId": 25,
      "sceneId": 3
    },
    {
      "circleCampId": 303003016,
      "cityId": 3,
      "monsterLevel": 78,
      "pos": {
        "x": -3768.305,
        "y": 236.256,
        "z": -2276.185
      },
      "refreshId": 25,
      "sceneId": 3
    },
    {
      "circleCampId": 303004013,
      "cityId": 3,
      "monsterLevel": 78,
      "pos": {
        "x": -3631.343,
        "y": 191.707,
        "z": -633.737
      },
      "refreshId": 25,
      "sceneId": 3
    },
    {
      "circleCampId": 303004015,
      "cityId": 3,
      "monsterLevel": 78,
      "pos": {
        "x": -3840.389,
        "y": 200.021,
        "z": -1193.66
      },
      "refreshId": 25,
      "sceneId": 3
    }
  ]
}
    *
    * */
}
