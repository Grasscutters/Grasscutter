package emu.grasscutter.game.managers.cooking;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.CompoundData;
import emu.grasscutter.game.player.BasePlayerManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.proto.CompoundQueueDataOuterClass.CompoundQueueData;
import emu.grasscutter.net.proto.GetCompoundDataReqOuterClass.GetCompoundDataReq;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.net.proto.PlayerCompoundMaterialReqOuterClass.PlayerCompoundMaterialReq;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.net.proto.TakeCompoundOutputReqOuterClass.TakeCompoundOutputReq;
import emu.grasscutter.server.packet.send.PackageTakeCompoundOutputRsp;
import emu.grasscutter.server.packet.send.PacketGetCompoundDataRsp;
import emu.grasscutter.server.packet.send.PacketPlayerCompoundMaterialRsp;
import emu.grasscutter.utils.Utils;

import java.util.*;

public class CookingCompoundManager extends BasePlayerManager {
    private static Set<Integer> defaultUnlockedCompounds;
    private static Map<Integer, Set<Integer>> compoundGroups;

    public CookingCompoundManager(Player player) {
        super(player);
    }

    public static void initialize() {
        defaultUnlockedCompounds = new HashSet<>();
        compoundGroups = new HashMap<>();
        for (var compound : GameData.getCompoundDataMap().values()) {
            if (compound.isDefaultUnlocked()) {
                defaultUnlockedCompounds.add(compound.getId());
            }
            if (!compoundGroups.containsKey(compound.getGroupId())) {
                compoundGroups.put(compound.getGroupId(), new HashSet<>());
            }
            compoundGroups.get(compound.getGroupId()).add(compound.getId());
        }
    }

    private synchronized List<CompoundQueueData> getCompoundQueueData() {
        List<CompoundQueueData> compoundQueueData = new ArrayList<>(player.getActiveCookCompounds().size());
        int currentTime = Utils.getCurrentSeconds();
        for (var item : player.getActiveCookCompounds().values()) {
            var data = CompoundQueueData.newBuilder().setCompoundId(item.getCompoundId()).setOutputCount(item.getOutputCount(currentTime)).setOutputTime(item.getOutputTime(currentTime)).setWaitCount(item.getWaitCount(currentTime)).build();
            compoundQueueData.add(data);
        }
        return compoundQueueData;
    }

    public synchronized void handleGetCompoundDataReq(GetCompoundDataReq req) {
        //TODO:Add the extra compound player unlocked,such as fish or meat.
        player.sendPacket(new PacketGetCompoundDataRsp(defaultUnlockedCompounds, getCompoundQueueData()));
    }

    public synchronized void handlePlayerCompoundMaterialReq(PlayerCompoundMaterialReq req) {
        int id = req.getCompoundId(), count = req.getCount();
        CompoundData compound = GameData.getCompoundDataMap().get(id);
        var activeCompounds = player.getActiveCookCompounds();

        //check whether the compound is available
        //TODO:add other compounds,see my pr for detail
        if (!defaultUnlockedCompounds.contains(id)) {
            player.sendPacket(new PacketPlayerCompoundMaterialRsp(Retcode.RET_FAIL_VALUE));
        }
        //check whether the queue is full
        if (activeCompounds.containsKey(id) && activeCompounds.get(id).getTotalCount() + count > compound.getQueueSize()) {
            player.sendPacket(new PacketPlayerCompoundMaterialRsp(Retcode.RET_COMPOUND_QUEUE_FULL_VALUE));
        }
        //try to consume raw materials
        if (!player.getInventory().payItems(compound.getInputVec())) {
            //TODO:I'm not sure whether retcode is correct.
            player.sendPacket(new PacketPlayerCompoundMaterialRsp(Retcode.RET_ITEM_COUNT_NOT_ENOUGH_VALUE));
        }
        ActiveCookCompoundData c;
        int currentTime = Utils.getCurrentSeconds();
        if (activeCompounds.containsKey(id)) {
            c = activeCompounds.get(id);
            c.addCompound(count,Utils.getCurrentSeconds());
        } else {
            c = new ActiveCookCompoundData(id, compound.getCostTime(), count, currentTime);
            activeCompounds.put(id, c);
        }
        var data = CompoundQueueData.newBuilder().setCompoundId(id).setOutputCount(c.getOutputCount(currentTime)).setOutputTime(c.getOutputTime(currentTime)).setWaitCount(c.getWaitCount(currentTime)).build();
        player.sendPacket(new PacketPlayerCompoundMaterialRsp(data));
    }

    public synchronized void handleTakeCompoundOutputReq(TakeCompoundOutputReq req) {
        //Client won't set compound_id and will set group_id instead.
        int groupId = req.getCompoundGroupId();
        var activeCompounds = player.getActiveCookCompounds();
        int now = Utils.getCurrentSeconds();
        //check available queues
        boolean success = false;
        for (int id : compoundGroups.get(groupId)) {
            if (activeCompounds.containsKey(id)) {
                int quantity = activeCompounds.get(id).takeCompound(now);
                if (activeCompounds.get(id).getTotalCount() == 0) activeCompounds.remove(id);
                if (quantity > 0) {
                    List<ItemParamData> rewards = GameData.getCompoundDataMap().get(id).getOutputVec();
                    if (rewards.get(rewards.size() - 1).getId() == 0) rewards.remove(rewards.size() - 1);
                    player.getInventory().addItems(rewards, quantity, ActionReason.Compound);
                    player.sendPacket(new PackageTakeCompoundOutputRsp(rewards.stream().map(i -> ItemParam.newBuilder().setItemId(i.getId()).setCount(i.getCount()*quantity).build()).toList(), Retcode.RET_SUCC_VALUE));
                    success = true;
                }
            }
        }
        //If all failed
        if (!success) player.sendPacket(new PackageTakeCompoundOutputRsp(null, Retcode.RET_COMPOUND_NOT_FINISH_VALUE));
    }
}
