package emu.grasscutter.data.binout.routes;

import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SceneRoutes {
    private int sceneId;
    @Nullable private Route[] routes;
}
