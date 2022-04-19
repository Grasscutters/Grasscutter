package emu.grasscutter.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) 
public @interface Command {
	String label() default "";

	String usage() default "";
	
	String[] aliases() default {""};
	
	Execution execution() default Execution.ALL;
	
	String permission() default "";
	
	enum Execution {
		ALL,
		CONSOLE,
		PLAYER
	}
}
