package emu.grasscutter.command.commands;

import emu.grasscutter.command.*;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.achievement.AchievementData;
import emu.grasscutter.game.achievement.*;
import emu.grasscutter.game.player.Player;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Command(
        label = "achievement",
        usage = {
            "(grant|revoke) <achievementId>",
            "progress <achievementId> <progress>",
            "grantall",
            "revokeall"
        },
        aliases = {"am"},
        permission = "player.achievement",
        permissionTargeted = "player.achievement.others",
        targetRequirement = Command.TargetRequirement.PLAYER,
        threading = true)
public final class AchievementCommand implements CommandHandler {
    private static void sendSuccessMessage(Player sender, String cmd, Object... args) {
        CommandHandler.sendTranslatedMessage(
                sender, AchievementControlReturns.Return.SUCCESS.getKey() + cmd, args);
    }

    private static Optional<Integer> parseInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static void grantAll(Player sender, Player targetPlayer, Achievements achievements) {
        var counter = new AtomicInteger();
        GameData.getAchievementDataMap().values().stream()
                .filter(AchievementData::isUsed)
                .filter(AchievementData::isParent)
                .forEach(
                        data -> {
                            var success = achievements.grant(data.getId());
                            if (success.getRet() == AchievementControlReturns.Return.SUCCESS) {
                                counter.addAndGet(success.getChangedAchievementStatusNum());
                            }
                        });

        sendSuccessMessage(sender, "grantall", counter.intValue(), targetPlayer.getNickname());
    }

    private static void revokeAll(Player sender, Player targetPlayer, Achievements achievements) {
        var counter = new AtomicInteger();
        GameData.getAchievementDataMap().values().stream()
                .filter(AchievementData::isUsed)
                .filter(AchievementData::isParent)
                .forEach(
                        data -> {
                            var success = achievements.revoke(data.getId());
                            if (success.getRet() == AchievementControlReturns.Return.SUCCESS) {
                                counter.addAndGet(success.getChangedAchievementStatusNum());
                            }
                        });

        sendSuccessMessage(sender, "revokeall", counter.intValue(), targetPlayer.getNickname());
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            this.sendUsageMessage(sender);
            return;
        }

        var command = args.remove(0).toLowerCase();
        var achievements = Achievements.getByPlayer(targetPlayer);
        switch (command) {
            case "grant" -> this.grant(sender, targetPlayer, achievements, args);
            case "revoke" -> this.revoke(sender, targetPlayer, achievements, args);
            case "progress" -> this.progress(sender, targetPlayer, achievements, args);
            case "grantall" -> grantAll(sender, targetPlayer, achievements);
            case "revokeall" -> revokeAll(sender, targetPlayer, achievements);
            default -> this.sendUsageMessage(sender);
        }
    }

    private void grant(
            Player sender, Player targetPlayer, Achievements achievements, List<String> args) {
        if (args.size() < 1) {
            this.sendUsageMessage(sender);
        }

        parseInt(args.remove(0))
                .ifPresentOrElse(
                        integer -> {
                            var ret = achievements.grant(integer);
                            switch (ret.getRet()) {
                                case SUCCESS -> sendSuccessMessage(sender, "grant", targetPlayer.getNickname());
                                case ACHIEVEMENT_NOT_FOUND -> CommandHandler.sendTranslatedMessage(
                                        sender, ret.getRet().getKey());
                                case ALREADY_ACHIEVED -> CommandHandler.sendTranslatedMessage(
                                        sender, ret.getRet().getKey(), targetPlayer.getNickname());
                            }
                        },
                        () -> this.sendUsageMessage(sender));
    }

    private void revoke(
            Player sender, Player targetPlayer, Achievements achievements, List<String> args) {
        if (args.size() < 1) {
            this.sendUsageMessage(sender);
        }

        parseInt(args.remove(0))
                .ifPresentOrElse(
                        integer -> {
                            var ret = achievements.revoke(integer);
                            switch (ret.getRet()) {
                                case SUCCESS -> sendSuccessMessage(sender, "revoke", targetPlayer.getNickname());
                                case ACHIEVEMENT_NOT_FOUND -> CommandHandler.sendTranslatedMessage(
                                        sender, ret.getRet().getKey());
                                case NOT_YET_ACHIEVED -> CommandHandler.sendTranslatedMessage(
                                        sender, ret.getRet().getKey(), targetPlayer.getNickname());
                            }
                        },
                        () -> this.sendUsageMessage(sender));
    }

    private void progress(
            Player sender, Player targetPlayer, Achievements achievements, List<String> args) {
        if (args.size() < 2) {
            this.sendUsageMessage(sender);
        }

        parseInt(args.remove(0))
                .ifPresentOrElse(
                        integer -> {
                            parseInt(args.remove(0))
                                    .ifPresentOrElse(
                                            progress -> {
                                                var ret = achievements.progress(integer, progress);
                                                switch (ret.getRet()) {
                                                    case SUCCESS -> sendSuccessMessage(
                                                            sender, "progress", targetPlayer.getNickname(), integer, progress);
                                                    case ACHIEVEMENT_NOT_FOUND -> CommandHandler.sendTranslatedMessage(
                                                            sender, ret.getRet().getKey());
                                                }
                                            },
                                            () -> this.sendUsageMessage(sender));
                        },
                        () -> this.sendUsageMessage(sender));
    }
}
