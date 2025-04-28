package emu.grasscutter.game.tower;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TowerScheduleConfig {
    private int scheduleId;

    private Date scheduleStartTime;
    private Date nextScheduleChangeTime;
}
