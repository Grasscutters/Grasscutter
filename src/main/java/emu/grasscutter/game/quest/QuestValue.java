package emu.grasscutter.game.quest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import emu.grasscutter.game.quest.enums.QuestTriggerType;

@Retention(RetentionPolicy.RUNTIME) 
public @interface QuestValue {
	QuestTriggerType value();
}
