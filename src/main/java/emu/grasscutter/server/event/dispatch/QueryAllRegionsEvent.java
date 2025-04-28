package emu.grasscutter.server.event.dispatch;

import emu.grasscutter.server.event.types.ServerEvent;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class QueryAllRegionsEvent extends ServerEvent {
    private String regionList;

    public QueryAllRegionsEvent(String regionList) {
        super(Type.DISPATCH);

        this.regionList = regionList;
    }
}
