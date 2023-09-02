package emu.grasscutter.command.commands;

import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Utils;
import java.util.*;

@Command(
        label = "language",
        usage = {"[<language code>]"},
        aliases = {"lang"},
        targetRequirement = Command.TargetRequirement.NONE)
public final class LanguageCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.isEmpty()) {
            String curLangCode = null;
            if (sender != null) {
                curLangCode = Utils.getLanguageCode(sender.getAccount().getLocale());
            } else {
                curLangCode = Grasscutter.getLanguage().getLanguageCode();
            }
            CommandHandler.sendMessage(
                    sender, translate(sender, "commands.language.current_language", curLangCode));
            return;
        }

        String langCode = args.get(0);

        var languageInst = Grasscutter.getLanguage(langCode);
        var actualLangCode = languageInst.getLanguageCode();
        var locale = Locale.forLanguageTag(actualLangCode);
        if (sender != null) {
            var account = sender.getAccount();
            account.setLocale(locale);
            account.save();
        } else {
            Grasscutter.setLanguage(languageInst);
            var config = Grasscutter.getConfig();
            config.language.language = locale;
            Grasscutter.saveConfig(config);
        }

        if (!langCode.equals(actualLangCode)) {
            CommandHandler.sendMessage(
                    sender, translate(sender, "commands.language.language_not_found", langCode));
        }

        CommandHandler.sendMessage(
                sender, translate(sender, "commands.language.language_changed", actualLangCode));
    }
}
