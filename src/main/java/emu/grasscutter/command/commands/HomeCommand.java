package emu.grasscutter.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import emu.grasscutter.command.BrigadierCommand;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.arguments.player.PlayerArgument;
import emu.grasscutter.command.source.CommandSource;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketHomeAllUnlockedBgmIdListNotify;
import emu.grasscutter.server.packet.send.PacketHomeNewUnlockedBgmIdListNotify;
import emu.grasscutter.utils.text.Text;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static emu.grasscutter.command.CommandManager.argument;
import static emu.grasscutter.command.CommandManager.literal;

@Command(
    label = "home",
    aliases = {"teapot"},
    usage = {
        "setlevel <lvl>",
        "unlock (module|bgm) (<id>|all)"
    },
    permission = "player.home",
    permissionTargeted = "player.home.others",
    brigadier = true
)
public class HomeCommand implements BrigadierCommand {
    private static final DynamicCommandExceptionType BGM_NOT_EXIST = new DynamicCommandExceptionType(o -> Text.translatable("commands.home.unlock.bgm_not_exist", o));

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        var home =
            dispatcher.register(
                literal("home")
                    .then(argument("target", PlayerArgument.onlinePlayer())
                        .then(literal("setlevel")
                            .then(argument("lvl", IntegerArgumentType.integer(1, 10))
                                .executes(context -> {
                                    return setLevel(context.getSource(), PlayerArgument.getPlayer(context, "target"), IntegerArgumentType.getInteger(context, "lvl"));
                                })))

                        .then(literal("unlock")
                            .then(literal("module")
                                .then(argument("id", IntegerArgumentType.integer(1, 5))
                                    .executes(context -> {
                                        return unlockModule(context.getSource(), PlayerArgument.getPlayer(context, "target"), IntegerArgumentType.getInteger(context, "id"));
                                    }))
                                .then(literal("all")
                                    .executes(context -> {
                                        return unlockAllModule(context.getSource(), PlayerArgument.getPlayer(context, "target"));
                                    })))

                            .then(literal("bgm")
                                .then(argument("id", IntegerArgumentType.integer())
                                    .executes(context -> {
                                        return unlockBgm(context.getSource(), PlayerArgument.getPlayer(context, "target"), IntegerArgumentType.getInteger(context, "id"));
                                    }))
                                .then(literal("all")
                                    .executes(context -> {
                                        return unlockAllBgm(context.getSource(), PlayerArgument.getPlayer(context, "target"));
                                    }))))));

        dispatcher.register(literal("teapot").redirect(home)); // alias: /teapot
    }

    private static int setLevel(CommandSource source, Player target, int level) {
        var home = target.getHome();
        home.setLevel(level);
        home.save();
        home.onOwnerLogin(home.getPlayer()); // Notify new level to client.
        source.sendMessage(Text.translatable("commands.home.setlevel", target.getNickname(), level));
        return level;
    }

    private static int unlockModule(CommandSource source, Player target, int id) {
        target.addRealmList(id);
        source.sendMessage(Text.translatable("commands.home.unlock.module", id));
        return id;
    }

    private static int unlockAllModule(CommandSource source, Player target) {
        var modules = IntStream.range(1, 6).boxed().collect(Collectors.toUnmodifiableSet()); // TODO retrieve all module ids from HomeWorldModuleConfigData
        modules.forEach(target::addRealmList);
        source.sendMessage(Text.translatable("commands.home.unlock.allmodule"));
        return modules.size();
    }

    private static int unlockBgm(CommandSource source, Player target, int id) throws CommandSyntaxException {
        if (!GameData.getHomeWorldBgmDataMap().containsKey(id)) {
            throw BGM_NOT_EXIST.create(id);
        }

        target.getHome().addUnlockedHomeBgm(id);
        source.sendMessage(Text.translatable("commands.home.unlock.bgm", id));
        return id;
    }

    private static int unlockAllBgm(CommandSource source, Player target) {
        var home = target.getHome();
        var player = home.getPlayer();
        var ids = GameData.getHomeWorldBgmDataMap().keySet();
        home.getUnlockedHomeBgmList().addAll(ids);
        home.save();
        player.sendPacket(new PacketHomeNewUnlockedBgmIdListNotify(ids));
        player.sendPacket(new PacketHomeAllUnlockedBgmIdListNotify(player));
        source.sendMessage(Text.translatable("commands.home.unlock.allbgm"));
        return ids.size();
    }
}
