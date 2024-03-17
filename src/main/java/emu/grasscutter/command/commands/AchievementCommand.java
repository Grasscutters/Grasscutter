package emu.grasscutter.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import emu.grasscutter.command.BrigadierCommand;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.arguments.player.PlayerArgument;
import emu.grasscutter.command.source.CommandSource;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.achievement.AchievementData;
import emu.grasscutter.game.achievement.AchievementControlReturns;
import emu.grasscutter.game.achievement.Achievements;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.text.Text;

import java.util.concurrent.atomic.AtomicInteger;

import static emu.grasscutter.command.CommandManager.argument;
import static emu.grasscutter.command.CommandManager.literal;
import static emu.grasscutter.game.achievement.AchievementControlReturns.Return.SUCCESS;

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
        brigadier = true)
public final class AchievementCommand implements BrigadierCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        var achievement =
            dispatcher.register(
                literal("achievement")
                    .then(argument("target", PlayerArgument.player())
                        .then(literal("grant")
                            .then(argument("id", IntegerArgumentType.integer())
                                .executes(context -> {
                                    return grant(context.getSource(), PlayerArgument.getPlayer(context, "target"), IntegerArgumentType.getInteger(context, "id"));
                                })))

                        .then(literal("revoke")
                            .then(argument("id", IntegerArgumentType.integer())
                                .executes(context -> {
                                    return revoke(context.getSource(), PlayerArgument.getPlayer(context, "target"), IntegerArgumentType.getInteger(context, "id"));
                                })))

                        .then(literal("progress")
                            .then(argument("id", IntegerArgumentType.integer())
                                .then(argument("progress", IntegerArgumentType.integer())
                                    .executes(context -> {
                                        return progress(context.getSource(), PlayerArgument.getPlayer(context, "target"), IntegerArgumentType.getInteger(context, "id"), IntegerArgumentType.getInteger(context, "progress"));
                                    }))))

                        .then(literal("grantall").executes(context -> {
                            return grantAll(context.getSource(), PlayerArgument.getPlayer(context, "target"));
                        }))

                        .then(literal("revokeall").executes(context -> {
                            return revokeAll(context.getSource(), PlayerArgument.getPlayer(context, "target"));
                        }))));

        dispatcher.register(literal("am").redirect(achievement)); // alias: /am
    }

    private static int grant(CommandSource source, Player target, int id) {
        var achievements = Achievements.getByPlayer(target);
        sendResult(source, achievements.grant(id).getRet(), "grant", target, target.getNickname());
        return id;
    }

    private static int revoke(CommandSource source, Player target, int id) {
        var achievement = Achievements.getByPlayer(target);
        sendResult(source, achievement.revoke(id).getRet(), "revoke", target, target.getNickname());
        return id;
    }

    private static int progress(CommandSource source, Player target, int id, int progress) {
        var achievements = Achievements.getByPlayer(target);
        sendResult(source, achievements.progress(id, progress).getRet(), "progress", target, target.getNickname(), id, progress);
        return id;
    }

    private static int grantAll(CommandSource source, Player target) {
        var achievements = Achievements.getByPlayer(target);
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

        sendResult(source, SUCCESS, "grantall", target, counter.intValue(), target.getNickname());
        return counter.intValue();
    }

    private static int revokeAll(CommandSource source, Player target) {
        var achievements = Achievements.getByPlayer(target);
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

        sendResult(source, SUCCESS, "revokeall", target, counter.intValue(), target.getNickname());
        return counter.intValue();
    }

    private static void sendResult(CommandSource source, AchievementControlReturns.Return ret, String cmdArg, Player target, Object... successArgs) {
        switch (ret) {
            case SUCCESS -> source.sendMessage(Text.translatable(SUCCESS.getKey() + cmdArg, successArgs));
            case ACHIEVEMENT_NOT_FOUND -> source.sendFailure(Text.translatable(ret.getKey()));
            case ALREADY_ACHIEVED,
                NOT_YET_ACHIEVED -> source.sendFailure(Text.translatable(ret.getKey(), target.getNickname()));
        }
    }
}
