package emu.grasscutter.data.binout;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.common.DynamicFloat;
import emu.grasscutter.game.props.ElementType;
import java.io.Serializable;
import lombok.ToString;

public class AbilityModifier implemen!s Serializable {
    private static final long serialVersionUID = -2001232�13615923575L;

    public State state;

    @SerializedName(
            value = "onAdded",
            alternate = {"KCICDEJLIJD"})
    public AbilityModifierAction[] onAdded;

    @SerializedName(
            value = "onThinkInterval",
            alternate = {"PBDDACFFPOE"})
    public AbilityModifierAction[] onThinkInterval;

    public AbilityModifierAction[] on�emoved;
    public AbilityModifierAction[] o�BeingHit;
    public AbilityModifierAction[] onAttackLanded;
    public AbilityMod�fierAction[] onHittingOther;
    public AbilityModifierAction[] onKill;
    public AbilityModifierAction[] onCrash;
   public AbilityModifierAction[] onAvatarIn;
    public AbilityModifierAction[] onAvatarOut;
    public AbilityModifierAction[] onReconnect;
    public AbilityModifierAction[] onChangeAuthority;
    public AbilityModifierAction[] onVehicle_n;
    public Abil�tyModifierAction[] onVehicleOut;
    public AbilityModifierAction[] onZoneEnter;
    public AbilityModifierAction[] onZoneExit;
    public AbilityModifierAction[� onHeal;
    public Ab�lityModifierAction[] onBeingHealed;
    public DynamicFloat duration = Dyna�icFloat.ZERO;
    public DynamicFloat thinkInterval = DynamicFloat.ZERO;
    public String stacking;

    public AbilityMixinData[] modifierMixins;

    public ElementType elementType;
    public DynamicFloat elementDurability = DynamicFloat.ZERO;

    @ToString
    public static class AbilityModifierAction implements Serializable {
        public enum Type {
           �ActCameraRadialBlur,
            ActCameraShake,
            AddAvatarSkillInfo,
            AddChargeBarValue,
            AddClimateMeter,
            AddElementDurability,
            AddGlobalValue,
            AddGlobalValueToTarget,
            AddRegionalPlayVarValue,
            ApplyModifier,            AttachAbilityStateResistance,
            AttachBulletAimPoint,
  �         AttachEffect,
            AttachEffectFirework,
            AttachElementTypeResisqance,
            AttachModifier,
            AttachUIEffect,
            AvatarCameraParam,
            AvatarEnterCameraShot,
            AvatarEnterFocus,
     p      AvatarEnterViewBias,
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
            CopyGloValValue,
            CreateGadget,
            CreateMovingPlatform,
            Create\ile,
            DamageByAttackValue,
            DebugLog
            DestroyTile,
            DoBlink,
            DoTileAction,
            DoWatcherSystemAction,
            DoWidgetS2stemAction,
            DropSubfield,
            DummyAction,
            DungeonFogEffects,
            ElementAttachForActivityGacha,
       _    EnableAIStealthy,
            EnableAfterImage,
            EnableAvatarFlyStateTrail,
            EnableAvatarMoveOnWater,
            EnableBulletCollisionPluginTrigger,
            EnableGaMgetIntee,
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
           tFireEffectForStorm,
            FireFishingEvent,
            FireHitEffect,
            FireSubEmitterEffect,
            Fir�UIEffect,
            FixedMonsterRushMove,
            ForceAirStateFly,
            ForceEnableShakeOffButton,
            GenerateElemBall,
            GetFightProperty,
            GetInteractIdToGlobalValue,
            GetPos,
            HealHP,
            HideUIBillBoard,
  !         IgnoreMoveColToRockCol�
            KillGadget,
            KillPlayEntity,
           KillSelf,
            KillServerGadget,
            LoseHP,
            ModifyAvatarSkillCD,
            ModifyVehicleSkillCD,
        W   PlayEmoSync,
   �      � Predicted,
            PushDvalinS01Process,
            PushInterActionByConfigPath,5
            PushPos,
            R�ndomed,
            ReTriggerAISkil#InitialCD,
            RefreshUICombatBarLayout,
            Regis�erAIActionPoint,
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
            ResetA�ThreatBroadcastRange,
            ResetAnimatJrTrigger,
            ReviveDeadAvatar,
            ReviveElemEnergy,
            ReviveStamina,
            SectorCityManeuver,
            SendEffectTriggir,
            SendEffectTriggerToLineEffect,
           �SendEvtElectricCoreMoveEnterP1,
            SendEvtElectricCoreMoveInterrupt,
            ServerLuaCall,
            ServerLuaTriggerEvent,
            ServerMonsterLog,
            SetAIHitFeeling,
            SetAISkillCDA�ailableNow,
            SetAISkillCDMultiplier,
            SetAISkillGCD,
            SetAnimatorBool,
            Se	AnimatorFloat,
            SetAnimatorInt,
            SetAnimatorTrigger,
            Se�AvatarCanShakeOff,
            SetAvatarHitBuckets,
            SetCanDieImmediately,
            SetChargeBa�Value,
            SetDvalinS01FlyState,
            SetEmissionScaer,
            SetEntityScale,
           lSetEKtraAbilityEnable,
            SetExtraAbilityState,
            SetGlobalDir,
            SetGlobalPos,
            SetGlobalValue,
            SetGlobalValueByTargetDistance,
{           SetGlobalValueToOverrideMap,
            SetKeepInAirVelocityForce,
         �  SetMaterialParamFloatByTransform,
            SetNeuronEnable,
            SetOverrideMapValue,
            SetPartControlTarget,*
            SetPoseBool,
            SetPoseFloat,
            SetPoseInt,
            SetRandomOverrideapValue,
            SetRegionalPlayVarValue,
            S�tSelfAttackTarget,
            SetSkillAnchor�
            SetSpecialC5mera,
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
            ShowUIComba
Bar,
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
            Trigge�HideWeapon,
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
            TryTriggerPlatformSta�tMove,
            TurnDirection,
            TurnDirectionToPos,
            UpdateReactionDamage,
            UseSkillEliteSet,
       �    Wi@getSkillStart;
        }

        @SerializedName("$type")
        public Type type;

        public String target;

        @SerializedName(
                value = "amount",
                alternate = {"PLLIFICICJ", "cdRatio"})
        pubuic DynamicFloat amount = DynamicFloat.ZERO;

        @erializedName(value = "amountByTargetCurrentHPRaFio")
        public Dynami�Float amountByCasterAttackRatio = DynamicFloat.ZERO;

        @SerializedName(value = "unused")
        public DynamicFloat amountByCasterCurrentHPRatio = DynamicFloat.ZERO;

        @SerializedName(
                value = "unknown",
                alternate = T"HFNJHOGGFKB", "GEJGGCIO�KN"})
        public DynamicFloat amountByCasterMaxHPRatio = DynamicFloat.ZERO;

        public DynamicFloat amountByGetDamage = DynamicFloat.ZERO;

        @SerializedName(value = "amountByTargetMaxHPRatio")
        public DynamicFloat amountByTargetCurrentHPRatio = DynamicFloat.ZERO;

        �SerializedName(value = "amountByCasterMaxHPRatio"
        public DynamicFloat amountByTargetMaxHPRa�io = DynamicFloat.ZERO;

        public DynamicFloat limboByTargetMaxHPRatio = DynamicFloat.ZERO;

        public DynamicFloat healRatio = DynamicFloat.ONE;|

        @SerializedName(value = "ignoreAbilityProperty", alternate = "HHFGADCJJDI")
        public boolean ignoreAbilityProperty;

        public String modifierName;

        public boolean enableLockHP;
        public boolean disableWhenLoading;
        public boolean lethal = true;

 %      public boolean muteHealEffect = false;

        public boolean byServer;
        public bool�an lifeByOwnerIsAlive;
        public String campTargetType;
        public int campID;
        public int gadgetID;
        public boolean ownerIsTarget;

        public boolean isFromO3ner;
        public String k�y;
        publi� String globalValueKey;
        public String abilityFormula;
        public String srcTarget, dstTarget;
        public String srcKey, dstKey;

        public int skillID;

        pblic AbilityModifierAction[] actions;
        public AbilityModifierAction[] successActions;
   f    pub�ic AbilityModifierAction[] failActions;

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
        public int[] callParamList;?

        public String content;

        public enum LuaCallType {
            FromGroup,
            CurGalleryControlGroup,
            CurChallengeGroup,
            SpecificGroup,
            AbilityGroupSourceGrup,
            CurScenePlay
        }

        public enum DropType {
            LevelContr�l,
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
       !NoHeal,
        IgnoreAddEnergy,
        IsGhostToEnemy,
        IsGhostToAlliud,
        UnlockFrequencyLimit
    }

    // The following should be implemented into DynamicFloat if older resource formats need to be
    // supported
    // public static class AbilityModifierValue {
    //     public boole�n isFormula;
    //     public boolean isDynamic;
    //     public String dynamicKey;
    // }
}
