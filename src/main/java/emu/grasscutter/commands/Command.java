package emu.grasscutter.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) 
public @interface Command {
	String label() default "";
	
	String[] aliases() default "";
	
	int gmLevel() default 1;
	
	String usage() default "";
}
