package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.config.ConfigContainer;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.GadgetData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.LittleGameData;
import emu.grasscutter.data.excels.MonsterData;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.EntityVehicle;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.utils.Position;
import emu.grasscutter.command.commands.SpawnCommand;

import java.util.*;

import static emu.grasscutter.config.Configuration.GAME_OPTIONS;
import static emu.grasscutter.utils.Language.translate;

@Command(
    label = "build",
    usage = {"game <GameID> [<x> <y> <z>]"},
    aliases = {"game"},
    permission = "game.build",
    permissionTargeted = "game.build.others")
public final class GameCommand implements CommandHandler {

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
                    CommandHandler.sendMessage(sender, "case 4");
                }
            case 1:
                try {
                    gameId = Integer.parseInt(args.get(0));
                }catch (NumberFormatException ignored){
                    CommandHandler.sendMessage(sender, "case 1");
                }
                break;
            default:
                sendUsageMessage(sender);
                return;
        }

        LoadGame(sender, targetPlayer, gameId, gameStartPos);
    }

    private void LoadGame(Player sender, Player targetPlayer, int gameId, Position gameStartPos) {
        Scene scene = targetPlayer.getScene();

        List<Integer> sceneList = LoadSceneJson(gameId);
        List<Integer> monsterList = LoadMonsterJson(gameId);

        for (Integer integer : sceneList) {
            GameEntity entity;
            LittleGameData littleGameData = GameData.getLittleGameDataMap().get(integer.intValue());
            GadgetData gadgetData = GameData.getGadgetDataMap().get(integer);


            entity = new EntityVehicle(scene, targetPlayer, littleGameData.getEntityId(), 0, littleGameData.getPos(), littleGameData.getRot());
            scene.addEntity(entity);
        }

        for (Integer integer : monsterList) {
            GameEntity entity;
            LittleGameData littleGameData = GameData.getLittleGameDataMap().get(integer.intValue());
            MonsterData monsterData = GameData.getMonsterDataMap().get(littleGameData.getEntityId());

            entity = new EntityMonster(scene, monsterData, littleGameData.getPos(), littleGameData.getLevel());
            scene.addEntity(entity);
        }
    }

    private List<Integer> LoadSceneJson(int gameId) {
        List<Integer> sceneList = new ArrayList<>();

        for(int i = 1; i < GameData.getLittleGameDataMap().size() + 1; i++) {
            LittleGameData data = GameData.getLittleGameDataMap().get(i);
            if (data.getGameId() == gameId && Objects.equals(data.getType(), "SCENE")) {
                sceneList.add(i);
            }
        }
        return sceneList;
    }

    private List<Integer> LoadMonsterJson(int gameId) {
        List<Integer> monsterList = new ArrayList<>();

        for(int i = 1; i < GameData.getLittleGameDataMap().size() + 1; i++) {
            LittleGameData data = GameData.getLittleGameDataMap().get(i);
            if (data.getGameId() == gameId && Objects.equals(data.getType(), "MONSTER")) {
                monsterList.add(i);
            };
        }
        return monsterList;

    }

}
