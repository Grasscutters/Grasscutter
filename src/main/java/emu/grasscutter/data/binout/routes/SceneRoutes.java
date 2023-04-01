package emu.grasscutter.data.binout.routes;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nullable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SceneRoutes {
    private int sceneId;
    @Nullable private Route[] routes;
}
