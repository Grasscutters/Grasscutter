package emu.grasscutter.game.entity;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.AbilityData;
import emu.grasscutter.game.world.*;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import it.unimi.dsi.fastutil.ints.*;

public class EntityScene extends GameEntity {

    public EntityScene(Scene scene) {
        super(scene);
        initAbilities();
    }

    @Override
    public void initAbilities() {
        // Load abilities from levelElementAbilities
        for (var ability :
                GameData.getConfigGlobalCombat().getDefaultAbilities().getLevelElementAbilities()) {
            AbilityData data = GameData.getAbilityData(ability);
            if (data != null)
                getScene().getWorld().getHost().getAbilityManager().addAbilityToEntity(this, data);
        }
    }

    @Override
    public int getEntityTypeId() {
        return 0x13;
    }

    @Override
    public Int2FloatMap getFightProperties() {
        // TODO
        return new Int2FloatArrayMap();
    }

    @Override
    public Position getPosition() {
        // TODO Auto-generated method stub
        return new Position(0, 0, 0);
    }

    @Override
    public Position getRotation() {
        return new Position(0, 0, 0);
    }

    @Override
    public SceneEntityInfo toProto() {
        return null;
    }
}
