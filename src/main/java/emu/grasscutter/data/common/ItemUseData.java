package emu.grasscutter.data.common;

import java.util.List;

public class ItemUseData {
	private String useOp;
	private List<String> useParam;

	public String getUseOp() {
		return useOp;
	}

	public void setUseOp(String useOp) {
		this.useOp = useOp;
	}

	public List<String> getUseParam() {
		return useParam;
	}
	
	public void setUseParam(List<String> useParam) {
		this.useParam = useParam;
	}
}
