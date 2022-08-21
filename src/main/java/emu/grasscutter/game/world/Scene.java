package emu.grasscutter.game.world;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameDepot;
import emu.grasscutter.data.binout.SceneNpcBornEntry;
import emu.grasscutter.data.excels.*;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.dungeons.DungeonSettleListener;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.entity.gadget.GadgetWorktop;
import emu.grasscutter.game.managers.blossom.BlossomManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.TeamInfo;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.props.SceneType;
import emu.grasscutter.game.quest.QuestGroupSuite;
import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.proto.AttackResultOuterClass.AttackResult;
import emu.grasscutter.net.proto.SelectWorktopOptionReqOuterClass;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;
import emu.grasscutter.scripts.SceneIndexManager;
import emu.grasscutter.scripts.SceneScriptManager;
import emu.grasscutter.scripts.data.SceneBlock;
import emu.grasscutter.scripts.data.SceneGadget;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Scene {
    @Getter private final World world;
    @Getter private final SceneData sceneData;
    @Getter private final List<Player> players;
    @Getter private final Map<Integer, GameEntity> entities;
    @Getter private final Set<SpawnDataEntry> spawnedEntities;
    @Getter private final Set<SpawnDataEntry> deadSpawnedEntities;
    @Getter private final Set<SceneBlock> loadedBlocks;
    @Getter private final BlossomManager blossomManager;
    private Set<SpawnDataEntry.GridBlockId> loadedGridBlocks;
    @Getter @Setter private boolean dontDestroyWhenEmpty;

    @Getter @Setter private int autoCloseTime;
    @Getter private int time;

    @Getter private SceneScriptManager scriptManager;
    @Getter @Setter private WorldChallenge challenge;
    @Getter private List<DungeonSettleListener> dungeonSettleListeners;
    @Getter private DungeonData dungeonData;
    @Getter @Setter private int prevScene; // Id of the previous scene
    @Getter @Setter private int prevScenePoint;
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
        this.blossomManager = new BlossomManager(this);
    }

    public int getId() {
        return sceneData.getId();
    }

    public SceneType getSceneType() {
        return getSceneData().getSceneType();
    }

    public int getPlayerCount() {
        return this.getPlayers().size();
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

    public void changeTime(int time) {
        this.time = time % 1440;
    }

    public void setDungeonData(DungeonData dungeonData) {
        if (dungeonData == null || this.dungeonData != null || this.getSceneType() != SceneType.SCENE_DUNGEON || dungeonData.getSceneId() != this.getId()) {
            return;
        }
        this.dungeonData = dungeonData;
    }

    public void addDungeonSettleObserver(DungeonSettleListener dungeonSettleListener) {
        if (dungeonSettleListeners == null) {
            dungeonSettleListeners = new ArrayList<>();
        }
        dungeonSettleListeners.add(dungeonSettleListener);
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
        if (this.getPlayerCount() <= 0 && !this.dontDestroyWhenEmpty) {
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

    private synchronized void removePlayerAvatars(Player player) {
        var team = player.getTeamManager().getActiveTeam();
        // removeEntities(team, VisionType.VISION_TYPE_REMOVE);  // List<SubType> isn't cool apparently :(
        team.forEach(e -> removeEntity(e, VisionType.VISION_TYPE_REMOVE));
        team.clear();
    }

    public void spawnPlayer(Player player) {
        var teamManager = player.getTeamManager();
        if (this.isInScene(teamManager.getCurrentAvatarEntity())) {
            return;
        }

        if (teamManager.getCurrentAvatarEntity().getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) <= 0f) {
            teamManager.getCurrentAvatarEntity().setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 1f);
        }

        this.addEntity(teamManager.getCurrentAvatarEntity());

        // Notify the client of any extra skill charges
        teamManager.getActiveTeam().stream().map(EntityAvatar::getAvatar).forEach(Avatar::sendSkillExtraChargeMap);
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

        blossomManager.onTick();

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

                    gadget.setFightProperty(FightProperty.FIGHT_PROP_BASE_HP, Float.POSITIVE_INFINITY);
                    gadget.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, Float.POSITIVE_INFINITY);
                    gadget.setFightProperty(FightProperty.FIGHT_PROP_MAX_HP, Float.POSITIVE_INFINITY);

                    entity = gadget;
                    blossomManager.initBlossom(gadget);
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
            blossomManager.recycleGadgetEntity(toRemove);
        }
    }

    public List<SceneBlock> getPlayerActiveBlocks(Player player) {
        // consider the borders' entities of blocks, so we check if contains by index
        return SceneIndexManager.queryNeighbors(getScriptManager().getBlocksIndex(),
                player.getPosition().toXZDoubleArray(), Grasscutter.getConfig().server.game.loadEntitiesForPlayerRange);
    }

    private boolean unloadBlockIfNotVisible(Collection<SceneBlock> visible, SceneBlock block) {
        if (visible.contains(block)) return false;
        this.onUnloadBlock(block);
        return true;
    }

    private synchronized boolean loadBlock(SceneBlock block) {
        if (this.loadedBlocks.contains(block)) return false;
        this.onLoadBlock(block, this.players);
        this.loadedBlocks.add(block);
        return true;
    }

    public synchronized void checkBlocks() {
        Set<SceneBlock> visible = this.players.stream()
            .map(player -> this.getPlayerActiveBlocks(player))
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());

        this.loadedBlocks.removeIf(block -> unloadBlockIfNotVisible(visible, block));
        visible.stream()
            .filter(block -> !this.loadBlock(block))
            .forEach(block -> {
                // dynamic load the groups for players in a loaded block
                var toLoad = this.players.stream()
                    .filter(p -> block.contains(p.getPosition()))
                    .map(p -> this.playerMeetGroups(p, block))
                    .flatMap(Collection::stream)
                    .toList();
                this.onLoadGroup(toLoad);
            });
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
    public void loadTriggerFromGroup(SceneGroup group, String triggerName) {
        //Load triggers and regions
        getScriptManager().registerTrigger(group.triggers.values().stream().filter(p -> p.name.contains(triggerName)).toList());
        group.regions.values().stream().filter(q -> q.config_id == Integer.parseInt(triggerName.substring(13))).map(region -> new EntityRegion(this, region))
            .forEach(getScriptManager()::registerRegion);
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

    public void selectWorktopOptionWith(SelectWorktopOptionReqOuterClass.SelectWorktopOptionReq req) {
        GameEntity entity = getEntityById(req.getGadgetEntityId());
        if (entity == null) {
            return;
        }
        // Handle
        if (entity instanceof EntityGadget gadget) {
            if (gadget.getContent() instanceof GadgetWorktop worktop) {
                boolean shouldDelete = worktop.onSelectWorktopOption(req);
                if (shouldDelete) {
                    entity.getScene().removeEntity(entity, VisionType.VISION_TYPE_REMOVE);
                }
            }
        }
    }
}
