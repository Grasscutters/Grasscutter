package emu.grasscutter.game.managers.energy;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarSkillDepotData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.MonsterData.HpDrops;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.entity.EntityClientGadget;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.BasePlayerManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.MonsterType;
import emu.grasscutter.game.props.WeaponType;
import emu.grasscutter.net.proto.AbilityActionGenerateElemBallOuterClass.AbilityActionGenerateElemBall;
import emu.grasscutter.net.proto.AbilityIdentifierOuterClass.AbilityIdentifier;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.AttackResultOuterClass.AttackResult;
import emu.grasscutter.net.proto.ChangeEnergyReasonOuterClass.ChangeEnergyReason;
import emu.grasscutter.net.proto.EvtBeingHitInfoOuterClass.EvtBeingHitInfo;
import emu.grasscutter.net.proto.PropChangeReasonOuterClass.PropChangeReason;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static emu.grasscutter.config.Configuration.GAME_OPTIONS;
import static java.util.Map.entry;

import com.google.gson.reflect.TypeToken;
import com.google.protobuf.InvalidProtocolBufferException;

public class EnergyManager extends BasePlayerManager {
    private final Map<EntityAvatar, Integer> avatarNormalProbabilities;
//    energyUsage for each player
    private Boolean energyUsage;
    private final static Int2ObjectMap<List<EnergyDropInfo>> energyDropData = new Int2ObjectOpenHashMap<>();
    private final static Int2ObjectMap<List<SkillParticleGenerationInfo>> skillParticleGenerationData = new Int2ObjectOpenHashMap<>();

    public EnergyManager(Player player) {
        super(player);
        this.avatarNormalProbabilities = new HashMap<>();
        this.energyUsage=GAME_OPTIONS.energyUsage;
    }

    public static void initialize() {
        // Read the data we need for monster energy drops.
        try (Reader fileReader = DataLoader.loadReader("EnergyDrop.json")) {
            List<EnergyDropEntry> energyDropList = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, EnergyDropEntry.class).getType());

            for (EnergyDropEntry entry : energyDropList) {
                energyDropData.put(entry.getDropId(), entry.getDropList());
            }

            Grasscutter.getLogger().debug("Energy drop data successfully loaded.");
        }
        catch (Exception ex) {
            Grasscutter.getLogger().error("Unable to load energy drop data.", ex);
        }

        // Read the data for particle generation from skills
        try (Reader fileReader = new InputStreamReader(DataLoader.load("SkillParticleGeneration.json"))) {
            List<SkillParticleGenerationEntry> skillParticleGenerationList = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, SkillParticleGenerationEntry.class).getType());

            for (SkillParticleGenerationEntry entry : skillParticleGenerationList) {
                skillParticleGenerationData.put(entry.getAvatarId(), entry.getAmountList());
            }

            Grasscutter.getLogger().debug("Skill particle generation data successfully loaded.");
        }
        catch (Exception ex) {
            Grasscutter.getLogger().error("Unable to load skill particle generation data data.", ex);
        }
    }

    /**********
     Particle creation for elemental skills.
     **********/
    private int getBallCountForAvatar(int avatarId) {
        // We default to two particles.
        int count = 2;

        // If we don't have any data for this avatar, stop.
        if (!skillParticleGenerationData.containsKey(avatarId)) {
            Grasscutter.getLogger().warn("No particle generation data for avatarId {} found.", avatarId);
        }
        // If we do have data, roll for how many particles we should generate.
        else {
            int roll = ThreadLocalRandom.current().nextInt(0, 100);
            int percentageStack = 0;
            for (SkillParticleGenerationInfo info : skillParticleGenerationData.get(avatarId)) {
                int chance = info.getChance();
                percentageStack += chance;
                if (roll < percentageStack) {
                    count = info.getValue();
                    break;
                }
            }
        }

        // Done.
        return count;
    }

    private int getBallIdForElement(ElementType element) {
        // If we have no element, we default to an elementless particle.
        if (element == null) {
            return 2024;
        }

        // Otherwise, we determin the particle's ID based on the element.
        return switch (element) {
            case Fire -> 2017;
            case Water -> 2018;
            case Grass -> 2019;
            case Electric -> 2020;
            case Wind -> 2021;
            case Ice -> 2022;
            case Rock -> 2023;
            default -> 2024;
        };
    }

    public void handleGenerateElemBall(AbilityInvokeEntry invoke) throws InvalidProtocolBufferException {
        // ToDo:
        // This is also called when a weapon like Favonius Warbow etc. creates energy through its passive.
        // We are not handling this correctly at the moment.

        // Get action info.
        AbilityActionGenerateElemBall action = AbilityActionGenerateElemBall.parseFrom(invoke.getAbilityData());
        if (action == null) {
            return;
        }

        // Default to an elementless particle.
        int itemId = 2024;

        // Generate 2 particles by default.
        int amount = 2;

        // Try to get the casting avatar from the player's party.
        Optional<EntityAvatar> avatarEntity = getCastingAvatarEntityForEnergy(invoke.getEntityId());

        // Bug: invokes twice sometimes, Ayato, Keqing
        // ToDo: deal with press, hold difference. deal with charge(Beidou, Yunjin)
        if (avatarEntity.isPresent()) {
            Avatar avatar = avatarEntity.get().getAvatar();

            if (avatar != null) {
                int avatarId = avatar.getAvatarId();
                AvatarSkillDepotData skillDepotData = avatar.getSkillDepot();

                // Determine how many particles we need to create for this avatar.
                amount = this.getBallCountForAvatar(avatarId);

                // Determine the avatar's element, and based on that the ID of the
                // particles we have to generate.
                if (skillDepotData != null) {
                    ElementType element = skillDepotData.getElementType();
                    itemId = getBallIdForElement(element);
                }
            }
        }

        // Generate the particles.
        for (int i = 0; i < amount; i++) {
            generateElemBall(itemId, new Position(action.getPos()), 1);
        }
    }

    /**********
     Pickup of elemental particles and orbs.
     **********/
    public void handlePickupElemBall(GameItem elemBall) {
        // Check if the item is indeed an energy particle/orb.
        if (elemBall.getItemId() < 2001 ||elemBall.getItemId() > 2024) {
            return;
        }

        // Determine the base amount of energy given by the particle/orb.
        // Particles have a base amount of 1.0, and orbs a base amount of 3.0.
        float baseEnergy = (elemBall.getItemId() <= 2008) ? 3.0f : 1.0f;

        // Add energy to every team member.
        for (int i = 0; i < this.player.getTeamManager().getActiveTeam().size(); i++) {
            EntityAvatar entity = this.player.getTeamManager().getActiveTeam().get(i);

            // On-field vs off-field multiplier.
            // The on-field character gets no penalty.
            // Off-field characters get a penalty depending on the team size, as follows:
            // 		- 2 character team: 0.8
            // 		- 3 character team: 0.7
            // 		- 4 character team: 0.6
            // 		- etc.
            // We set a lower bound of 0.1 here, to avoid gaining no or negative energy.
            float offFieldPenalty =
                    (this.player.getTeamManager().getCurrentCharacterIndex() == i)
                            ? 1.0f
                            : 1.0f - this.player.getTeamManager().getActiveTeam().size() * 0.1f;
            offFieldPenalty = Math.max(offFieldPenalty, 0.1f);

            // Same element/neutral bonus.
            // Same-element characters get a bonus of *3, while different-element characters get no bonus at all.
            // For neutral particles/orbs, the multiplier is always *2.
            if (entity.getAvatar().getSkillDepot() == null) {
                continue;
            }

            ElementType avatarElement = entity.getAvatar().getSkillDepot().getElementType();
            ElementType ballElement = switch (elemBall.getItemId()) {
                case 2001, 2017 -> ElementType.Fire;
                case 2002, 2018 -> ElementType.Water;
                case 2003, 2019 -> ElementType.Grass;
                case 2004, 2020 -> ElementType.Electric;
                case 2005, 2021 -> ElementType.Wind;
                case 2006, 2022 -> ElementType.Ice;
                case 2007, 2023 -> ElementType.Rock;
                default -> null;
            };

            float elementBonus = (ballElement == null) ? 2.0f : (avatarElement == ballElement) ? 3.0f : 1.0f;

            // Add the energy.
            entity.addEnergy(baseEnergy * elementBonus * offFieldPenalty * elemBall.getCount(), PropChangeReason.PROP_CHANGE_REASON_ENERGY_BALL);
        }
    }

    /**********
     Energy generation for NAs/CAs.
     **********/
    private void generateEnergyForNormalAndCharged(EntityAvatar avatar) {
        // This logic is based on the descriptions given in
        //     https://genshin-impact.fandom.com/wiki/Energy#Energy_Generated_by_Normal_Attacks
        //     https://library.keqingmains.com/combat-mechanics/energy#auto-attacking
        // Those descriptions are lacking in some information, so this implementation most likely
        // does not fully replicate the behavior of the official server. Open questions:
        //    - Does the probability for a character reset after some time?
        //    - Does the probability for a character reset when switching them out?
        //    - Does this really count every individual hit separately?

        // Get the avatar's weapon type.
        WeaponType weaponType = avatar.getAvatar().getAvatarData().getWeaponType();

        // Check if we already have probability data for this avatar. If not, insert it.
        if (!this.avatarNormalProbabilities.containsKey(avatar)) {
            this.avatarNormalProbabilities.put(avatar, weaponType.getEnergyGainInitialProbability());
        }

        // Roll for energy.
        int currentProbability = this.avatarNormalProbabilities.get(avatar);
        int roll = ThreadLocalRandom.current().nextInt(0, 100);

        // If the player wins the roll, we increase the avatar's energy and reset the probability.
        if (roll < currentProbability) {
            avatar.addEnergy(1.0f, PropChangeReason.PROP_CHANGE_REASON_ABILITY, true);
            this.avatarNormalProbabilities.put(avatar, weaponType.getEnergyGainInitialProbability());
        }
        // Otherwise, we increase the probability for the next hit.
        else {
            this.avatarNormalProbabilities.put(avatar, currentProbability + weaponType.getEnergyGainIncreaseProbability());
        }
    }

    public void handleAttackHit(EvtBeingHitInfo hitInfo) {
        // Get the attack result.
        AttackResult attackRes = hitInfo.getAttackResult();

        // Make sure the attack was performed by the currently active avatar. If not, we ignore the hit.
        Optional<EntityAvatar> attackerEntity = this.getCastingAvatarEntityForEnergy(attackRes.getAttackerId());
        if (attackerEntity.isEmpty() || this.player.getTeamManager().getCurrentAvatarEntity().getId() != attackerEntity.get().getId()) {
            return;
        }

        // Make sure the target is an actual enemy.
        GameEntity targetEntity = this.player.getScene().getEntityById(attackRes.getDefenseId());
        if (!(targetEntity instanceof EntityMonster)) {
            return;
        }

        EntityMonster targetMonster = (EntityMonster)targetEntity;
        MonsterType targetType = targetMonster.getMonsterData().getType();
        if (targetType != MonsterType.MONSTER_ORDINARY && targetType != MonsterType.MONSTER_BOSS) {
            return;
        }

        // Get the ability that caused this hit.
        AbilityIdentifier ability = attackRes.getAbilityIdentifier();

        // Make sure there is no actual "ability" associated with the hit. For now, this is how we
        // identify normal and charged attacks. Note that this is not completely accurate:
        //    - Many character's charged attacks have an ability associated with them. This means that,
        //      for now, we don't identify charged attacks reliably.
        //    - There might also be some cases where we incorrectly identify something as a normal or
        //      charged attack that is not (Diluc's E?).
        //    - Catalyst normal attacks have an ability, so we don't handle those for now.
        // ToDo: Fix all of that.
        if (ability != AbilityIdentifier.getDefaultInstance()) {
            return;
        }

        // Handle the energy generation.
        this.generateEnergyForNormalAndCharged(attackerEntity.get());
    }


    /**********
     Energy logic related to using skills.
     **********/
    private void handleBurstCast(Avatar avatar, int skillId) {
        // Don't do anything if energy usage is disabled.
        if (!GAME_OPTIONS.energyUsage || !this.energyUsage) {
            return;
        }

        // If the cast skill was a burst, consume energy.
        if (avatar.getSkillDepot() != null && skillId == avatar.getSkillDepot().getEnergySkill()) {
            avatar.getAsEntity().clearEnergy(ChangeEnergyReason.CHANGE_ENERGY_REASON_SKILL_START);
        }
    }

    public void handleEvtDoSkillSuccNotify(GameSession session, int skillId, int casterId) {
        // Determine the entity that has cast the skill. Cancel if we can't find that avatar.
        Optional<EntityAvatar> caster = this.player.getTeamManager().getActiveTeam().stream()
                .filter(character -> character.getId() == casterId)
                .findFirst();

        if (caster.isEmpty()) {
            return;
        }

        Avatar avatar = caster.get().getAvatar();

        // Handle elemental burst.
        this.handleBurstCast(avatar, skillId);
    }

    /**********
     Monster energy drops.
     **********/
    private void generateElemBallDrops(EntityMonster monster, int dropId) {
        // Generate all drops specified for the given drop id.
        if (!energyDropData.containsKey(dropId)) {
            Grasscutter.getLogger().warn("No drop data for dropId {} found.", dropId);
            return;
        }

        for (EnergyDropInfo info : energyDropData.get(dropId)) {
            this.generateElemBall(info.getBallId(), monster.getPosition(), info.getCount());
        }
    }
    public void handleMonsterEnergyDrop(EntityMonster monster, float hpBeforeDamage, float hpAfterDamage) {
        // Make sure this is actually a monster.
        // Note that some wildlife also has that type, like boars or birds.
        MonsterType type = monster.getMonsterData().getType();
        if (type != MonsterType.MONSTER_ORDINARY && type != MonsterType.MONSTER_BOSS) {
            return;
        }

        // Calculate the HP tresholds for before and after the damage was taken.
        float maxHp = monster.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
        float thresholdBefore = hpBeforeDamage / maxHp;
        float thresholdAfter = hpAfterDamage / maxHp;

        // Determine the thresholds the monster has passed, and generate drops based on that.
        for (HpDrops drop : monster.getMonsterData().getHpDrops()) {
            if (drop.getDropId() == 0) {
                continue;
            }

            float threshold = drop.getHpPercent() / 100.0f;
            if (threshold < thresholdBefore && threshold >= thresholdAfter) {
                generateElemBallDrops(monster, drop.getDropId());
            }
        }

        // Handle kill drops.
        if (hpAfterDamage <= 0 && monster.getMonsterData().getKillDropId() != 0) {
            generateElemBallDrops(monster, monster.getMonsterData().getKillDropId());
        }
    }

    /**********
     Utility.
     **********/
    private void generateElemBall(int ballId, Position position, int count) {
        // Generate a particle/orb with the specified parameters.
        ItemData itemData = GameData.getItemDataMap().get(ballId);
        if (itemData == null) {
            return;
        }

        EntityItem energyBall = new EntityItem(this.getPlayer().getScene(), this.getPlayer(), itemData, position, count);
        this.getPlayer().getScene().addEntity(energyBall);
    }

    private Optional<EntityAvatar> getCastingAvatarEntityForEnergy(int invokeEntityId) {
        // To determine the avatar that has cast the skill that caused the energy particle to be generated,
        // we have to look at the entity that has invoked the ability. This can either be that avatar directly,
        // or it can be an `EntityClientGadget`, owned (some way up the owner hierarchy) by the avatar
        // that cast the skill.

        // Try to get the invoking entity from the scene.
        GameEntity entity = player.getScene().getEntityById(invokeEntityId);

        // Determine the ID of the entity that originally cast this skill. If the scene entity is null,
        // or not an `EntityClientGadget`, we assume that we are directly looking at the casting avatar
        // (the null case will happen if the avatar was switched out between casting the skill and the
        // particle being generated). If the scene entity is an `EntityClientGadget`, we need to find the
        // ID of the original owner of that gadget.
        int avatarEntityId =
                (!(entity instanceof EntityClientGadget))
                        ? invokeEntityId
                        : ((EntityClientGadget)entity).getOriginalOwnerEntityId();

        // Finally, find the avatar entity in the player's team.
        return player.getTeamManager().getActiveTeam()
                .stream()
                .filter(character -> character.getId() == avatarEntityId)
                .findFirst();
    }

    public Boolean getEnergyUsage() {
        return energyUsage;
    }

    public void setEnergyUsage(Boolean energyUsage) {
        this.energyUsage = energyUsage;
        if (!energyUsage) {  // Refill team energy if usage is disabled
            for (EntityAvatar entityAvatar : player.getTeamManager().getActiveTeam()) {
                entityAvatar.addEnergy(1000, PropChangeReason.PROP_CHANGE_REASON_GM,true);
            }
        }
    }
}
