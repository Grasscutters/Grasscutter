package emu.grasscutter.game.entity;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.AbilityData;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.game.world.Position;
import it.unimi.dsi.fastutil.ints.Int2FloatArrayMap;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;

public class EntityTeam extends GameEntity {

    public EntityTeam(Scene scene) {
        super(scene);
        initAbilities();
        this.id = scene.getWorld().getNextEntityId(EntityIdType.TEAM);
    }

    @Override
    public void initAbilities() {
        //Load abilities from levelElementAbilities
        for(var ability : GameData.getConfigGlobalCombat().getDefaultAbilities().getDefaultTeamAbilities()) {
            AbilityData data =  GameData.getAbilityData(ability);
            if(data != null)
                getScene().getWorld().getHost().getAbilityManager().addAbilityToEntity(
                    this, data);
        }
    }

    @Override
    public int getEntityTypeId() {
        return EntityIdType.TEAM.getId();
    }

    @Override
    public Int2FloatMap getFightProperties() {
        //TODO
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
