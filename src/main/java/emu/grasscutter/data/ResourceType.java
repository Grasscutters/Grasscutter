package emu.grasscutter.data;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) 
public @interface ResourceType {

	/** Names of the file that this Resource loads from */
	String[] name();
	
	/** Load priority - dictates which order to load this resource, with "highest" being loaded first */
	LoadPriority loadPriority() default LoadPriority.NORMAL;

	/** use extend config json, which will merge into the current config, the extend json with the name of string append of original config json name and place in the data folder, eg: AvatarSkillExcelConfigData.json AvatarSkillExcelConfigDataEx.json */
	boolean useExtendConfig() default false;
	
	public enum LoadPriority {
		HIGHEST	(4), 
		HIGH	(3), 
		NORMAL	(2), 
		LOW		(1),
		LOWEST	(0);

		private final int value;
		
		LoadPriority(int value) {
			this.value = value;
		}
		
		public int value() {
			return value;
		}
	}
}
