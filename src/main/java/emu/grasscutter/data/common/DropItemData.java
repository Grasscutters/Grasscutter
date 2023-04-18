package emu.grasscutter.data.common;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class DropItemData {
    @SerializedName(value = "itemId")
    private int id;

    private String countRange;
    private int weight;
}
