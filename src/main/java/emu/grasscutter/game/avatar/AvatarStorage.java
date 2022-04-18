package emu.grasscutter.game.avatar;

import java.util.Iterator;
import java.util.List;

import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.server.packet.send.PacketAvatarChangeCostumeNotify;
import emu.grasscutter.server.packet.send.PacketAvatarFlycloakChangeNotify;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

public class AvatarStorage implements Iterable<GenshinAvatar> {
	private final GenshinPlayer player;
	private final Int2ObjectMap<GenshinAvatar> avatars;
	private final Long2ObjectMap<GenshinAvatar> avatarsGuid;
	
	public AvatarStorage(GenshinPlayer player) {
		this.player = player;
		this.avatars = new Int2ObjectOpenHashMap<>();
		this.avatarsGuid = new Long2ObjectOpenHashMap<>();
	}
	
	public GenshinPlayer getPlayer() {
		return player;
	}

	public Int2ObjectMap<GenshinAvatar> getAvatars() {
		return avatars;
	}
	
	public int getAvatarCount() {
		return this.avatars.size();
	}
	
	public GenshinAvatar getAvatarById(int id) {
		return getAvatars().get(id);
	}
	
	public GenshinAvatar getAvatarByGuid(long id) {
		return avatarsGuid.get(id);
	}
	
	public boolean hasAvatar(int id) {
		return getAvatars().containsKey(id);
	}
	
	public boolean addAvatar(GenshinAvatar avatar) {
		if (avatar.getAvatarData() == null || this.hasAvatar(avatar.getAvatarId())) {
			return false;
		}
		
		// Set owner first
		avatar.setOwner(getPlayer());

		// Put into maps
		this.avatars.put(avatar.getAvatarId(), avatar);
		this.avatarsGuid.put(avatar.getGuid(), avatar);

		avatar.save();

		return true;
	}
	
	public void addStartingWeapon(GenshinAvatar avatar) {
		// Make sure avatar owner is this player
		if (avatar.getPlayer() != this.getPlayer()) {
			return;
		}
		
		// Create weapon
		GenshinItem weapon = new GenshinItem(avatar.getAvatarData().getInitialWeapon());
		
		if (weapon.getItemData() != null) {
			this.getPlayer().getInventory().addItem(weapon);
			
			avatar.equipItem(weapon, true);
		}
	}
	
	public boolean wearFlycloak(long avatarGuid, int flycloakId) {
		GenshinAvatar avatar = this.getAvatarByGuid(avatarGuid);

		if (avatar == null || !getPlayer().getFlyCloakList().contains(flycloakId)) {
			return false;
		}
		
		avatar.setFlyCloak(flycloakId);
		avatar.save();
		
		// Update
		getPlayer().sendPacket(new PacketAvatarFlycloakChangeNotify(avatar));

		return true;
	}
	
	public boolean changeCostume(long avatarGuid, int costumeId) {
		GenshinAvatar avatar = this.getAvatarByGuid(avatarGuid);

		if (avatar == null) {
			return false;
		}
		
		if (costumeId != 0 && !getPlayer().getCostumeList().contains(costumeId)) {
			return false;
		}
		
		// TODO make sure avatar can wear costume
		
		avatar.setCostume(costumeId);
		avatar.save();

		// Update entity
		EntityAvatar entity = avatar.getAsEntity();
		if (entity == null) {
			entity = new EntityAvatar(avatar);
			getPlayer().sendPacket(new PacketAvatarChangeCostumeNotify(entity));
		} else {
			getPlayer().getScene().broadcastPacket(new PacketAvatarChangeCostumeNotify(entity));
		}
		
		// Done
		return true;
	}
	
	public void loadFromDatabase() {
		List<GenshinAvatar> avatars = DatabaseHelper.getAvatars(getPlayer());
		
		for (GenshinAvatar avatar : avatars) {
			// Should never happen
			if (avatar.getObjectId() == null) {
				continue;
			}
			
			AvatarData avatarData = GenshinData.getAvatarDataMap().get(avatar.getAvatarId());
			if (avatarData == null) {
				continue;
			}
			
			// Set ownerships
			avatar.setAvatarData(avatarData);
			avatar.setOwner(getPlayer());
			
			// Force recalc of const boosted skills
			avatar.recalcProudSkillBonusMap();
			
			// Add to avatar storage
			this.avatars.put(avatar.getAvatarId(), avatar);
			this.avatarsGuid.put(avatar.getGuid(), avatar);
		}
	}
	
	public void postLoad() {
		for (GenshinAvatar avatar : this) {
			// Weapon check
			if (avatar.getWeapon() == null) {
				this.addStartingWeapon(avatar);
			}
			// Recalc stats
			avatar.recalcStats();
		}
	}

	@Override
	public Iterator<GenshinAvatar> iterator() {
		return getAvatars().values().iterator();
	}
}
