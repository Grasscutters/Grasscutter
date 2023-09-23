package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ResourceType(name = "CoopChapterExcelConfigData.json")
@Getter
@Setter // TODO: remove setters next API break
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoopChapterData extends GameResource {
    @Getter(onMethod_ = @Override)
    int id;

    int avatarId;
    //    int chapterNameTextMapHash;
    //    int coopPageTitleTextMapHash;
    //    int chapterSortId;
    //    int avatarSortId;
    //    String chapterIcon;
    List<CoopCondition> unlockCond;
    //    int [] unlockCondTips;
    //    int openMaterialId;
    //    int openMaterialNum;
    //    String beginTimeStr;
    //    int confidenceValue;
    //    String pointGraphPath;
    //    Double graphXRatio;
    //    Double graphYRatio;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static class CoopCondition {
        @SerializedName(
                value = "_condType",
                alternate = {"condType"})
        String type = "COOP_COND_NONE";

        @SerializedName(
                value = "_args",
                alternate = {"args"})
        int[] args;
    }
}
