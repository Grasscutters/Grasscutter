package emu.grasscutter.game.world;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameDepot;
import emu.grasscutter.data.binout.SceneNpcBornEntry;
import emu.grasscutter.data.excels.*;
import emu.grasscutter.game.dungeons.DungeonSettleListener;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.TeamInfo;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.props.SceneType;
import emu.grasscutter.game.quest.QuestGroupSuite;
import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Scene {
    private final World world;
    private final SceneData sceneData;
    private final List<Player> players;
    private final Map<Integer, GameEntity> entities;
    private final Set<SpawnDataEntry> spawnedEntities;
    private final Set<SpawnDataEntry> deadSpawnedEntities;
    private final Set<SceneBlock> loadedBlocks;
    private Set<SpawnDataEntry.GridBlockId> loadedGridBlocks;
    private boolean dontDestroyWhenEmpty;

    private int autoCloseTime;
    private int time;

    private SceneScriptManager scriptManager;
    private WorldChallenge challenge;
    private List<DungeonSettleListener> dungeonSettleListeners;
    private DungeonData dungeonData;
    private int prevScene; // Id of the previous scene
    private int prevScenePoint;
    private Set<SceneNpcBornEntry> npcBornEntrySet;
    public Scene(World world, SceneData sceneData) {
        this.world = world;
        this.sceneData = sceneData;
        this.players = new CopyOnWriteArrayList<>();
        this.entities = new ConcurrentHashMap<>();

        this.time = 8 * 60;
        this.prevScene = 3;

        this.spawnedEntities = ConcurrentHashMap.newKeySet();
        this.deadSpawnedEntities = ConcurrentHashMap.newKeySet();
        this.loadedBlocks = ConcurrentHashMap.newKeySet();
        this.loadedGridBlocks = new HashSet<>();
        this.npcBornEntrySet = ConcurrentHashMap.newKeySet();
        this.scriptManager = new SceneScriptManager(this);
    }

    public int getId() {
        return sceneData.getId();
    }

    public World getWorld() {
        return world;
    }

    public SceneData getSceneData() {
        return this.sceneData;
    }

    public SceneType getSceneType() {
        return getSceneData().getSceneType();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getPlayerCount() {
        return this.getPlayers().size();
    }

    public Map<Integer, GameEntity> getEntities() {
        return entities;
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
        return autoCloseTime;
    }

    /**
     * @param autoCloseTime the autoCloseTime to set
     */
    public void setAutoCloseTime(int autoCloseTime) {
        this.autoCloseTime = autoCloseTime;
    }

    public int getTime() {
        return time;
    }

    public void changeTime(int time) {
        this.time = time % 1440;
    }

    public int getPrevScene() {
        return prevScene;
    }

    public void setPrevScene(int prevScene) {
        this.prevScene = prevScene;
    }

    public int getPrevScenePoint() {
        return prevScenePoint;
    }

    public void setPrevScenePoint(int prevPoint) {
        this.prevScenePoint = prevPoint;
    }

    public boolean dontDestroyWhenEmpty() {
        return dontDestroyWhenEmpty;
    }

    public void setDontDestroyWhenEmpty(boolean dontDestroyWhenEmpty) {
        this.dontDestroyWhenEmpty = dontDestroyWhenEmpty;
    }

    public Set<SceneBlock> getLoadedBlocks() {
        return loadedBlocks;
    }

    public Set<SpawnDataEntry> getSpawnedEntities() {
        return spawnedEntities;
    }

    public Set<SpawnDataEntry> getDeadSpawnedEntities() {
        return deadSpawnedEntities;
    }

    public SceneScriptManager getScriptManager() {
        return scriptManager;
    }

    public DungeonData getDungeonData() {
        return dungeonData;
    }

    public void setDungeonData(DungeonData dungeonData) {
        if (dungeonData == null || this.dungeonData != null || this.getSceneType() != SceneType.SCENE_DUNGEON || dungeonData.getSceneId() != this.getId()) {
            return;
        }
        this.dungeonData = dungeonData;
    }

    public WorldChallenge getChallenge() {
        return challenge;
    }

    public void setChallenge(WorldChallenge challenge) {
        this.challenge = challenge;
    }

    public void addDungeonSettleObserver(DungeonSettleListener dungeonSettleListener) {
        if (dungeonSettleListeners == null) {
            dungeonSettleListeners = new ArrayList<>();
        }
        dungeonSettleListeners.add(dungeonSettleListener);
    }

    public List<DungeonSettleListener> getDungeonSettleObservers() {
        return dungeonSettleListeners;
    }

    public boolean isInScene(GameEntity entity) {
        return this.entities.containsKey(entity.getId());
    }

    public synchronized void addPlayer(Player player) {
        // Check if player already in
        if (getPlayers().contains(player)) {
            return;
        }

        // Remove player from prev scene
        if (player.getScene() != null) {
            player.getScene().removePlayer(player);
        }

        // Add
        getPlayers().add(player);
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
        getPlayers().remove(player);
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
        getEntities().put(entity.getId(), entity);
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
        addEntities(entities, VisionType.VISION_TYPE_BORN);
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
        return getEntities().remove(entity.getId());
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
        GameEntity target = getEntityById(result.getDefenseId());

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

    public void killEntity(GameEntity target) {
        killEntity(target, 0);
    }

    public void killEntity(GameEntity target, int attackerId) {
        GameEntity attacker = null;

        if (attackerId > 0) {
            attacker = getEntityById(attackerId);
        }

        if (attacker != null) {
            // Check codex
            if (attacker instanceof EntityClientGadget gadgetAttacker) {
                var clientGadgetOwner = getEntityById(gadgetAttacker.getOwnerEntityId());
                if (clientGadgetOwner instanceof EntityAvatar) {
                    ((EntityClientGadget) attacker).getOwner().getCodex().checkAnimal(target, CodexAnimalData.CodexAnimalUnlockCondition.CODEX_COUNT_TYPE_KILL);
                }
            } else if (attacker instanceof EntityAvatar avatarAttacker) {
                avatarAttacker.getPlayer().getCodex().checkAnimal(target, CodexAnimalData.CodexAnimalUnlockCondition.CODEX_COUNT_TYPE_KILL);
            }
        }

        // Packet
        this.broadcastPacket(new PacketLifeStateChangeNotify(attackerId, target, LifeState.LIFE_DEAD));

        // Reward drop
        if (target instanceof EntityMonster && this.getSceneType() != SceneType.SCENE_DUNGEON) {
            getWorld().getServer().getDropSystem().callDrop((EntityMonster) target);
        }

        // Remove entity from world
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

        if (challenge != null) {
            challenge.onCheckTimeOut();
        }

        checkNpcGroup();
    }

    public int getEntityLevel(int baseLevel, int worldLevelOverride) {
        int level = worldLevelOverride > 0 ? worldLevelOverride + baseLevel - 22 : baseLevel;
        level = level >= 100 ? 100 : level;
        level = level <= 0 ? 1 : level;

        return level;
    }
    public void checkNpcGroup() {
        Set<SceneNpcBornEntry> npcBornEntries = ConcurrentHashMap.newKeySet();
        for (Player player : this.getPlayers()) {
            npcBornEntries.addAll(loadNpcForPlayer(player));
        }

        // clear the unreachable group for client
        var toUnload = this.npcBornEntrySet.stream()
            .filter(i -> !npcBornEntries.contains(i))
            .map(SceneNpcBornEntry::getGroupId)
            .toList();

        if (toUnload.size() > 0) {
            broadcastPacket(new PacketGroupUnloadNotify(toUnload));
            Grasscutter.getLogger().debug("Unload NPC Group {}", toUnload);
        }
        // exchange the new npcBornEntry Set
        this.npcBornEntrySet = npcBornEntries;
    }

    public synchronized void checkSpawns() {
        Set<SpawnDataEntry.GridBlockId> loadedGridBlocks = new HashSet<>();
        for (Player player : this.getPlayers()) {
            for (SpawnDataEntry.GridBlockId block : SpawnDataEntry.GridBlockId.getAdjacentGridBlockIds(player.getSceneId(), player.getPosition()))
                loadedGridBlocks.add(block);
        }
        if (this.loadedGridBlocks.containsAll(loadedGridBlocks)) {  // Don't recalculate static spawns if nothing has changed
            return;
        }
        this.loadedGridBlocks = loadedGridBlocks;
        var spawnLists = GameDepot.getSpawnLists();
        Set<SpawnDataEntry> visible = new HashSet<>();
        for (var block : loadedGridBlocks) {
            var spawns = spawnLists.get(block);
            if (spawns!=null) {
                visible.addAll(spawns);
            }
        }

        // World level
        WorldLevelData worldLevelData = GameData.getWorldLevelDataMap().get(getWorld().getWorldLevel());
        int worldLevelOverride = 0;

        if (worldLevelData != null) {
            worldLevelOverride = worldLevelData.getMonsterLevel();
        }

        // Todo
        List<GameEntity> toAdd = new LinkedList<>();
        List<GameEntity> toRemove = new LinkedList<>();
        var spawnedEntities = this.getSpawnedEntities();
        for (SpawnDataEntry entry : visible) {
            // If spawn entry is in our view and hasnt been spawned/killed yet, we should spawn it
            if (!spawnedEntities.contains(entry) && !this.getDeadSpawnedEntities().contains(entry)) {
                // Entity object holder
                GameEntity entity = null;

                // Check if spawn entry is monster or gadget
                if (entry.getMonsterId() > 0) {
                    MonsterData data = GameData.getMonsterDataMap().get(entry.getMonsterId());
                    if (data == null) continue;

                    int level = this.getEntityLevel(entry.getLevel(), worldLevelOverride);

                    EntityMonster monster = new EntityMonster(this, data, entry.getPos(), level);
                    monster.getRotation().set(entry.getRot());
                    monster.setGroupId(entry.getGroup().getGroupId());
                    monster.setPoseId(entry.getPoseId());
                    monster.setConfigId(entry.getConfigId());
                    monster.setSpawnEntry(entry);

                    entity = monster;
                } else if (entry.getGadgetId() > 0) {
                    EntityGadget gadget = new EntityGadget(this, entry.getGadgetId(), entry.getPos(), entry.getRot());
                    gadget.setGroupId(entry.getGroup().getGroupId());
                    gadget.setConfigId(entry.getConfigId());
                    gadget.setSpawnEntry(entry);
                    int state = entry.getGadgetState();
                    if (state>0) {
                        gadget.setState(state);
                    }
                    gadget.buildContent();

                    gadget.setFightProperty(FightProperty.FIGHT_PROP_BASE_HP, 99999);
                    gadget.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 99999);
                    gadget.setFightProperty(FightProperty.FIGHT_PROP_MAX_HP, 99999);

                    entity = gadget;
                }

                if (entity == null) continue;

                // Add to scene and spawned list
                toAdd.add(entity);
                spawnedEntities.add(entry);
            }
        }

        for (GameEntity entity : this.getEntities().values()) {
            var spawnEntry = entity.getSpawnEntry();
            if (spawnEntry != null && !visible.contains(spawnEntry)) {
                toRemove.add(entity);
                spawnedEntities.remove(spawnEntry);
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
        return SceneIndexManager.queryNeighbors(getScriptManager().getBlocksIndex(),
                player.getPosition().toXZDoubleArray(), Grasscutter.getConfig().server.game.loadEntitiesForPlayerRange);
    }
    public void checkBlocks() {
        Set<SceneBlock> visible = new HashSet<>();
        for (Player player : this.getPlayers()) {
            var blocks = getPlayerActiveBlocks(player);
            visible.addAll(blocks);
        }

        Iterator<SceneBlock> it = this.getLoadedBlocks().iterator();
        while (it.hasNext()) {
            SceneBlock block = it.next();

            if (!visible.contains(block)) {
                it.remove();

                onUnloadBlock(block);
            }
        }

        for (var block : visible) {
            if (!this.getLoadedBlocks().contains(block)) {
                this.onLoadBlock(block, this.getPlayers());
                this.getLoadedBlocks().add(block);
            }else {
                // dynamic load the groups for players in a loaded block
                var toLoad = this.getPlayers().stream()
                        .filter(p -> block.contains(p.getPosition()))
                        .map(p -> playerMeetGroups(p, block))
                        .flatMap(Collection::stream)
                        .toList();
                onLoadGroup(toLoad);
            }
        }

    }
    public List<SceneGroup> playerMeetGroups(Player player, SceneBlock block) {
        List<SceneGroup> sceneGroups = SceneIndexManager.queryNeighbors(block.sceneGroupIndex, player.getPosition().toDoubleArray(),
                Grasscutter.getConfig().server.game.loadEntitiesForPlayerRange);

        List<SceneGroup> groups = sceneGroups.stream()
                .filter(group -> !scriptManager.getLoadedGroupSetPerBlock().get(block.id).contains(group))
                .peek(group -> scriptManager.getLoadedGroupSetPerBlock().get(block.id).add(group))
                .toList();

        if (groups.size() == 0) {
            return List.of();
        }

        return groups;
    }
    public void onLoadBlock(SceneBlock block, List<Player> players) {
        this.getScriptManager().loadBlockFromScript(block);
        scriptManager.getLoadedGroupSetPerBlock().put(block.id , new HashSet<>());

        // the groups form here is not added in current scene
        var groups = players.stream()
                .filter(player -> block.contains(player.getPosition()))
                .map(p -> playerMeetGroups(p, block))
                .flatMap(Collection::stream)
                .toList();

        onLoadGroup(groups);
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
                entities.addAll(garbageGadgets.stream().map(g -> scriptManager.createGadget(group.id, group.block_id, g))
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
            suiteData.sceneTriggers.forEach(getScriptManager()::registerTrigger);

            entities.addAll(scriptManager.getGadgetsInGroupSuite(group, suiteData));
            entities.addAll(scriptManager.getMonstersInGroupSuite(group, suiteData));

            scriptManager.registerRegionInGroupSuite(group, suiteData);
        }

        scriptManager.meetEntities(entities);
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
                group.triggers.values().forEach(getScriptManager()::deregisterTrigger);
            }
            if (group.regions != null) {
                group.regions.values().forEach(getScriptManager()::deregisterRegion);
            }
        }
        scriptManager.getLoadedGroupSetPerBlock().remove(block.id);
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
        GameEntity entity = getEntities().get(entityId);

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
                addEntity(entity);
            }
        } else {
            EntityItem entity = new EntityItem(this, null, itemData, bornForm.getPosition().clone().addZ(.9f), amount);
            addEntity(entity);
        }
    }
    public void loadNpcForPlayerEnter(Player player) {
        this.npcBornEntrySet.addAll(loadNpcForPlayer(player));
    }
    private List<SceneNpcBornEntry> loadNpcForPlayer(Player player) {
        var pos = player.getPosition();
        var data = GameData.getSceneNpcBornData().get(getId());
        if (data == null) {
            return List.of();
        }

        var npcList = SceneIndexManager.queryNeighbors(data.getIndex(), pos.toDoubleArray(),
                Grasscutter.getConfig().server.game.loadEntitiesForPlayerRange);

        var sceneNpcBornEntries = npcList.stream()
            .filter(i -> !this.npcBornEntrySet.contains(i))
            .toList();

        if (sceneNpcBornEntries.size() > 0) {
            this.broadcastPacket(new PacketGroupSuiteNotify(sceneNpcBornEntries));
            Grasscutter.getLogger().debug("Loaded Npc Group Suite {}", sceneNpcBornEntries);
        }
        return npcList;
    }

    public void loadGroupForQuest(List<QuestGroupSuite> sceneGroupSuite) {
        if (!scriptManager.isInit()) {
            return;
        }

        sceneGroupSuite.forEach(i -> {
            var group = scriptManager.getGroupById(i.getGroup());
            if (group == null) {
                return;
            }
            var suite = group.getSuiteByIndex(i.getSuite());
            if (suite == null) {
                return;
            }
            scriptManager.addGroupSuite(group, suite);
        });
    }
}
