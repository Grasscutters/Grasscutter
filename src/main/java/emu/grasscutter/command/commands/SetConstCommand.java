package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;

import static emu.grasscutter.utils.Language.translate;

@Command(
    label = "setConst",
    aliases = {"setconstellation"},
    usage = "<constellation level>",
    permission = "player.setconstellation")
public final class SetConstCommand implements CommandHandler {

}
