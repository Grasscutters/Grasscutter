package emu.grasscutter.game.expedition;

import dev.morphia.annotations.Entity;
import emu.grasscutter.net.proto.AvatarExpeditionInfoOuterClass.AvatarExpeditionInfo;
import lombok.*;

@Entity
@Getter
@Setter
public class ExpeditionInfo {
    private int state;
    private int expId;
    private int hourTime;
    private int startTime;

    public AvatarExpeditionInfo toProto() {
        return AvatarExpeditionInfo.newBuilder()
                .setStateValue(this.getState())
                .setExpId(this.getExpId())
                .setHourTime(this.getHourTime())
                .setStartTime(this.getStartTime())
                .build();
    }
}
