package emu.grasscutter.data.common;

public class ItemParamStringData {
    private int id;
    private String count;

    public ItemParamStringData() {
    }

    public int getId() {
        return this.id;
    }

    public String getCount() {
        return this.count;
    }

    public ItemParamData toItemParamData() {
        if (this.count.contains(";")) {
            String[] split = this.count.split(";");
            this.count = this.count.split(";")[split.length - 1];
        } else if (this.count.contains(".")) {
            return new ItemParamData(this.id, (int) Math.ceil(Double.parseDouble(this.count)));
        }
        return new ItemParamData(this.id, Integer.parseInt(this.count));
    }
}
