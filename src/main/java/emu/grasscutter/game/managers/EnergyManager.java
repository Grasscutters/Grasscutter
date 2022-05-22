package emu.grasscutter.game.managers;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.AvatarSkillDepotData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.entity.EntityClientGadget;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.net.proto.AbilityActionGenerateElemBallOuterClass.AbilityActionGenerateElemBall;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.PropChangeReasonOuterClass.PropChangeReason;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.utils.Position;

import static emu.grasscutter.Configuration.GAME_OPTIONS;

import java.util.Optional;

import com.google.protobuf.InvalidProtocolBufferException;

public class EnergyManager {
	private final Player player;

	public EnergyManager(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return this.player;
	}

	/**********
		Particle creation for elemental skills.
	**********/
	private int getCastingAvatarIdForElemBall(int invokeEntityId) {
		// To determine the avatar that has cast the skill that caused the energy particle to be generated,
		// we have to look at the entity that has invoked the ability. This can either be that avatar directly, 
		// or it can be an `EntityClientGadget`, owned (some way up the owner hierarchy) by the avatar 
		// that cast the skill.
		int res = 0;

		// Try to get the invoking entity from the scene.
		GameEntity entity = player.getScene().getEntityById(invokeEntityId);

		// If this entity is null, or not an `EntityClientGadget`, we assume that we are directly 
		// looking at the casting avatar (the null case will happen if the avatar was switched out 
		// between casting the skill and the particle being generated).
		if (!(entity instanceof EntityClientGadget)) {
			res = invokeEntityId;
		}
		// If the entity is a `EntityClientGadget`, we need to "walk up" the owner hierarchy,
		// until the owner is no longer a gadget. This should then be the ID of the casting avatar.
		else {	
			while (entity instanceof EntityClientGadget gadget) {
				res = gadget.getOwnerEntityId();
				entity = player.getScene().getEntityById(gadget.getOwnerEntityId());
			}
		}

		return res;
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

		// Determine the element of the energy particle that we have to generate.
		// In case we can't, we default to an elementless particle.
		// The element is the element of the avatar that has cast the ability.
		// We can get that from the avatar's skill depot.
		int itemId = 2024;

		// Try to fetch the avatar from the player's party and determine their element.
		// ToDo: Does this work in co-op?
		int avatarId = getCastingAvatarIdForElemBall(invoke.getEntityId());	
		Optional<EntityAvatar> avatarEntity = player.getTeamManager().getActiveTeam()
													.stream()
													.filter(character -> character.getId() == avatarId)
													.findFirst();

		if (avatarEntity.isPresent()) {
			Avatar avatar = avatarEntity.get().getAvatar();

			if (avatar != null) {
				AvatarSkillDepotData skillDepotData = avatar.getSkillDepot();

				if (skillDepotData != null) {
					ElementType element = skillDepotData.getElementType();

					// If we found the element, we use it to deterine the ID of the
					// energy particle that we have to generate.
		 			if (element != null) {
		 				itemId = switch (element) {
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
		 		}
		 	}
		}

		// Get the item data for an energy particle of the correct element.
		ItemData itemData = GameData.getItemDataMap().get(itemId);
		if (itemData == null) {
			return; // Should never happen
		}
	
		// Generate entity.
		EntityItem energyBall = new EntityItem(getPlayer().getScene(), getPlayer(), itemData, new Position(action.getPos()), 1);
		energyBall.getRotation().set(action.getRot());

		this.getPlayer().getScene().addEntity(energyBall);
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
			entity.addEnergy(baseEnergy * elementBonus * offFieldPenalty, PropChangeReason.PROP_CHANGE_ENERGY_BALL);
		}
	}


	/**********
		Energy logic related to using skills.
	**********/
	private void handleBurstCast(Avatar avatar, int skillId) {
		// Don't do anything if energy usage is disabled.
		if (!GAME_OPTIONS.energyUsage) {
			return;
		}

		// If the cast skill was a burst, consume energy.
		if (avatar.getSkillDepot() != null && skillId == avatar.getSkillDepot().getEnergySkill()) {
			avatar.getAsEntity().clearEnergy(PropChangeReason.PROP_CHANGE_ABILITY);
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
}
