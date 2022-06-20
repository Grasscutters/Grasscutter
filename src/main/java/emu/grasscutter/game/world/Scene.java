package emu.grasscutter.game.world;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameDepot;
import emu.grasscutter.data.excels.*;
import emu.grasscutter.game.dungeons.DungeonSettleListener;
import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.TeamInfo;
import emu.grasscutter.game.props.ClimateType;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.props.SceneType;
import emu.grasscutter.game.world.SpawnDataEntry.SpawnGroupEntry;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.proto.AttackResultOuterClass.AttackResult;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;
import emu.grasscutter.scripts.SceneIndexManager;
import emu.grasscutter.scripts.SceneScriptManager;
import emu.grasscutter.scripts.data.SceneBlock;
import emu.grasscutter.scripts.data.SceneGadget;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.danilopianini.util.SpatialIndex;

import java.util.*;

public class Scene {
    private final World world;
    private final SceneData sceneData;
    private final List<Player> players;
    private final Int2ObjectMap<GameEntity> entities;

    private final Set<SpawnDataEntry> spawnedEntities;
    private final Set<SpawnDataEntry> deadSpawnedEntities;
    private final Set<SceneBlock> loadedBlocks;
    private boolean dontDestroyWhenEmpty;

    private int autoCloseTime;
    private int time;
    private ClimateType climate;
    private int weather;

    private final SceneScriptManager scriptManager;
    private WorldChallenge challenge;
    private List<DungeonSettleListener> dungeonSettleListeners;
    private DungeonData dungeonData;
    private int prevScene; // Id of the previous scene
    private int prevScenePoint;

    public Scene(World world, SceneData sceneData) {
        this.world = world;
        this.sceneData = sceneData;
        this.players = Collections.synchronizedList(new ArrayList<>());
        this.entities = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());

        this.time = 8 * 60;
        this.climate = ClimateType.CLIMATE_SUNNY;
        this.prevScene = 3;

        this.spawnedEntities = new HashSet<>();
        this.deadSpawnedEntities = new HashSet<>();
        this.loadedBlocks = new HashSet<>();
        this.scriptManager = new SceneScriptManager(this);
    }

    public int getId() {
        return this.sceneData.getId();
    }

    public World getWorld() {
        return this.world;
    }

    public SceneData getSceneData() {
        return this.sceneData;
    }

    public SceneType getSceneType() {
        return this.getSceneData().getSceneType();
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public int getPlayerCount() {
        return this.getPlayers().size();
    }

    public Int2ObjectMap<GameEntity> getEntities() {
        return this.entities;
    }

    public GameEntity getEntityById(int id) {
        return this.entities.get(id);
    }

    public GameEntity getEntityByConfigId(int configId) {
        return this.entities.values().stream()
            .filter(x -> x.getConfigId() == configId)
            .findFirst()
            .orElse(null);
    }

    /**
     * @return the autoCloseTime
     */
    public int getAutoCloseTime() {
        return this.autoCloseTime;
    }

    /**
     * @param autoCloseTime the autoCloseTime to set
     */
    public void setAutoCloseTime(int autoCloseTime) {
        this.autoCloseTime = autoCloseTime;
    }

    public int getTime() {
        return this.time;
    }

    public void changeTime(int time) {
        this.time = time % 1440;
    }

    public ClimateType getClimate() {
        return this.climate;
    }

    public int getWeather() {
        return this.weather;
    }

    public void setClimate(ClimateType climate) {
        this.climate = climate;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public int getPrevScene() {
        return this.prevScene;
    }

    public void setPrevScene(int prevScene) {
        this.prevScene = prevScene;
    }

    public int getPrevScenePoint() {
        return this.prevScenePoint;
    }

    public void setPrevScenePoint(int prevPoint) {
        this.prevScenePoint = prevPoint;
    }

    public boolean dontDestroyWhenEmpty() {
        return this.dontDestroyWhenEmpty;
    }

    public void setDontDestroyWhenEmpty(boolean dontDestroyWhenEmpty) {
        this.dontDestroyWhenEmpty = dontDestroyWhenEmpty;
    }

    public Set<SceneBlock> getLoadedBlocks() {
        return this.loadedBlocks;
    }

    public Set<SpawnDataEntry> getSpawnedEntities() {
        return this.spawnedEntities;
    }

    public Set<SpawnDataEntry> getDeadSpawnedEntities() {
        return this.deadSpawnedEntities;
    }

    public SceneScriptManager getScriptManager() {
        return this.scriptManager;
    }

    public DungeonData getDungeonData() {
        return this.dungeonData;
    }

    public void setDungeonData(DungeonData dungeonData) {
        if (dungeonData == null || this.dungeonData != null || this.getSceneType() != SceneType.SCENE_DUNGEON || dungeonData.getSceneId() != this.getId()) {
            return;
        }
        this.dungeonData = dungeonData;
    }

    public WorldChallenge getChallenge() {
        return this.challenge;
    }

    public void setChallenge(WorldChallenge challenge) {
        this.challenge = challenge;
    }

    public void addDungeonSettleObserver(DungeonSettleListener dungeonSettleListener) {
        if (this.dungeonSettleListeners == null) {
            this.dungeonSettleListeners = new ArrayList<>();
        }
        this.dungeonSettleListeners.add(dungeonSettleListener);
    }

    public List<DungeonSettleListener> getDungeonSettleObservers() {
        return this.dungeonSettleListeners;
    }

    public boolean isInScene(GameEntity entity) {
        return this.entities.containsKey(entity.getId());
    }

    public synchronized void addPlayer(Player player) {
        // Check if player already in
        if (this.getPlayers().contains(player)) {
            return;
        }

        // Remove player from prev scene
        if (player.getScene() != null) {
            player.getScene().removePlayer(player);
        }

        // Add
        this.getPlayers().add(player);
        player.setSceneId(this.getId());
        player.setScene(this);

        this.setupPlayerAvatars(player);
    }

    public synchronized void removePlayer(Player player) {
        // Remove from challenge if leaving
        if (this.getChallenge() != null && this.getChallenge().inProgress()) {
            player.sendPacket(new PacketDungeonChallengeFinishNotify(this.getChallenge()));
        }

        // Remove player from scene
        this.getPlayers().remove(player);
        player.setScene(null);

        // Remove player avatars
        this.removePlayerAvatars(player);

        // Remove player gadgets
        for (EntityBaseGadget gadget : player.getTeamManager().getGadgets()) {
            this.removeEntity(gadget);
        }

        // Deregister scene if not in use
        if (this.getPlayerCount() <= 0 && !this.dontDestroyWhenEmpty()) {
            this.getWorld().deregisterScene(this);
        }
    }

    private void setupPlayerAvatars(Player player) {
        // Clear entities from old team
        player.getTeamManager().getActiveTeam().clear();

        // Add new entities for player
        TeamInfo teamInfo = player.getTeamManager().getCurrentTeamInfo();
        for (int avatarId : teamInfo.getAvatars()) {
            EntityAvatar entity = new EntityAvatar(player.getScene(), player.getAvatars().getAvatarById(avatarId));
            player.getTeamManager().getActiveTeam().add(entity);
        }

        // Limit character index in case its out of bounds
        if (player.getTeamManager().getCurrentCharacterIndex() >= player.getTeamManager().getActiveTeam().size() || player.getTeamManager().getCurrentCharacterIndex() < 0) {
            player.getTeamManager().setCurrentCharacterIndex(player.getTeamManager().getCurrentCharacterIndex() - 1);
        }
    }

    private void removePlayerAvatars(Player player) {
        Iterator<EntityAvatar> it = player.getTeamManager().getActiveTeam().iterator();
        while (it.hasNext()) {
            this.removeEntity(it.next(), VisionType.VISION_TYPE_REMOVE);
            it.remove();
        }
    }

    public void spawnPlayer(Player player) {
        if (this.isInScene(player.getTeamManager().getCurrentAvatarEntity())) {
            return;
        }

        if (player.getTeamManager().getCurrentAvatarEntity().getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) <= 0f) {
            player.getTeamManager().getCurrentAvatarEntity().setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 1f);
        }

        this.addEntity(player.getTeamManager().getCurrentAvatarEntity());

        // Notify the client of any extra skill charges
        for (EntityAvatar entity : player.getTeamManager().getActiveTeam()) {
            if (entity.getAvatar().getSkillExtraChargeMap().size() > 0) {
                player.sendPacket(new PacketAvatarSkillInfoNotify(entity.getAvatar()));
            }
        }
    }

    private void addEntityDirectly(GameEntity entity) {
        this.getEntities().put(entity.getId(), entity);
        entity.onCreate(); // Call entity create event
    }

    public synchronized void addEntity(GameEntity entity) {
        this.addEntityDirectly(entity);
        this.broadcastPacket(new PacketSceneEntityAppearNotify(entity));
    }

    public synchronized void addEntityToSingleClient(Player player, GameEntity entity) {
        this.addEntityDirectly(entity);
        player.sendPacket(new PacketSceneEntityAppearNotify(entity));

    }

    public void addEntities(Collection<? extends GameEntity> entities) {
        this.addEntities(entities, VisionType.VISION_TYPE_BORN);
    }

    public synchronized void addEntities(Collection<? extends GameEntity> entities, VisionType visionType) {
        if (entities == null || entities.isEmpty()) {
            return;
        }
        for (GameEntity entity : entities) {
            this.addEntityDirectly(entity);
        }

        this.broadcastPacket(new PacketSceneEntityAppearNotify(entities, visionType));
    }

    private GameEntity removeEntityDirectly(GameEntity entity) {
        return this.getEntities().remove(entity.getId());
    }

    public void removeEntity(GameEntity entity) {
        this.removeEntity(entity, VisionType.VISION_TYPE_DIE);
    }

    public synchronized void removeEntity(GameEntity entity, VisionType visionType) {
        GameEntity removed = this.removeEntityDirectly(entity);
        if (removed != null) {
            this.broadcastPacket(new PacketSceneEntityDisappearNotify(removed, visionType));
        }
    }

    public synchronized void removeEntities(List<GameEntity> entity, VisionType visionType) {
        var toRemove = entity.stream()
            .map(this::removeEntityDirectly)
            .toList();
        if (toRemove.size() > 0) {
            this.broadcastPacket(new PacketSceneEntityDisappearNotify(toRemove, visionType));
        }
    }

    public synchronized void replaceEntity(EntityAvatar oldEntity, EntityAvatar newEntity) {
        this.removeEntityDirectly(oldEntity);
        this.addEntityDirectly(newEntity);
        this.broadcastPacket(new PacketSceneEntityDisappearNotify(oldEntity, VisionType.VISION_TYPE_REPLACE));
        this.broadcastPacket(new PacketSceneEntityAppearNotify(newEntity, VisionType.VISION_TYPE_REPLACE, oldEntity.getId()));
    }

    public void showOtherEntities(Player player) {
        List<GameEntity> entities = new LinkedList<>();
        GameEntity currentEntity = player.getTeamManager().getCurrentAvatarEntity();

        for (GameEntity entity : this.getEntities().values()) {
            if (entity == currentEntity) {
                continue;
            }
            entities.add(entity);
        }

        player.sendPacket(new PacketSceneEntityAppearNotify(entities, VisionType.VISION_TYPE_MEET));
    }

    public void handleAttack(AttackResult result) {
        //GameEntity attacker = getEntityById(result.getAttackerId());
        GameEntity target = this.getEntityById(result.getDefenseId());

        if (target == null) {
            return;
        }

        // Godmode check
        if (target instanceof EntityAvatar) {
            if (((EntityAvatar) target).getPlayer().inGodmode()) {
                return;
            }
        }

        // Sanity check
        target.damage(result.getDamage(), result.getAttackerId());
    }

    public void killEntity(GameEntity target, int attackerId) {
        GameEntity attacker = this.getEntityById(attackerId);

        //Check codex
        if (attacker instanceof EntityClientGadget) {
            var clientGadgetOwner = this.getEntityById(((EntityClientGadget) attacker).getOwnerEntityId());
            if (clientGadgetOwner instanceof EntityAvatar) {
                ((EntityClientGadget) attacker).getOwner().getCodex().checkAnimal(target, CodexAnimalData.CodexAnimalUnlockCondition.CODEX_COUNT_TYPE_KILL);
            }
        } else if (attacker instanceof EntityAvatar) {
            ((EntityAvatar) attacker).getPlayer().getCodex().checkAnimal(target, CodexAnimalData.CodexAnimalUnlockCondition.CODEX_COUNT_TYPE_KILL);
        }

        // Packet
        this.broadcastPacket(new PacketLifeStateChangeNotify(attackerId, target, LifeState.LIFE_DEAD));

        // Reward drop
        if (target instanceof EntityMonster && this.getSceneType() != SceneType.SCENE_DUNGEON) {
            this.getWorld().getServer().getDropManager().callDrop((EntityMonster) target);
        }

        this.removeEntity(target);

        // Death event
        target.onDeath(attackerId);
    }

    public void onTick() {
        // disable script for home
        if (this.getSceneType() == SceneType.SCENE_HOME_WORLD || this.getSceneType() == SceneType.SCENE_HOME_ROOM) {
            return;
        }
        if (this.getScriptManager().isInit()) {
            this.checkBlocks();
        } else {
            // TEMPORARY
            this.checkSpawns();
        }
        // Triggers
        this.scriptManager.checkRegions();

        if (this.challenge != null) {
            this.challenge.onCheckTimeOut();
        }
    }

    // TODO - Test
    public void checkSpawns() {
        SpatialIndex<SpawnGroupEntry> list = GameDepot.getSpawnListById(this.getId());
        Set<SpawnDataEntry> visible = new HashSet<>();

        for (Player player : this.getPlayers()) {
            int RANGE = 100;
            Collection<SpawnGroupEntry> entries = list.query(
                new double[]{player.getPos().getX() - RANGE, player.getPos().getZ() - RANGE},
                new double[]{player.getPos().getX() + RANGE, player.getPos().getZ() + RANGE}
            );

            for (SpawnGroupEntry entry : entries) {
                for (SpawnDataEntry spawnData : entry.getSpawns()) {
                    visible.add(spawnData);
                }
            }
        }

        // World level
        WorldLevelData worldLevelData = GameData.getWorldLevelDataMap().get(this.getWorld().getWorldLevel());
        int worldLevelOverride = 0;

        if (worldLevelData != null) {
            worldLevelOverride = worldLevelData.getMonsterLevel();
        }

        // Todo
        List<GameEntity> toAdd = new LinkedList<>();
        List<GameEntity> toRemove = new LinkedList<>();

        for (SpawnDataEntry entry : visible) {
            if (!this.getSpawnedEntities().contains(entry) && !this.getDeadSpawnedEntities().contains(entry)) {
                // Spawn entity
                MonsterData data = GameData.getMonsterDataMap().get(entry.getMonsterId());

                if (data == null) {
                    continue;
                }

                int level = worldLevelOverride > 0 ? worldLevelOverride + entry.getLevel() - 22 : entry.getLevel();
                level = level >= 100 ? 100 : level;
                level = level <= 0 ? 1 : level;

                EntityMonster entity = new EntityMonster(this, data, entry.getPos(), level);
                entity.getRotation().set(entry.getRot());
                entity.setGroupId(entry.getGroup().getGroupId());
                entity.setPoseId(entry.getPoseId());
                entity.setConfigId(entry.getConfigId());
                entity.setSpawnEntry(entry);

                toAdd.add(entity);

                // Add to spawned list
                this.getSpawnedEntities().add(entry);
            }
        }

        for (GameEntity entity : this.getEntities().values()) {
            if (entity.getSpawnEntry() != null && !visible.contains(entity.getSpawnEntry())) {
                toRemove.add(entity);
            }
        }

        if (toAdd.size() > 0) {
            toAdd.stream().forEach(this::addEntityDirectly);
            this.broadcastPacket(new PacketSceneEntityAppearNotify(toAdd, VisionType.VISION_TYPE_BORN));
        }
        if (toRemove.size() > 0) {
            toRemove.stream().forEach(this::removeEntityDirectly);
            this.broadcastPacket(new PacketSceneEntityDisappearNotify(toRemove, VisionType.VISION_TYPE_REMOVE));
        }
    }

    public List<SceneBlock> getPlayerActiveBlocks(Player player) {
        // consider the borders' entities of blocks, so we check if contains by index
        return SceneIndexManager.queryNeighbors(this.getScriptManager().getBlocksIndex(),
            player.getPos().toXZDoubleArray(), Grasscutter.getConfig().server.game.loadEntitiesForPlayerRange);
    }

    public void checkBlocks() {
        Set<SceneBlock> visible = new HashSet<>();
        for (Player player : this.getPlayers()) {
            var blocks = this.getPlayerActiveBlocks(player);
            visible.addAll(blocks);
        }

        Iterator<SceneBlock> it = this.getLoadedBlocks().iterator();
        while (it.hasNext()) {
            SceneBlock block = it.next();

            if (!visible.contains(block)) {
                it.remove();

                this.onUnloadBlock(block);
            }
        }

        for (var block : visible) {
            if (!this.getLoadedBlocks().contains(block)) {
                this.onLoadBlock(block, this.getPlayers());
                this.getLoadedBlocks().add(block);
            } else {
                // dynamic load the groups for players in a loaded block
                var toLoad = this.getPlayers().stream()
                    .filter(p -> block.contains(p.getPos()))
                    .map(p -> this.playerMeetGroups(p, block))
                    .flatMap(Collection::stream)
                    .toList();
                this.onLoadGroup(toLoad);
            }
            for (Player player : this.getPlayers()) {
                this.getScriptManager().meetEntities(this.loadNpcForPlayer(player, block));
            }
        }

    }

    public List<SceneGroup> playerMeetGroups(Player player, SceneBlock block) {
        List<SceneGroup> sceneGroups = SceneIndexManager.queryNeighbors(block.sceneGroupIndex, player.getPos().toDoubleArray(),
            Grasscutter.getConfig().server.game.loadEntitiesForPlayerRange);

        List<SceneGroup> groups = sceneGroups.stream()
            .filter(group -> !this.scriptManager.getLoadedGroupSetPerBlock().get(block.id).contains(group))
            .peek(group -> this.scriptManager.getLoadedGroupSetPerBlock().get(block.id).add(group))
            .toList();

        if (groups.size() == 0) {
            return List.of();
        }

        return groups;
    }

    public void onLoadBlock(SceneBlock block, List<Player> players) {
        this.getScriptManager().loadBlockFromScript(block);
        this.scriptManager.getLoadedGroupSetPerBlock().put(block.id, new HashSet<>());

        // the groups form here is not added in current scene
        var groups = players.stream()
            .filter(player -> block.contains(player.getPos()))
            .map(p -> this.playerMeetGroups(p, block))
            .flatMap(Collection::stream)
            .toList();

        this.onLoadGroup(groups);
        Grasscutter.getLogger().info("Scene {} Block {} loaded.", this.getId(), block.id);
    }

    public void onLoadGroup(List<SceneGroup> groups) {
        if (groups == null || groups.isEmpty()) {
            return;
        }
        for (SceneGroup group : groups) {
            // We load the script files for the groups here
            this.getScriptManager().loadGroupFromScript(group);
        }

        // Spawn gadgets AFTER triggers are added
        // TODO
        var entities = new ArrayList<GameEntity>();
        for (SceneGroup group : groups) {
            if (group.init_config == null) {
                continue;
            }

            // Load garbages
            List<SceneGadget> garbageGadgets = group.getGarbageGadgets();

            if (garbageGadgets != null) {
                entities.addAll(garbageGadgets.stream().map(g -> this.scriptManager.createGadget(group.id, group.block_id, g))
                    .filter(Objects::nonNull)
                    .toList());
            }

            // Load suites
            int suite = group.init_config.suite;

            if (suite == 0 || group.suites == null || group.suites.size() == 0) {
                continue;
            }

            // just load the 'init' suite, avoid spawn the suite added by AddExtraGroupSuite etc.
            var suiteData = group.getSuiteByIndex(suite);
            suiteData.sceneTriggers.forEach(this.getScriptManager()::registerTrigger);

            entities.addAll(suiteData.sceneGadgets.stream()
                .map(g -> this.scriptManager.createGadget(group.id, group.block_id, g))
                .filter(Objects::nonNull)
                .toList());
            entities.addAll(suiteData.sceneMonsters.stream()
                .map(mob -> this.scriptManager.createMonster(group.id, group.block_id, mob))
                .filter(Objects::nonNull)
                .toList());

        }

        this.scriptManager.meetEntities(entities);
        //scriptManager.callEvent(EventType.EVENT_GROUP_LOAD, null);
        //groups.forEach(g -> scriptManager.callEvent(EventType.EVENT_GROUP_LOAD, null));
        Grasscutter.getLogger().info("Scene {} loaded {} group(s)", this.getId(), groups.size());
    }

    public void onUnloadBlock(SceneBlock block) {
        List<GameEntity> toRemove = this.getEntities().values().stream()
            .filter(e -> e.getBlockId() == block.id).toList();

        if (toRemove.size() > 0) {
            toRemove.forEach(this::removeEntityDirectly);
            this.broadcastPacket(new PacketSceneEntityDisappearNotify(toRemove, VisionType.VISION_TYPE_REMOVE));
        }

        for (SceneGroup group : block.groups.values()) {
            if (group.triggers != null) {
                group.triggers.values().forEach(this.getScriptManager()::deregisterTrigger);
            }
            if (group.regions != null) {
                group.regions.forEach(this.getScriptManager()::deregisterRegion);
            }
        }
        this.scriptManager.getLoadedGroupSetPerBlock().remove(block.id);
        Grasscutter.getLogger().info("Scene {} Block {} is unloaded.", this.getId(), block.id);
    }

    // Gadgets

    public void onPlayerCreateGadget(EntityClientGadget gadget) {
        // Directly add
        this.addEntityDirectly(gadget);

        // Add to owner's gadget list
        gadget.getOwner().getTeamManager().getGadgets().add(gadget);

        // Optimization
        if (this.getPlayerCount() == 1 && this.getPlayers().get(0) == gadget.getOwner()) {
            return;
        }

        this.broadcastPacketToOthers(gadget.getOwner(), new PacketSceneEntityAppearNotify(gadget));
    }

    public void onPlayerDestroyGadget(int entityId) {
        GameEntity entity = this.getEntities().get(entityId);

        if (entity == null || !(entity instanceof EntityClientGadget)) {
            return;
        }

        // Get and remove entity
        EntityClientGadget gadget = (EntityClientGadget) entity;
        this.removeEntityDirectly(gadget);

        // Remove from owner's gadget list
        gadget.getOwner().getTeamManager().getGadgets().remove(gadget);

        // Optimization
        if (this.getPlayerCount() == 1 && this.getPlayers().get(0) == gadget.getOwner()) {
            return;
        }

        this.broadcastPacketToOthers(gadget.getOwner(), new PacketSceneEntityDisappearNotify(gadget, VisionType.VISION_TYPE_DIE));
    }

    // Broadcasting

    public void broadcastPacket(BasePacket packet) {
        // Send to all players - might have to check if player has been sent data packets
        for (Player player : this.getPlayers()) {
            player.getSession().send(packet);
        }
    }

    public void broadcastPacketToOthers(Player excludedPlayer, BasePacket packet) {
        // Optimization
        if (this.getPlayerCount() == 1 && this.getPlayers().get(0) == excludedPlayer) {
            return;
        }
        // Send to all players - might have to check if player has been sent data packets
        for (Player player : this.getPlayers()) {
            if (player == excludedPlayer) {
                continue;
            }
            // Send
            player.getSession().send(packet);
        }
    }

    public void addItemEntity(int itemId, int amount, GameEntity bornForm) {
        ItemData itemData = GameData.getItemDataMap().get(itemId);
        if (itemData == null) {
            return;
        }
        if (itemData.isEquip()) {
            float range = (3f + (.1f * amount));
            for (int i = 0; i < amount; i++) {
                Position pos = bornForm.getPosition().clone().addX((float) (Math.random() * range) - (range / 2)).addZ((float) (Math.random() * range) - (range / 2)).addZ(.9f);
                EntityItem entity = new EntityItem(this, null, itemData, pos, 1);
                this.addEntity(entity);
            }
        } else {
            EntityItem entity = new EntityItem(this, null, itemData, bornForm.getPosition().clone().addZ(.9f), amount);
            this.addEntity(entity);
        }
    }

    public List<EntityNPC> loadNpcForPlayer(Player player, SceneBlock block) {
        if (!block.contains(player.getPos())) {
            return List.of();
        }

        var pos = player.getPos();
        var data = GameData.getSceneNpcBornData().get(this.getId());
        if (data == null) {
            return List.of();
        }

        var npcs = SceneIndexManager.queryNeighbors(data.getIndex(), pos.toDoubleArray(),
            Grasscutter.getConfig().server.game.loadEntitiesForPlayerRange);
        var entityNPCS = npcs.stream().map(item -> {
                var group = data.getGroups().get(item.getGroupId());
                if (group == null) {
                    group = SceneGroup.of(item.getGroupId());
                    data.getGroups().putIfAbsent(item.getGroupId(), group);
                    group.load(this.getId());
                }

                if (group.npc == null) {
                    return null;
                }
                var npc = group.npc.get(item.getConfigId());
                if (npc == null) {
                    return null;
                }

                return this.getScriptManager().createNPC(npc, block.id, item.getSuiteIdList().get(0));
            })
            .filter(Objects::nonNull)
            .filter(item -> this.getEntities().values().stream()
                .filter(e -> e instanceof EntityNPC)
                .noneMatch(e -> e.getConfigId() == item.getConfigId()))
            .toList();

        if (entityNPCS.size() > 0) {
            this.broadcastPacket(new PacketGroupSuiteNotify(entityNPCS));
        }

        return entityNPCS;
    }
}
