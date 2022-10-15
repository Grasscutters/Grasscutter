package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ResourceType(name = {"DailyTaskExcelConfigData.json"})
public class DailyTaskData extends GameResource {
    int id;
    int cityId;
    int poolId;
    String type;
    int rarity;
    List<Integer> oldGroupVec;
    List<Integer> newGroupVec;
    String finishType;
    int finishParam1;
    int finishParam2;
    int finishProgress;
    int taskRewardId;
    String centerPosition;
    int enterDistance;
    int exitDistance;
    long titleTextMapHash;
    long descriptionTextMapHash;
    long targetTextMapHash;
    int radarRadius;

    @Override
    public int getId() {
        return id;
    }
}
