package emu.grasscutter.data.common;

import lombok.Getter;

@Getter
public class ItemParamStringData {
    private int id;
    private String count;

    public ItemParamStringData() {}

    public ItemParamData toItemParamData() {
        if (count.contains(";")) {
            String[] split = count.split(";");
            count = count.split(";")[split.length - 1];
        } else if (count.contains(".")) {
            return new ItemParamData(id, (int) Math.ceil(Double.parseDouble(count)));
        }
        return new ItemParamData(id, Integer.parseInt(count));
    }
}
