package emu.grasscutter.command.commands;

import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.command.*;
import emu.grasscutter.game.activity.trialavatar.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActivityType;
import emu.grasscutter.server.packet.send.PacketActivityInfoNotify;
import emu.grasscutter.utils.JsonUtils;
import java.util.List;

@Command(
        label = "trialAvatarActivity",
        aliases = {"taa"},
        usage = {
            "change <scheduleId>",
            "toggleDungeon <index(start from 1)|all>",
            "toggleReward <index(start from 1)|all>"
        },
        permission = "player.trialavataractivity",
        permissionTargeted = "player.trialavataractivity.others")
public final class TrialAvatarActivityCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 2) {
            sendUsageMessage(sender);
            return;
        }

        var action = args.get(0).toLowerCase();
        var param = args.get(1);

        var playerDataOption =
                targetPlayer
                        .getActivityManager()
                        .getPlayerActivityDataByActivityType(ActivityType.NEW_ACTIVITY_TRIAL_AVATAR);
        if (playerDataOption.isEmpty()) {
            CommandHandler.sendMessage(
                    sender, translate(sender, "commands.trialAvatarActivity.not_found"));
            return;
        }

        var playerData = playerDataOption.get();
        var handler = (TrialAvatarActivityHandler) playerData.getActivityHandler();
        if (handler == null) {
            CommandHandler.sendMessage(
                    sender, translate(sender, "commands.trialAvatarActivity.not_found"));
            return;
        }

        var trialAvatarPlayerData =
                JsonUtils.decode(playerData.getDetail(), TrialAvatarPlayerData.class);
        if (trialAvatarPlayerData == null) {
            CommandHandler.sendMessage(
                    sender, translate(sender, "commands.trialAvatarActivity.not_found"));
            return;
        }

        switch (action) {
            default -> this.sendUsageMessage(sender);
            case "change" -> {
                if (!param.chars().allMatch(Character::isDigit)) { // if its not number
                    CommandHandler.sendMessage(
                            sender, translate(sender, "commands.trialAvatarActivity.invalid_param"));
                    return;
                }
                if (TrialAvatarPlayerData.getAvatarIdList(Integer.parseInt(param)).isEmpty()) {
                    CommandHandler.sendMessage(
                            sender,
                            translate(
                                    sender,
                                    "commands.trialAvatarActivity.schedule_not_found",
                                    Integer.parseInt(param)));
                    return;
                }
                playerData.setDetail(TrialAvatarPlayerData.create(Integer.parseInt(param)));
                playerData.save();
                CommandHandler.sendMessage(
                        sender,
                        translate(
                                sender, "commands.trialAvatarActivity.success_schedule", Integer.parseInt(param)));
            }
            case "toggledungeon" -> {
                if (param.chars().allMatch(Character::isDigit)) { // if its number
                    if (Integer.parseInt(param) - 1 >= trialAvatarPlayerData.getRewardInfoList().size()
                            || Integer.parseInt(param) - 1 <= 0) {
                        CommandHandler.sendMessage(
                                sender, translate(sender, "commands.trialAvatarActivity.invalid_param"));
                        return;
                    }
                    TrialAvatarPlayerData.RewardInfoItem rewardInfo =
                            trialAvatarPlayerData.getRewardInfoList().get(Integer.parseInt(param) - 1);
                    rewardInfo.setPassedDungeon(!rewardInfo.isPassedDungeon());
                    playerData.setDetail(trialAvatarPlayerData);
                    playerData.save();
                    CommandHandler.sendMessage(
                            sender,
                            translate(
                                    sender, "commands.trialAvatarActivity.success_dungeon", Integer.parseInt(param)));
                } else {
                    if (!param.equals("all")) {
                        CommandHandler.sendMessage(
                                sender, translate(sender, "commands.trialAvatarActivity.invalid_param"));
                        return;
                    }
                    trialAvatarPlayerData
                            .getRewardInfoList()
                            .forEach(r -> r.setPassedDungeon(!r.isPassedDungeon()));
                    playerData.setDetail(trialAvatarPlayerData);
                    playerData.save();
                    CommandHandler.sendMessage(
                            sender, translate(sender, "commands.trialAvatarActivity.success_dungeon_all"));
                }
            }
            case "togglereward" -> {
                if (param.chars().allMatch(Character::isDigit)) { // if its number
                    if (Integer.parseInt(param) - 1 >= trialAvatarPlayerData.getRewardInfoList().size()
                            || Integer.parseInt(param) - 1 <= 0) {
                        CommandHandler.sendMessage(
                                sender, translate(sender, "commands.trialAvatarActivity.invalid_param"));
                        return;
                    }
                    TrialAvatarPlayerData.RewardInfoItem rewardInfo =
                            trialAvatarPlayerData.getRewardInfoList().get(Integer.parseInt(param) - 1);
                    rewardInfo.setReceivedReward(!rewardInfo.isReceivedReward());
                    playerData.setDetail(trialAvatarPlayerData);
                    playerData.save();
                    CommandHandler.sendMessage(
                            sender,
                            translate(
                                    sender, "commands.trialAvatarActivity.success_reward", Integer.parseInt(param)));
                } else {
                    if (!param.toLowerCase().equals("all")) {
                        CommandHandler.sendMessage(
                                sender, translate(sender, "commands.trialAvatarActivity.invalid_param"));
                        return;
                    }
                    trialAvatarPlayerData
                            .getRewardInfoList()
                            .forEach(r -> r.setReceivedReward(!r.isReceivedReward()));
                    playerData.setDetail(trialAvatarPlayerData);
                    playerData.save();
                    CommandHandler.sendMessage(
                            sender, translate(sender, "commands.trialAvatarActivity.success_reward_all"));
                }
            }
        }

        targetPlayer.sendPacket(
                new PacketActivityInfoNotify(
                        handler.toProto(playerData, targetPlayer.getActivityManager().getConditionExecutor())));
    }
}
