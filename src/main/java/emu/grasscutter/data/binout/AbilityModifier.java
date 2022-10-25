package emu.grasscutter.data.binout;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import emu.grasscutter.data.common.DynamicFloat;

public class AbilityModifier implements Serializable {
    private static final long serialVersionUID = -2001232313615923575L;

    @SerializedName(value="onAdded", alternate={"KCICDEJLIJD"})
    public AbilityModifierAction[] onAdded;
    @SerializedName(value="onThinkInterval", alternate={"PBDDACFFPOE"})
    public AbilityModifierAction[] onThinkInterval;
    public AbilityModifierAction[] onRemoved;
    public DynamicFloat duration = DynamicFloat.ZERO;

    public static class AbilityModifierAction {
        public enum Type {
            ActCameraRadialBlur, ActCameraShake, AddAvatarSkillInfo, AddChargeBarValue,
            AddClimateMeter, AddElementDurability, AddGlobalValue, AddGlobalValueToTarget,
            AddRegionalPlayVarValue, ApplyModifier, AttachAbilityStateResistance, AttachBulletAimPoint,
            AttachEffect, AttachEffectFirework, AttachElementTypeResistance, AttachModifier,
            AttachUIEffect, AvatarCameraParam, AvatarEnterCameraShot, AvatarEnterFocus,
            AvatarEnterViewBias, AvatarExitCameraShot, AvatarExitClimb, AvatarExitFocus,
            AvatarExitViewBias, AvatarShareCDSkillStart, AvatarSkillStart, BroadcastNeuronStimulate,
            CalcDvalinS04RebornPoint, CallLuaTask, ChangeEnviroWeather, ChangeFollowDampTime,
            ChangeGadgetUIInteractHint, ChangePlayMode, ChangeTag, ChangeUGCRayTag,
            ClearEndura, ClearGlobalPos, ClearGlobalValue, ClearLocalGadgets,
            ClearLockTarget, ClearPos, ConfigAbilityAction, ControlEmotion,
            CopyGlobalValue, CreateGadget, CreateMovingPlatform, CreateTile,
            DamageByAttackValue, DebugLog, DestroyTile, DoBlink,
            DoTileAction, DoWatcherSystemAction, DoWidgetSystemAction, DropSubfield,
            DummyAction, DungeonFogEffects, ElementAttachForActivityGacha, EnableAIStealthy,
            EnableAfterImage, EnableAvatarFlyStateTrail, EnableAvatarMoveOnWater, EnableBulletCollisionPluginTrigger,
            EnableGadgetIntee, EnableHeadControl, EnableHitBoxByName, EnableMainInterface,
            EnablePartControl, EnablePositionSynchronization, EnablePushColliderName, EnableRocketJump,
            EnableSceneTransformByName, EnterCameraLock, EntityDoSkill, EquipAffixStart,
            ExecuteGadgetLua, FireAISoundEvent, FireChargeBarEffect, FireEffect,
            FireEffectFirework, FireEffectForStorm, FireFishingEvent, FireHitEffect,
            FireSubEmitterEffect, FireUIEffect, FixedMonsterRushMove, ForceAirStateFly,
            ForceEnableShakeOffButton, GenerateElemBall, GetFightProperty, GetInteractIdToGlobalValue,
            GetPos, HealHP, HideUIBillBoard, IgnoreMoveColToRockCol,
            KillGadget, KillPlayEntity, KillSelf, KillServerGadget,
            LoseHP, ModifyAvatarSkillCD, ModifyVehicleSkillCD, PlayEmoSync,
            Predicated, PushDvalinS01Process, PushInterActionByConfigPath, PushPos,
            Randomed, ReTriggerAISkillInitialCD, RefreshUICombatBarLayout, RegisterAIActionPoint,
            ReleaseAIActionPoint, RemoveAvatarSkillInfo, RemoveModifier, RemoveModifierByAbilityStateResistanceID,
            RemoveServerBuff, RemoveUniqueModifier, RemoveVelocityForce, Repeated,
            ResetAIAttackTarget, ResetAIResistTauntLevel, ResetAIThreatBroadcastRange, ResetAnimatorTrigger,
            ReviveDeadAvatar, ReviveElemEnergy, ReviveStamina, SectorCityManeuver,
            SendEffectTrigger, SendEffectTriggerToLineEffect, SendEvtElectricCoreMoveEnterP1, SendEvtElectricCoreMoveInterrupt,
            ServerLuaCall, ServerLuaTriggerEvent, ServerMonsterLog, SetAIHitFeeling,
            SetAISkillCDAvailableNow, SetAISkillCDMultiplier, SetAISkillGCD, SetAnimatorBool,
            SetAnimatorFloat, SetAnimatorInt, SetAnimatorTrigger, SetAvatarCanShakeOff,
            SetAvatarHitBuckets, SetCanDieImmediately, SetChargeBarValue, SetDvalinS01FlyState,
            SetEmissionScaler, SetEntityScale, SetExtraAbilityEnable, SetExtraAbilityState,
            SetGlobalDir, SetGlobalPos, SetGlobalValue, SetGlobalValueByTargetDistance,
            SetGlobalValueToOverrideMap, SetKeepInAirVelocityForce, SetMaterialParamFloatByTransform, SetNeuronEnable,
            SetOverrideMapValue, SetPartControlTarget, SetPoseBool, SetPoseFloat,
            SetPoseInt, SetRandomOverrideMapValue, SetRegionalPlayVarValue, SetSelfAttackTarget,
            SetSkillAnchor, SetSpecialCamera, SetSurroundAnchor, SetSystemValueToOverrideMap,
            SetTargetNumToGlobalValue, SetUICombatBarAsh, SetUICombatBarSpark, SetVelocityIgnoreAirGY,
            SetWeaponAttachPointRealName, SetWeaponBindState, ShowExtraAbility, ShowProgressBarAction,
            ShowReminder, ShowScreenEffect, ShowTextMap, ShowUICombatBar,
            StartDither, SumTargetWeightToSelfGlobalValue, Summon, SyncToStageScript,
            TriggerAbility, TriggerAttackEvent, TriggerAttackTargetMapEvent, TriggerAudio,
            TriggerAuxWeaponTrans, TriggerBullet, TriggerCreateGadgetToEquipPart, TriggerDropEquipParts,
            TriggerFaceAnimation, TriggerGadgetInteractive, TriggerHideWeapon, TriggerSetCastShadow,
            TriggerSetPassThrough, TriggerSetRenderersEnable, TriggerSetShadowRamp, TriggerSetVisible,
            TriggerTaunt, TriggerThrowEquipPart, TriggerUGCGadgetMove, TryFindBlinkPoint,
            TryFindBlinkPointByBorn, TryTriggerPlatformStartMove, TurnDirection, TurnDirectionToPos,
            UpdateReactionDamage, UseSkillEliteSet, WidgetSkillStart;
        }
        @SerializedName("$type")
        public Type type;
        public String target;
        @SerializedName(value = "amount", alternate = "PDLLIFICICJ")
        public DynamicFloat amount = DynamicFloat.ZERO;
        public DynamicFloat amountByCasterAttackRatio = DynamicFloat.ZERO;
        public DynamicFloat amountByCasterCurrentHPRatio = DynamicFloat.ZERO;
        public DynamicFloat amountByCasterMaxHPRatio = DynamicFloat.ZERO;
        public DynamicFloat amountByGetDamage = DynamicFloat.ZERO;
        public DynamicFloat amountByTargetCurrentHPRatio = DynamicFloat.ZERO;
        public DynamicFloat amountByTargetMaxHPRatio = DynamicFloat.ZERO;
        @SerializedName(value = "ignoreAbilityProperty", alternate = "HHFGADCJJDI")
        public boolean ignoreAbilityProperty;
        public String modifierName;
    }

    //The following should be implemented into DynamicFloat if older resource formats need to be supported
    // public static class AbilityModifierValue {
    //     public boolean isFormula;
    //     public boolean isDynamic;
    //     public String dynamicKey;
    // }
}
