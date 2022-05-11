package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Utils;

import java.util.List;
import java.util.Locale;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "language", usage = "language [language code]", description = "commands.language.description", aliases = {"lang"})
public final class LanguageCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.isEmpty()) {
            String curLangCode = null;
            if (sender != null) {
                curLangCode = Utils.getLanguageCode(sender.getAccount().getLocale());
            }
            else {
                curLangCode = Grasscutter.getLanguage().getLanguageCode();
            }
            CommandHandler.sendMessage(sender, translate(sender, "commands.language.current_language", curLangCode));
            return;
        }

        String langCode = args.get(0);
        String actualLangCode = null;
        if (sender != null) {
            var locale = Locale.forLanguageTag(langCode);
            actualLangCode = Utils.getLanguageCode(locale);
            var account = sender.getAccount();
            account.setLocale(locale);
            account.save();
        }
        else {
            var languageInst = Grasscutter.getLanguage(langCode);
            actualLangCode = languageInst.getLanguageCode();
            Grasscutter.setLanguage(languageInst);
        }
        CommandHandler.sendMessage(sender, translate(sender, "commands.language.language_changed", actualLangCode));

    }
}
