package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.EntityType;
import emu.grasscutter.scripts.data.SceneRegion;
import emu.grasscutter.utils.Position;
import org.luaj.vm2.ast.Str;

@ResourceType(name = "LittleGameData.json")
public class LittleGameData extends GameResource {

    private int id;
    private int gameId;
    private int entityId;
    private String type;
    private Position pos;
    private Position rot;
    private int level;
    private float hpBase;
    private float attackBase;
    private float defenseBase;
    private float fireSubHurt;
    private float elecSubHurt;
    private float grassSubHurt;
    private float waterSubHurt;
    private float windSubHurt;
    private float rockSubHurt;
    private float iceSubHurt;
    private float physicalSubHurt;

    private String state;


    public int getGameId() {
        return this.gameId;
    }
    @Override
    public int getId(){return this.id;}

    public int getEntityId(){return this.entityId;}

    public String getType() {
        return this.type;
    }

    public Position getPos(){return this.pos;}

    public Position getRot(){return this.rot;}

    public int getLevel(){return this.level;}

    public float getHpBase(){return this.hpBase;}

    public float getAttackBase(){return this.attackBase;}

    public float getDefenseBase(){return this.defenseBase;}

    public float getFireSubHurt(){return this.fireSubHurt;}

    public float getElecSubHurt(){return this.elecSubHurt;}

    public float getGrassSubHurt(){return this.grassSubHurt;}

    public float getWaterSubHurt(){return this.waterSubHurt;}

    public float getWindSubHurt(){return this.windSubHurt;}

    public float getRockSubHurt(){return this.rockSubHurt;}

    public float getIceSubHurt(){return this.iceSubHurt;}

    public float getPhysicalSubHurt(){return this.physicalSubHurt;}



    @Override
    public void onLoad() {

    }
}
