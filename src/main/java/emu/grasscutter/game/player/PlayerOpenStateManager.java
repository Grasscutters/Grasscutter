package emu.grasscutter.game.player;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.OpenState;
import emu.grasscutter.server.packet.send.PacketOpenStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketOpenStateUpdateNotify;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static emu.grasscutter.game.props.OpenState.*;

@Entity
public class PlayerOpenStateManager {

    @Transient private Player player;
    @Getter private Map<Integer,Integer> openStateMap;
     /*
    //DO NOT MODIFY. Based on conversation of official server and client, game version 2.7
    private static Set<OpenState> newPlayerOpenStates = Set.of(OPEN_STATE_DERIVATIVE_MALL,OPEN_STATE_PHOTOGRAPH,OPEN_STATE_BATTLE_PASS,OPEN_STATE_SHOP_TYPE_GENESISCRYSTAL,OPEN_STATE_SHOP_TYPE_RECOMMANDED,
        OPEN_STATE_SHOP_TYPE_GIFTPACKAGE,OPEN_STATE_GUIDE_RELIC_PROM,OPEN_STATE_GUIDE_TALENT,OPEN_STATE_SHOP_TYPE_BLACKSMITH,OPEN_STATE_SHOP_TYPE_PAIMON,OPEN_STATE_WEAPON_AWAKEN,
        OPEN_STATE_WEAPON_PROMOTE,OPEN_STATE_AVATAR_PROMOTE,OPEN_STATE_AVATAR_TALENT,OPEN_STATE_WEAPON_UPGRADE,OPEN_STATE_RESIN,OPEN_STATE_RELIQUARY_UPGRADE,
        OPEN_STATE_SHOP_TYPE_VIRTUAL_SHOP,OPEN_STATE_RELIQUARY_PROMOTE);
    */
      //For development. Remove entry when properly implemented
    private static Set<OpenState> devOpenStates = Set.of(
        OPEN_STATE_PAIMON,
        OPEN_STATE_PAIMON_NAVIGATION,
        OPEN_STATE_AVATAR_PROMOTE,
        OPEN_STATE_AVATAR_TALENT,
        OPEN_STATE_WEAPON_PROMOTE,
        OPEN_STATE_WEAPON_AWAKEN,
        OPEN_STATE_QUEST_REMIND,
        OPEN_STATE_GAME_GUIDE,
        OPEN_STATE_COOK,
        OPEN_STATE_WEAPON_UPGRADE,
        OPEN_STATE_RELIQUARY_UPGRADE,
        OPEN_STATE_RELIQUARY_PROMOTE,
        OPEN_STATE_WEAPON_PROMOTE_GUIDE,
        OPEN_STATE_WEAPON_CHANGE_GUIDE,
        OPEN_STATE_PLAYER_LVUP_GUIDE,
        OPEN_STATE_FRESHMAN_GUIDE,
        OPEN_STATE_SKIP_FRESHMAN_GUIDE,
        OPEN_STATE_GUIDE_MOVE_CAMERA,
        OPEN_STATE_GUIDE_SCALE_CAMERA,
        OPEN_STATE_GUIDE_KEYBOARD,
        OPEN_STATE_GUIDE_MOVE,
        OPEN_STATE_GUIDE_JUMP,
        OPEN_STATE_GUIDE_SPRINT,
        OPEN_STATE_GUIDE_MAP,
        OPEN_STATE_GUIDE_ATTACK,
        OPEN_STATE_GUIDE_FLY,
        OPEN_STATE_GUIDE_TALENT,
        OPEN_STATE_GUIDE_RELIC,
        OPEN_STATE_GUIDE_RELIC_PROM,
        OPEN_STATE_COMBINE,
        OPEN_STATE_GACHA,
        OPEN_STATE_GUIDE_GACHA,
        OPEN_STATE_GUIDE_TEAM,
        OPEN_STATE_GUIDE_PROUD,
        OPEN_STATE_GUIDE_AVATAR_PROMOTE,
        OPEN_STATE_GUIDE_ADVENTURE_CARD,
        OPEN_STATE_FORGE,
        OPEN_STATE_GUIDE_BAG,
        OPEN_STATE_EXPEDITION,
        OPEN_STATE_GUIDE_ADVENTURE_DAILYTASK,
        OPEN_STATE_GUIDE_ADVENTURE_DUNGEON,
        OPEN_STATE_TOWER,
        OPEN_STATE_WORLD_STAMINA,
        OPEN_STATE_TOWER_FIRST_ENTER,
        OPEN_STATE_RESIN,
        OPEN_STATE_LIMIT_REGION_FRESHMEAT,
        OPEN_STATE_LIMIT_REGION_GLOBAL,
        OPEN_STATE_MULTIPLAYER,
        OPEN_STATE_GUIDE_MOUSEPC,
        OPEN_STATE_GUIDE_MULTIPLAYER,
        OPEN_STATE_GUIDE_DUNGEONREWARD,
        OPEN_STATE_GUIDE_BLOSSOM,
        OPEN_STATE_AVATAR_FASHION,
        OPEN_STATE_PHOTOGRAPH,
        OPEN_STATE_GUIDE_KSLQUEST,
        OPEN_STATE_PERSONAL_LINE,
        OPEN_STATE_GUIDE_PERSONAL_LINE,
        OPEN_STATE_GUIDE_APPEARANCE,
        OPEN_STATE_GUIDE_PROCESS,
        OPEN_STATE_GUIDE_PERSONAL_LINE_KEY,
        OPEN_STATE_GUIDE_WIDGET,
        OPEN_STATE_GUIDE_ACTIVITY_SKILL_ASTER,
        OPEN_STATE_GUIDE_COLDCLIMATE,
        OPEN_STATE_DERIVATIVE_MALL,
        OPEN_STATE_GUIDE_EXITMULTIPLAYER,
        OPEN_STATE_GUIDE_THEATREMACHANICUS_BUILD,
        OPEN_STATE_GUIDE_THEATREMACHANICUS_REBUILD,
        OPEN_STATE_GUIDE_THEATREMACHANICUS_CARD,
        OPEN_STATE_GUIDE_THEATREMACHANICUS_MONSTER,
        OPEN_STATE_GUIDE_THEATREMACHANICUS_MISSION_CHECK,
        OPEN_STATE_GUIDE_THEATREMACHANICUS_BUILD_SELECT,
        OPEN_STATE_GUIDE_THEATREMACHANICUS_CHALLENGE_START,
        OPEN_STATE_GUIDE_CONVERT,
        OPEN_STATE_GUIDE_THEATREMACHANICUS_MULTIPLAYER,
        OPEN_STATE_GUIDE_COOP_TASK,
        OPEN_STATE_GUIDE_HOMEWORLD_ADEPTIABODE,
        OPEN_STATE_GUIDE_HOMEWORLD_DEPLOY,
        OPEN_STATE_GUIDE_CHANNELLERSLAB_EQUIP,
        OPEN_STATE_GUIDE_CHANNELLERSLAB_MP_SOLUTION,
        OPEN_STATE_GUIDE_CHANNELLERSLAB_POWER,
        OPEN_STATE_GUIDE_HIDEANDSEEK_SKILL,
        OPEN_STATE_GUIDE_HOMEWORLD_MAPLIST,
        OPEN_STATE_GUIDE_RELICRESOLVE,
        OPEN_STATE_GUIDE_GGUIDE,
        OPEN_STATE_GUIDE_GGUIDE_HINT,
        OPEN_STATE_GUIDE_RIGHT_TEAM, // mobile phone only!
        OPEN_STATE_CITY_REPUATION_MENGDE,
        OPEN_STATE_CITY_REPUATION_LIYUE,
        OPEN_STATE_CITY_REPUATION_UI_HINT,
        OPEN_STATE_CITY_REPUATION_INAZUMA,
        OPEN_STATE_SHOP_TYPE_MALL,
        OPEN_STATE_SHOP_TYPE_RECOMMANDED,
        OPEN_STATE_SHOP_TYPE_GENESISCRYSTAL,
        OPEN_STATE_SHOP_TYPE_GIFTPACKAGE,
        OPEN_STATE_SHOP_TYPE_PAIMON,
        OPEN_STATE_SHOP_TYPE_CITY,
        OPEN_STATE_SHOP_TYPE_BLACKSMITH,
        OPEN_STATE_SHOP_TYPE_GROCERY,
        OPEN_STATE_SHOP_TYPE_FOOD,
        OPEN_STATE_SHOP_TYPE_SEA_LAMP,
        OPEN_STATE_SHOP_TYPE_VIRTUAL_SHOP,
        OPEN_STATE_SHOP_TYPE_LIYUE_GROCERY,
        OPEN_STATE_SHOP_TYPE_LIYUE_SOUVENIR,
        OPEN_STATE_SHOP_TYPE_LIYUE_RESTAURANT,
        OPEN_STATE_SHOP_TYPE_INAZUMA_SOUVENIR,
        OPEN_STATE_SHOP_TYPE_NPC_TOMOKI,
        OPEN_ADVENTURE_MANUAL,
        OPEN_ADVENTURE_MANUAL_CITY_MENGDE,
        OPEN_ADVENTURE_MANUAL_CITY_LIYUE,
        OPEN_ADVENTURE_MANUAL_MONSTER,
        OPEN_ADVENTURE_MANUAL_BOSS_DUNGEON,
        OPEN_STATE_ACTIVITY_SEALAMP,
        OPEN_STATE_ACTIVITY_SEALAMP_TAB2,
        OPEN_STATE_ACTIVITY_SEALAMP_TAB3,
        OPEN_STATE_BATTLE_PASS,
        OPEN_STATE_BATTLE_PASS_ENTRY,
        OPEN_STATE_ACTIVITY_CRUCIBLE,
        OPEN_STATE_ACTIVITY_NEWBEEBOUNS_OPEN,
        OPEN_STATE_ACTIVITY_NEWBEEBOUNS_CLOSE,
        OPEN_STATE_ACTIVITY_ENTRY_OPEN,
        OPEN_STATE_MENGDE_INFUSEDCRYSTAL,
        OPEN_STATE_LIYUE_INFUSEDCRYSTAL,
        OPEN_STATE_SNOW_MOUNTAIN_ELDER_TREE,
        OPEN_STATE_MIRACLE_RING,
        OPEN_STATE_COOP_LINE,
        OPEN_STATE_INAZUMA_INFUSEDCRYSTAL,
        OPEN_STATE_FISH,
        OPEN_STATE_GUIDE_SUMO_TEAM_SKILL,
        OPEN_STATE_GUIDE_FISH_RECIPE,
        OPEN_STATE_HOME,
        OPEN_STATE_ACTIVITY_HOMEWORLD,
        OPEN_STATE_ADEPTIABODE,
        OPEN_STATE_HOME_AVATAR,
        OPEN_STATE_HOME_EDIT,
        OPEN_STATE_HOME_EDIT_TIPS,
        OPEN_STATE_RELIQUARY_DECOMPOSE,
        OPEN_STATE_ACTIVITY_H5,
        OPEN_STATE_ORAIONOKAMI,
        OPEN_STATE_GUIDE_CHESS_MISSION_CHECK,
        OPEN_STATE_GUIDE_CHESS_BUILD,
        OPEN_STATE_GUIDE_CHESS_WIND_TOWER_CIRCLE,
        OPEN_STATE_GUIDE_CHESS_CARD_SELECT,
        OPEN_STATE_INAZUMA_MAINQUEST_FINISHED,
        OPEN_STATE_PAIMON_LVINFO,
        OPEN_STATE_TELEPORT_HUD,
        OPEN_STATE_GUIDE_MAP_UNLOCK,
        OPEN_STATE_GUIDE_PAIMON_LVINFO,
        OPEN_STATE_GUIDE_AMBORTRANSPORT,
        OPEN_STATE_GUIDE_FLY_SECOND,
        OPEN_STATE_GUIDE_KAEYA_CLUE,
        OPEN_STATE_CAPTURE_CODEX,
        OPEN_STATE_ACTIVITY_FISH_OPEN,
        OPEN_STATE_ACTIVITY_FISH_CLOSE,
        OPEN_STATE_GUIDE_ROGUE_MAP,
        OPEN_STATE_GUIDE_ROGUE_RUNE,
        OPEN_STATE_GUIDE_BARTENDER_FORMULA,
        OPEN_STATE_GUIDE_BARTENDER_MIX,
        OPEN_STATE_GUIDE_BARTENDER_CUP,
        OPEN_STATE_GUIDE_MAIL_FAVORITES,
        OPEN_STATE_GUIDE_POTION_CONFIGURE,
        OPEN_STATE_GUIDE_LANV2_FIREWORK,
        OPEN_STATE_LOADINGTIPS_ENKANOMIYA,
        OPEN_STATE_MICHIAE_CASKET,
        OPEN_STATE_MAIL_COLLECT_UNLOCK_RED_POINT,
        OPEN_STATE_LUMEN_STONE,
        OPEN_STATE_GUIDE_CRYSTALLINK_BUFF
        );

    public PlayerOpenStateManager(Player player) {
        this.openStateMap = new HashMap<>();
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getOpenState(OpenState openState) {
        return this.openStateMap.getOrDefault(openState.getValue(), 0);
    }
    public void setOpenState(OpenState openState, Integer value) {
        Integer previousValue = this.openStateMap.getOrDefault(openState.getValue(),0);
        if(!(value == previousValue)) {
            this.openStateMap.put(openState.getValue(), value);
            player.getSession().send(new PacketOpenStateChangeNotify(openState.getValue(),value));
        } else {
            Grasscutter.getLogger().debug("Warning: OpenState {} is already set to {}!", openState, value);
        }
    }

    public void setOpenStates(Map<OpenState,Integer> openStatesChanged) {
        for(Map.Entry<OpenState, Integer> entry : openStatesChanged.entrySet()) {
            setOpenState(entry.getKey(), entry.getValue());
        }
    }

    public void onNewPlayerCreate() {
        //newPlayerOpenStates.forEach(os -> this.setOpenState(os, 1));
        //setAllOpenStates();
        devOpenStates.forEach(os -> this.setOpenState(os, 1));
    }
    public void onPlayerLogin() {
        /*
        //little hack to give all openStates on second login
        if(openStateMap.containsKey(OPEN_STATE_FRESHMAN_GUIDE.getValue())) {
            setAllOpenStates();
        }
        */
        player.getSession().send(new PacketOpenStateUpdateNotify(player));
    }

    public void setAllOpenStates() {
        Stream.of(OpenState.values()).forEach(os -> this.setOpenState(os, 1));
    }
}