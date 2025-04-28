package emu.grasscutter.server.event.dispatch;

import emu.grasscutter.server.event.types.ServerEvent;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class QueryCurrentRegionEvent extends ServerEvent {
    private String regionInfo;

    public QueryCurrentRegionEvent(String regionInfo) {
        super(Type.DISPATCH);

        this.regionInfo = regionInfo;
    }
}
