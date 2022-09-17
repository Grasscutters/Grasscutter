package emu.grasscutter.data.excels;


import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.LittleGameData;
import emu.grasscutter.utils.Position;

import java.util.List;

@ResourceType(name = "BuildData.json")
public class BuildData extends GameResource {

    private int id;
    private String NLSDPADFFC; // type
    private Position startPos;
    private List<LittleGameData> sceneData;
    private List<LittleGameData> monsterData;


    public enum NLSDPADFFC {
        creat, game, sceneOnly, monsterOnly
    }

    @Override
    public int getId() {
        return id;
    }

    public String getType() {
        return NLSDPADFFC;
    }

    public Position getStartPos(){
        return startPos;
    }

    public List<LittleGameData> getSceneData(){
        return sceneData;
    }

    public List<LittleGameData> getMonsterData(){
        return monsterData;
    }
}
