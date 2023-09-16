package emu.grasscutter.data.binout;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.common.DynamicFloat;
import emu.grasscutter.game.props.ElementType;
import java.io.Serializable;
import lombok.ToString;

public class AbilityModifier implements Serializable {
    private static final long serialVersionUID = -2001232313615923575L;

    public State state;

    @SerializedName(
            value = "onAdded",
            alternate = {"KCICDEJLIJD"})
    public AbilityModifierAction[] onAdded;

    @SerializedName(
            value = "onThinkInterval",
            alternate = {"PBDDACFFPOE"})
    public AbilityModifierAction[] onThinkInterval;

    public AbilityModifierAction[] onRemoved;
    public AbilityModifierAction[] onBeingHit;
    public AbilityModifierAction[] onAttackLanded;
    public AbilityModifierAction[] onHittingOther;
    public AbilityModifierAction[] onKill;
    public AbilityModifierAction[] onCrash;
    public AbilityModifierAction[] onAvatarIn;
    public AbilityModifierAction[] onAvatarOut;
    public AbilityModifierAction[] onReconnect;
    public AbilityModifierAction[] onChangeAuthority;
    public AbilityModifierAction[] onVehicleIn;
    public AbilityModifierAction[] onVehicleOut;
    public AbilityModifierAction[] onZoneEnter;
    public AbilityModifierAction[] onZoneExit;
    public AbilityModifierAction[] onHeal;
    public AbilityModifierAction[] onBeingHealed;
    public DynamicFloat duration = DynamicFloat.ZERO;
    public DynamicFloat thinkInterval = DynamicFloat.ZERO;
    public String stacking;

    public AbilityMixinData[] modifierMixins;

    public ElementType elementType;
    public DynamicFloat elementDurability = DynamicFloat.ZERO;

    @ToString
    public static class AbilityModifierAction implements Serializable {
        public enum Type {
            ActCameraRadialBlur,
            ActCameraShake,
            AddAvatarSkillInfo,
            AddChargeBarValue,
            AddClimateMeter,
            AddElementDurability,
            AddGlobalValue,
            AddGlobalValueToTarget,
            AddRegionalPlayVarValue,
            ApplyModifier,
            AttachAbilityStateResistance,
            AttachBulletAimPoint,
            AttachEffect,
            AttachEffectFirework,
            AttachElementTypeResistance,
            AttachModifier,
            AttachUIEffect,
            AvatarCameraParam,
            AvatarEnterCameraShot,
            AvatarEnterFocus,
            AvatarEnterViewBias,
            AvatarExitCameraShot,
            AvatarExitClimb,
            AvatarExitFocus,
            AvatarExitViewBias,
            AvatarShareCDSkillStart,
            AvatarSkillStart,
            BroadcastNeuronStimulate,
            CalcDvalinS04RebornPoint,
            CallLuaTask,
            ChangeEnviroWeather,
            ChangeFollowDampTime,
            ChangeGadgetUIInteractHint,
            ChangePlayMode,
            ChangeTag,
            ChangeUGCRayTag,
            ClearEndura,
            ClearGlobalPos,
            ClearGlobalValue,
            ClearLocalGadgets,
            ClearLockTarget,
            ClearPos,
            ConfigAbilityAction,
            ControlEmotion,
            CopyGlobalValue,
            CreateGadget,
            CreateMovingPlatform,
            CreateTile,
            DamageByAttackValue,
            DebugLog,
            DestroyTile,
            DoBlink,
            DoTileAction,
            DoWatcherSystemAction,
            DoWidgetSystemAction,
            DropSubfield,
            DummyAction,
            DungeonFogEffects,
            ElementAttachForActivityGacha,
            EnableAIStealthy,
            EnableAfterImage,
            EnableAvatarFlyStateTrail,
            EnableAvatarMoveOnWater,
            EnableBulletCollisionPluginTrigger,
            EnableGadgetIntee,
            EnableHeadControl,
            EnableHitBoxByName,
            EnableMainInterface,
            EnablePartControl,
            EnablePositionSynchronization,
            EnablePushColliderName,
            EnableRocketJump,
            EnableSceneTransformByName,
            EnterCameraLock,
            EntityDoSkill,
            EquipAffixStart,
            ExecuteGadgetLua,
            FireAISoundEvent,
            FireChargeBarEffect,
            FireEffect,
            FireEffectFirework,
            FireEffectForStorm,
            FireFishingEvent,
            FireHitEffect,
            FireSubEmitterEffect,
            FireUIEffect,
            FixedMonsterRushMove,
            ForceAirStateFly,
            ForceEnableShakeOffButton,
            GenerateElemBall,
            GetFightProperty,
            GetInteractIdToGlobalValue,
            GetPos,
            HealHP,
            HideUIBillBoard,
            IgnoreMoveColToRockCol,
            KillGadget,
            KillPlayEntity,
            KillSelf,
            KillServerGadget,
            LoseHP,
            ModifyAvatarSkillCD,
            ModifyVehicleSkillCD,
            PlayEmoSync,
            Predicated,
            PushDvalinS01Process,
            PushInterActionByConfigPath,
            PushPos,
            Randomed,
            ReTriggerAISkillInitialCD,
            RefreshUICombatBarLayout,
            RegisterAIActionPoint,
            ReleaseAIActionPoint,
            RemoveAvatarSkillInfo,
            RemoveModifier,
            RemoveModifierByAbilityStateResistanceID,
            RemoveServerBuff,
            RemoveUniqueModifier,
            RemoveVelocityForce,
            Repeated,
            ResetAIAttackTarget,
            ResetAIResistTauntLevel,
            ResetAIThreatBroadcastRange,
            ResetAnimatorTrigger,
            ReviveDeadAvatar,
            ReviveElemEnergy,
            ReviveStamina,
            SectorCityManeuver,
            SendEffectTrigger,
            SendEffectTriggerToLineEffect,
            SendEvtElectricCoreMoveEnterP1,
            SendEvtElectricCoreMoveInterrupt,
            ServerLuaCall,
            ServerLuaTriggerEvent,
            ServerMonsterLog,
            SetAIHitFeeling,
            SetAISkillCDAvailableNow,
            SetAISkillCDMultiplier,
            SetAISkillGCD,
            SetAnimatorBool,
            SetAnimatorFloat,
            SetAnimatorInt,
            SetAnimatorTrigger,
            SetAvatarCanShakeOff,
            SetAvatarHitBuckets,
            SetCanDieImmediately,
            SetChargeBarValue,
            SetDvalinS01FlyState,
            SetEmissionScaler,
            SetEntityScale,
            SetExtraAbilityEnable,
            SetExtraAbilityState,
            SetGlobalDir,
            SetGlobalPos,
            SetGlobalValue,
            SetGlobalValueByTargetDistance,
            SetGlobalValueToOverrideMap,
            SetKeepInAirVelocityForce,
            SetMaterialParamFloatByTransform,
            SetNeuronEnable,
            SetOverrideMapValue,
            SetPartControlTarget,
            SetPoseBool,
            SetPoseFloat,
            SetPoseInt,
            SetRandomOverrideMapValue,
            SetRegionalPlayVarValue,
            SetSelfAttackTarget,
            SetSkillAnchor,
            SetSpecialCamera,
            SetSurroundAnchor,
            SetSystemValueToOverrideMap,
            SetTargetNumToGlobalValue,
            SetUICombatBarAsh,
            SetUICombatBarSpark,
            SetVelocityIgnoreAirGY,
            SetWeaponAttachPointRealName,
            SetWeaponBindState,
            ShowExtraAbility,
            ShowProgressBarAction,
            ShowReminder,
            ShowScreenEffect,
            ShowTextMap,
            ShowUICombatBar,
            StartDither,
            SumTargetWeightToSelfGlobalValue,
            Summon,
            SyncToStageScript,
            TriggerAbility,
            TriggerAttackEvent,
            TriggerAttackTargetMapEvent,
            TriggerAudio,
            TriggerAuxWeaponTrans,
            TriggerBullet,
            TriggerCreateGadgetToEquipPart,
            TriggerDropEquipParts,
            TriggerFaceAnimation,
            TriggerGadgetInteractive,
            TriggerHideWeapon,
            TriggerSetCastShadow,
            TriggerSetPassThrough,
            TriggerSetRenderersEnable,
            TriggerSetShadowRamp,
            TriggerSetVisible,
            TriggerTaunt,
            TriggerThrowEquipPart,
            TriggerUGCGadgetMove,
            TryFindBlinkPoint,
            TryFindBlinkPointByBorn,
            TryTriggerPlatformStartMove,
            TurnDirection,
            TurnDirectionToPos,
            UpdateReactionDamage,
            UseSkillEliteSet,
            WidgetSkillStart;
        }

        @SerializedName("$type")
        public Type type;

        public String target;

        @SerializedName(
                value = "amount",
                alternate = {"LNFMOCKIAGK", "PDLLIFICICJ", "cdRatio"})
        public DynamicFloat amount = DynamicFloat.ZERO;

        @SerializedName(
                value = "amountByTargetCurrentHPRatio",
                alternate = {"GMFELAKANEF"})
        public DynamicFloat amountByCasterAttackRatio = DynamicFloat.ZERO;

        @SerializedName(value = "unknown2")
        public DynamicFloat amountByCasterCurrentHPRatio = DynamicFloat.ZERO;

        @SerializedName(
                value = "amountByCasterMaxHPRatio",
                alternate = {"PKPBLCNMPIG", "HFNJHOGGFKB", "GEJGGCIOLKN"})
        public DynamicFloat amountByCasterMaxHPRatio = DynamicFloat.ZERO;

        public DynamicFloat amountByGetDamage = DynamicFloat.ZERO;

        @SerializedName(value = "amountByTargetMaxHPRatio")
        public DynamicFloat amountByTargetCurrentHPRatio = DynamicFloat.ZERO;

        @SerializedName(value = "unknown1", alternate = "GGLMMJHNGMO")
        public DynamicFloat amountByTargetMaxHPRatio = DynamicFloat.ZERO;

        public DynamicFloat limboByTargetMaxHPRatio = DynamicFloat.ZERO;

        public DynamicFloat healRatio = DynamicFloat.ONE;

        @SerializedName(value = "ignoreAbilityProperty", alternate = "HHFGADCJJDI")
        public boolean ignoreAbilityProperty;

        public String modifierName;

        public boolean enableLockHP;
        public boolean disableWhenLoading;
        public boolean lethal = true;

        public boolean muteHealEffect = false;

        public boolean byServer;
        public boolean lifeByOwnerIsAlive;
        public String campTargetType;
        public int campID;
        public int gadgetID;
        public boolean ownerIsTarget;

        public boolean isFromOwner;
        public String key;
        public String globalValueKey;
        public String abilityFormula;
        public String srcTarget, dstTarget;
        public String srcKey, dstKey;

        public int skillID;

        public AbilityModifierAction[] actions;
        public AbilityModifierAction[] successActions;
        public AbilityModifierAction[] failActions;

        public DropType dropType = DropType.LevelControl;
        public DynamicFloat baseEnergy;
        public DynamicFloat ratio = DynamicFloat.ONE;
        public int configID;

        public DynamicFloat valueRangeMin;
        public DynamicFloat valueRangeMax;
        public String overrideMapKey;

        public int param1;
        public int param2;
        public int param3;

        public String funcName;
        public LuaCallType luaCallType;

        @SerializedName("CallParamList")
        public int[] callParamList;

        public String content;

        public enum LuaCallType {
            FromGroup,
            CurGalleryControlGroup,
            CurChallengeGroup,
            SpecificGroup,
            AbilityGroupSourceGroup,
            CurScenePlay
        }

        public enum DropType {
            LevelControl,
            BigWorldOnly,
            ForceDrop
        }
    }

    public enum State {
        LockHP,
        Invincible,
        ElementFreeze,
        ElementPetrifaction,
        DenyLockOn,
        Limbo,
        NoHeal,
        IgnoreAddEnergy,
        IsGhostToEnemy,
        IsGhostToAllied,
        UnlockFrequencyLimit
    }

    // The following should be implemented into DynamicFloat if older resource formats need to be
    // supported
    // public static class AbilityModifierValue {
    //     public boolean isFormula;
    //     public boolean isDynamic;
    //     public String dynamicKey;
    // }
}
