package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ResourceType(name = {"HomeWorldBgmExcelConfigData.json"})
public class HomeWorldBgmData extends GameResource {
    @SerializedName(
            value = "homeBgmId",
            alternate = {"MJJENLEBKEF"})
    private int homeBgmId;

    @SerializedName(
            value = "isDefaultUnlock",
            alternate = {"GBEONILEOBA"})
    private boolean isDefaultUnlock;

    private boolean NBIDHGOOCKD;
    private boolean JJMNJMCCOKP;
    private int cityId;
    private int sortOrder;
    private String GEGHMJBJMGB;

    @SerializedName(value = "bgmNameTextMapHash", alternate = "LMLNBMJFFML")
    private long bgmNameTextMapHash;

    @Override
    public int getId() {
        return this.homeBgmId;
    }
}
