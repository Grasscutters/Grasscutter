package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.BuildData;
import emu.grasscutter.data.excels.GadgetData;
import emu.grasscutter.data.common.LittleGameData;
import emu.grasscutter.data.excels.MonsterData;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.EntityVehicle;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.utils.Position;

import java.util.*;

import static emu.grasscutter.utils.Language.translate;

@Command(
    label = "build",
    aliases = {"game"},
    permission = "game.build",
    permissionTargeted = "game.build.others")
public final class BuildCommand implements CommandHandler {
    private List<Integer> faildBuild;
    private int sumBuild = 0;

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        Position gameStartPos = new Position(targetPlayer.getPosition().getX(), targetPlayer.getPosition().getY() ,targetPlayer.getPosition().getZ());
        int gameId = 1;


        switch (args.size()) {
            case 4:
                try {
                    gameId = Integer.parseInt(args.get(0));
                    gameStartPos.setX(Float.parseFloat(args.get(1)));
                    gameStartPos.setY(Float.parseFloat(args.get(2)));
                    gameStartPos.setZ(Float.parseFloat(args.get(3)));
                }catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.build.description"));
                }
            case 1:
                try {
                    gameId = Integer.parseInt(args.get(0));
                }catch (NumberFormatException ignored){
                    CommandHandler.sendMessage(sender, translate(sender, "commands.build.description"));
                }
                break;
            default:
                CommandHandler.sendMessage(sender, translate(sender, "commands.build.description"));
                return;
        }

        BuildData data = GameData.getBuildDataMap().get(Integer.parseInt(args.get(0)));
        if (data == null) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.build.unknown_buildId"));
            return;
        }
        Load(targetPlayer, gameId, gameStartPos);
        CommandHandler.sendTranslatedMessage(sender, "commands.build.success", String.valueOf(sumBuild), String.valueOf(faildBuild.size()));
    }

    private void Load(Player targetPlayer, int gameId, Position gameStartPos) {
        Scene scene = targetPlayer.getScene();
        List<Integer> sceneList = LoadSceneJson(gameId);
        List<Integer> monsterList = LoadMonsterJson(gameId);

        BuildData buildData = GameData.getBuildDataMap().get(gameId);

        if (sceneList != null){
            for (int i = 0; i < sceneList.size(); i++) {
                GameEntity entity;
                try {
                    LittleGameData data = buildData.getSceneData().get(sceneList.get(i));
                    GadgetData gadgetData = GameData.getGadgetDataMap().get(i);
                    entity = new EntityVehicle(scene, targetPlayer, data.getEntityId(), 0, data.getPos(), data.getRot());
                    scene.addEntity(entity);
                }catch (Exception e) {
                    faildBuild.add(i);
                }
            }
        }
        if (monsterList != null){
            for (int i = 0; i < monsterList.size(); i++) {
                GameEntity entity;
                try {
                    LittleGameData data = buildData.getMonsterData().get(monsterList.get(i));
                    MonsterData monsterData = GameData.getMonsterDataMap().get(data.getEntityId());
                    entity = new EntityMonster(scene, monsterData, data.getPos(), data.getLevel());
                    scene.addEntity(entity);
                }catch(Exception e) {
                    faildBuild.add(i);
                }
            }
        }

        if (sceneList != null) {
            sumBuild += sceneList.size();
        }
        if (monsterList != null) {
            sumBuild += monsterList.size();
        }
    }

    private List<Integer> LoadSceneJson(int gameId) {
        BuildData buildData = GameData.getBuildDataMap().get(gameId);
        Grasscutter.getLogger().info(String.valueOf(buildData));

        if (buildData.getSceneData() == null){
            return null;
        }
        List<Integer> sceneList = new ArrayList<>();
        int id = 0;

        do {
            try{
                LittleGameData data = buildData.getSceneData().get(id);
                if (Objects.equals(data.getType(), "SCENE")) {
                    sceneList.add(id);
                    Grasscutter.getLogger().info("OK!");
                }
            }catch (Exception ignored){

            }
            id++;
        }while (sceneList.size() != buildData.getSceneData().size());

        return sceneList;
    }

    private List<Integer> LoadMonsterJson(int gameId) {
        BuildData buildData = GameData.getBuildDataMap().get(gameId);
        if (buildData.getMonsterData() == null){
            return null;
        }
        List<Integer> monsterList = new ArrayList<>();

        int id = 0;

        do {
            try{
                LittleGameData data = buildData.getMonsterData().get(id);
                if (Objects.equals(data.getType(), "MONSTER")) {
                    monsterList.add(id);
                }
            }catch (Exception ignored){

            }
            id++;
        }while (monsterList.size() != buildData.getMonsterData().size());

        return monsterList;
    }

}
