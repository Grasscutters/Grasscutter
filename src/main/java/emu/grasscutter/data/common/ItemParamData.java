package emu.grasscutter.data.common;

import com.google.gson.annotations.SerializedName;

public class ItemParamData {
    @SerializedName(value = "id", alternate = {"itemId"})
    private int id;

    @SerializedName(value = "count", alternate = {"itemCount"})
    private int count;

    public ItemParamData() {
    }

    public ItemParamData(int id, int count) {
        this.id = id;
        this.count = count;
    }

    public int getId() {
        return this.id;
    }

    public int getItemId() {
        return this.id;
    }

    public int getCount() {
        return this.count;
    }

    public int getItemCount() {
        return this.count;
    }
}
