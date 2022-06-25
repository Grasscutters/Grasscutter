package emu.grasscutter.game.activity.musicgame;

import emu.grasscutter.game.activity.ActivityHandler;
import emu.grasscutter.game.activity.ActivityType;
import emu.grasscutter.game.activity.PlayerActivityData;
import emu.grasscutter.net.proto.ActivityInfoOuterClass;

@ActivityType("NEW_ACTIVITY_MUSIC_GAME")
public class MusicGameActivityHandler extends ActivityHandler {

    @Override
    public void buildProto(PlayerActivityData playerActivityData, ActivityInfoOuterClass.ActivityInfo.Builder activityInfo) {
        super.buildProto(playerActivityData, activityInfo);


    }
}
