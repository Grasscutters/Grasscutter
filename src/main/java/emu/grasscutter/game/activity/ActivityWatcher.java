package emu.grasscutter.game.activity;

import emu.grasscutter.data.excels.ActivityWatcherData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ActivityWatcher {
    int watcherId;
    ActivityWatcherData activityWatcherData;
    ActivityHandler activityHandler;

    protected abstract boolean isMeet(String... param);

    public void trigger(PlayerActivityData playerActivityData, String... param){
        if(isMeet(param)){
            playerActivityData.addWatcherProgress(watcherId);
            playerActivityData.save();
        }
    }

}
