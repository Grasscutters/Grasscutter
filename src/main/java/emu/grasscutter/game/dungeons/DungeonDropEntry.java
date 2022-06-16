package emu.grasscutter.game.dungeons;

import java.util.List;

public class DungeonDropEntry {
	private List<Integer> counts;
	private List<Integer> items;
	private List<Integer> probabilities;

	public List<Integer> getCounts() {
		return counts;
	}
	public void setCounts(List<Integer> counts) {
		this.counts = counts;
	}
	
	public List<Integer> getItems() {
		return items;
	}
	public void setItems(List<Integer> items) {
		this.items = items;
	}

	public List<Integer> getProbabilities() {
		return probabilities;
	}
	public void setProbabilities(List<Integer> probabilities) {
		this.probabilities = probabilities;
	}
}
