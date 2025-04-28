package emu.grasscutter.data.common;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

// Used in excels
@Getter
public class ItemParamData {
    @SerializedName(
            value = "id",
            alternate = {"itemId"})
    private int id;

    @SerializedName(
            value = "count",
            alternate = {"itemCount"})
    private int count;

    public ItemParamData() {}

    public ItemParamData(int id, int count) {
        this.id = id;
        this.count = count;
    }

    public int getItemId() {
        return id;
    }

    public int getItemCount() {
        return count;
    }
}
