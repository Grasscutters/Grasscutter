package emu.grasscutter.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) 
public @interface Command {
	public String[] aliases() default "";
	
	public int gmLevel() default 1;
	
	public String helpText() default "";
}
