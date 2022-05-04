package emu.grasscutter.data.common;

public class ItemParamStringData {
	private int Id;
    private String Count;

    public ItemParamStringData() {}

	public int getId() {
		return Id;
	}

	public String getCount() {
		return Count;
	}
	
	public ItemParamData toItemParamData() {
		if (Count.contains(";")) {
			String[] split = Count.split(";");
			Count = Count.split(";")[split.length - 1];
		} else if (Count.contains(".")) {
			return new ItemParamData(Id, (int) Math.ceil(Double.parseDouble(Count)));
		}
		return new ItemParamData(Id, Integer.parseInt(Count));
	}
}
