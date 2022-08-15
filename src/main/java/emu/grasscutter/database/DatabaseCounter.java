package emu.grasscutter.database;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity(value = "counters", useDiscriminator = false)
public class DatabaseCounter {
	@Id
	private String id;
	private int count;
	
	public DatabaseCounter() {}
	
	public DatabaseCounter(String id) {
		this.id = id;
		this.count = 216272600;
	}
	
	public int getNextId() {
		int id = ++count;
		return id;
	}
}
