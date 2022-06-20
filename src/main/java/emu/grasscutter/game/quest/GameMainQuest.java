package emu.grasscutter.game.quest;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import dev.morphia.annotations.Transient;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.data.excels.RewardData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.quest.enums.ParentQuestState;
import emu.grasscutter.net.proto.ChildQuestOuterClass.ChildQuest;
import emu.grasscutter.net.proto.ParentQuestOuterClass.ParentQuest;
import emu.grasscutter.server.packet.send.PacketCodexDataUpdateNotify;
import emu.grasscutter.server.packet.send.PacketFinishedParentQuestUpdateNotify;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Map;

@Entity(value = "quests", useDiscriminator = false)
public class GameMainQuest {
    @Id
    private ObjectId id;

    @Indexed
    private int ownerUid;
    @Transient
    private Player owner;

    private Map<Integer, GameQuest> childQuests;

    private int parentQuestId;
    private int[] questVars;
    private ParentQuestState state;
    private boolean isFinished;

    @Deprecated // Morphia only. Do not use.
    public GameMainQuest() {
    }

    public GameMainQuest(Player player, int parentQuestId) {
        this.owner = player;
        this.ownerUid = player.getUid();
        this.parentQuestId = parentQuestId;
        this.childQuests = new HashMap<>();
        this.questVars = new int[5];
        this.state = ParentQuestState.PARENT_QUEST_STATE_NONE;
    }

    public int getParentQuestId() {
        return this.parentQuestId;
    }

    public int getOwnerUid() {
        return this.ownerUid;
    }

    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player player) {
        if (player.getUid() != this.getOwnerUid()) return;
        this.owner = player;
    }

    public Map<Integer, GameQuest> getChildQuests() {
        return this.childQuests;
    }

    public GameQuest getChildQuestById(int id) {
        return this.getChildQuests().get(id);
    }

    public int[] getQuestVars() {
        return this.questVars;
    }

    public ParentQuestState getState() {
        return this.state;
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public void finish() {
        this.isFinished = true;
        this.state = ParentQuestState.PARENT_QUEST_STATE_FINISHED;

        this.getOwner().getSession().send(new PacketFinishedParentQuestUpdateNotify(this));
        this.getOwner().getSession().send(new PacketCodexDataUpdateNotify(this));

        this.save();

        // Add rewards
        MainQuestData mainQuestData = GameData.getMainQuestDataMap().get(this.getParentQuestId());
        for (int rewardId : mainQuestData.getRewardIdList()) {
            RewardData rewardData = GameData.getRewardDataMap().get(rewardId);

            if (rewardData == null) {
                continue;
            }

            this.getOwner().getInventory().addItemParamDatas(rewardData.getRewardItemList(), ActionReason.QuestReward);
        }
    }

    public void save() {
        DatabaseHelper.saveQuest(this);
    }

    public ParentQuest toProto() {
        ParentQuest.Builder proto = ParentQuest.newBuilder()
            .setParentQuestId(this.getParentQuestId())
            .setIsFinished(this.isFinished())
            .setParentQuestState(this.getState().getValue());

        for (GameQuest quest : this.getChildQuests().values()) {
            ChildQuest childQuest = ChildQuest.newBuilder()
                .setQuestId(quest.getQuestId())
                .setState(quest.getState().getValue())
                .build();

            proto.addChildQuestList(childQuest);
        }

        if (this.getQuestVars() != null) {
            for (int i : this.getQuestVars()) {
                proto.addQuestVar(i);
            }
        }

        return proto.build();
    }
}
