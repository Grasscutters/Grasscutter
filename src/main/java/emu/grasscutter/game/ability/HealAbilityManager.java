package emu.grasscutter.game.ability;

import java.util.*;
import java.util.Optional;
import java.util.Map.Entry;

import com.google.protobuf.InvalidProtocolBufferException;

import emu.grasscutter.Grasscutter;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.AbilityModifierEntry;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.data.excels.AvatarSkillDepotData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.entity.EntityClientGadget;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.net.proto.AbilityActionGenerateElemBallOuterClass.AbilityActionGenerateElemBall;
import emu.grasscutter.net.proto.AbilityInvokeEntryHeadOuterClass.AbilityInvokeEntryHead;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.AbilityMetaModifierChangeOuterClass.AbilityMetaModifierChange;
import emu.grasscutter.net.proto.AbilityMetaReInitOverrideMapOuterClass.AbilityMetaReInitOverrideMap;
import emu.grasscutter.net.proto.AbilityMixinCostStaminaOuterClass.AbilityMixinCostStamina;
import emu.grasscutter.net.proto.AbilityScalarValueEntryOuterClass.AbilityScalarValueEntry;
import emu.grasscutter.net.proto.ModifierActionOuterClass.ModifierAction;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.Utils;
import emu.grasscutter.game.props.FightProperty;

public class HealAbilityManager {
    private class HealData {
        public boolean isString = true;
        public String abilityType = ""; //"E" or "Q"
        public String sRatio = "";
        public String sBase = "";
        public float fRatio = 0;
        public float fBase = 0;
        public boolean healAll = false;
        
        public HealData(String _abilityType, String _sRatio, String _sBase, boolean _healAll) {
            abilityType = _abilityType;
            isString = true;
            sRatio = _sRatio;
            sBase = _sBase;
            healAll = _healAll;
        }

        public HealData(String _abilityType, String _sRatio, float _fRatio, float _fBase, boolean _healAll) {
            abilityType = _abilityType;
            isString = false;
            sRatio = _sRatio;
            fRatio = _fRatio;
            fBase = _fBase;
            healAll = _healAll;
        }
    }

    private class HealDataAvatar {
        public static int MAX_NUM_HEAL_ABILITY = 4;
        public String avatar = "";
        public int fightPropertyType= 0; //0: maxHP, 1: curAttack, 2: curDefense
        public ArrayList<HealData> healDataList;

        public HealDataAvatar(String _avatar, int _fightPropertyType) {
            avatar = _avatar;
            fightPropertyType = _fightPropertyType;
            healDataList = new ArrayList();
        }

        public HealDataAvatar addHealData(String abilityType, String sRatio, String sBase, boolean healAll) {
            HealData healData = new HealData(abilityType, sRatio, sBase, healAll);
            healDataList.add(healData);
            return this;
        }

        public HealDataAvatar addHealData(String abilityType, String sRatio, float fRatio, float fBase, boolean healAll) {
            HealData healData = new HealData(abilityType, sRatio, fRatio, fBase, healAll);
            healDataList.add(healData);
            return this;
        }
    }

    ArrayList<HealDataAvatar> healDataAvatarList;
	private Player player;
    
    public HealAbilityManager (Player player) {
		this.player = player;
        healDataAvatarList = new ArrayList();
        healDataAvatarList.add(new HealDataAvatar("Kokomi", 0).addHealData("E", "ElementalArt_Heal_MaxHP_Base_Percentage", "ElementalArt_Heal_Base_Amount", false));
        healDataAvatarList.add(new HealDataAvatar("Qin", 1).addHealData("Q", "Heal", "BurstHealConst", true)); 
        healDataAvatarList.add(new HealDataAvatar("Noel", 2).addHealData("E", "OnAttack_HealthRate", 0.452f, 282f, true));
        healDataAvatarList.add(new HealDataAvatar("Bennett", 0).addHealData("Q", "HealMaxHpRatio", "HealConst", false));
        healDataAvatarList.add(new HealDataAvatar("Diona", 0).addHealData("Q", "HealHPRatio", "HealHP_Const", false));
        healDataAvatarList.add(new HealDataAvatar("Sayu", 1).addHealData("Q", "Constellation_6_Damage", "Heal_BaseAmount", true).addHealData("Q", "Heal_AttackRatio", "Constellation_6_Heal", true));
        healDataAvatarList.add(new HealDataAvatar("Barbara", 0).addHealData("E", "HealHPOnAdded", "HealHPOnAdded_Const", true).addHealData("E", "HealHP_OnHittingOthers", "HealHP_Const_OnHittingOthers", true));
        healDataAvatarList.add(new HealDataAvatar("Shinobu", 0).addHealData("E", "ElementalArt_Heal_MaxHP_Percentage", 0.064f, 795f, false));
        healDataAvatarList.add(new HealDataAvatar("Qiqi", 1).addHealData("E", "HealHP_OnHittingOthers", "HealHP_Const_OnHittingOthers", true).addHealData("E", "ElementalArt_HealHp_Ratio", "ElementalArt_HealHp_Const", true));
    }

	public Player getPlayer() {
		return this.player;
	}

    public void healHandler(AbilityInvokeEntry invoke) throws Exception {
		AbilityMetaModifierChange data = AbilityMetaModifierChange.parseFrom(invoke.getAbilityData());
		
		if (data == null) {
			return;
		}
		
		GameEntity sourceEntity = player.getScene().getEntityById(data.getApplyEntityId());

        String modifierString = "";
        if(data.getParentAbilityName() != null)
			modifierString = data.getParentAbilityName().getStr();

        if(sourceEntity != null)
            checkAndHeal(sourceEntity, modifierString);
    }

    public void checkAndHeal(GameEntity sourceEntity, String modifierString) {
        int fightPropertyType = 0;
        float healAmount = 0;
        float ratio = 0, base = 0;
        float maxHP, curAttack, curDefense;
        Map<String, Float> map = sourceEntity.getMetaOverrideMap();
        for(int i = 0 ; i < healDataAvatarList.size() ; i ++) {
            HealDataAvatar healDataAvatar = healDataAvatarList.get(i);
            if(modifierString.contains(healDataAvatar.avatar)) {
                fightPropertyType = healDataAvatar.fightPropertyType;
                ArrayList<HealData> healDataList = healDataAvatar.healDataList;

                for(int j = 0 ; j < healDataList.size(); j++) {
                    HealData healData = healDataList.get(j);
                    if(map.containsKey(healData.sRatio)) {
                        if(healData.isString) {
                            ratio = map.get(healData.sRatio);
                            base = map.get(healData.sBase);
                        }
                        else {
                            ratio = healData.fRatio;
                            base = healData.fBase;
                        }
                    }

                    List<EntityAvatar> activeTeam = player.getTeamManager().getActiveTeam();
                    List<EntityAvatar> needHealAvatars = new ArrayList();
                    int currentIndex = player.getTeamManager().getCurrentCharacterIndex();
                    EntityAvatar currentAvatar = activeTeam.get(currentIndex);
                    if(healData.healAll) {
                        needHealAvatars = activeTeam;
                    }
                    else {
                        needHealAvatars.add(currentAvatar);
                    }

                    maxHP = currentAvatar.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
                    curAttack = currentAvatar.getFightProperty(FightProperty.FIGHT_PROP_CUR_ATTACK);
                    curDefense = currentAvatar.getFightProperty(FightProperty.FIGHT_PROP_CUR_DEFENSE);
                    switch(fightPropertyType) {
                        case 0:
                            healAmount = ratio * maxHP + base;
                            break;
                        case 1:
                            healAmount = ratio * curAttack + base;
                            break;
                        case 2:
                            healAmount = ratio * curDefense + base;
                            break;
                    }
                    for(int k = 0 ; k < needHealAvatars.size() ; k ++) {
                        EntityAvatar avatar = needHealAvatars.get(k);
                        avatar.heal(healAmount);
                    }
                }
                break;
            }
        }
    }
}
